package ua.omniway.dao.ot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.ObjectData;
import ua.omniway.client.ot.soap.RequiredField;
import ua.omniway.client.ot.soap.operations.SoapOperations;
import ua.omniway.client.ot.soap.service.SimpleOtCrud;
import ua.omniway.client.ot.soap.types.DateTimeVal;
import ua.omniway.client.ot.soap.types.LongIntVal;
import ua.omniway.client.ot.soap.types.ReferenceVal;
import ua.omniway.client.ot.soap.types.StringVal;
import ua.omniway.models.ot.Contact;
import ua.omniway.models.ot.ObjectType;
import ua.omniway.models.ot.ProcessData;

import java.util.ArrayList;
import java.util.List;

import static ua.omniway.models.ot.ProcessData.*;

@Component
public class ProcessDataDao extends SimpleOtCrud<ProcessData> {

    @Autowired
    public ProcessDataDao(SoapOperations soapOperations) {
        super(soapOperations);
    }

    /**
     * @return list of {@link RequiredField} consist all fields of service request
     */
    public List<RequiredField> getRequiredFields() {
        List<RequiredField> fields = new ArrayList<>();
        fields.add(new RequiredField(ID_ALIAS));
        fields.add(new RequiredField(SUMMARY_ALIAS));
        fields.add(new RequiredField(DESCRIPTION_ALIAS));
        fields.add(new RequiredField(DEADLINE_ALIAS));
        fields.add(new RequiredField(STATE_ALIAS));
        fields.add(new RequiredField(OBJECT_TYPE_NAME_ALIAS));
        fields.add(new RequiredField(CALLER_ALIAS));
        fields.add(new RequiredField(DATE_CLOSED_ALIAS));
        return fields;
    }

    @Override
    protected List<ProcessData> parseFind(List<ObjectData> objects) {
        List<ProcessData> data = new ArrayList<>();
        if (objects != null && !objects.isEmpty()) {
            for (ObjectData objectData : objects) {
                ProcessData processData = new ProcessData(objectData.getId());
                for (Object otValue : objectData.getOtValues()) {
                    if (otValue instanceof LongIntVal && ID_ALIAS.equals(((LongIntVal) otValue).getName())) {
                        processData.setId(((LongIntVal) otValue).getValue());
                    }
                    if (otValue instanceof StringVal && SUMMARY_ALIAS.equals(((StringVal) otValue).getName())) {
                        processData.setSummary(((StringVal) otValue).getValue());
                    }
                    if (otValue instanceof StringVal && DESCRIPTION_ALIAS.equals(((StringVal) otValue).getName())) {
                        processData.setDescription(((StringVal) otValue).getValue());
                    }
                    if (otValue instanceof DateTimeVal && DEADLINE_ALIAS.equals(((DateTimeVal) otValue).getName())) {
                        processData.setDeadline(((DateTimeVal) otValue).getValue());
                    }
                    if (otValue instanceof StringVal && STATE_ALIAS.equals(((StringVal) otValue).getName())) {
                        processData.setState(((StringVal) otValue).getValue());
                    }
                    if (otValue instanceof StringVal && OBJECT_TYPE_NAME_ALIAS.equals(((StringVal) otValue).getName())) {
                        processData.setObjectType(ObjectType.getByAlias(((StringVal) otValue).getValue()));
                    }
                    if (otValue instanceof ReferenceVal && CALLER_ALIAS.equals(((ReferenceVal) otValue).getName())) {
                        processData.setCaller(new Contact(((ReferenceVal) otValue).getObjectId()));
                    }
                    if (otValue instanceof DateTimeVal && DATE_CLOSED_ALIAS.equals(((DateTimeVal) otValue).getName())) {
                        processData.setDateClosed(((DateTimeVal) otValue).getValue());
                    }
                }
                data.add(processData);
            }
        }
        return data;
    }
}