package fr.prunetwork.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/07/2014
 */
public class JsonPrinter {

    public static void print(final Map m) {
        print(m, 0);
    }

    public static void printRecursive(final Object o) {
        assert o != null : "null parameter";

        printRecursive(o, 0);
    }

    public static void print(final List l) {
        assert l != null : "null parameter";

        print(l, 0);
    }

    private static void print(final Map m, final int level) {
        assert m != null : "null parameter";
        assert level >= 0 : "level should be positive or null";


        Iterator iterator = m.entrySet().iterator();
        print(iterator, level);
    }

    private static void print(final List l, final int level) {
        assert l != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        StringBuilder sb = getLevelBuilder(level);

        for (Object aL : l) {
            sb.append(aL).append("\n");
        }
        System.out.print(sb.toString());
    }

    private static void print(final String s, final int level) {
        assert s != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        StringBuilder sb = getLevelBuilder(level);

        sb.append(s).append("\n");
        System.out.print(sb.toString());
    }

    private static void printRecursive(final Object o, final int level) {
        assert o != null : "null parameter";
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

    private static void printRecursive(final Map map, final int level) {
        assert map != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        if (map == null) {
            return;

        } else if (map.size() > 0) {
            print(map, level);

            for (Object o : map.values()) {
                if ((o instanceof HashMap) || (o instanceof LinkedList)) {
                    printRecursive(o, level + 1);
                }
            }

        } else {
            throw new IllegalStateException("map is empty");
        }
    }

    private static void printRecursive(final List list, final int level) {
        assert list != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        if (list == null) {
            return;

        } else if (list.size() > 0) {
            print(list, level);

            for (Object o : list) {
                if ((o instanceof HashMap) || (o instanceof LinkedList)) {
                    printRecursive(o, level + 1);
                }
            }

        } else {
            throw new IllegalStateException("list is empty");
        }
    }

    private static void printRecursive(final JSONArray a, final int level) {
        assert a != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        if (a == null) {
            return;

        } else {

            System.out.println(getLevelBuilder(level) + "[");

            for (int i = 0; i < a.length(); i++) {
                final Object object = a.get(i);
                printRecursive(object, level + 1);
            }

            System.out.println(getLevelBuilder(level) + "]");
        }
    }

    private static void printRecursive(final JSONObject o, final int level) {
        assert o != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        if (o == null) {
            return;

        } else {
            System.out.println(getLevelBuilder(level) + "{");

            for (String key : o.keySet()) {
                StringBuilder sb = getLevelBuilder(level);

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

            System.out.println(getLevelBuilder(level) + "}");
        }
    }

    private static void print(final Iterator iterator, final int level) {
        assert iterator != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            StringBuilder sb = getLevelBuilder(level);

            sb.append(entry.getKey()).append(" => ").append(entry.getValue());
            System.out.println(sb);
        }
    }

    private static StringBuilder getLevelBuilder(final int level) {
        assert level >= 0 : "level should be positive or null";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb;
    }
}
