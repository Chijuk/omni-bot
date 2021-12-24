package ua.omniway.models.converters;

import ua.omniway.models.db.ActiveAction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DbUserActiveActionConverter implements AttributeConverter<ActiveAction, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ActiveAction activeAction) {
        return activeAction.getId();
    }

    @Override
    public ActiveAction convertToEntityAttribute(Integer integer) {
        return ActiveAction.fromValue(integer);
    }
}
