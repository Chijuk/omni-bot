package ua.omniway.dao.ot;

import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.services.exceptions.ServiceException;

import java.util.List;

public interface WorkOrdersApi {
    void sendMessageToCustomer(long objectUniqueId, String messageText, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException;

    boolean canSendMessageToCustomer(long objectUniqueId, long userUniqueId) throws OmnitrackerException;
}
