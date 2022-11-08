package com.example.service;

import com.example.aop.logging.Loggable;
import com.example.dto.user.UserDTO;
import com.example.dto.user.UserDetailDTO;
import com.example.enums.Role;
import com.example.enums.Status;
import com.example.exception.ProjectBadRequestException;
import com.example.exception.ProjectNotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    @Loggable
    public UserDetailDTO get(Long companyId, Long id) {
        User user = userRepository.findByIdAndCompanyId(id, companyId).orElseThrow(() -> new ProjectNotFoundException("Employee by given id and company id not found"));
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(user.getId());
        userDetailDTO.setUsername(user.getUsername());
        userDetailDTO.setEmail(user.getEmail());
        userDetailDTO.setStatus(user.getStatus());
        userDetailDTO.setCompanyId(user.getCompanyId());
        userDetailDTO.setRole(user.getRole());
        return userDetailDTO;
    }

    @Transactional
    @Loggable
    public void create(Long companyId, UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new ProjectBadRequestException("User by given username " + userDTO.getUsername() + " already exists");
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ProjectBadRequestException("User by given email " + userDTO.getUsername() + " already exists");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setCompanyId(companyId);
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.EMPLOYEE);
        log.info("user create {} ", user);
        userRepository.save(user);
    }

    @Transactional
    @Loggable
    public void update(Long companyId, Long id, UserDTO userDTO) {
        User user = userRepository.findByIdAndCompanyId(id, companyId).orElseThrow(() -> new ProjectNotFoundException("Employee by given id and company id not found"));

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        log.info("user update {}", user);
        userRepository.save(user);
    }


    @Transactional
    @Loggable
    public void delete(Long companyId, Long id) {
        User user = userRepository.findByIdAndCompanyId(id, companyId).orElseThrow(() -> new ProjectNotFoundException("Employee by given id and company id not found"));
        user.setStatus(Status.IN_ACTIVE);
        log.info("user delete {}", user);
        userRepository.save(user);
    }


    @Transactional
    @Loggable
    public List<UserDetailDTO> getAll(Long companyId) {
        List<User> users = userRepository.findAllByCompanyId(companyId).orElseThrow(() -> new ProjectNotFoundException("Employee by given id and company id not found"));
        List<UserDetailDTO> userDetailDTOS = new ArrayList<>();
        for (User user : users) {
            userDetailDTOS.add(convertEntityToDto(user));
        }
        return userDetailDTOS;
    }


    @Transactional
    @Loggable
    public User findByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new ProjectNotFoundException("Employee with this username not found "+ username));
    }


    public static UserDetailDTO convertEntityToDto(User user) {
        if (user != null) {
            UserDetailDTO userDetailDTOS = new UserDetailDTO();
            userDetailDTOS.setId(user.getId());
            userDetailDTOS.setUsername(user.getUsername());
            userDetailDTOS.setCompanyId(user.getCompany().getId());
            userDetailDTOS.setStatus(user.getStatus());
            userDetailDTOS.setEmail(user.getEmail());
            return userDetailDTOS;
        }
        return null;
    }


}
