package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionDto {
    private Long id;
    @NotBlank(message = "The media service must not be empty")
    private String mediaService;

    public SubscriptionDto() {}

    public SubscriptionDto(String mediaService) {
        this.mediaService = mediaService;
    }
}
