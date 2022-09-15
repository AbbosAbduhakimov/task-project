package com.example.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignInDTO {
    @NotBlank(message = ("email can't be null or empty"))
    private String email;

    @NotBlank(message = ("password can't be null or empty"))
    private String password;

}
