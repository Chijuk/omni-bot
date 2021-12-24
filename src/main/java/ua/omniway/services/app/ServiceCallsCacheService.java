package ua.omniway.services.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.converters.ProcessDataServiceCallCacheMapper;
import ua.omniway.models.db.ServiceCallCache;
import ua.omniway.models.ot.ProcessData;
import ua.omniway.repository.ServiceCallsCacheRepository;
import ua.omniway.services.exceptions.ServiceException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServiceCallsCacheService {
    public static final int SC_ON_PAGE = 3;
    private final ServiceCallsCacheRepository callsCacheRepository;
    private final ServiceCallsApiService serviceCallsApi;

    @Autowired
    public ServiceCallsCacheService(ServiceCallsCacheRepository callsCacheRepository, ServiceCallsApiService serviceCallsApi) {
        this.callsCacheRepository = callsCacheRepository;
        this.serviceCallsApi = serviceCallsApi;
    }

    /**
     * Updates cache for userId and caller.
     * <br>
     * If there are already some cache in DB: deletes and insert new one from list.
     *
     * @param userId telegram user id
     * @param caller caller id from OT
     * @param cache  list to insert into DB
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateCallerCache(Long userId, long caller, List<ServiceCallCache> cache) {
        Objects.requireNonNull(userId, "userId can not be null");
        Objects.requireNonNull(cache, "cache can not be null");
        if (cache.isEmpty()) throw new IllegalArgumentException("cache is empty");
        callsCacheRepository.removeByUserIdAndCaller(userId, caller);
        callsCacheRepository.saveAll(cache);
    }

    /**
     * Get cache for some page number.
     * <br>
     * Used for list menu builder
     *
     * @param messageId  messageId from Telegram API
     * @param pageNumber page number
     * @return cache list for pageNumber. May be empty, not null
     * @throws ServiceException general service exception
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ServiceCallCache> getPageData(long messageId, int pageNumber) throws ServiceException {
        List<ServiceCallCache> serviceCalls;
        try {
            serviceCalls = callsCacheRepository.findByMessageIdOrderByScIdDesc(messageId,
                    PageRequest.of(pageNumber - 1, SC_ON_PAGE));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return serviceCalls;
    }

    /**
     * @param callerId user unique id in OMNITRACKER
     * @param userId   Telegram user id
     * @return converted cache list
     * @throws ServiceException general service exception
     */
    public List<ServiceCallCache> getActualServiceCalls(long callerId, Long userId) throws ServiceException {
        final List<ProcessData> processDataObjectList = serviceCallsApi.getProcessDataObjectList(callerId);
        return processDataObjectList.stream()
                .map(ProcessDataServiceCallCacheMapper.INSTANCE::processDataToServiceCallCache)
                .peek(serviceCallCache -> serviceCallCache.setUserId(userId))
                .collect(Collectors.toList());
    }
}
