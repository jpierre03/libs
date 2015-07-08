package fr.prunetwork.database;

import fr.prunetwork.json.JsonExportable;
import org.jetbrains.annotations.NotNull;
import org.postgresql.util.PGobject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 *         Created on 15/09/14.
 */
public class ConnectionPostgreSQL {

    @NotNull
    private static final SecureRandom random = new SecureRandom();
    @NotNull
    protected final Connection connection;
    @NotNull
    protected final Object mutex = new Object();
    protected final String random_id = nextSessionId();

    public ConnectionPostgreSQL(@NotNull final String url,
                                @NotNull final String username,
                                @NotNull final String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(true);
    }

    private String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    @Override
    protected void finalize() throws Throwable {
        if (connection != null
                && !connection.isClosed()) {
            connection.close();
        }
        super.finalize();
    }

    public void insertRawJsonMessage(@NotNull final JsonExportable message) throws Exception {
        synchronized (mutex) {

            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO \"T_RawMessage\" (content) VALUES (?)")) {

                PGobject jsonObject = new PGobject();
                jsonObject.setType("json");
                jsonObject.setValue(message.toJSONString());

                stmt.setObject(1, jsonObject);

                stmt.execute();
                connection.commit();
            }
            connection.setAutoCommit(true);
        }
    }

    @NotNull
    public Collection<JsonExportable> getRawJsonMessages() throws Exception {
        synchronized (mutex) {
            final Collection<JsonExportable> messages = new ArrayList<>();

            connection.setAutoCommit(false);
            try (Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CLOSE_CURSORS_AT_COMMIT)) {
                final ResultSet resultSet = stmt.executeQuery("SELECT * FROM \"V_RawMessage\"");

                final String TIMESTAMP = "timestamp";
                final String CONTENT = "content";

                while (resultSet.next()) {
                    messages.add(new Log(resultSet.getString(CONTENT)));
                }
                stmt.executeBatch();
                connection.commit();
            }

            return messages;
        }
    }

    public static class Log implements JsonExportable {

        @NotNull
        private final String content;

        Log(@NotNull final String content) {
            this.content = content;
        }

        @NotNull
        @Override
        public String toJSONString() {
            return content;
        }
    }
}
