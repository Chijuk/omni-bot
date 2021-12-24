package ua.omniway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.db.InteractionsDraft;

public interface InteractionsDraftRepository extends JpaRepository<InteractionsDraft, Long> {

    @Transactional
    InteractionsDraft findByTextMessageId(Integer id);
}
