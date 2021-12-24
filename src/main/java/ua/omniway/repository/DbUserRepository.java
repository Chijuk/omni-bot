package ua.omniway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.db.DbUser;

public interface DbUserRepository extends JpaRepository<DbUser, Long> {

    @Transactional
    DbUser findByPersonId(Long uniqueId);
}
