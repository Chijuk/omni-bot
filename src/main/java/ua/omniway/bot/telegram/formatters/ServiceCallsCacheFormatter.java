package ua.omniway.bot.telegram.formatters;

import com.google.common.base.Splitter;
import com.google.common.xml.XmlEscapers;
import org.springframework.stereotype.Component;
import ua.omniway.bot.telegram.handlers.OpenObjectCallbackEnums;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.ServiceCallCache;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallsCacheService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class ServiceCallsCacheFormatter implements GeneralFormatter<ServiceCallCache> {
    private Map<String, String> workItemStatusLocalization;
    private Map<String, String> categoryLocalization;

    private void initLocalizationMeta(String language) {
        this.workItemStatusLocalization = Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(L10n.getString("omnitracker.meta.processData.state", language));
        this.categoryLocalization = Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(L10n.getString("omnitracker.meta.objectTypes.names", language));
    }

    @Override
    public String summary(ServiceCallCache object, DbUser dbUser) {
        return "Message is absent :(";
    }

    @Override
    public String pages(List<ServiceCallCache> list, DbUser dbUser) {
        final String language = dbUser.getSetting().getLanguage();
        StringBuilder stringBuilder = new StringBuilder();
        list.sort((sc1, sc2) -> (int) (sc2.getScId() - sc1.getScId()));         // sort by WorkItemID

        if (list.size() > ServiceCallsCacheService.SC_ON_PAGE) {
            list = list.subList(0, ServiceCallsCacheService.SC_ON_PAGE);  // get sublist if list is initial
        }
        initLocalizationMeta(language);

        for (ServiceCallCache cache : list) {
            stringBuilder
                    .append(L10n.getString("commands.processData.element.header", language));
            if (cache.getCategory() != null) {
                stringBuilder.append(categoryLocalization.get(cache.getCategory().getAlias()));
            }
            stringBuilder.append(" ").append(cache.getScId())
                    .append("\n")
                    .append(L10n.getString("commands.processData.element.state", language))
                    .append(workItemStatusLocalization.get(cache.getState()))
                    .append("\n")
                    .append(L10n.getString("commands.processData.element.summary", language));
            String summary = cache.getSummary();
            if (summary.length() > 254) summary = summary.substring(0, 254) + " ...";   // trim
            summary = XmlEscapers.xmlAttributeEscaper().escape(summary);                // escapes original SC summary
            stringBuilder.append(summary)
                    .append("\n")
                    .append(L10n.getString("commands.processData.element.deadline", language));
            if (cache.getDeadline() != null) {
                stringBuilder.append(cache.getDeadline().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            }
            String handler = OpenObjectCallbackEnums.getByObjectType(cache.getCategory()).getHandler();
            stringBuilder.append("\n")
                    .append(L10n.getString("commands.serviceCalls.element.open", language))
                    .append(handler).append("_").append(cache.getOtUniqueId())                  // text: /osr_475646
                    .append("\n").append("\n");
        }
        return stringBuilder.toString();
    }
}
