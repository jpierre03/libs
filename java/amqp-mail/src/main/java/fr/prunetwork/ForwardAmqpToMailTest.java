package fr.prunetwork;

import fr.prunetwork.mail.AmqpToMailForwarderService;
import fr.prunetwork.mail.MailSenderConfiguration;

import javax.mail.PasswordAuthentication;
import java.util.Properties;

import static fr.prunetwork.mail.MailDefaultProperties.DEFAULT_AMQP_CONFIGURATION;

/**
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public class ForwardAmqpToMailTest {

    public static void main(String... argv) throws Exception {


        // libmail.marseille@gmail.com
        // libmail marseille
        // dxJQRe$38+d-RG#r?
        // 01/01/2000

        final String username = "libmail.marseille@gmail.com";
        final String password = "dxJQRe$38+d-RG#r?";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        MailSenderConfiguration mailSenderConfiguration = new MailSenderConfiguration(
                props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                },
                true);
        final AmqpToMailForwarderService service = new AmqpToMailForwarderService(DEFAULT_AMQP_CONFIGURATION, mailSenderConfiguration);

        service.start();

        Thread.sleep(1 * 1000);

        service.stop();
    }
}
