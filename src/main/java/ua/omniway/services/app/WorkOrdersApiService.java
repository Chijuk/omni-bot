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
import ua.omniway.dao.ot.AttachmentMeta;
import ua.omniway.dao.ot.WorkOrdersApi;
import ua.omniway.services.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrdersApiService implements WorkOrdersApi {
    public static final String DISPATCHER_SCRIPT = "Api.WorkOrder.Dispatcher";
    public static final String OBJECT_ID = "objectId";
    public static final String USER_ID = "userId";
    public static final String MESSAGE_TEXT = "messageText";
    public static final String ATTACHMENTS = "attachments";

    private final CommonApiService commonApiService;

    /**
     * @param objectUniqueId mandatory object unique id in OMNITRACKER
     * @param messageText    mandatory message text
     * @param attachments    optional attachments metadata
     * @throws OmnitrackerException exception from OMNITRACKER
     * @throws ServiceException     general exception in service
     */
    @Override
    public void sendMessageToCustomer(long objectUniqueId, String messageText, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException {
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
     */
    @Override
    public boolean canSendMessageToCustomer(long objectUniqueId, long userUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new LongIntVal(USER_ID, userUniqueId));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "CanSendMessageToCustomer", params);
    }
}
