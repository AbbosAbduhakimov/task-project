package com.example.controller;

import com.example.dto.auth.ActivationResultDTO;
import com.example.dto.auth.SignInDTO;
import com.example.dto.auth.SignUpDTO;
import com.example.service.ActivationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ActivationUserService activationUserService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> singUp(@RequestBody @Valid SignUpDTO signUpDTO) {
        return ResponseEntity.ok(activationUserService.signUp(signUpDTO));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ActivationResultDTO> signIn(@RequestBody @Valid SignInDTO signInDTO) {
        return ResponseEntity.ok(activationUserService.signIn(signInDTO));
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verification(@PathVariable("token") String token) {
        return ResponseEntity.ok(activationUserService.verification(token));
    }
}
