package com.library.management.dto;

import com.library.management.model.AvailabilityStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {
    @NotBlank(message = "Title cannot be empty")
    private String title;
    
    @NotBlank(message = "Author cannot be empty")
    private String author;
    
    @NotBlank(message = "Genre cannot be empty")
    private String genre;
    
    @NotBlank(message = "Availability status is necessary")
    private String availability;
}