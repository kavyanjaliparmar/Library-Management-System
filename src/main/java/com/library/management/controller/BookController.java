package com.library.management.controller;

import com.library.management.model.Book;
import com.library.management.model.BookIssue;
import com.library.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }

    @GetMapping("/category")
    public List<Book> getBooksByCategory(@RequestParam String categoryName) {
        return bookService.findBooksByCategory(categoryName);
    }

    @PostMapping("/{bookId}/borrow")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.borrowBook(bookId));
    }

    @PostMapping("/return/{issueId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> returnBook(@PathVariable Long issueId) {
        return ResponseEntity.ok(bookService.returnBook(issueId));
    }

    @GetMapping("/mybooks")
    @PreAuthorize("hasRole('STUDENT')")
    public List<BookIssue> getMyBooks() {
        return bookService.getMyBooks();
    }
}
