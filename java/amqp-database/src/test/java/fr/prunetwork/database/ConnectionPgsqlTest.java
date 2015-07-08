package fr.prunetwork.database;

import fr.prunetwork.amqp.message.JsonMessage;
import fr.prunetwork.json.JsonExportable;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 */
public class ConnectionPgsqlTest {

    public static final String PG_PORT_NUMBER = "5432";
    public static final String PG_TEST_HOSTNAME = "127.0.0.1";
    public static final String PG_TEST_DATABASE = "db_json_test";
    public static final String PG_TEST_URL = "jdbc:postgresql://" + PG_TEST_HOSTNAME + ":" + PG_PORT_NUMBER + "/" + PG_TEST_DATABASE;
    public static final String PG_TEST_PASS = "sandbox";
    public static final String PG_USER = "sandbox";

    public static void main(String[] args) {
        try {
            ConnectionPostgreSQL pgsql = new ConnectionPostgreSQL(
                    PG_TEST_URL,
                    PG_USER,
                    PG_TEST_PASS
            );

            for (int i = 0; i < 100 * 1000; i++) {
                final Date date = new Date();
                JsonExportable object = new JsonMessage("", "{\"essai\": \"salut |" + date.toString() + "|---" + date.getTime() + "\"}");

                pgsql.insertRawJsonMessage(object);
            }

            for (JsonExportable json : pgsql.getRawJsonMessages()) {
                System.out.println(json.toJSONString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
