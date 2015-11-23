package fr.prunetwork.elasticsearch.push;

import fr.prunetwork.elasticsearch.generator.JsonGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
public class FileGenerator {

    private FileGenerator() {
    }

    public static void main(String[] args) throws Exception {

        @NotNull final JsonGenerator generator = new JsonGenerator("sandbox");
        final int COUNT = 40 * 1000;

        for (int i = 0; i < 1000; i++) {

            String s = String.format("%04d", i);
            @NotNull File file = new File("data" + s + ".json");

            try (@NotNull FileOutputStream fos = new FileOutputStream(file)) {


                try (@NotNull BufferedOutputStream bos = new BufferedOutputStream(fos)) {

                    try (@NotNull PrintWriter pw = new PrintWriter(bos)) {

                        pw.append(generator.getHeader());
                        pw.append("\n");

                        for (int j = 0; j < COUNT; j++) {

                            pw.append(generator.getBody());
                            pw.append("\n");

                            if (j % (20 * 1000) == 0) {
                                //if (i % 5000 == 0) {
                                System.out.println("#ajouté: " + j);
                                pw.flush();
                            }
                        }

                    } catch (@NotNull final Exception e) {
                        throw e;
                    }

                } catch (@NotNull final Exception e) {
                    throw e;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //System.out.print(sb);
    }
}
