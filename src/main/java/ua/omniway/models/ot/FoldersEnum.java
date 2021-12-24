package ua.omniway.models.ot;

import ua.omniway.client.ot.soap.types.OtFolder;

public enum FoldersEnum {
    PROCESS_DATA(new OtFolder("ProcessData", null)),
    SERVICE_REQUEST(new OtFolder("ServiceRequest", PROCESS_DATA.folder)),
    INCIDENT(new OtFolder("Incident", PROCESS_DATA.folder)),
    CHANGE(new OtFolder("Changes", PROCESS_DATA.folder)),
    APPROVAL_REQUEST(new OtFolder("ApprovalRequests", PROCESS_DATA.folder)),
    SYSTEM_REFS(new OtFolder("SystemRefs", null)),
    USERS(new OtFolder("Users", SYSTEM_REFS.folder)),
    OBJECT_TYPES(new OtFolder("ObjectTypes", SYSTEM_REFS.folder)),
    PROCESS_REFS(new OtFolder("ProcessRefs", null)),
    CONTACTS(new OtFolder("Contacts", PROCESS_REFS.folder)),
    SYSTEM_DATA_CHAT_BOT(new OtFolder("SystemData\\ChatBot", null)),
    CHAT_BOT_SETTINGS(new OtFolder("ChatBotSettings", SYSTEM_DATA_CHAT_BOT.folder));


    private final OtFolder folder;

    FoldersEnum(OtFolder folder) {
        this.folder = folder;
    }

    public OtFolder getFolder() {
        return folder;
    }
}
