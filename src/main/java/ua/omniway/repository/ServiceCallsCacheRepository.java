package ua.omniway.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.db.ServiceCallCache;

import java.util.List;

public interface ServiceCallsCacheRepository extends JpaRepository<ServiceCallCache, Long> {

    List<ServiceCallCache> findByMessageIdOrderByScIdDesc(long messageId, Pageable pageable);

    @Transactional
    long removeByUserIdAndCaller(Long userId, long caller);

}
