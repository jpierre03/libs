package fr.prunetwork.database;

/**
 * @author Jean-Pierre PRUNARET
 *         Created on 2016-11-22
 */
public final class PostgresqlSetting {

    public static final String PG_PORT_NUMBER = "5432";
    public static final String PG_TEST_HOSTNAME = "127.0.0.1";
    public static final String PG_TEST_DATABASE = "db_json_test";
    public static final String PG_TEST_URL = "jdbc:postgresql://" + PG_TEST_HOSTNAME + ":" + PG_PORT_NUMBER + "/" + PG_TEST_DATABASE;
    public static final String PG_TEST_PASS = "sandbox";
    public static final String PG_USER = "sandbox";

    private PostgresqlSetting() {
        // do nothing
    }
}
