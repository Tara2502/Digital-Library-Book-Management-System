package com.library.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.dto.BookRequestDTO;
import com.library.management.dto.BookResponseDTO;
import com.library.management.exception.BookNotFoundException;
import com.library.management.exception.GlobalExceptionHandler;
import com.library.management.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private BookResponseDTO bookResponse;
    private BookRequestDTO bookRequest;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new GlobalExceptionHandler()) // ✅ Exception handling added
                .build();

        bookRequest = new BookRequestDTO("Spring Boot Guide", "Test Case", "Technology", "AVAILABLE");
        bookResponse = new BookResponseDTO(1L, "Spring Boot Guide", "Test Case", "Technology", "AVAILABLE");
    }

    @Test
    void testAddBook_Success() throws Exception {
        when(bookService.addBook(any(BookRequestDTO.class))).thenReturn(bookResponse);

        mockMvc.perform(post("/api/books/addBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book added successfully"))
                .andExpect(jsonPath("$.data.title").value("Spring Boot Guide"));

        verify(bookService, times(1)).addBook(any(BookRequestDTO.class));
    }



    @Test
    void testGetAllBooks_Success() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(bookResponse));

        mockMvc.perform(get("/api/books/getAllBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Books retrieved successfully"))
                .andExpect(jsonPath("$.data[0].title").value("Spring Boot Guide"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById_Success() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(bookResponse);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book found"))
                .andExpect(jsonPath("$.data.title").value("Spring Boot Guide"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(2L)).thenThrow(new BookNotFoundException("Book not found with id: 2"));

        mockMvc.perform(get("/api/books/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(true)) // ✅ Fixed incorrect JSON path
                .andExpect(jsonPath("$.message").value("Book not found with id: 2"))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(bookService, times(1)).getBookById(2L);
    }

    @Test
    void testUpdateBook_Success() throws Exception {
        when(bookService.updateBook(eq(1L), any(BookRequestDTO.class))).thenReturn(bookResponse);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book updated successfully"))
                .andExpect(jsonPath("$.data.title").value("Spring Boot Guide"));

        verify(bookService, times(1)).updateBook(eq(1L), any(BookRequestDTO.class));
    }

    @Test
    void testDeleteBook_Success() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book deleted successfully"));

        verify(bookService, times(1)).deleteBook(1L);
    }
}
