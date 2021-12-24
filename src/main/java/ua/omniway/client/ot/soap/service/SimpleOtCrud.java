package ua.omniway.client.ot.soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.*;
import ua.omniway.client.ot.soap.operations.SoapOperations;
import ua.omniway.client.ot.soap.types.OtRequest;
import ua.omniway.dao.exceptions.OmnitrackerException;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class SimpleOtCrud<T extends OtRequest> implements OtCrud<T> {
    private final SoapOperations soapOperations;

    protected abstract List<RequiredField> getRequiredFields();

    @Autowired
    protected SimpleOtCrud(SoapOperations soapOperations) {
        this.soapOperations = soapOperations;
    }

    @Override
    public T findById(Long id) throws OmnitrackerException {
        return findById(id, this.getRequiredFields());
    }

    @Override
    public T findById(Long id, List<RequiredField> requiredFields) throws OmnitrackerException {
        List<Long> idList = new ArrayList<>();
        idList.add(id);
        return findAll(idList).stream().findFirst().orElse(null);
    }

    @Override
    public T findByFilter(String folderAlias, Filter filter) throws OmnitrackerException {
        return findAll(folderAlias, filter).stream().findFirst().orElse(null);
    }

    @Override
    public List<T> findAll(List<Long> idList) throws OmnitrackerException {
        List<T> list = new ArrayList<>();
        GetObjectListResult result = soapOperations.getObjectListData(idList, this.getRequiredFields());
        if (result.isSuccess()) {
            if (result.getTotalNumberResults() > 0) {
                return this.parseFind(result.getObject());
            }
        } else {
            throw new OmnitrackerException(result.getErrorMsg());
        }
        return list;
    }

    @Override
    public List<T> findAll(String folderAlias, Filter filter) throws OmnitrackerException {
        return findAll(folderAlias, filter, this.getRequiredFields(), false);
    }

    @Override
    public List<T> findAll(String folderAlias, Filter filter, List<RequiredField> requiredFields, boolean recursive) throws OmnitrackerException {
        List<T> list = new ArrayList<>();
        GetObjectListResult result = soapOperations.getObjectListData(folderAlias, requiredFields, filter, recursive);
        if (result.isSuccess()) {
            if (result.getTotalNumberResults() > 0) {
                return this.parseFind(result.getObject());
            }
        } else {
            throw new OmnitrackerException(result.getErrorMsg());
        }
        return list;
    }

    @Override
    public void modify(Long uniqueId, List<Object> values) throws OmnitrackerException {
        ModifyObjectData data = new ModifyObjectData();
        data.setObjectId(uniqueId);
        data.getOtValues().addAll(values);

        ModifyObjectResult result = soapOperations.modifyObject(data);
        if (!result.isSuccess()) {
            throw new OmnitrackerException(result.getErrorMsg());
        }
    }

    protected List<T> parseFind(List<ObjectData> objects) {
        List<T> list = new ArrayList<>();
        if (objects != null && !objects.isEmpty()) {
            for (ObjectData objectData : objects) {
                list.add((T) new OtRequest(objectData.getId()));
            }
        }
        return list;
    }
}
