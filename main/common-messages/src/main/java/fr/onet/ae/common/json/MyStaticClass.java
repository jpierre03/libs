package fr.onet.ae.common.json;

import java.util.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/07/2014
 */
public class MyStaticClass {

    public static void print(Map m) {
        print(m, 0);
    }

    public static void printRecursive(Object o) {
        printRecursive(o, 0);
    }

    public static void print(List l) {
        print(l, 0);
    }

    private static void print(Map m, int level) {
        Iterator iterator = m.entrySet().iterator();
        print(iterator, level);
    }

    private static void print(List l, int level) {
        StringBuilder sb = getLevelBuilder(level);

        for (Object aL : l) {
            sb.append(aL).append("\n");
        }
        System.out.print(sb.toString());
    }

    private static void printRecursive(Object o, int level) {
        if (o == null) {
            return;
        }

        if (o instanceof java.util.LinkedList) {
            printRecursive((List) o, level++);
        }

        if (o instanceof java.util.HashMap) {
            printRecursive((Map) o, level++);
        }
    }

    private static void printRecursive(Map map, int level) {
        if (map == null) {
            return;
        }

        if (map.size() > 0) {
            print(map, level);

            for (Object o : map.values()) {
                if ((o instanceof HashMap) || (o instanceof LinkedList)) {
                    printRecursive(o, level++);
                }
            }
        }
    }

    private static void printRecursive(List list, int level) {
        if (list == null) {
            return;
        }

        if (list.size() > 0) {
            print(list, level);

            for (Object o : list) {
                if ((o instanceof HashMap) || (o instanceof LinkedList)) {
                    printRecursive(o, level++);
                }
            }
        }
    }

    private static void print(Iterator iterator, int level) {
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            StringBuilder sb = getLevelBuilder(level);

            sb.append(entry.getKey()).append(" => ").append(entry.getValue());
            System.out.println(sb);
        }
    }

    private static StringBuilder getLevelBuilder(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb;
    }
}
