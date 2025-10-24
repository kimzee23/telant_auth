package org.example.enumtalentapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignupRequest {
    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;

    // getters/setters
}
