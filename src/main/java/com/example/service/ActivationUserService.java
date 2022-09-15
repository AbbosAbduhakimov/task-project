package com.example.service;

import com.example.dto.auth.ActivationResultDTO;
import com.example.dto.auth.SignInDTO;
import com.example.dto.auth.SignUpDTO;
import com.example.enums.Role;
import com.example.enums.Status;
import com.example.exception.EmailBadRequestException;
import com.example.exception.JwtAuthenticationException;
import com.example.exception.ProjectBadRequestException;
import com.example.exception.ProjectNotFoundException;
import com.example.model.Company;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivationUserService {


    private final UserRepository userRepository;

    private final CompanyService companyService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final MailSenderService mailSenderService;

    @Value("${mail.message.subject}")
    private String messageSubject;

    @Value("${mail.message.content}")
    private String content;

    @Value("${auth.verificationlink}")
    private String link;


    public String signUp(SignUpDTO signUpDTO) {

        if (userRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            throw new ProjectBadRequestException("User by given email " + signUpDTO.getUsername() + " already exists");
        }

        User user = new User();
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setEmail(signUpDTO.getEmail());
        user.setRole(Role.OWNER);

        Company company = new Company();
        company.setName(signUpDTO.getCompanyName());
        company.setAddress(signUpDTO.getCompanyAddress());
        company.setZipCode(signUpDTO.getCompanyZipCode());
        company.setUsers(Collections.singleton(user));
        Long company1 = companyService.createCompany(company);
        user.setCompanyId(company1);
        userRepository.save(user);
        if (sendMessageToEmail(user)) {
            throw new EmailBadRequestException("Email sending failed");
        }

        return "Failed in send message";
    }

    public String verification(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new JwtAuthenticationException("Token invalid");
        }
        String email = jwtTokenProvider.getUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ProjectNotFoundException("User with this email not found"));
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        return "Successful verified";
    }

    public ActivationResultDTO signIn(SignInDTO signInDTO) {
        User signInUser = userRepository.findByEmail(signInDTO.getEmail()).orElseThrow(() -> new ProjectNotFoundException("User with this email not found"));

        if (!passwordEncoder.matches(signInDTO.getPassword(), signInUser.getPassword())) {
            throw new ProjectBadRequestException("Email or password error");
        }
        String token = jwtTokenProvider.createToken(signInUser.getEmail(), Collections.singletonList(signInUser.getRole()));
        return new ActivationResultDTO(signInDTO.getEmail(), token);
    }

    public boolean sendMessageToEmail(User user) {
        String token = jwtTokenProvider.createToken(user.getEmail(), Collections.singletonList(user.getRole()));
        content = link + token;

        try {
            mailSenderService.send(user.getEmail(), messageSubject, content);
        } catch (Exception e) {
            userRepository.delete(user);
            return false;
        }
        return true;
    }
}
