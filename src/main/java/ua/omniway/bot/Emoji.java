package ua.omniway.bot;

public enum Emoji {
    // Language flags
    UKRAINIAN('\uD83C', '\uDDFA', '\uD83C', '\uDDE6'),
    RUSSIAN('\uD83C', '\uDDF7', '\uD83C', '\uDDFA'),
    ENGLISH('\uD83C', '\uDDFA', '\uD83C', '\uDDF8'),

    //General
    CHECK_MARK('\u2713', '\uFE0F'),
    HEAVY_CHECK_MARK('\u2714', '\uFE0F'),
    WHITE_CHECK_MARK('\u2705'), // feedback accepted
    WHITE_QUESTION_MARK('\u2754'),
    BALLOT_BOX_WITH_CHECK('\u2611', '\uFE0F'),
    WARNING_SIGN('\u26A0', '\uFE0F'),
    CROSS_MARK('\u274C'), // feedback rejected
    INFORMATION_SOURCE('\u2139', '\uFE0F'),
    GEAR('\u2699', '\uFE0F'),
    CLOSED_LOCK_WITH_KEY('\uD83D', '\uDD10'),
    WHITE_SMALL_SQUARE('\u25AB', '\uFE0F'),
    RADIO_BUTTON('\uD83D', '\uDD18'),
    THUMBS_UP('\uD83D', '\uDC4D'),
    PUBLIC_ADDRESS_LOUDSPEAKER('\uD83D', '\uDCE2'), // Service calls
    HEAVY_EXCLAMATION_MARK('\u2757'),
    RED_DOUBLE_EXCLAMATION_MARK('\u203C', '\uFE0F'),
    NO_ENTRY_SIGN('\uD83D', '\uDEAB'),
    SCROLL('\uD83D', '\uDCDC'); // main menu

    private final Character firstChar;
    private Character secondChar;
    private Character thirdChar;
    private Character fourthChar;

    Emoji(Character firstChar) {
        this.firstChar = firstChar;
    }

    Emoji(Character firstChar, Character secondChar) {
        this.firstChar = firstChar;
        this.secondChar = secondChar;
    }

    Emoji(Character firstChar, Character secondChar, Character thirdChar, Character fourthChar) {
        this.firstChar = firstChar;
        this.secondChar = secondChar;
        this.thirdChar = thirdChar;
        this.fourthChar = fourthChar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.firstChar != null) {
            sb.append(this.firstChar);
        }
        if (this.secondChar != null) {
            sb.append(this.secondChar);
        }
        if (this.thirdChar != null) {
            sb.append(this.thirdChar);
        }
        if (this.fourthChar != null) {
            sb.append(this.fourthChar);
        }

        return sb.toString();
    }
}
