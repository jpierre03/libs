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

    @NotNull
    public static String print(@NotNull final Map m) {
        return print(m, 0);
    }

    @NotNull
    public static String print(@NotNull final List l) {
        return print(l, 0);
    }

    public static String printRecursive(@NotNull final Object o) {
        return printRecursive(o, 0);
    }

    @NotNull
    private static String print(@NotNull final Map m, final int level) {
        assert level >= 0 : "level should be positive or null";

        @NotNull Iterator iterator = m.entrySet().iterator();
        return print(iterator, level);
    }

    @NotNull
    private static String print(@NotNull final List l, final int level) {
        assert level >= 0 : "level should be positive or null";

        @NotNull StringBuilder sb = getLevelBuilder(level);

        for (Object aL : l) {
            sb.append(aL).append("\n");
        }
        System.out.print(sb.toString());

        return sb.toString();
    }

    @NotNull
    private static String print(@NotNull final Iterator iterator, final int level) {
        assert level >= 0 : "level should be positive or null";
        final StringBuilder stringBuilder = new StringBuilder();

        while (iterator.hasNext()) {
            @NotNull Map.Entry entry = (Map.Entry) iterator.next();

            @NotNull StringBuilder sb = getLevelBuilder(level);

            sb.append(entry.getKey()).append(" => ").append(entry.getValue());
            System.out.println(sb);
            stringBuilder.append(sb);
        }

        return stringBuilder.toString();
    }

    @NotNull
    private static String print(@NotNull final String s, final int level) {
        assert level >= 0 : "level should be positive or null";

        @NotNull StringBuilder sb = getLevelBuilder(level);

        sb.append(s).append("\n");
        System.out.print(sb.toString());

        return sb.toString();
    }

    @NotNull
    private static String printRecursive(@Nullable final Object o, final int level) {
        assert level >= 0 : "level should be positive or null";
        final String result;

        if (o == null) {
            result = "";

        } else if (o instanceof java.util.List) {
            result = printRecursive((List) o, level + 1);

        } else if (o instanceof java.util.Map) {
            result = printRecursive((Map) o, level + 1);

        } else if (o instanceof JSONObject) {
            result = printRecursive((JSONObject) o, level + 1);

        } else if (o instanceof JSONArray) {
            result = printRecursive((JSONArray) o, level + 1);

        } else if (o instanceof String) {
            result = print((String) o, level + 1);
        } else {
            result = print(o.toString(), level + 1);
            //throw new IllegalStateException("case is missing or invalid input: " + o.getClass().getCanonicalName());
        }

        return result;
    }

    @NotNull
    private static String printRecursive(@Nullable final Map map, final int level) {
        assert level >= 0 : "level should be positive or null";
        final String result;

        if (map == null) {
            result = "";

        } else if (map.size() > 0) {
            StringBuilder sb = new StringBuilder();

            sb.append(print(map, level));

            map.values()
                    .stream()
                    .filter(o -> (o instanceof HashMap) || (o instanceof LinkedList))
                    .forEach(o -> sb.append(printRecursive(o, level + 1)));

            result = sb.toString();
        } else {
            throw new IllegalStateException("map is empty");
        }
        return result;
    }

    @NotNull
    private static String printRecursive(@Nullable final List list, final int level) {
        assert level >= 0 : "level should be positive or null";
        final String result;


        if (list == null) {
            result = "";

        } else if (list.size() > 0) {
            StringBuilder sb = new StringBuilder();

            sb.append(print(list, level));

            list.stream()
                    .filter(o -> (o instanceof HashMap) || (o instanceof LinkedList))
                    .forEach(o -> sb.append(printRecursive(o, level + 1)));

            result = sb.toString();
        } else {
            throw new IllegalStateException("list is empty");
        }
        return result;
    }

    @NotNull
    private static String printRecursive(@Nullable final JSONArray a, final int level) {
        assert level >= 0 : "level should be positive or null";
        final StringBuilder builder = new StringBuilder();

        if (a == null) {
            builder.append("");

        } else {
            builder.append(
                    print("[", level)
            );

            for (int i = 0; i < a.length(); i++) {
                final Object object = a.get(i);
                builder.append(
                        printRecursive(object, level + 1)
                );
            }

            builder.append(
                    print("]", level)
            );
        }
        return builder.toString();
    }

    @NotNull
    private static String printRecursive(@Nullable final JSONObject o, final int level) {
        assert level >= 0 : "level should be positive or null";
        final StringBuilder builder = new StringBuilder();

        if (o == null) {
            builder.append(
                    ""
            );

        } else {
            builder.append(
                    print("{", level)
            );

            for (String key : o.keySet()) {
                @NotNull StringBuilder sb = getLevelBuilder(level);

                final Object value = o.get(key);

                sb.append(key).append(" => ");
                System.out.print(sb);
                builder.append(
                        sb
                );

                sb.setLength(0);

                if (value instanceof JSONObject) {
                    System.out.println(sb);

                    builder.append(
                            printRecursive((JSONObject) value, level + 1)
                    );
                } else if (value instanceof JSONArray) {
                    System.out.println(sb);

                    builder.append(
                            printRecursive((JSONArray) value, level + 1)
                    );
                } else {
                    sb.append(value);
                    System.out.println(sb);

                    builder.append(
                            value
                    );
                }
            }

            builder.append(
                    print("}", level)
            );
        }
        return builder.toString();
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
