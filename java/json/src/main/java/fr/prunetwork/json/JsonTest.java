package fr.prunetwork.json;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * http://www.infoq.com/fr/news/2013/05/standard-java-api-for-json
 * http://www.oracle.com/technetwork/articles/java/json-1973242.html
 *
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class JsonTest {

    public static void main(String... args) {

        parser();

        System.out.println("------");

        generator();
    }

    private static void parser() {

        @NotNull String json = "{\"type\":\"home\",\"number\":\"(800) 111-1111\"}";

        @NotNull final JSONObject object = new JSONObject(json);
        for (String key : object.keySet()) {
            System.out.printf("%s := %s%n", key, object.get(key));
        }
    }

    private static void generator() {

        final String s = new JSONStringer()
                .array()
                .object()
                .key("type").value("home")
                .key("number").value("(800) 111-1111")
                .endObject()
                .object()
                .key("type").value("cell")
                .key("number").value("(800) 222-2222")
                .endObject()
                .endArray()
                .toString();

        System.out.println(s);
    }
}
