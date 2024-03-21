package com.wtacademyplatform.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wtacademyplatform.backend.dto.ResponseDto;
import com.wtacademyplatform.backend.dto.SaveUserDto;
import com.wtacademyplatform.backend.dto.UserDto;
import com.wtacademyplatform.backend.entities.User;
import com.wtacademyplatform.backend.repositories.IUserRepository;

@Service
public class UserService implements UserDetailsService {
    private final IUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserDto> findAllUsers() {
        List<User> users = this.repo.findAll();
        return users.stream().map(user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole())).collect(Collectors.toList());
    }

    public Optional<User> findUserById(long id) {
        return this.repo.findById(id);
    }

    public Optional<UserDto> findUserDtoById(long id) {
        Optional<User> foundUser = this.repo.findById(id);
        return foundUser.map(User::ToDto);
    }

    public ResponseDto create(SaveUserDto saveUserDto) {
        Optional<User> existingUser = this.repo.findUserByEmail(saveUserDto.getEmail());

        if (existingUser.isPresent()) {
            return new ResponseDto(false, "User with the given e-mail already exists");
        }

        User user = new User();
        user.setFirstName(saveUserDto.getFirstName());
        user.setLastName(saveUserDto.getLastName());
        user.setEmail(saveUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(saveUserDto.getPassword()));
        user.setRole(saveUserDto.getRole());

        return new ResponseDto(true, this.repo.save(user).ToDto());
    }

    public ResponseDto update(long id, SaveUserDto saveUserDto) {
        Optional<User> userOptional = this.findUserById(id);
        if (userOptional.isEmpty()) {
            return new ResponseDto(false, null);
        }

        User user = userOptional.get();

        Optional<User> foundUser = this.repo.findUserByEmail(saveUserDto.getEmail());

        if (foundUser.isPresent() && foundUser.get().getId() != id) {
            return new ResponseDto(false, "User with the given e-mail already exists");
        }

        user.setFirstName(saveUserDto.getFirstName());
        user.setLastName(saveUserDto.getLastName());
        user.setEmail(saveUserDto.getEmail());
        user.setPassword(saveUserDto.getPassword());
        user.setRole(saveUserDto.getRole());

        return new ResponseDto(true, this.repo.save(user).ToDto());
    }
    public ResponseDto delete(long id) {
        Optional<User> userOptional = this.findUserById(id);
        if (userOptional.isEmpty()) {
            return new ResponseDto(false, null);
        }

        this.repo.deleteById(id);

        return new ResponseDto(true, null);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
