package json;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.StringReader;

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

        String json =
                "[\n" +
                        "  {\n" +
                        "    \"type\" : \"home\",\n" +
                        "    \"number\" : \"(800) 111-1111\"\n" +
                        "  },\n" +
                        "    {\n" +
                        "    \"type\" : \"cell\",\n" +
                        "    \"number\" : \"(800) 222-2222\"\n" +
                        "    }\n" +
                        "]";

        JsonParserFactory factory = Json.createParserFactory(null);
        JsonParser parser = factory.createParser(new StringReader(json));

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();

            switch (event) {
                case KEY_NAME: {
                    System.out.print(parser.getString() + "=");
                    break;
                }
                case VALUE_STRING: {
                    System.out.println(parser.getString());
                    break;
                }
            }
        }
    }

    private static void generator() {
        JsonGeneratorFactory factory = Json.createGeneratorFactory(null);
        JsonGenerator generator = factory.createGenerator(System.out);

        generator.writeStartArray().
                writeStartObject().
                write("type", "home").
                write("number", "(800) 111-1111").writeEnd().
                writeStartObject().
                write("type", "cell").
                write("number", "(800) 222-2222").writeEnd().
                writeEnd().close();
    }
}
