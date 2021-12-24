package ua.omniway.models.ot;

import ua.omniway.bot.Emoji;

import java.util.Arrays;

public enum ApprovalResultEnum {
    NOT_VOTED("NotVoted", Emoji.WHITE_QUESTION_MARK),
    APPROVED("Yes", Emoji.WHITE_CHECK_MARK),
    REJECTED("No", Emoji.CROSS_MARK);

    private final String alias;
    private final Emoji emoji;

    ApprovalResultEnum(String alias, Emoji emoji) {
        this.alias = alias;
        this.emoji = emoji;
    }

    public String getAlias() {
        return alias;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    public static ApprovalResultEnum getByAlias(String alias) {
        return Arrays.stream(ApprovalResultEnum.values()).filter(x -> x.getAlias().equals(alias)).findFirst().orElse(null);
    }
}
