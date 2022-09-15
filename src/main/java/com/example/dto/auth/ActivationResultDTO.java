package com.example.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class ActivationResultDTO {

    @NotBlank(message = "email name can't be null or empty")
    @Email(message = "invalid email")
    private String email;

    private String token;
}
