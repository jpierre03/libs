package fr.prunetwork.json;

import org.json.JSONObject;

/**
 * @author Jean-Pierre PRUNARET
 * @since 07/01/15
 */
public class JsonPrinterTest {

    private JsonPrinterTest() {
    }

    public static void main(String... args) {
        String json = "{"
                + "\"type\":\"home\","
                + "\"number\":\"(800) 111-1111\","
                + " \"obj\": {"
                + "  " + "\"inside_key\": \"inside_value\""
                + "  " + "}"
                + "}";

        final JSONObject object = new JSONObject(json);

        JsonPrinter.printRecursive(object);
    }
}
