package com.wtacademyplatform.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveLendingDto {
    private long itemId;

    private long adminId;

    private long lendingUserId;
}
