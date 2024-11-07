package ait.cohort46.book.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class AuthorDto {
	private String name;
	private LocalDate birthDate;
}
