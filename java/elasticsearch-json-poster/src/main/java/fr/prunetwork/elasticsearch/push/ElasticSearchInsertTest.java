package fr.prunetwork.elasticsearch.push;

import fr.prunetwork.elasticsearch.generator.JsonGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jean-Pierre PRUNARET
 *         <p>
 *         commande de suppression de l'index : curl -XDELETE  'http://<nom domaine>/sandbox/'
 *         commande de création de l'index : curl -XPUT  'http://<nom domaine>/sandbox/'
 */
public class ElasticSearchInsertTest {

    private static final String BASE_URL = "http://localhost:9200";
    private static final String ES_URL = BASE_URL + "/";
    //private static final String ES_URL = BASE_URL + "/sandbox/json/";
    //private static final String ES_URL = BASE_URL + "/_search?q=*";
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final JsonGenerator generator = new JsonGenerator("sandbox");

    public static void main(String[] args) throws Exception {

        SCHEDULER.scheduleAtFixedRate(() -> {
            @NotNull ThreadPoolExecutor executor = (ThreadPoolExecutor) EXECUTOR;
            final int doneCount = count.get();
            final int toProcessCount = executor.getQueue().size();
            System.out.println("#réalisé: " + doneCount + ", restant: " + toProcessCount);
            if (toProcessCount == 0 && doneCount > 0) {
                EXECUTOR.shutdown();
                SCHEDULER.shutdown();
            }
        }, 3, 10, TimeUnit.SECONDS);

        for (int i = 0; i < 20000; i++) {
            EXECUTOR.submit(() -> {
                try {
                    //URL url = new URL(String.format("%s/%d", ES_URL, random.nextInt()));
                    @NotNull URL url = new URL(ES_URL + "_bulk");

                    @NotNull HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestMethod("POST");

                    @NotNull OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                    //osw.write(getBody());
                    {
                        @NotNull StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < 3333; j++) {
                            sb.append(generator.getHeader());
                            sb.append("\n");
                            sb.append(generator.getBody());
                            sb.append("\n");

                        }
                        //System.out.println(sb.toString());
                        osw.write(sb.toString());
                    }
                    osw.flush();


                    //display what returns the POST request
                    @NotNull StringBuilder sb = new StringBuilder();
                    int HttpResult = connection.getResponseCode();

                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        @NotNull BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                        @Nullable String line = null;

                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }

                        br.close();
                        //System.out.println("" + sb.toString());

                    } else {
                        System.err.println(HttpResult);
                        System.err.println(connection.getResponseMessage());
                    }

                    osw.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                count.incrementAndGet();
            });

            if (i % 10 == 0) {
                //if (i % 5000 == 0) {
                System.out.println("#invoqué: " + i);
            }

            if (i % 101 == 0) {
                try {
                    Thread.sleep(10 * 1000);
                } catch (Exception e) {

                }
            }
        }
    }
}
