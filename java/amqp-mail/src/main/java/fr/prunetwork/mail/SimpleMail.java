package fr.prunetwork.mail;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * A default implementation
 *
 * @see fr.prunetwork.mail.Mail
 *
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
}
