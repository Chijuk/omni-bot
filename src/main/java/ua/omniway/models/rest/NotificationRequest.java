package ua.omniway.models.rest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * JSON serializable object. Used by external system to notify user about some action
 */
@Data
public class NotificationRequest {
    private long userUniqueId;
    private long objectUniqueId;
    @NotEmpty(message = "property can not be null or empty")
    private String messageBody;
}