package ua.omniway.bot.telegram.handlers.menu.interactions;

public enum InteractionsCallbackEnums {
    NEW_INTERACTION("/newInteraction"),
    EDIT_MESSAGE_TEXT(NEW_INTERACTION.getCommand() + "EditText"),
    SEND(NEW_INTERACTION.getCommand() + "Send");

    private final String command;

    InteractionsCallbackEnums(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
