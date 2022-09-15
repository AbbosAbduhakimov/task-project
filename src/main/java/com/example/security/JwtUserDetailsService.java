package com.example.security;


import com.example.exception.ProjectBadRequestException;
import com.example.model.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new ProjectBadRequestException("User with username: " + username + " not found");
        }

        CustomJwtUser customJwtUser = JwtUserFactory.create(user);
        log.info("loadUserByUsername - user with username: {} successfully loaded", username);
        return customJwtUser;
    }
}
