package com.wtacademyplatform.backend.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LendingDto {
    private long id;

    private UserDto adminDto;

    private UserDto lendingUserDto;

    private ItemDto itemDto;

    private Instant startDate;

    private Instant endDate;
}
