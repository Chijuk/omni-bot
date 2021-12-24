package ua.omniway.models.ot;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.omniway.client.ot.soap.types.OtFolder;

import static ua.omniway.models.ot.FoldersEnum.CHANGE;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Change extends ProcessData {
    public static final OtFolder FOLDER = CHANGE.getFolder();

    public Change() {
    }

    public Change(long uniqueId) {
        super(uniqueId);
    }

    public Change(ProcessData processData) {
        super(processData);
    }
}
