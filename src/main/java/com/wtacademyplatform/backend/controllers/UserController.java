package com.wtacademyplatform.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wtacademyplatform.backend.dto.ResponseDto;
import com.wtacademyplatform.backend.dto.SaveUserDto;
import com.wtacademyplatform.backend.dto.UserDto;
import com.wtacademyplatform.backend.entities.User;
import com.wtacademyplatform.backend.services.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDto> findAllUsers() {
        return this.userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> findUserById(@PathVariable("id") long id) {
        return this.userService.findUserById(id);
    }

    @PostMapping("/create")
    public ResponseDto create(@RequestBody SaveUserDto saveUserDto) {
        return this.userService.create(saveUserDto);
    }

    @PutMapping("/update/{id}")
    public ResponseDto update(@PathVariable("id") long id, @RequestBody SaveUserDto saveUserDto) {
        return this.userService.update(id, saveUserDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable("id") long id) {
        return this.userService.delete(id);
    }

}
