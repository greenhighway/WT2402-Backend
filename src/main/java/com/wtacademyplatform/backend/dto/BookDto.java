package com.wtacademyplatform.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
	private long id;

	private String isbn;

	private String title;

	private String author;

	private boolean available;

	private int publicationYear;

	private int itemCount;
}
