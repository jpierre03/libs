package fr.prunetwork;

import fr.prunetwork.mail.Mail;
import fr.prunetwork.mail.MailSender;
import fr.prunetwork.mail.SimpleMail;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Hello world!
 */
public class MailTest {

    public static void main(String[] args) {

        @NotNull final MailSender sender = new MailSender("192.168.1.50", false);

        for (int i = 0; i < 10; i++) {

            try {
                @NotNull final Mail mail = new SimpleMail(
                        "java@spam.prunetwork.fr",
                        Arrays.asList("blackhole@prunetwork.fr"),
                        "Test sujet",
                        "Test contenu mail"
                );

                sender.send(mail);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
}
