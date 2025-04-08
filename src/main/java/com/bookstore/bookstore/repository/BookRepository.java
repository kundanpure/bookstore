// BookRepository.java
package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByCategoryContainingIgnoreCase(String category);
    List<Book> findByRatingGreaterThanEqual(Double rating); // Changed to more useful query
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Added custom query for filtering with all three parameters
    @Query("SELECT b FROM Book b WHERE " +
            "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
            "(:category IS NULL OR LOWER(b.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
            "(:rating IS NULL OR b.rating >= :rating)")
    List<Book> findByFilters(@Param("author") String author,
                             @Param("category") String category,
                             @Param("rating") Double rating);
}