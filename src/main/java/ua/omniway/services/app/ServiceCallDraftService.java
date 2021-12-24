package ua.omniway.services.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.omniway.models.db.ServiceCallDraft;
import ua.omniway.repository.ServiceCallDraftRepository;

import java.util.Objects;

@Service
public class ServiceCallDraftService {
    private final ServiceCallDraftRepository repository;

    @Autowired
    public ServiceCallDraftService(ServiceCallDraftRepository repository) {
        this.repository = repository;
    }

    public ServiceCallDraft saveDraft(ServiceCallDraft draft) {
        Objects.requireNonNull(draft, "draft can not be null");
        return repository.saveAndFlush(draft);
    }

    public ServiceCallDraft findById(Long id) {
        Objects.requireNonNull(id, "id can not be null");
        return repository.findById(id).orElse(null);
    }

    public ServiceCallDraft findBySummaryMessageId(Integer id) {
        Objects.requireNonNull(id, "summaryMessageId can not be null");
        return repository.findBySummaryMessageId(id);
    }

    public void deleteById(Long id) {
        Objects.requireNonNull(id, "id can not be null");
        repository.deleteById(id);
    }
}