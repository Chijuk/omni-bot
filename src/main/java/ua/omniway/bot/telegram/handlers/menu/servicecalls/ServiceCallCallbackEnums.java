package ua.omniway.bot.telegram.handlers.menu.servicecalls;

public enum ServiceCallCallbackEnums {
    NEW_COMMAND("/newservicecall"),
    ADD_SUMMARY(NEW_COMMAND.getCommand() + "AddSummary"),
    EDIT_SUMMARY(NEW_COMMAND.getCommand() + "EditSummary"),
    SEND(NEW_COMMAND.getCommand() + "Send"),
    SHOW_INFO("/showServiceCallInfo"),
    SHOW_SC("/serviceCalls"),
    MENU_BACK(SHOW_SC.getCommand() + "_back"),
    SHOW_SC_CALLER(SHOW_SC.getCommand() + "_caller");

    private final String command;

    ServiceCallCallbackEnums(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
