package ua.omniway.models.db;

/**
 * Enums used to define {@link DbUser} active action.
 */
public enum ActiveAction {
    START(0), LOGIN_PASSWORD(1), LOGIN_PHONE_NUMBER(2), MAIN_MENU(3),
    APPROVAL_REJECT(4), NEW_SC(5), FEEDBACK_REJECT(6), NEW_INTERACTION(7);

    private final int id;

    ActiveAction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ActiveAction fromValue(int value) {
        for (ActiveAction action : ActiveAction.values()) {
            if (action.getId() == value) {
                return action;
            }
        }
        throw new IllegalArgumentException("Unknown value:" + value);
    }
}
