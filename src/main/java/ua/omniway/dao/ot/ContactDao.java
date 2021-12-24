package ua.omniway.dao.ot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.ObjectData;
import ua.omniway.client.ot.soap.RequiredField;
import ua.omniway.client.ot.soap.operations.SoapOperations;
import ua.omniway.client.ot.soap.service.SimpleOtCrud;
import ua.omniway.client.ot.soap.types.StringVal;
import ua.omniway.models.ot.Contact;

import java.util.ArrayList;
import java.util.List;

import static ua.omniway.models.ot.Contact.NAME_ALIAS;

@Component
public class ContactDao extends SimpleOtCrud<Contact> {

    @Autowired
    protected ContactDao(SoapOperations soapOperations) {
        super(soapOperations);
    }

    @Override
    protected List<RequiredField> getRequiredFields() {
        List<RequiredField> fields = new ArrayList<>();
        fields.add(new RequiredField(NAME_ALIAS));
        return fields;
    }

    @Override
    protected List<Contact> parseFind(List<ObjectData> objects) {
        List<Contact> data = new ArrayList<>();
        if (objects != null && !objects.isEmpty()) {
            for (ObjectData objectData : objects) {
                Contact contact = new Contact(objectData.getId());
                for (Object otValue : objectData.getOtValues()) {
                    if (otValue instanceof StringVal && NAME_ALIAS.equals(((StringVal) otValue).getName())) {
                        contact.setName(((StringVal) otValue).getValue());
                    }
                }
                data.add(contact);
            }
        }
        return data;
    }
}
