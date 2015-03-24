package fr.prunetwork.mail;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static fr.prunetwork.mail.MailDefaultProperties.MAILER_VERSION;

/**
 * An SMTP wrapper.
 *
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public class MailSender {

    @NotNull
    private final Session session;
    //private ExecutorService executor = Executors.newFixedThreadPool(1);

    private MailSender(@NotNull final Properties properties,
                       @Nullable final Authenticator authenticator,
                       final boolean isDebug) {

        session = Session.getDefaultInstance(properties, authenticator);
        session.setDebug(isDebug);
    }

    public MailSender(@NotNull final MailSenderConfiguration conf) {
        this(conf.getProps(), conf.getAuthenticator(), conf.isDebug());
    }

    /**
     * @param mail email to be send
     * @throws Exception is thrown when : email address format is wrong (FROM & Destination)
     */
    public void send(@NotNull final Mail mail) throws Exception {

        if (mail.isValid()) {
            throw new IllegalArgumentException("mail not valid. Please check");
        }

        @NotNull final Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mail.getFromMailAddress()));

        @NotNull final InternetAddress[] internetAddresses = mail.getDestinationAddresses();

        message.addHeader("X-Mailer", MAILER_VERSION);
        message.setSentDate(new Date());
        message.setRecipients(Message.RecipientType.TO, internetAddresses);

        message.setSubject(mail.getSubject());
        message.setText(mail.getBody());

        Transport.send(message);
    }
}
