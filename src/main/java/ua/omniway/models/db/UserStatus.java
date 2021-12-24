package ua.omniway.models.db;

/**
 * Enums used to define {@link DbUser} status .
 * <br>
 * Values: {@link UserStatus#DISABLED}, {@link UserStatus#ENABLED}
 */
public enum UserStatus {
    DISABLED, ENABLED;

    public static UserStatus fromValue(int value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.ordinal() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value:" + value);
    }
}
