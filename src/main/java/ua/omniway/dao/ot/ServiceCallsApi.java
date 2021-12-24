package ua.omniway.dao.ot;

import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.services.exceptions.ServiceException;

import java.util.List;

public interface ServiceCallsApi {
    long createGeneral(long userUniqueId, String subject, String description, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException;

    boolean isCreationGeneralAttachmentSizeAccepted(long size) throws OmnitrackerException;

    void sendMessageToCustomer(long objectUniqueId, String messageText, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException;

    boolean canSendMessageToCustomer(long objectUniqueId, long userUniqueId) throws OmnitrackerException;

    boolean isSendMessageToCustomerAttachmentSizeAccepted(long size) throws OmnitrackerException;

    void sendMessageToSpecialist(long objectUniqueId, String messageText, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException;

    boolean canSendMessageToSpecialist(long objectUniqueId, long userUniqueId) throws OmnitrackerException;

    boolean isSendMessageToSpecialistAttachmentSizeAccepted(long size) throws OmnitrackerException;

    void setCustomerFeedbackAccepted(long objectUniqueId) throws OmnitrackerException;

    boolean canSetCustomerFeedback(long objectUniqueId, long userUniqueId) throws OmnitrackerException;

    void setCustomerFeedbackRejected(long objectUniqueId, String commentsText, List<AttachmentMeta> attachments) throws OmnitrackerException, ServiceException;

    boolean isSetCustomerFeedbackRejectedAttachmentSizeAccepted(long size) throws OmnitrackerException;

    void setQualityRating(long objectUniqueId, String rating) throws OmnitrackerException;

    boolean canSetQualityRating(long objectUniqueId, long userUniqueId) throws OmnitrackerException;
}
