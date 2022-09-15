package com.example.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpDTO {

    @NotBlank(message = "username name can't be null or empty")
    private String username;

    @NotBlank(message = "password name can't be null or empty")
    private String password;

    @NotBlank(message = "email name can't be null or empty")
    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "companyName name can't be null or empty")
    private String companyName;

    @NotBlank(message = "companyAddress name can't be null or empty")
    private String companyAddress;

    @NotBlank(message = "companyZipCode name can't be null or empty")
    private String companyZipCode;

}
