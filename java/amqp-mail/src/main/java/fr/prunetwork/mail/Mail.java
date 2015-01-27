package fr.prunetwork.mail;

import org.jetbrains.annotations.NotNull;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * A Mail interface. Provides standard mail handler.
 *
 * @author Jean-Pierre PRUNARET
 * @date 15/01/2015.
 */
public interface Mail {

    @NotNull
    String getFromMailAddress();

    @NotNull
    String getSubject();

    @NotNull
    String getBody();

    @NotNull
    List<String> getToMailAddresses();

    @NotNull
    InternetAddress[] getDestinationAddresses() throws Exception;
}
