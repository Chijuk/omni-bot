package ua.omniway.bot.telegram.formatters;

import com.google.common.base.Splitter;
import org.springframework.stereotype.Component;
import ua.omniway.services.app.L10n;

@Component
public class ChangeFormatter extends ProcessDataFormatter {
    @Override
    protected void initLocalizationMeta(String language) {
        setStateLocalization(Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(L10n.getString("omnitracker.meta.changeRequest.state", language)));
        setCategoryLocalization(Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(L10n.getString("omnitracker.meta.objectTypes.names", language)));
    }
}
