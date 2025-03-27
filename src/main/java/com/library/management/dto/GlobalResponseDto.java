package com.library.management.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalResponseDto {
	private boolean error;
	private String message;
	private Object data;
}
