package ua.omniway.client.ot.soap.operations;

import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.*;
import ua.omniway.client.ot.soap.enums.ScriptExecutionSite;
import ua.omniway.client.ot.soap.service.OTWebServiceSoap;
import ua.omniway.dao.exceptions.DaoException;
import ua.omniway.dao.ot.SoapServiceBuilder;

import javax.xml.ws.WebServiceException;
import java.util.List;

@Component
public class SoapOperations {
    private final SoapServiceBuilder serviceBuilder;

    public SoapOperations(SoapServiceBuilder serviceBuilder) {
        this.serviceBuilder = serviceBuilder;
    }

    private OTWebServiceSoap getService() {
        return serviceBuilder.getService();
    }

    //region GetObjectList methods

    /**
     * Get all objects from folderPath recursive
     *
     * @param folderPath
     * @param requiredField
     * @return
     */
    public GetObjectListResult getObjectListData(String folderPath, List<RequiredField> requiredField) throws WebServiceException {
        GetObjectListData data = new GetObjectListData();
        data.setFolderPath(folderPath);
        data.setRecursive(true);
        data.setGetHistory(false);
        data.setGetNoFields(false);
        data.getRequiredField().addAll(requiredField);
        return getService().getObjectList(data);
    }

    /**
     * Get object by uniqueId
     *
     * @param objectID
     * @param requiredField
     * @return
     */
    public GetObjectListResult getObjectListData(long objectID, List<RequiredField> requiredField) throws WebServiceException {
        GetObjectListData data = new GetObjectListData();
        data.setFolderPath("");
        data.setRecursive(true);
        data.setGetHistory(false);
        data.setGetNoFields(false);
        ObjectIDs objectIDs = new ObjectIDs();
        objectIDs.getObjectIDs().add(objectID);
        data.setObjectIDs(objectIDs);
        data.getRequiredField().addAll(requiredField);
        return getService().getObjectList(data);
    }

    /**
     * Get objects by uniqueId list
     * <br>
     * Result contains fields from requiredField
     *
     * @param objectIDs     {@link List} of {@link Long} objects uniqueId from OMNITRACKER
     * @param requiredField {@link List} of {@link RequiredField} that has to be returned
     * @return {@link GetObjectListResult} object with request result
     * @throws WebServiceException Transport errors while sending SOAP request
     */
    public GetObjectListResult getObjectListData(List<Long> objectIDs, List<RequiredField> requiredField) throws WebServiceException {
        GetObjectListData data = new GetObjectListData();
        data.setFolderPath("");
        data.setRecursive(false);
        data.setGetHistory(false);
        data.setGetNoFields(false);
        ObjectIDs objIDs = new ObjectIDs();
        objIDs.getObjectIDs().addAll(objectIDs);
        data.setObjectIDs(objIDs);
        data.getRequiredField().addAll(requiredField);
        return getService().getObjectList(data);
    }

    /**
     * Get objects by predefined OMNITRACKER user filter.
     * <br>
     * Result contains fields from requiredField
     *
     * @param folderPath    {@link String} absolute path of OMNITRACKER folder
     * @param requiredField {@link List} of {@link RequiredField} that has to be returned
     * @param filter        {@link Filter} object containing OMNITRACKER filter definitions
     * @return {@link GetObjectListResult} object with request result
     * @throws WebServiceException Transport errors while sending SOAP request
     */
    public GetObjectListResult getObjectListData(String folderPath, List<RequiredField> requiredField, Filter filter) throws WebServiceException {
        return getObjectListData(folderPath, requiredField, filter, false);
    }

    /**
     * Get objects by predefined OMNITRACKER user filter.
     * <br>
     * Result contains fields from requiredField
     *
     * @param folderPath    {@link String} absolute path of OMNITRACKER folder
     * @param requiredField {@link List} of {@link RequiredField} that has to be returned
     * @param filter        {@link Filter} object containing OMNITRACKER filter definitions
     * @param recursive     get objects in subfolder
     * @return {@link GetObjectListResult} object with request result
     * @throws WebServiceException Transport errors while sending SOAP request
     */
    public GetObjectListResult getObjectListData(String folderPath, List<RequiredField> requiredField, Filter filter, boolean recursive) throws WebServiceException {
        GetObjectListData data = new GetObjectListData();
        data.setFolderPath(folderPath);
        data.setRecursive(recursive);
        data.setGetHistory(false);
        data.setGetNoFields(false);
        data.setFilter(filter);
        data.getRequiredField().addAll(requiredField);
        return getService().getObjectList(data);
    }

    /**
     * Get objects by predefined OMNITRACKER user filter
     * <br>
     * Result contains object uniqueId only
     *
     * @param folderPath
     * @param filter
     * @return
     */
    public GetObjectListResult getObjectListData(String folderPath, Filter filter) throws WebServiceException {
        GetObjectListData data = new GetObjectListData();
        data.setFolderPath(folderPath);
        data.setRecursive(true);
        data.setGetHistory(false);
        data.setGetNoFields(true);
        data.setFilter(filter);
        return getService().getObjectList(data);
    }
    //endregion

    //region ModifyObject methods

    /**
     * Used to modify some object in OMNITRACKER
     *
     * @param data Precompiled {@link ModifyObjectData} object
     * @return {@link ModifyObjectResult} object with request result
     * @throws WebServiceException Transport errors while sending SOAP request
     */
    public ModifyObjectResult modifyObject(ModifyObjectData data) throws WebServiceException {
        return getService().modifyObject(data);
    }
    //endregion

    //region InvokeScript methods

    /**
     * Used to invoke some Global server side script in OMNITRACKER
     *
     * @param parameters Precompiled {@link InvokeScriptParameters} object
     * @return {@link InvokeScriptResult} object with request result
     * @throws WebServiceException Transport errors while sending SOAP request
     */
    public InvokeScriptResult invokeScript(InvokeScriptParameters parameters) throws WebServiceException {
        return getService().invokeScript(parameters);
    }
    //endregion

    /**
     * Used to check SOAP connection to OMNITRACKER
     *
     * @throws WebServiceException Transport errors while sending SOAP request
     * @throws DaoException        Error if request result does not success
     * @deprecated since ot common api functions
     */
    @Deprecated(since = "ot common api functions")
    public void checkConnection() throws WebServiceException, DaoException {
        InvokeScriptParameters scriptParameters = new InvokeScriptParameters();
        scriptParameters.setRunAt(ScriptExecutionSite.DEFAULT);
        scriptParameters.setName("ChatBot.CheckStatus");
        InvokeScriptResult result = getService().invokeScript(scriptParameters);
        if (!result.isSuccess()) throw new DaoException(result.getErrorMsg());
    }
}
