package com.wtacademyplatform.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveItemDto {
	private boolean available;
	
	private String itemCondition;
	
	private long bookId;
}
