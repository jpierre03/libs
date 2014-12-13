package fr.prunetwork;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class MailTest {

    static ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    private final static String MAILER_VERSION = "Java";

    public static boolean envoyerMailSMTP(String serveur,
                                          boolean debug,
                                          final String fromMailAddress,
                                          final String toMailAddress,
                                          final String subject,
                                          final String body) throws Exception {
        boolean result = false;

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", serveur);
        Session session = Session.getDefaultInstance(prop, null);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromMailAddress));

        InternetAddress[] internetAddresses = new InternetAddress[1];
        internetAddresses[0] = new InternetAddress(toMailAddress);

        message.setRecipients(Message.RecipientType.TO, internetAddresses);
        message.setSubject(subject);
        message.setText(body);
        message.setHeader("X-Mailer", MAILER_VERSION);
        message.setSentDate(new Date());
        session.setDebug(debug);
        Transport.send(message);
        result = true;

        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            EXECUTOR.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        MailTest.envoyerMailSMTP(
                                "192.168.1.50",
                                false,
                                "java@spam.prunetwork.fr",
                                "blackhole@prunetwork.fr",
                                "Test sujet",
                                "Test contenu mail"
                        );
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            });
        }
    }
}
