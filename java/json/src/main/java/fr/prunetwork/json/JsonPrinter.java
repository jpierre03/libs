package fr.prunetwork.json;

import org.json.JSONObject;

import java.util.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/07/2014
 */
public class JsonPrinter {

    public static void print(Map m) {
        print(m, 0);
    }

    public static void printRecursive(Object o) {
        assert o != null : "null parameter";

        printRecursive(o, 0);
    }

    public static void print(List l) {
        assert l != null : "null parameter";

        print(l, 0);
    }

    private static void print(Map m, int level) {
        assert m != null : "null parameter";
        assert level >= 0 : "level should be positive or null";


        Iterator iterator = m.entrySet().iterator();
        print(iterator, level);
    }

    private static void print(List l, int level) {
        assert l != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        StringBuilder sb = getLevelBuilder(level);

        for (Object aL : l) {
            sb.append(aL).append("\n");
        }
        System.out.print(sb.toString());
    }

    private static void printRecursive(Object o, int level) {
        assert o != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        if (o == null) {
            return;

        } else if (o instanceof java.util.List) {
            printRecursive((List) o, level++);

        } else if (o instanceof java.util.Map) {
            printRecursive((Map) o, level++);

        } else if (o instanceof JSONObject) {
            printRecursive((JSONObject) o, level++);

        } else {
            throw new IllegalStateException("case is missing or invalid input");
        }
    }

    private static void printRecursive(Map map, int level) {
        assert map != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        if (map == null) {
            return;

        } else if (map.size() > 0) {
            print(map, level);

            for (Object o : map.values()) {
                if ((o instanceof HashMap) || (o instanceof LinkedList)) {
                    printRecursive(o, level++);
                }
            }

        } else {
            throw new IllegalStateException("map is empty");
        }
    }

    private static void printRecursive(List list, int level) {
        assert list != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        if (list == null) {
            return;

        } else if (list.size() > 0) {
            print(list, level);

            for (Object o : list) {
                if ((o instanceof HashMap) || (o instanceof LinkedList)) {
                    printRecursive(o, level++);
                }
            }

        } else {
            throw new IllegalStateException("list is empty");
        }
    }

    private static void printRecursive(JSONObject o, int level) {
        assert o != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        if (o == null) {
            return;

        } else {

            for (String key : o.keySet()) {
                StringBuilder sb = getLevelBuilder(level);

                sb.append(key).append(" => ").append(o.get(key));
                System.out.println(sb);
            }
        }
    }

    private static void print(Iterator iterator, int level) {
        assert iterator != null : "null parameter";
        assert level >= 0 : "level should be positive or null";

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            StringBuilder sb = getLevelBuilder(level);

            sb.append(entry.getKey()).append(" => ").append(entry.getValue());
            System.out.println(sb);
        }
    }

    private static StringBuilder getLevelBuilder(int level) {
        assert level >= 0 : "level should be positive or null";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb;
    }
}
