package com.wtacademyplatform.backend.dto;

import com.wtacademyplatform.backend.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

}