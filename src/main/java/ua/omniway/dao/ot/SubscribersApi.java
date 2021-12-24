package ua.omniway.dao.ot;

import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.models.ot.ChatBotSetting;
import ua.omniway.models.ot.Contact;

public interface SubscribersApi {
    Long authorizeWithPassword(String password) throws OmnitrackerException;

    void deactivate(long subscriberId) throws OmnitrackerException;

    void setLocalization(long subscriberId, String localization) throws OmnitrackerException;

    ChatBotSetting getChatBotSetting(long objectUniqueId) throws OmnitrackerException;

    Contact getContact(long objectUniqueId) throws OmnitrackerException;
}
