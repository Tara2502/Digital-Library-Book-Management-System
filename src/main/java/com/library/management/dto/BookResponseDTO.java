package com.library.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
	@JsonIgnore
	private Long id;
	private String title;
	private String author;
	private String genre;
	private String availability;
}
