package com.library.management.controller;

import java.util.List;

import com.library.management.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.dto.BookRequestDTO;
import com.library.management.dto.BookResponseDTO;
import com.library.management.dto.GlobalResponseDto;
import com.library.management.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

	@Autowired
	private BookService bookService;

	// API to add book
	@PostMapping("/addBook")
	public ResponseEntity<GlobalResponseDto> addBook(@Valid @RequestBody BookRequestDTO bookRequest) {
		BookResponseDTO book = bookService.addBook(bookRequest);
		return ResponseEntity.ok(new GlobalResponseDto(false, "Book added successfully", book));
	}

	// API to fetch all the books
	@GetMapping("/getAllBooks")
	public ResponseEntity<GlobalResponseDto> getAllBooks() {
		List<BookResponseDTO> books = bookService.getAllBooks();
		return ResponseEntity.ok(new GlobalResponseDto(false, "Books retrieved successfully", books));
	}

	// API to fetch a book by id
	@GetMapping("/{id}")
	public ResponseEntity<GlobalResponseDto> getBookById(@PathVariable Long id) {
		BookResponseDTO book = bookService.getBookById(id);
		if (book == null) {
			throw new BookNotFoundException("Book not found with id: " + id);
		}
		return ResponseEntity.ok(new GlobalResponseDto(false, "Book found", book));
	}

	// API to update the book
	@PutMapping("/{id}")
	public ResponseEntity<GlobalResponseDto> updateBook(@PathVariable Long id,
			@Valid @RequestBody BookRequestDTO bookRequest) {
		BookResponseDTO updatedBook = bookService.updateBook(id, bookRequest);
		return ResponseEntity.ok(new GlobalResponseDto(false, "Book updated successfully", updatedBook));
	}

	// API to delete a book
	@DeleteMapping("/{id}")
	public ResponseEntity<GlobalResponseDto> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.ok(new GlobalResponseDto(false, "Book deleted successfully", null));
	}

	// API to exit the application
	@PostMapping("/exit")
	public void exitApplication() {
		System.exit(0);
	}
}
