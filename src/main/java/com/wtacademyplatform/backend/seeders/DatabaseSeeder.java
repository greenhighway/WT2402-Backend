package com.wtacademyplatform.backend.seeders;

import java.time.Instant;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wtacademyplatform.backend.entities.User;
import com.wtacademyplatform.backend.enums.Role;
import com.wtacademyplatform.backend.repositories.IUserRepository;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(PasswordEncoder passwordEncoder, IUserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!hasAlreadySeeded()) {
            seedDatabase();
        }
    }

    private boolean hasAlreadySeeded() {
        return this.userRepository.count() > 0;
    }

    private void seedDatabase() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("test@gmail.com");
        user.setPassword(passwordEncoder.encode("welkom01"));
        user.setRole(Role.ADMIN);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        this.userRepository.save(user);
    }
}
