package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.db.ServiceCallCache;
import ua.omniway.models.ot.ObjectType;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@Transactional
@Sql("classpath:sql/service_calls_cache/insert.sql")
class ServiceCallsCacheRepositoryTest {
    @Autowired
    private ServiceCallsCacheRepository repository;

    @Test
    void whenFindByMessageIdOrderByScIdDesc_thenSuccess() {
        final List<ServiceCallCache> callCaches = repository.findByMessageIdOrderByScIdDesc(2000,
                PageRequest.of(1, 2));
        assertThat(callCaches.size()).isEqualTo(2);
        assertThat(callCaches.get(0).getScId()).isEqualTo(1002L);
        assertThat(callCaches.get(1).getScId()).isEqualTo(1001L);
    }

    @Test
    void whenRemoveByUserIdAndCaller_thenSuccess() {
        final long actual = repository.removeByUserIdAndCaller(5L, 501L);
        assertThat(actual).isEqualTo(6);
    }

    @Test
    void whenRemoveByUserIdAndCaller_thenEmptyResult() {
        final long actual = repository.removeByUserIdAndCaller(10L, 0);
        assertThat(actual).isZero();
    }

    @Test
    void whenNullUserId_thenException() {
        final ServiceCallCache nullUserId =
                new ServiceCallCache(1000L, 1000L, 1000L, "new", ObjectType.INCIDENT
                        , "test", "test", null, 0, null, 0);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> repository.saveAndFlush(nullUserId));
    }

    @Test
    void whenNullObjectType_thenException() {
        final ServiceCallCache nullObjectType =
                new ServiceCallCache(1000L, 1000L, 1000L, "new", null, "test"
                        , "test", null, 0, 100L, 0);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> repository.saveAndFlush(nullObjectType));
    }

    @Test
    void whenNullSummary_thenException() {
        final ServiceCallCache nullSummary =
                new ServiceCallCache(1000L, 1000L, 1000L, "new", ObjectType.WORK_ORDER
                        , null, "test", null, 0, 100L, 0);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> repository.saveAndFlush(nullSummary));
    }

    @Test
    void whenEmptySummary_thenException() {
        final ServiceCallCache emptySummary =
                new ServiceCallCache(1000L, 1000L, 1000L, "new", ObjectType.SERVICE_REQUEST
                        , "", "test", null, 0, 100L, 0);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> repository.saveAndFlush(emptySummary));
    }
}