package ua.omniway.services.app;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.repository.AttachmentInfoRepository;

@AllArgsConstructor
@Service
public class AttachmentInfoService {
    private final AttachmentInfoRepository repository;
    private final ServiceCallDraftService callDraftService;
    private final InteractionsDraftService interactionsDraftService;
    private final ServiceCallFeedbackDraftService feedbackDraftService;

    public AttachmentInfo findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void saveInfo(AttachmentInfo attachment) {
        repository.saveAndFlush(attachment);
    }

    public void deleteInfo(Long id) {
        repository.deleteById(id);
    }

    public AttachmentInfo findByReplyMessageId(Integer messageId) {
        return repository.findByReplyMessageId(messageId);
    }

    public boolean parentObjectExists(Long id) {
        if (feedbackDraftService.findById(id) != null) return true;
        if (callDraftService.findById(id) != null) return true;
        return interactionsDraftService.findById(id) != null;
    }
}