package com.library.management.repository;

import com.library.management.model.Book;
import com.library.management.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingOrAuthorContainingOrIsbnContaining(String title, String author, String isbn);
    List<Book> findByCategoryName(String categoryName);
    
    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0")
    List<Book> findAvailableBooks();
}
