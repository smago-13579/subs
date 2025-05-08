package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    @NotBlank(message = "The username must not be empty.")
    private String username;
    @NotBlank(message = "The first name must not be empty.")
    private String firstName;
    @NotBlank(message = "The last name must not be empty.")
    private String lastName;
    private boolean active;

    public UserDto() {}
}
