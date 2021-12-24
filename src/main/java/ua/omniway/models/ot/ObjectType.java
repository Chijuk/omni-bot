package ua.omniway.models.ot;

import java.util.Arrays;

public enum ObjectType {
    APPROVAL_REQUEST("APPROVAL_REQUEST"),
    CHANGE_REQUEST("CHANGE_REQUEST"),
    SERVICE_REQUEST("SERVICE_REQUEST"),
    INCIDENT("INCIDENT"),
    WORK_ORDER("WORK_ORDER");

    private final String alias;

    ObjectType(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
    public static ObjectType getByAlias(String alias) {
        return Arrays.stream(ObjectType.values()).filter(x -> x.getAlias().equals(alias)).findFirst().orElse(null);
    }
}
