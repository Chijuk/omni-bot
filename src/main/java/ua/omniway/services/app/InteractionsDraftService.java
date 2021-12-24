package ua.omniway.services.app;

import org.springframework.stereotype.Service;
import ua.omniway.models.db.InteractionsDraft;
import ua.omniway.repository.InteractionsDraftRepository;

import java.util.Objects;

@Service
public class InteractionsDraftService {
    private final InteractionsDraftRepository repository;

    public InteractionsDraftService(InteractionsDraftRepository repository) {
        this.repository = repository;
    }

    public InteractionsDraft findById(Long id) {
        Objects.requireNonNull(id, "id can not be null");
        return repository.findById(id).orElse(null);
    }

    public InteractionsDraft findByTextMessageId(Integer id) {
        Objects.requireNonNull(id, "textMessageId can not be null");
        return repository.findByTextMessageId(id);
    }

    public void deleteById(Long id) {
        if (id == null) throw new IllegalArgumentException("id can not be null");
        repository.deleteById(id);
    }

    public InteractionsDraft save(InteractionsDraft draft) {
        if (draft == null) throw new IllegalArgumentException("draft can not be null");
        return repository.saveAndFlush(draft);
    }
}
