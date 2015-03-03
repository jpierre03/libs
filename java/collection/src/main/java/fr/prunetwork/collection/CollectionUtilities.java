package fr.prunetwork.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/05/2014
 */
public final class CollectionUtilities {

    private CollectionUtilities() {
    }

    /**
     * Remove Duplicates items.
     */
    @NotNull
    public static <T extends Comparable<?>> Collection<T> withoutDuplicates(@NotNull final Collection<T> objects) {
        @NotNull final Map<T, T> map = new TreeMap<>();
        for (T o : objects) {
            map.put(o, o);
        }

        return map.values();
    }

    /**
     * Extract elements present in each (both) collections.
     *
     * @param a   first collection
     * @param b   second collection
     * @param <T> a generic type
     * @return a collection that contains only elements in both lists
     */
    @Nullable
    public static <T extends Comparable<?>> Collection<T> inBothLists(@NotNull Collection<T> a,
                                                                      @NotNull Collection<T> b) {

        @NotNull final Collection<T> side1 = new ArrayList<>(a);
        @NotNull final Collection<T> side2 = new ArrayList<>(b);

        /** retainAll: permet de retirer de list tout ce qui n'est pas contenu dans newB */
        side1.retainAll(side2);

        return new ArrayList<>(side1);
    }
}
