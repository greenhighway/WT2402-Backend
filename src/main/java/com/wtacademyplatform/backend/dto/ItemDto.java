package com.wtacademyplatform.backend.dto;

import com.wtacademyplatform.backend.entities.Book;
import com.wtacademyplatform.backend.enums.Condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private long id;

    private boolean available;

    private Condition itemCondition;

    private String wtKey;

    private Book book;
}
