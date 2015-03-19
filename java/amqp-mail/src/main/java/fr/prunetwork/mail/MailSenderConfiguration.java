package fr.prunetwork.mail;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.mail.Authenticator;
import java.util.Properties;

/**
 * @author Jean-Pierre PRUNARET
 * @since 2015-03-19
 */
public class MailSenderConfiguration {

    @Nullable
    private final Authenticator authenticator;
    @NotNull
    private final Properties props;
    private final boolean isDebug;

    public MailSenderConfiguration(@NotNull final String serverHostname,
                                   @Nullable final Authenticator authenticator,
                                   final boolean isDebug) {

        props = new Properties();
        props.put("mail.smtp.host", serverHostname);
        this.isDebug = isDebug;
        this.authenticator = authenticator;
    }

    public MailSenderConfiguration(@NotNull final String serverHostname,
                                   final boolean isDebug) {
        this(serverHostname, null, isDebug);
    }

    public MailSenderConfiguration(@NotNull final String serverHostname) {
        this(serverHostname, null, false);
    }

    public MailSenderConfiguration(@NotNull final Properties props,
                                   @Nullable final Authenticator authenticator,
                                   boolean isDebug) {

        this.props = props;
        this.authenticator = authenticator;
        this.isDebug = isDebug;
    }

    public boolean isDebug() {
        return isDebug;
    }

    @Nullable
    public Authenticator getAuthenticator() {
        return authenticator;
    }

    @NotNull
    public Properties getProps() {
        return props;
    }
}
