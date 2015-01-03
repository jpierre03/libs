package fr.prunetwork.sandbox.common.json;


import fr.prunetwork.sandbox.MyStaticClass;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/07/2014
 */
public class ReadJSONSimpleExample {

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("test.json"));

            JSONObject jsonObject = (JSONObject) obj;

            MyStaticClass.printRecursive(jsonObject);

            /*
            //display(jsonObject);

            final Map<SimpleJsonField, Object> map = new TreeMap<>();
            jsonObject.forEach(new BiConsumer<String, Object>() {
                @Override
                public void accept(String o, Object o2) {
                    map.put(SimpleJsonField.valueOf(o), o2);
                }

                @Override
                public BiConsumer andThen(BiConsumer after) {
                    return null;
                }
            });



            final StringBuilder sb = new StringBuilder();

            for (Map.Entry<SimpleJsonField, Object> entry : map.entrySet()) {
                sb.append("key: ").append(entry.getKey());
                sb.append(" v: ").append(entry.getValue());
                sb.append("\n");
            }
            System.out.println(sb.toString());


            String name = (String) jsonObject.get(SimpleJsonField.name);
            System.out.println(name);

            Integer age = (Integer) jsonObject.get(SimpleJsonField.age);
            System.out.println(age);

            // loop array
            final Object messages = jsonObject.get(SimpleJsonField.messages);
            JSONArray fields = (JSONArray) messages;
            for (Object field : fields) {
                System.out.println(field.toString());
            }
*/



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void display(JSONObject jsonObject) {
        final StringBuilder sb = new StringBuilder();

        jsonObject.forEach(new BiConsumer() {
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
        });
        System.out.println(sb.toString());
    }
}
