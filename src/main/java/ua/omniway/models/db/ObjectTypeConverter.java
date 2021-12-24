package ua.omniway.models.db;

import ua.omniway.models.ot.ObjectType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ObjectTypeConverter implements AttributeConverter<ObjectType, String> {
    @Override
    public String convertToDatabaseColumn(ObjectType attribute) {
        return attribute.getAlias();
    }

    @Override
    public ObjectType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ObjectType.getByAlias(dbData);
    }
}
