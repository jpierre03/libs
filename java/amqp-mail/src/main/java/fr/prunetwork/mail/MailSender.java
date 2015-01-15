package fr.prunetwork.mail;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static fr.prunetwork.mail.MailDefaultProperties.MAILER_VERSION;

/**
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public class MailSender {

    private final Session session;
    //private ExecutorService executor = Executors.newFixedThreadPool(1);

    public MailSender(final String serverHostname, final boolean isDebug) {
        final Properties prop = System.getProperties();
        prop.put("mail.smtp.host", serverHostname);

        session = Session.getDefaultInstance(prop, null);
        session.setDebug(isDebug);
    }

    public void send(final Mail mail) throws Exception {
        if(mail == null){
            throw new IllegalArgumentException();
        }

        final Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mail.getFromMailAddress()));

        final InternetAddress[] internetAddresses = mail.getDestinationAddresses();

        message.addHeader("X-Mailer", MAILER_VERSION);
        message.setSentDate(new Date());
        message.setRecipients(Message.RecipientType.TO, internetAddresses);

        message.setSubject(mail.getSubject());
        message.setText(mail.getBody());

        Transport.send(message);
    }
}
