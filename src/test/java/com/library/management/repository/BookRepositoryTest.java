package com.library.management.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.library.management.model.Book;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testSaveBook() {
        Book book = new Book(null, "Spring Boot Guide", "Tara Chaurasiya", "Technology", "AVAILABLE");
        Book savedBook = bookRepository.save(book);

        assertNotNull(savedBook.getId());
        assertEquals("Spring Boot Guide", savedBook.getTitle());
    }

    @Test
    void testFindBookById() {
        Book book = new Book(null, "Java Basics", "Sumit Yadav", "Programming", "AVAILABLE");
        Book savedBook = bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Java Basics", foundBook.get().getTitle());
    }

    @Test
    void testFindAllBooks() {
        Book book1 = new Book(null, "Book 1", "Author 1", "Genre 1", "AVAILABLE");
        Book book2 = new Book(null, "Book 2", "Author 2", "Genre 2", "NOT_AVAILABLE");

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book(null, "Test Book", "Test Author", "Test Genre", "AVAILABLE");
        Book savedBook = bookRepository.save(book);

        bookRepository.deleteById(savedBook.getId());

        Optional<Book> deletedBook = bookRepository.findById(savedBook.getId());
        assertFalse(deletedBook.isPresent());
    }
}
