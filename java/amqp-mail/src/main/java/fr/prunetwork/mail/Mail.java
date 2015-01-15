package fr.prunetwork.mail;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * A Mail interface. Provides standard mail handler.
 *
 * @author Jean-Pierre PRUNARET
 * @date 15/01/2015.
 */
public interface Mail {

    String getFromMailAddress();

    String getSubject();

    String getBody();

    List<String> getToMailAddresses();

    InternetAddress[] getDestinationAddresses() throws Exception;
}
