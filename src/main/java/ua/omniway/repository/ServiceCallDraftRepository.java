package ua.omniway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.db.ServiceCallDraft;

public interface ServiceCallDraftRepository extends JpaRepository<ServiceCallDraft, Long> {

    @Transactional
    ServiceCallDraft findBySummaryMessageId(Integer id);
}