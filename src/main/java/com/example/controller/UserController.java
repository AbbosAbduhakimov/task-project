package com.example.controller;

import com.example.dto.user.UserDTO;
import com.example.dto.user.UserDetailDTO;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{companyId}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDTO> get(@PathVariable(value = "companyId") Long companyId, @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(userService.get(companyId, id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable(value = "companyId") Long companyId, @RequestBody @Valid UserDTO userDTO) {
        userService.create(companyId, userDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable(value = "companyId") Long companyId, @PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.update(companyId, id, userDTO);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "companyId") Long companyId, @PathVariable(value = "id") Long id) {
        userService.delete(companyId, id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@PathVariable(value = "companyId") Long companyId) {
        List<UserDetailDTO> response = userService.getAll(companyId);
        return ResponseEntity.ok().build();
    }
}
