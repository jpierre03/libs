package fr.prunetwork.mail;

import org.jetbrains.annotations.NotNull;

import javax.mail.internet.InternetAddress;
import java.util.Collections;
import java.util.List;

/**
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public class SimpleMail implements Mail {

    @NotNull
    private final String fromMailAddress;
    @NotNull
    private final List<String> toMailAddresses;
    @NotNull
    private final String subject;
    @NotNull
    private final String body;

    public SimpleMail(@NotNull final String fromMailAddress,
                      @NotNull final List<String> toMailAddresses,
                      @NotNull final String subject,
                      @NotNull final String body) {
        this.fromMailAddress = fromMailAddress;
        this.toMailAddresses = toMailAddresses;
        this.subject = subject;
        this.body = body;
    }

    @NotNull
    @Override
    public String getBody() {
        return body;
    }

    @NotNull
    @Override
    public String getFromMailAddress() {
        return fromMailAddress;
    }

    @NotNull
    @Override
    public String getSubject() {
        return subject;
    }

    @NotNull
    @Override
    public List<String> getToMailAddresses() {
        return Collections.unmodifiableList(toMailAddresses);
    }

    @NotNull
    @Override
    public InternetAddress[] getDestinationAddresses() throws Exception {

        @NotNull final InternetAddress[] internetAddresses = new InternetAddress[getToMailAddresses().size()];
        for (int i = 0; i < getToMailAddresses().size(); i++) {
            final String emailAddress = getToMailAddresses().get(i);
            internetAddresses[i] = new InternetAddress(emailAddress);
        }

        return internetAddresses;
    }
}
