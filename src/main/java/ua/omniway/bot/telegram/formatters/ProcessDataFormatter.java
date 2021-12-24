package ua.omniway.bot.telegram.formatters;

import com.google.common.base.Splitter;
import com.google.common.xml.XmlEscapers;
import org.springframework.stereotype.Component;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.ot.ProcessData;
import ua.omniway.services.app.L10n;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class ProcessDataFormatter implements GeneralFormatter<ProcessData> {
    private Map<String, String> stateLocalization;
    private Map<String, String> categoryLocalization;

    public Map<String, String> getStateLocalization() {
        return stateLocalization;
    }

    public void setStateLocalization(Map<String, String> stateLocalization) {
        this.stateLocalization = stateLocalization;
    }

    public Map<String, String> getCategoryLocalization() {
        return categoryLocalization;
    }

    public void setCategoryLocalization(Map<String, String> categoryLocalization) {
        this.categoryLocalization = categoryLocalization;
    }

    protected void initLocalizationMeta(String language) {
        this.setStateLocalization(Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(L10n.getString("omnitracker.meta.processData.state", language)));
        this.setCategoryLocalization(Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(L10n.getString("omnitracker.meta.objectTypes.names", language)));
    }

    @Override
    public String summary(ProcessData object, DbUser dbUser) {
        final String language = dbUser.getSetting().getLanguage();
        StringBuilder stringBuilder = new StringBuilder();
        this.initLocalizationMeta(language);

        stringBuilder
                .append(L10n.getString("commands.processData.element.header", language));
        if (object.getObjectType() != null) {
            stringBuilder.append(getCategoryLocalization().get(object.getObjectType().getAlias()));
        }
        stringBuilder.append(" ").append(object.getId())
                .append("\n")
                .append(L10n.getString("commands.processData.element.state", language))
                .append(getStateLocalization().get(object.getState()))
                .append("\n")
                .append(L10n.getString("commands.processData.element.summary", language));
        if (object.getSummary().length() > 254) {
            object.setSummary(object.getSummary().substring(0, 254) + " ...");
        }
        stringBuilder.append(XmlEscapers.xmlAttributeEscaper().escape(object.getSummary()))
                .append("\n")
                .append(L10n.getString("commands.processData.element.deadline", language));
        if (object.getDeadline() != null) {
            stringBuilder.append(object.getDeadline().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public String detailed(ProcessData object, DbUser dbUser) {
        StringBuilder stringBuilder = new StringBuilder(this.summary(object, dbUser));
        stringBuilder.append("\n")
                .append(L10n.getString("commands.processData.element.information", dbUser.getSetting().getLanguage()));
        if (object.getDescription() != null) {
            String description = "";
            if (object.getDescription().length() > 600) {
                description = object.getDescription().substring(0, 600) + " ...";
            } else {
                description = object.getDescription();
            }
            stringBuilder.append(XmlEscapers.xmlAttributeEscaper().escape(description));
        }
        return stringBuilder.append("\n").toString();
    }
}
