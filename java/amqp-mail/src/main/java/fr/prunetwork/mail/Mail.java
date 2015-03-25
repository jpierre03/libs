package fr.prunetwork.mail;

import org.jetbrains.annotations.NotNull;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mail interface with usual mail handler.
 *
 * @author Jean-Pierre PRUNARET
 * @date 15/01/2015.
 */
public interface Mail {

    @NotNull
    String getFromMailAddress();

    @NotNull
    List<String> getToMailAddresses();

    @NotNull
    String getSubject();

    @NotNull
    String getBody();

    @NotNull
    default InternetAddress[] getDestinationAddresses() throws Exception {
        @NotNull final Collection<InternetAddress> internetAddresses = new ArrayList<>();
        for (String emailAddress : getToMailAddresses()) {
            internetAddresses.add(new InternetAddress(emailAddress));
        }

        return internetAddresses.toArray(new InternetAddress[internetAddresses.size()]);
    }

    default boolean isValid() {
        return !getFromMailAddress().trim().isEmpty()
                && !getToMailAddresses().isEmpty()
                && !getSubject().trim().isEmpty()
                && !getBody().trim().isEmpty();
    }
}
