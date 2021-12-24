package ua.omniway.services.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.omniway.models.db.DbUser;
import ua.omniway.repository.InteractionsDraftRepository;
import ua.omniway.repository.ServiceCallDraftRepository;
import ua.omniway.repository.ServiceCallFeedbackDraftRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommonDraftsService {
    private final ServiceCallFeedbackDraftRepository feedbackDraftRepository;
    private final InteractionsDraftRepository interactionDraftRepository;
    private final ServiceCallDraftRepository serviceCallDraftRepository;

    /**
     * Clean dbUser cache objects
     *
     * @param dbUser dbUser
     */
    public void cleanAllDraftObjects(DbUser dbUser) {
        Objects.requireNonNull(dbUser, "dbUser can not be null");
        if (dbUser.getServiceCallFeedbackDraft() != null) {
            feedbackDraftRepository.delete(dbUser.getServiceCallFeedbackDraft());
        }
        if (dbUser.getInteractionsDraft() != null) {
            interactionDraftRepository.delete(dbUser.getInteractionsDraft());
        }
        if (dbUser.getServiceCallDraft() != null) {
            serviceCallDraftRepository.delete(dbUser.getServiceCallDraft());
        }
    }
}
