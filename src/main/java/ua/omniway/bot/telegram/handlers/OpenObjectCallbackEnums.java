package ua.omniway.bot.telegram.handlers;

import ua.omniway.models.ot.ObjectType;

import java.util.Arrays;

import static ua.omniway.models.ot.ObjectType.*;

public enum OpenObjectCallbackEnums {
    OPEN_PROCESS_DATA("/opd", null),
    OPEN_SERVICE_REQUEST("/osr", SERVICE_REQUEST),
    OPEN_INCIDENT("/oit", INCIDENT),
    OPEN_CHANGE("/oce", CHANGE_REQUEST);

    private final String handler;
    private final ObjectType objectType;

    OpenObjectCallbackEnums(String handler, ObjectType objectType) {
        this.handler = handler;
        this.objectType = objectType;
    }

    public String getHandler() {
        return handler;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public static OpenObjectCallbackEnums getByObjectType(ObjectType objectType) {
        return Arrays.stream(OpenObjectCallbackEnums.values()).filter(x -> x.getObjectType() == objectType).findFirst().orElse(null);
    }
}
