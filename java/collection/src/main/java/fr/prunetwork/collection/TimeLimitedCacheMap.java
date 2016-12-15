package fr.prunetwork.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread safe object map, which clears objects after a specified time. The objects are stored in the
 * underlying hashMap.
 *
 * @author rongkan
 * @author Jean-Pierre PRUNARET
 */
public final class TimeLimitedCacheMap<K, V> implements Iterable<K>, Serializable {

    @NotNull
    private final Map<K, V> objectMap = new HashMap<>(1 << 7);
    @NotNull
    private final Map<K, Long> timeMap = new HashMap<>(1 << 7);
    @NotNull
    private final Map<Long, HistoryEntry<V>> history = new ConcurrentHashMap<>();
    /**
     * I need a shared lock, readwrite lock is an excellent candidate.
     * eviction is run with writeLock, put/remove with readLock
     */
    @NotNull
    private final ReentrantReadWriteLock accessLock = new ReentrantReadWriteLock();
    @NotNull
    private final Runnable evictor = new Runnable() {

        /* evictor thread removes data, and changes map state. This is
         * in conflict with put() and remove(). So we need sync for these operations
         *
         * In case you are wondering why evictor needs sync (it just calls remove() right?)
         * eviction is a compound action that spans more than a single remove(). It enters
         * into a conflict with put()
         *
         * evictor: start looping ------------------------------> keyset is stale, evictor removes the recent put & armagedon comes
         * Thrd1:--------------------->put(same key, new object)
         *
         */
        @Override
        public void run() {
            // avoid runs on empty maps
            if (timeMap.isEmpty()) {
                Thread.yield();
            }
            long currentTime = System.nanoTime();
            accessLock.writeLock().lock();
            @NotNull Set<K> keys = new HashSet<>(timeMap.keySet());
            accessLock.writeLock().unlock();
            /* First attempt to detect & mark stale entries, but don't delete
             * The hash map may contain 1000s of objects don't block it. The returned
             * Set returned may be stale, implying:
             * 1. contains keys for objects which are removed by user, using remove() (not a problem)
             * 2. contains keys for objects which are updated by user, using put() [a big problem]
             */
            @NotNull Set<K> markedForRemoval = new HashSet<>();
            for (K key : keys) {
                long lastTime = timeMap.get(key);
                if (lastTime == 0) {
                    continue;
                }
                long interval = currentTime - lastTime;
                long elapsedTime = expiryTimeUnit.convert(interval, TimeUnit.NANOSECONDS);
                if (elapsedTime - expiryTime >= 0) {
                    markedForRemoval.add(key);
                }
            }

            if (isDebug) {
                accessLock.readLock().lock();
                System.err.println("# to evict: " + markedForRemoval.size() + " #stored elements: " + objectMap.size() + " #history size: " + history.size());
                accessLock.readLock().unlock();
            }

            /* Actual removal call, which runs on the objects marked earlier.
             * Assumption: marked objects.size() < hashmap.size()
             * Do not delete blindly, check for staleness before calling remove
             */
            accessLock.writeLock().lock();
            for (K key : markedForRemoval) {
                long lastTime = timeMap.get(key);
                if (lastTime == 0) {
                    continue;
                }
                long interval = currentTime - lastTime;
                long elapsedTime = TimeUnit.NANOSECONDS.convert(interval, expiryTimeUnit);
                if (elapsedTime > expiryTime) {
                    remove(key);
                }
            }
            accessLock.writeLock().unlock();
        }
    };
    @NotNull
    private final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor(new MyThreadFactory(true));
    private final long expiryTime;
    @NotNull
    private final TimeUnit expiryTimeUnit;
    public boolean isDebug = false;

    /**
     * Users can play around with evictionDelay and expiryTime.
     * 1. Large evictionDelay => less frequent checks, hence chances of finding expired Objects are more
     * 2. Lean evictionDelay => aggressive checks, and hence more sync overhead with put() and remove()
     * 3. Large expiryTime => increases the time object stays in object map and less chance of cache miss (cache miss is bad)
     * 4. Lean expiryTime => by itself does not force object to be removed aggressively, needs lean eviction to be configured
     * <p>
     * In case you are wondering, above picture is not complete.
     * Another key aspect is "Arrival Periodicty", or the rate at which put() is called.
     * <p>
     * Ideally: expiryTime == arrival periodicity + 1 [read '+1' as slightly greater]
     * evictionDelay == expiryTime + 1
     * <p>
     * For random arrival times, which is a more common scenario, use the following pointers
     * 1. eviction Delay > expiry Time
     * Here user needs to think of the impact of stale hit (define how stale is stale!)
     * 2. eviction Delay < arrival time
     * This has higher chances of cache miss and accidental treatment as failure     *
     * 3. eviction Delay < expiry Time
     * Unwanted eviction run(s) resulting in sync overhead on map
     * 4. eviction Delay > arrival Time
     * Unwanted eviction run(s) resulting in sync overhead on map
     *
     * @param initialDelay,  time after which scheduler starts
     * @param evictionDelay, periodicity with which eviction is carried out
     * @param expiryTime,    age of the object, exceeding which the object is  to be removed
     * @param unit
     */
    public TimeLimitedCacheMap(long initialDelay, long evictionDelay, long expiryTime, @NotNull TimeUnit unit) {
        if (initialDelay < 0) {
            throw new IllegalArgumentException("initialDelay should be positive");
        }
        if (evictionDelay <= 0) {
            throw new IllegalArgumentException("evictionDelay should be strictly positive");
        }

        if (expiryTime <= 0) {
            throw new IllegalArgumentException("expiryTime should be strictly positive");
        }

        timer.scheduleWithFixedDelay(evictor, initialDelay, evictionDelay, unit);
        this.expiryTime = expiryTime;
        this.expiryTimeUnit = unit;
    }

