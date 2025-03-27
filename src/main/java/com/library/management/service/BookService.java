package com.library.management.service;

import java.util.List;
import java.util.Optional;

import com.library.management.dto.BookRequestDTO;
import com.library.management.dto.BookResponseDTO;
import com.library.management.model.Book;

public interface BookService {
	public BookResponseDTO addBook(BookRequestDTO bookRequest) ;

	public BookResponseDTO getBookById(Long id);

	public List<BookResponseDTO> getAllBooks();

	public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequest);

	public void deleteBook(Long id);


}
