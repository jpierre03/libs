package fr.prunetwork.mail;

import java.util.Collections;
import java.util.List;

/**
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public class Mail {

    private final String fromMailAddress;
    private final List<String> toMailAddresses;
    private final String subject;
    private final String body;

    public Mail(final String fromMailAddress,
                final List<String> toMailAddresses,
                final String subject,
                final String body) {
        this.fromMailAddress = fromMailAddress;
        this.toMailAddresses = toMailAddresses;
        this.subject = subject;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getFromMailAddress() {
        return fromMailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getToMailAddresses() {
        return Collections.unmodifiableList(toMailAddresses);
    }
}
