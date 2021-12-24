package ua.omniway.models.converters;

import org.junit.jupiter.api.Test;
import ua.omniway.models.db.ServiceCallCache;
import ua.omniway.models.ot.Contact;
import ua.omniway.models.ot.ObjectType;
import ua.omniway.models.ot.ProcessData;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessDataServiceCallCacheMapperTest {

    @Test
    void whenConvertProcessDataToServiceCallCache_thenSuccess() {
        ProcessData processData = new ProcessData();
        processData.setUniqueId(12324L);
        processData
                .setId(1345L)
                .setSummary("summary")
                .setDescription("description")
                .setDeadline(LocalDateTime.now())
                .setState("New")
                .setObjectType(ObjectType.SERVICE_REQUEST)
                .setCaller(new Contact(345L))
                .setDateClosed(LocalDateTime.now());
        ServiceCallCache serviceCallCache = ProcessDataServiceCallCacheMapper.INSTANCE.processDataToServiceCallCache(processData);
        assertThat(serviceCallCache.getOtUniqueId()).isEqualTo(processData.getUniqueId());
        assertThat(serviceCallCache.getScId()).isEqualTo(processData.getId());
        assertThat(serviceCallCache.getSummary()).isEqualTo(processData.getSummary());
        assertThat(serviceCallCache.getInformation()).isEqualTo(processData.getDescription());
        assertThat(serviceCallCache.getDeadline()).isEqualTo(processData.getDeadline());
        assertThat(serviceCallCache.getCategory()).isEqualTo(processData.getObjectType());
        assertThat(serviceCallCache.getState()).isEqualTo(processData.getState());
        assertThat(serviceCallCache.getCaller()).isEqualTo(processData.getCaller().getUniqueId());
        assertThat(serviceCallCache.getUserId()).isNull();
        assertThat(serviceCallCache.getMessageId()).isZero();
    }
}