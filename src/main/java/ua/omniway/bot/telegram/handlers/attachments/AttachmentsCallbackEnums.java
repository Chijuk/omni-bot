package ua.omniway.bot.telegram.handlers.attachments;

public enum AttachmentsCallbackEnums {
    BASE_COMMAND("/attachments"),
    ADD_ATTACHMENT(BASE_COMMAND.getCommand() + "Add"),
    DELETE_ATTACHMENT(BASE_COMMAND.getCommand() + "Delete");

    private final String command;

    AttachmentsCallbackEnums(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
