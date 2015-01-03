package fr.prunetwork.sandbox.common.message;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public enum MessageProperties {
    CreationDate(Date.class);
    private final Class<?> valueType;

    MessageProperties(Class<?> valueType) {
        this.valueType = valueType;
    }

    public Class<?> getValueType() {
        return valueType;
    }
}
