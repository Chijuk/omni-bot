package ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback;

import java.util.Arrays;

public enum FeedbackCallbackEnums {
    FEEDBACK("/serviceCallFeedback"),
    SOLUTION_ACCEPTED("Accepted"),
    SOLUTION_REJECTED("Rejected"),
    EDIT_MESSAGE_TEXT(FEEDBACK.getCommand() + "EditSummary"),
    SEND(FEEDBACK.getCommand() + "Send"),
    QUALITY_FEEDBACK_OPTIONS("/qualityFeedbackOptions");

    private final String command;

    FeedbackCallbackEnums(String command) {
        this.command = command;
    }

    public static FeedbackCallbackEnums byValue(String value) {
        return Arrays.stream(FeedbackCallbackEnums.values()).filter(x -> x.getCommand().equalsIgnoreCase(value)).findFirst().orElse(null);
    }

    public String getCommand() {
        return command;
    }
}
