package ait.cohort46.book.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class BookDto {
	private String isbn;
	private String title;
	private Set<AuthorDto> authors;
	private String publisher;
}
