package ua.omniway.bot.telegram.formatters;

import ua.omniway.models.db.DbUser;

import java.util.List;

public interface GeneralFormatter<T> {

    String summary(T object, DbUser dbUser);

    default String detailed(T object, DbUser dbUser) {
        return summary(object, dbUser);
    }

    default String pages(List<T> list, DbUser dbUser) {
        StringBuilder sb = new StringBuilder();
        list.forEach(t -> sb.append(this.summary(t, dbUser)).append("\n"));
        return sb.toString();
    }
}
