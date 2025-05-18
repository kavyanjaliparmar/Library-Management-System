package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.model.BookIssue;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book addBook(Book book);
    List<Book> searchBooks(String query);
    List<Book> findBooksByCategory(String categoryName);
    BookIssue borrowBook(Long bookId);
    BookIssue returnBook(Long issueId);
    List<BookIssue> getMyBooks();
}
