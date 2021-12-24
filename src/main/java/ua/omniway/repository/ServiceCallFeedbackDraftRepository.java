package ua.omniway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.db.ServiceCallFeedbackDraft;

public interface ServiceCallFeedbackDraftRepository extends JpaRepository<ServiceCallFeedbackDraft, Long> {

    @Transactional
    ServiceCallFeedbackDraft findByTextMessageId(Integer id);
}
