package ua.omniway.models.converters;

import ua.omniway.models.MessagingDirection;

import javax.persistence.AttributeConverter;

public class InteractionDirectionConverter implements AttributeConverter<MessagingDirection, String> {

    @Override
    public String convertToDatabaseColumn(MessagingDirection messagingDirection) {
        return messagingDirection == null ? null : messagingDirection.getValue();
    }

    @Override
    public MessagingDirection convertToEntityAttribute(String dbData) {
        return dbData == null ? null : MessagingDirection.getByValue(dbData);
    }
}
