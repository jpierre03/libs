package fr.prunetwork.elasticsearch.push;

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jean-Pierre PRUNARET
 *         <p>
 *         commande de suppression de l'index : curl -XDELETE  'http://<nom domaine>/sandbox/'
 *         commande de création de l'index : curl -XPUT  'http://<nom domaine>/sandbox/'
 */
public class MultipleInsertTest {

    private static final Random random = new SecureRandom();
    private static final String BASE_URL = "http://localhost:9200";
    private static final String ES_URL = BASE_URL + "/sandbox";
    //private static final String ES_URL = BASE_URL + "/sandbox/json/";
    //private static final String ES_URL = BASE_URL + "/_search?q=*";
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        @NotNull final AtomicInteger count = new AtomicInteger(0);

        for (int i = 0; i < 1; i++) {
            final int invocationNumber = i;
            EXECUTOR.submit(() -> {
                try {
                    @NotNull URL url = new URL(String.format("%s/%s", ES_URL, getId(25)));
                    //URL url = new URL(ES_URL);

                    @NotNull HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setAllowUserInteraction(true);
                    connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestMethod("POST");

                    @NotNull final StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < 1 * 10; j++) {
                        sb.append(getSimple());
                        sb.append("\n");
                    }

                    @NotNull OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());

                    osw.write(sb.toString());
                    osw.flush();


                    osw.close();

                    connection.disconnect();

                    int number = count.incrementAndGet();
                    if (number % 100 == 0) {
                        System.out.println("#réalisé: " + number + ", numéro d'invocation: " + invocationNumber);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    static String getSimple() {
        @NotNull JSONObject obj = new JSONObject();

        obj.put("dataset", "no6");

        obj.put("name", "laveuse" + getId(2));
        obj.put("site", "site" + getId(2));
        obj.put("mesure.moteur.tension", random.nextDouble() * 24D);
        obj.put("mesure.brosse.tension", random.nextDouble() * 24D);
        obj.put("mesure.batterie.tension", 22 + random.nextDouble() * 2D);
        obj.put("mesure.batterie.courant", random.nextDouble() * 40D);
        obj.put("is_ok", random.nextBoolean());
        obj.put("id", getId(25));

        //System.out.println(obj.toString());

        return obj.toString();
    }

    static String getId(int length) {
        String s = RandomStringUtils.randomAlphabetic(length);

        if (s.trim().isEmpty()) {
            s = getId(length);
        }

        return s;
    }
}
