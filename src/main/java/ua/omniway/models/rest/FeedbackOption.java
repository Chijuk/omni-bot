package ua.omniway.models.rest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FeedbackOption {
    @NotEmpty(message = "property can not be null or empty")
    private String value;
    @NotEmpty(message = "property can not be null or empty")
    private String callback;
}
