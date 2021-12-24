package ua.omniway.models.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerFeedbackNotification extends NotificationRequest {
    @NotEmpty(message = "property can not be null or empty")
    private List<@Valid FeedbackOption> options;
}
