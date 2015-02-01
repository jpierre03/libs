package fr.prunetwork.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * TODO : migrer vers java 8
 *
 * @author Jean-Pierre PRUNARET
 * @since 09/07/2014
 */
public class JsonPrinter {

     /*jsonObject.forEach(new BiConsumer() {
            @Override
            public void accept(Object o, Object o2) {
                sb.append("key: ").append(o);
                sb.append(" v: ").append(o2);
                sb.append("\n");
            }

            @Override
            public BiConsumer andThen(BiConsumer after) {
                return null;
            }
        });*/

    public static void print(@NotNull final Map m) {
        print(m, 0);
    }

    public static void print(@NotNull final List l) {
        print(l, 0);
    }

    public static void printRecursive(@NotNull final Object o) {
        printRecursive(o, 0);
    }

    private static void print(@NotNull final Map m, final int level) {
        assert level >= 0 : "level should be positive or null";

        @NotNull Iterator iterator = m.entrySet().iterator();
        print(iterator, level);
    }

    private static void print(@NotNull final List l, final int level) {
        assert level >= 0 : "level should be positive or null";

        @NotNull StringBuilder sb = getLevelBuilder(level);

        for (Object aL : l) {
            sb.append(aL).append("\n");
        }
        System.out.print(sb.toString());
    }

    private static void print(@NotNull final Iterator iterator, final int level) {
        assert level >= 0 : "level should be positive or null";

        while (iterator.hasNext()) {
            @NotNull Map.Entry entry = (Map.Entry) iterator.next();

            @NotNull StringBuilder sb = getLevelBuilder(level);

            sb.append(entry.getKey()).append(" => ").append(entry.getValue());
            System.out.println(sb);
        }
    }

    private static void print(@NotNull final String s, final int level) {
        assert level >= 0 : "level should be positive or null";

        @NotNull StringBuilder sb = getLevelBuilder(level);

        sb.append(s).append("\n");
        System.out.print(sb.toString());
    }

    private static void printRecursive(@Nullable final Object o, final int level) {
        assert level >= 0 : "level should be positive or null";

        if (o == null) {
            return;

        } else if (o instanceof java.util.List) {
            printRecursive((List) o, level + 1);

        } else if (o instanceof java.util.Map) {
            printRecursive((Map) o, level + 1);

        } else if (o instanceof JSONObject) {
            printRecursive((JSONObject) o, level + 1);

        } else if (o instanceof JSONArray) {
            printRecursive((JSONArray) o, level + 1);

        } else if (o instanceof String) {
            print((String) o, level + 1);

        } else {
            throw new IllegalStateException("case is missing or invalid input");
        }
    }

    private static void printRecursive(@Nullable final Map map, final int level) {
        assert level >= 0 : "level should be positive or null";

        if (map == null) {
            return;

        } else if (map.size() > 0) {
            print(map, level);

            map.values()
                    .stream()
                    .filter(o -> (o instanceof HashMap) || (o instanceof LinkedList))
                    .forEach(o -> printRecursive(o, level + 1));

        } else {
            throw new IllegalStateException("map is empty");
        }
    }

    private static void printRecursive(@Nullable final List list, final int level) {
        assert level >= 0 : "level should be positive or null";

        if (list == null) {
            return;

        } else if (list.size() > 0) {
            print(list, level);

            list.stream()
                    .filter(o -> (o instanceof HashMap) || (o instanceof LinkedList))
                    .forEach(o -> printRecursive(o, level + 1));

        } else {
            throw new IllegalStateException("list is empty");
        }
    }

    private static void printRecursive(@Nullable final JSONArray a, final int level) {
        assert level >= 0 : "level should be positive or null";

        if (a == null) {
            return;

        } else {
            print("[", level);

            for (int i = 0; i < a.length(); i++) {
                final Object object = a.get(i);
                printRecursive(object, level + 1);
            }

            print("]", level);

        }
    }

    private static void printRecursive(@Nullable final JSONObject o, final int level) {
        assert level >= 0 : "level should be positive or null";

        if (o == null) {
            return;

        } else {
            print("{", level);

            for (String key : o.keySet()) {
                @NotNull StringBuilder sb = getLevelBuilder(level);

                final Object value = o.get(key);

                sb.append(key).append(" => ");
                System.out.print(sb);

                sb.setLength(0);

                if (value instanceof JSONObject) {
                    System.out.println(sb);

                    printRecursive(value, level + 1);
                } else if (value instanceof JSONArray) {
                    System.out.println(sb);

                    printRecursive(value, level + 1);
                } else {
                    sb.append(value);
                    System.out.println(sb);
                }
            }

            print("}", level);
        }
    }

    @NotNull
    private static StringBuilder getLevelBuilder(final int level) {
        assert level >= 0 : "level should be positive or null";

        @NotNull StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb;
    }
}
