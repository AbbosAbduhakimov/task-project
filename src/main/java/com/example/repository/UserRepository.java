package com.example.repository;


import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndCompanyId(Long userId, Long companyId);

    Optional<List<User>> findAllByCompanyId(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

}
