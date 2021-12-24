package ua.omniway.dao.ot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.ObjectData;
import ua.omniway.client.ot.soap.RequiredField;
import ua.omniway.client.ot.soap.operations.SoapOperations;
import ua.omniway.client.ot.soap.service.SimpleOtCrud;
import ua.omniway.client.ot.soap.types.ReferenceVal;
import ua.omniway.models.ot.ChatBotSetting;
import ua.omniway.models.ot.Contact;

import java.util.ArrayList;
import java.util.List;

import static ua.omniway.models.ot.ChatBotSetting.PERSON_ALIAS;

@Component
public class ChatBotSettingsDao extends SimpleOtCrud<ChatBotSetting> {

    @Autowired
    protected ChatBotSettingsDao(SoapOperations soapOperations) {
        super(soapOperations);
    }

    @Override
    protected List<RequiredField> getRequiredFields() {
        List<RequiredField> fields = new ArrayList<>();
        fields.add(new RequiredField(PERSON_ALIAS));
        return fields;
    }

    @Override
    protected List<ChatBotSetting> parseFind(List<ObjectData> objects) {
        List<ChatBotSetting> data = new ArrayList<>();
        if (objects != null && !objects.isEmpty()) {
            for (ObjectData objectData : objects) {
                ChatBotSetting setting = new ChatBotSetting(objectData.getId());
                for (Object otValue : objectData.getOtValues()) {
                    if (otValue instanceof ReferenceVal && PERSON_ALIAS.equals(((ReferenceVal) otValue).getName())) {
                        setting.setPerson(new Contact(((ReferenceVal) otValue).getObjectId()));
                    }
                }
                data.add(setting);
            }
        }
        return data;
    }
}
