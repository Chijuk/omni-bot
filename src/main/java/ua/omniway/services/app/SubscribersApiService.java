package ua.omniway.services.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.omniway.client.ot.soap.service.CommonApiService;
import ua.omniway.client.ot.soap.types.LongIntVal;
import ua.omniway.client.ot.soap.types.StringVal;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.ChatBotSettingsDao;
import ua.omniway.dao.ot.ContactDao;
import ua.omniway.dao.ot.SubscribersApi;
import ua.omniway.models.ot.ChatBotSetting;
import ua.omniway.models.ot.Contact;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscribersApiService implements SubscribersApi {
    private static final String DISPATCHER_SCRIPT = "Api.Subscribers.Dispatcher";
    private static final String PASSWORD = "password";
    private static final String SUBSCRIBER_ID = "subscriberId";
    private static final String LOCALIZATION = "localization";

    private final CommonApiService commonApiService;
    private final ContactDao contactDao;
    private final ChatBotSettingsDao settingsDao;

    @Autowired
    public SubscribersApiService(CommonApiService commonApiService, ContactDao contactDao, ChatBotSettingsDao settingsDao) {
        this.commonApiService = commonApiService;
        this.contactDao = contactDao;
        this.settingsDao = settingsDao;
    }

    /**
     * Try to authorize telegram user in from OMNITRACKER using password string
     *
     * @param password String password from Telegram user
     * @return subscriber unique id. May be null if not found
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    @Override
    public Long authorizeWithPassword(String password) throws OmnitrackerException {
        if (StringUtils.isEmpty(password)) throw new IllegalArgumentException("password is null or empty");
        List<Object> params = new ArrayList<>();
        params.add(new StringVal(PASSWORD, password));
        return (Long) commonApiService.execute(DISPATCHER_SCRIPT, "AuthorizeWithPassword", params);
    }

    /**
     * Deactivate subscriber in OMNITRACKER.
     *
     * @param subscriberId subscriber unique id in OMNITRACKER
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    @Override
    @Async("asyncOtExecutor")
    public void deactivate(long subscriberId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(SUBSCRIBER_ID, subscriberId));
        commonApiService.execute(DISPATCHER_SCRIPT, "Deactivate", params);
    }

    /**
     * Set localization value for subscriber in OMNITRACKER
     *
     * @param subscriberId subscriber unique id in OMNITRACKER
     * @param localization localization in ISO 639-1 format
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    @Override
    public void setLocalization(long subscriberId, String localization) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(SUBSCRIBER_ID, subscriberId));
        params.add(new StringVal(LOCALIZATION, localization));
        commonApiService.execute(DISPATCHER_SCRIPT, "SetLocalization", params);
    }

    /**
     * @param objectUniqueId object unique id in OMNITRACKER
     * @return chat bot setting object or null
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    public ChatBotSetting getChatBotSetting(long objectUniqueId) throws OmnitrackerException {
        return settingsDao.findById(objectUniqueId);
    }

    /**
     * @param objectUniqueId object unique id in OMNITRACKER
     * @return contact object or null
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    public Contact getContact(long objectUniqueId) throws OmnitrackerException {
        return contactDao.findById(objectUniqueId);
    }
}
