package ua.omniway.models.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApprovalVoteNotification extends NotificationRequest {
    @NotNull(message = "property can not be null")
    private ApprovalVoteEvent event;
}
