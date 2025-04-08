package com.bookstore.bookstore.service;

import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Default to read-only for queries
public class BookService {

    private final BookRepository bookRepository;

    @Transactional // Required for write operations
    public Book saveBook(Book book) {
        System.out.println("Saving book: " + book); // Debug log
        Book savedBook = bookRepository.save(book);
        System.out.println("Saved book: " + savedBook); // Debug log
        return savedBook;
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        System.out.println("Retrieved books: " + books); // Debug log
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional // Required for write operations
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setCategory(updatedBook.getCategory());
                    book.setPrice(updatedBook.getPrice());
                    book.setRating(updatedBook.getRating());
                    book.setPublishedDate(updatedBook.getPublishedDate());
                    System.out.println("Updating book: " + book); // Debug log
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Transactional // Required for write operations
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            System.out.println("Deleted book with id: " + id); // Debug log
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    public List<Book> filterBooks(String author, String category, Double rating) {
        return bookRepository.findByFilters(author, category, rating);
    }

    public List<Book> searchByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Page<Book> getBooksPaginatedAndSorted(int page, int size, String sortBy) {
        return bookRepository.findAll(
                PageRequest.of(page, size, Sort.by(sortBy).ascending())
        );
    }
}