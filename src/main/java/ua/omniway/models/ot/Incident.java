package ua.omniway.models.ot;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.omniway.client.ot.soap.types.OtFolder;

import static ua.omniway.models.ot.FoldersEnum.INCIDENT;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Incident extends ProcessData {
    public static final OtFolder FOLDER = INCIDENT.getFolder();

    public Incident() {
    }

    public Incident(long uniqueId) {
        super(uniqueId);
    }

    public Incident(ProcessData processData) {
        super(processData);
    }
}
