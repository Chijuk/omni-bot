package ua.omniway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.omniway.models.db.ApprovalVoteTemp;

/**
 * May be used for additional approval operation
 * For now is not on use
 */
public interface ApprovalVoteTempRepository extends JpaRepository<ApprovalVoteTemp, Long> {
}
