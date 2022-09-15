package com.example.security;

import com.example.enums.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
public class CustomJwtUser implements UserDetails {

    private final String username;
    private final String password;
    private final String email;
    private final Status status;
    private final boolean enabled;

    private  final Collection<? extends GrantedAuthority> authorities;

    public CustomJwtUser(String username, String password, String email, Status status, boolean enabled, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.enabled = enabled;
        this.authorities = grantedAuthorities;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
