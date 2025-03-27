package com.library.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.library.management.dto.BookRequestDTO;
import com.library.management.dto.BookResponseDTO;
import com.library.management.exception.BookNotFoundException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookServiceImpl bookService;

	private Book book;
	private BookRequestDTO bookRequest;

	@BeforeEach
	void setup() {
		book = new Book(1L, "Spring Boot Guide", "John Doe", "Technology", "AVAILABLE");
		bookRequest = new BookRequestDTO("Spring Boot Guide", "John Doe", "Technology", "AVAILABLE");
	}


	@Test
	void testAddBook_Success() {
		when(bookRepository.save(any(Book.class))).thenReturn(book);

		BookResponseDTO response = bookService.addBook(bookRequest);

		assertNotNull(response);
		assertEquals("Spring Boot Guide", response.getTitle());
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	void testGetBookById_Found() {
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

		BookResponseDTO response = bookService.getBookById(1L);

		assertNotNull(response);
		assertEquals("Spring Boot Guide", response.getTitle());
		verify(bookRepository, times(1)).findById(1L);
	}

	@Test
	void testGetBookById_NotFound() {
		when(bookRepository.findById(2L)).thenReturn(Optional.empty());

		BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.getBookById(2L));
		assertEquals("Book not found with ID: 2", exception.getMessage());
	}

	@Test
	void testGetAllBooks() {
		when(bookRepository.findAll()).thenReturn(List.of(book));

		List<BookResponseDTO> books = bookService.getAllBooks();

		assertFalse(books.isEmpty());
		assertEquals(1, books.size());
		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testUpdateBook_Success() {
		BookRequestDTO updateRequest = new BookRequestDTO("Updated Title", "Updated Author", "Updated Genre", "NOT_AVAILABLE");
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(new Book(1L, "Updated Title", "Updated Author", "Updated Genre", "NOT_AVAILABLE"));

		BookResponseDTO updatedBook = bookService.updateBook(1L, updateRequest);

		assertNotNull(updatedBook);
		assertEquals("Updated Title", updatedBook.getTitle());
		assertEquals("Updated Author", updatedBook.getAuthor());
		verify(bookRepository, times(1)).save(any(Book.class));
	}


	@Test
	void testUpdateBook_NotFound() {
		when(bookRepository.findById(2L)).thenReturn(Optional.empty());

		BookRequestDTO updateRequest = new BookRequestDTO("Title", "Author", "Genre", "AVAILABLE");

		BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.updateBook(2L, updateRequest));
		assertEquals("Book not found with ID: 2", exception.getMessage());
	}


	@Test
	void testDeleteBook_Success() {
		when(bookRepository.existsById(1L)).thenReturn(true);
		doNothing().when(bookRepository).deleteById(1L);

		assertDoesNotThrow(() -> bookService.deleteBook(1L));

		verify(bookRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteBook_NotFound() {
		when(bookRepository.existsById(2L)).thenReturn(false);

		BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(2L));
		assertEquals("Book not found with ID: 2", exception.getMessage());
	}
}
