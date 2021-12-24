package ua.omniway.models.ot;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.omniway.client.ot.soap.types.OtFolder;

import static ua.omniway.models.ot.FoldersEnum.SERVICE_REQUEST;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ServiceRequest extends ProcessData {
    public static final OtFolder FOLDER = SERVICE_REQUEST.getFolder();

    public ServiceRequest() {
    }

    public ServiceRequest(long uniqueId) {
        super(uniqueId);
    }

    public ServiceRequest(ProcessData processData) {
        super(processData);
    }
}
