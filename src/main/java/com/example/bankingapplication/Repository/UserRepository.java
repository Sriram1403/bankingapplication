package com.example.bankingapplication.Repository;

import com.example.bankingapplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String Username);
    Optional<User> findById(Long id);

}
