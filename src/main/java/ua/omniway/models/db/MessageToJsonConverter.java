package ua.omniway.models.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Slf4j
@Converter
public class MessageToJsonConverter implements AttributeConverter<Message, String> {
    @Override
    public String convertToDatabaseColumn(Message message) {
        if (message == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error("Error while writing message json to DB {}", message, e);
        }
        return null;
    }

    @Override
    public Message convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) return null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(dbData, Message.class);
        } catch (JsonProcessingException e) {
            log.error("Error while reading from DB message json {}", dbData, e);
        }
        return null;
    }
}
