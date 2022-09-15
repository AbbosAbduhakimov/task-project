package com.example.config;

import com.example.enums.Role;
import com.example.security.JwtTokenFilter;
import com.example.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration{


    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers(EndPoints.SIGN_IN_ENDPOINT).permitAll()
//                .antMatchers(EndPoints.SIGN_UP_ENDPOINT).permitAll()
//                .antMatchers(EndPoints.VERIFICATION_ENDPOINT).permitAll()
//                .antMatchers(EndPoints.ACTIVATION_ENDPOINT).hasRole(Role.OWNER.toString())
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/editcompany/**").hasRole(Role.OWNER.toString())
                .antMatchers("/api/v1/users/create").hasRole(Role.OWNER.toString())
                .antMatchers("/api/v1/users/get").hasRole(Role.OWNER.toString())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

