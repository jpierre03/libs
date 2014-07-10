package fr.onet.ae.common.json;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * http://www.mkyong.com/java/json-simple-example-read-and-write-json/
 * https://code.google.com/p/json-simple/wiki/EncodingExamples
 *
 * @author Jean-Pierre PRUNARET
 * @since 09/07/2014
 */
public class WriteJSONSimpleExample {

    public static void main(String[] args) {

        JSONObject obj = new JSONObject();
        obj.put(SimpleJsonField.name, "mkyong.com");
        obj.put(SimpleJsonField.age, new Integer(100));

        JSONArray list = new JSONArray();
        list.add("msg 1");
        list.add("msg 2");
        list.add("msg 3");

        obj.put(SimpleJsonField.messages, list);

        try {

            FileWriter file = new FileWriter("/Users/jpierre03/test.json");
            file.write(obj.toJSONString());
            file.write("\n");
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(obj);
    }
}
