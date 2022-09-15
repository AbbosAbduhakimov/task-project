package com.example.security;

import com.example.enums.Role;
import com.example.model.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class JwtUserFactory {


    public static CustomJwtUser create(User user) {
        return new CustomJwtUser(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus(),
                true,
                mapToGrantedAuthorities(new ArrayList<>(Collections.singleton(user.getRole())))
        );
    }


    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority("ROLE_"+role.toString())
                ).collect(Collectors.toList());
    }
}
