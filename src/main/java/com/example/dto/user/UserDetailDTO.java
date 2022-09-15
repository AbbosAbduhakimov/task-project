package com.example.dto.user;

import com.example.enums.Role;
import com.example.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDTO {

    private Long id;

    private String username;

    private String email;

    private Status status;

    private Role role;

    private Long companyId;
}
