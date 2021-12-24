package ua.omniway.services.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.omniway.client.ot.soap.service.CommonApiService;
import ua.omniway.client.ot.soap.types.LongIntVal;
import ua.omniway.client.ot.soap.types.NullVal;
import ua.omniway.client.ot.soap.types.StringVal;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.*;
import ua.omniway.models.ot.Change;
import ua.omniway.models.ot.Incident;
import ua.omniway.models.ot.ProcessData;
import ua.omniway.models.ot.ServiceRequest;
import ua.omniway.services.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCallsApiService implements ServiceCallsApi {
    public static final String DISPATCHER_SCRIPT = "Api.ServiceCalls.Dispatcher";
    public static final String OBJECT_ID = "objectId";
    public static final String USER_ID = "userId";
    public static final String SUBJECT = "subject";
    public static final String DESCRIPTION = "description";
    public static final String ATTACHMENTS = "attachments";
    public static final String MESSAGE_TEXT = "messageText";
    public static final String SIZE = "size";
    public static final String COMMENTS_TEXT = "commentsText";
    public static final String RATING = "rating";

    private final CommonApiService commonApiService;
    private final ProcessDataDao processDataDao;
    private final ServiceRequestsDao serviceRequestsDao;
    private final IncidentsDao incidentsDao;
    private final ChangesDao changesDao;

    /**
     * @param userUniqueId mandatory subscriber unique id in OMNITRACKER
     * @param subject      mandatory subject text
     * @param description  optional description text
     * @param attachments  optional attachments metadata
     * @return created object autoincrement number
     * @throws OmnitrackerException exception from OMNITRACKER
     * @throws ServiceException     general exception in service
     */
    @Override
    public long createGeneral(long userUniqueId, String subject, String description, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException {
        if (StringUtils.isEmpty(subject)) throw new IllegalArgumentException("subject is null or empty");
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(USER_ID, userUniqueId));
        params.add(new StringVal(SUBJECT, subject));
        params.add(new StringVal(DESCRIPTION, description == null ? "" : description));
        if (attachments != null && !attachments.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                params.add(new StringVal(ATTACHMENTS, mapper.writeValueAsString(attachments)));
            } catch (JsonProcessingException e) {
                throw new ServiceException(e);
            }
        } else {
            params.add(new NullVal(ATTACHMENTS));
        }
        return (long) commonApiService.execute(DISPATCHER_SCRIPT, "CreateGeneral", params);
    }

    /**
     * @param size mandatory one attachment size
     * @return validation result
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public boolean isCreationGeneralAttachmentSizeAccepted(long size) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(SIZE, size));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "IsCreationGeneralAttachmentSizeAccepted", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param messageText    mandatory message text
     * @param attachments    optional attachments metadata
     * @throws OmnitrackerException exception from OMNITRACKER
     * @throws ServiceException     general exception in service
     * @deprecated moved to {@link WorkOrdersApiService}
     */
    @Override
    @Deprecated(since = "2.0")
    public void sendMessageToCustomer(long objectUniqueId, String messageText, List<AttachmentMeta> attachments) throws ServiceException, OmnitrackerException {
        if (StringUtils.isEmpty(messageText)) throw new IllegalArgumentException("messageText is null or empty");
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new StringVal(MESSAGE_TEXT, messageText));
        if (attachments != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                params.add(new StringVal(ATTACHMENTS, mapper.writeValueAsString(attachments)));
            } catch (JsonProcessingException e) {
                throw new ServiceException(e);
            }
        } else {
            params.add(new NullVal(ATTACHMENTS));
        }
        commonApiService.execute(DISPATCHER_SCRIPT, "SendMessageToCustomer", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param userUniqueId   mandatory subscriber unique id in OMNITRACKER
     * @return validation result
     * @throws OmnitrackerException exception from OMNITRACKER
     * @deprecated moved to {@link WorkOrdersApiService}
     */
    @Override
    @Deprecated(since = "2.0")
    public boolean canSendMessageToCustomer(long objectUniqueId, long userUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new LongIntVal(USER_ID, userUniqueId));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "CanSendMessageToCustomer", params);
    }

    /**
     * @param size mandatory one attachment size
     * @return validation result
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public boolean isSendMessageToCustomerAttachmentSizeAccepted(long size) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(SIZE, size));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "IsSendMessageToCustomerAttachmentSizeAccepted", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param messageText    mandatory message text
     * @param attachments    optional attachments metadata
     * @throws OmnitrackerException exception from OMNITRACKER
     * @throws ServiceException     general exception in service
     */
    @Override
    public void sendMessageToSpecialist(long objectUniqueId, String messageText, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException {
        if (StringUtils.isEmpty(messageText)) throw new IllegalArgumentException("messageText is null or empty");
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new StringVal(MESSAGE_TEXT, messageText));
        if (attachments != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                params.add(new StringVal(ATTACHMENTS, mapper.writeValueAsString(attachments)));
            } catch (JsonProcessingException e) {
                throw new ServiceException(e);
            }
        } else {
            params.add(new NullVal(ATTACHMENTS));
        }
        commonApiService.execute(DISPATCHER_SCRIPT, "SendMessageToSpecialist", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param userUniqueId   mandatory subscriber unique id in OMNITRACKER
     * @return validation result
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public boolean canSendMessageToSpecialist(long objectUniqueId, long userUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new LongIntVal(USER_ID, userUniqueId));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "CanSendMessageToSpecialist", params);
    }

    /**
     * @param size mandatory one attachment size
     * @return validation result
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public boolean isSendMessageToSpecialistAttachmentSizeAccepted(long size) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(SIZE, size));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "IsSendMessageToSpecialistAttachmentSizeAccepted", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public void setCustomerFeedbackAccepted(long objectUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        commonApiService.execute(DISPATCHER_SCRIPT, "SetCustomerFeedbackAccepted", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param commentsText   mandatory comments text
     * @param attachments    optional attachments metadata
     * @throws OmnitrackerException exception from OMNITRACKER
     * @throws ServiceException     general exception in service
     */
    @Override
    public void setCustomerFeedbackRejected(long objectUniqueId, String commentsText, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException {
        if (StringUtils.isEmpty(commentsText)) throw new IllegalArgumentException("commentsText is null or empty");
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new StringVal(COMMENTS_TEXT, commentsText));
        if (attachments != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                params.add(new StringVal(ATTACHMENTS, mapper.writeValueAsString(attachments)));
            } catch (JsonProcessingException e) {
                throw new ServiceException(e);
            }
        } else {
            params.add(new NullVal(ATTACHMENTS));
        }
        commonApiService.execute(DISPATCHER_SCRIPT, "SetCustomerFeedbackRejected", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param userUniqueId   mandatory subscriber unique id in OMNITRACKER
     * @return validation result
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public boolean canSetCustomerFeedback(long objectUniqueId, long userUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new LongIntVal(USER_ID, userUniqueId));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "CanSetCustomerFeedback", params);
    }

    /**
     * @param size mandatory one attachment size
     * @return validation result
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public boolean isSetCustomerFeedbackRejectedAttachmentSizeAccepted(long size) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(SIZE, size));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "IsSetCustomerFeedbackRejectedAttachmentSizeAccepted", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param rating         mandatory rating value
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public void setQualityRating(long objectUniqueId, String rating) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new StringVal(RATING, rating));
        commonApiService.execute(DISPATCHER_SCRIPT, "SetQualityRating", params);
    }

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param userUniqueId   mandatory subscriber unique id in OMNITRACKER
     * @return validation result
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    @Override
    public boolean canSetQualityRating(long objectUniqueId, long userUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new LongIntVal(USER_ID, userUniqueId));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "CanSetQualityRating", params);
    }

    /**
     * Complex request for Service requests, changes, incident for particular caller id
     * <br> Fozzy style
     *
     * @param callerId caller otUniqueId
     * @return list of objects or empty list
     * @throws ServiceException general exception in service
     */
    public List<ProcessData> getProcessDataObjectList(long callerId) throws ServiceException {
        final List<ServiceRequest> serviceRequests;
        final List<Change> changes;
        final List<Incident> incidents;
        try {
            serviceRequests = serviceRequestsDao.getRequestsForCaller(callerId);
            changes = changesDao.getRequestsForCaller(callerId);
            incidents = incidentsDao.getRequestsForCaller(callerId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        List<ProcessData> merged = new ArrayList<>();
        merged.addAll(serviceRequests);
        merged.addAll(changes);
        merged.addAll(incidents);
        return merged;
    }

    /**
     * @param otUniqueId mandatory user unique id in OMNITRACKER
     * @return object or null
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    public ProcessData getProcessDataObject(long otUniqueId) throws OmnitrackerException {
        return processDataDao.findById(otUniqueId);
    }

    /**
     * @param otUniqueId mandatory user unique id in OMNITRACKER
     * @return object or null
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    public ServiceRequest getServiceRequestObject(long otUniqueId) throws OmnitrackerException {
        return serviceRequestsDao.findById(otUniqueId);
    }

    /**
     * @param otUniqueId mandatory user unique id in OMNITRACKER
     * @return object or null
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    public Incident getIncidentObject(long otUniqueId) throws OmnitrackerException {
        return incidentsDao.findById(otUniqueId);
    }

    /**
     * @param otUniqueId mandatory user unique id in OMNITRACKER
     * @return object or null
     * @throws OmnitrackerException exception from OMNITRACKER
     */
    public Change getChangeObject(long otUniqueId) throws OmnitrackerException {
        return changesDao.findById(otUniqueId);
    }
}
