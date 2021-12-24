package ua.omniway.models.ot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.omniway.client.ot.soap.types.OtFolder;
import ua.omniway.client.ot.soap.types.OtRequest;

import static ua.omniway.models.ot.FoldersEnum.CHAT_BOT_SETTINGS;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class ChatBotSetting extends OtRequest {
    public static final OtFolder FOLDER = CHAT_BOT_SETTINGS.getFolder();
    public static final String PERSON_ALIAS = "Person";

    private Contact person;

    public ChatBotSetting() {
    }

    public ChatBotSetting(long uniqueId) {
        super(uniqueId);
    }
}