    public void clear() {
        objectMap.clear();
        timeMap.clear();
        history.clear();
    }

    /* The intention is to prevent user from modifying the object Map,
     * I want all adds/removals to be suspended. synchronizing on objectMap
     * would also work, but locks are easier to read without scope issues of {}
     *
     * Concurrent hashMap would have allowed put and remove to happen concurrently.
     * I did not use conc-map, because
     * 1. I want to update another map (timeMap)
     * 2. I want to sync the operation with evictor thread
     *
     * The unfortunate invariants:
     *     1. sync between evictor and put()
     *  2. sync between evictor and remove()
     *  imply
     *  3. sync lock between put() and remove()
     *
     *  3. is unfortunate side effect, as you need to sync all exposed invariants on the same lock.
     *  Lock duplication won't help. If I create putLock() and removeLock(), they will allow put() and remove()
     *  to happen concurrently, but will not help two put()/remove() calls to happen in parallel.
     */
    @Nullable
    public V put(K key, @NotNull V value) {
        accessLock.readLock().lock();
        @NotNull Long nanoTime = System.nanoTime();
        timeMap.put(key, nanoTime);

        if (!objectMap.containsKey(key)) {
            final long time = System.currentTimeMillis();
            history.put(time, new HistoryEntry<>(time, value, Status.ADDED));
            if (isDebug) {
                System.err.println("# ADDED: " + history.get(time));
            }
        }

        @Nullable final V v = objectMap.put(key, value);

        accessLock.readLock().unlock();
        return v;
    }

    /* Read comments for put() they apply here as well.
     * If had not allowed remove(), life would have been zillion times simpler.
     * However, an undoable action is quite bad.
     */
    @Nullable
    public V remove(@NotNull final Object key) {
        accessLock.readLock().lock();
        //accessLock.lock();
        @Nullable V value = objectMap.remove(key);
        timeMap.remove(key);

        @NotNull final Long time = System.currentTimeMillis();
        history.put(time, new HistoryEntry<>(time, value, Status.REMOVED));

        //accessLock.unlock();
        accessLock.readLock().unlock();
        return value;
    }

    /* Clone requires locking, to prevent the edge case where
     * the map is updated with clone is in progress
     */
    @NotNull
    public Map<K, V> getClonedMap() {
        accessLock.writeLock().lock();
        @NotNull HashMap<K, V> mapClone = new HashMap<>(objectMap);
        accessLock.writeLock().unlock();
        return Collections.unmodifiableMap(mapClone);
    }

    @NotNull
    @Override
    public Iterator<K> iterator() {
        return getClonedMap().keySet().iterator();
    }

    public void close() {
        timer.shutdown();
    }

    @NotNull
    public Collection<HistoryEntry<V>> getHistoryAndClear() {
        accessLock.writeLock().lock();

        @NotNull final Collection<HistoryEntry<V>> entries = Collections.unmodifiableCollection(new ArrayList<>(history.values()));
        history.clear();

        accessLock.writeLock().unlock();
        return entries;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    @Nullable
    public TimeUnit getExpiryTimeUnit() {
        return expiryTimeUnit;
    }

    public enum Status {ADDED, REMOVED}

    private final class MyThreadFactory implements ThreadFactory {

        private boolean isDaemon = false;

        public MyThreadFactory(boolean daemon) {
            isDaemon = daemon;
        }

        @NotNull
        @Override
        public Thread newThread(Runnable r) {
            @NotNull Thread t = new Thread(r);
            t.setDaemon(isDaemon);
            return t;
        }
    }

    public final class HistoryEntry<V> {
        @NotNull
        private final Long timestamp;
        @NotNull
        private final V v;
        @NotNull
        private final Status status;

        private HistoryEntry(@NotNull Long timestamp, @NotNull V v, @NotNull Status status) {
            this.timestamp = timestamp;
            this.v = v;
            this.status = status;
        }

        @NotNull
        public Long getTimestamp() {
            return timestamp;
        }

        @NotNull
        public V getV() {
            return v;
        }

        @NotNull
        public Status getStatus() {
            return status;
        }

        @NotNull
        @Override
        public String toString() {
            return "HistoryEntry{" +
                    "timestamp=" + getTimestamp() +
                    ", v=" + getV() +
                    ", status=" + getStatus() +
                    '}';
        }
    }
}
