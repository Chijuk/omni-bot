package ua.omniway.models.converters;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(source = "uniqueId", target = "otUniqueId")
@Mapping(source = "id", target = "scId")
@Mapping(source = "objectType", target = "category")
@Mapping(source = "description", target = "information")
@Mapping(source = "caller.uniqueId", target = "caller")
@Mapping(target = "userId", ignore = true)
@Mapping(target = "messageId", ignore = true)
public @interface ToProcessData {
}
