package com.example.dto.user;

import com.example.enums.Role;
import com.example.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "username can't be null or empty")
    private String username;

    @NotNull
    @NotBlank(message = "password can't be null or empty")
    private String password;

    @NotBlank(message = "email can't be null or empty")
    @Email
    private String email;

    private Status status;

    @JsonIgnore
    private Role role;
}
