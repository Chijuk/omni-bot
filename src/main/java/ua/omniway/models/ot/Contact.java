package ua.omniway.models.ot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.omniway.client.ot.soap.types.OtFolder;
import ua.omniway.client.ot.soap.types.OtRequest;

import static ua.omniway.models.ot.FoldersEnum.CONTACTS;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Contact extends OtRequest {
    public static final OtFolder FOLDER = CONTACTS.getFolder();
    public static final String NAME_ALIAS = "Name";

    private String name;

    public Contact(Long otUniqueId) {
        super(otUniqueId);
    }
}
