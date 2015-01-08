package fr.prunetwork.sandbox.common.json;

import org.json.JSONObject;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/07/2014
 */
public class ReadJSONSimpleExample {

    public static void main(String[] args) {

    }

    private static void display(JSONObject jsonObject) {
        final StringBuilder sb = new StringBuilder();

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
        System.out.println(sb.toString());
    }
}
