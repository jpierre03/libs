package fr.prunetwork.database;

import fr.prunetwork.amqp.message.JsonMessage;
import fr.prunetwork.json.JsonExportable;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 */
public class ConnectionPostgresqlTest {

    public static void main(String[] args) {
        try {
            ConnectionPostgresql pgsql = new ConnectionPostgresql(
                    PostgresqlSetting.PG_TEST_URL,
                    PostgresqlSetting.PG_USER,
                    PostgresqlSetting.PG_TEST_PASS
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
