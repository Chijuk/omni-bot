package ua.omniway.models;

import java.util.Arrays;

/**
 * Enums used to define {@link MessagingDirection} type in any type of messaging.
 */
public enum MessagingDirection {
    FROM_CUSTOMER("FROM-CUSTOMER"),
    TO_CUSTOMER("TO-CUSTOMER");

    private final String value;

    MessagingDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MessagingDirection getByValue(String value) {
        return Arrays.stream(MessagingDirection.values()).filter(x -> x.getValue().equalsIgnoreCase(value)).findFirst().orElse(null);
    }
}
