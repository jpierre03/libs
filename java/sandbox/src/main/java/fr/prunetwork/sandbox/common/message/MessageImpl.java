package fr.prunetwork.sandbox.common.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public class MessageImpl implements Message {

    private final String source;
    private final String destination;
    private final Map<MessageProperties, Object> properties;

    private MessageImpl(MessageBuilder builder) {
        this.source = builder.source;
        this.destination = builder.destination;
        this.properties = builder.properties;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public Map<MessageProperties, Object> getProperties() {
        return properties;
    }

    public class MessageBuilder {

        public final String source;
        public final String destination;
        public final Map<MessageProperties, Object> properties = new HashMap<>();

        public MessageBuilder(String source, String destination) {
            this.source = source;
            this.destination = destination;

            if (source == null || source.isEmpty()) {
                throw new IllegalArgumentException("null or empty source");
            }

            if (destination == null || destination.isEmpty()) {
                throw new IllegalArgumentException("null or empty source");
            }
        }

        public Message build() {
            return new MessageImpl(this);
        }

        public MessageBuilder addProperties(MessageProperties key, Object value) {
            if (key == null) {
                throw new IllegalArgumentException("null key");
            }

            final Class<?> expectedClass = key.getValueType();

            if (expectedClass.isInstance(value)) {
                this.properties.put(key, value);
            } else {
                throw new IllegalArgumentException("value is wrong type");
            }

            return this;
        }
    }
}
