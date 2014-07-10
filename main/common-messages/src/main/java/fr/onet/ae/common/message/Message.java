package fr.onet.ae.common.message;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public interface Message extends Serializable {

    String getSource();

    String getDestination();

    Map<MessageProperties, Object> getProperties();
}
