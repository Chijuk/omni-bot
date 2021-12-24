package ua.omniway.services.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.omniway.models.db.ApprovalVoteTemp;
import ua.omniway.repository.ApprovalVoteTempRepository;

/**
 * May be used for additional approval operation
 * For now is not on use
 */
@Service
public class ApprovalVoteTempService {
    private final ApprovalVoteTempRepository repository;

    @Autowired
    public ApprovalVoteTempService(ApprovalVoteTempRepository repository) {
        this.repository = repository;
    }

    public void save(ApprovalVoteTemp approvalVoteTemp) {
        repository.saveAndFlush(approvalVoteTemp);
    }

    public ApprovalVoteTemp findById(Long id) {
        if (id == null) throw new IllegalArgumentException("id can not be null");
        return repository.findById(id).orElse(null);
    }
}
