package ua.omniway.services.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.omniway.models.db.ServiceCallFeedbackDraft;
import ua.omniway.repository.ServiceCallFeedbackDraftRepository;

import java.util.Objects;

@Service
public class ServiceCallFeedbackDraftService {
    private final ServiceCallFeedbackDraftRepository repository;

    @Autowired
    public ServiceCallFeedbackDraftService(ServiceCallFeedbackDraftRepository repository) {
        this.repository = repository;
    }

    public ServiceCallFeedbackDraft save(ServiceCallFeedbackDraft draft) {
        Objects.requireNonNull(draft, "draft can not be null");
        return repository.saveAndFlush(draft);
    }

    public ServiceCallFeedbackDraft findById(Long id) {
        Objects.requireNonNull(id, "id can not be null");
        return repository.findById(id).orElse(null);
    }

    public ServiceCallFeedbackDraft findByTextMessageId(Integer id) {
        Objects.requireNonNull(id, "summaryMessageId can not be null");
        return repository.findByTextMessageId(id);
    }

    public void deleteById(Long id) {
        Objects.requireNonNull(id, "id can not be null");
        repository.deleteById(id);
    }
}
