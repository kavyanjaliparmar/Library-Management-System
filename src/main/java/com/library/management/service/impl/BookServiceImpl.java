package com.library.management.service.impl;

import com.library.management.model.Book;
import com.library.management.model.BookIssue;
import com.library.management.model.User;
import com.library.management.repository.BookIssueRepository;
import com.library.management.repository.BookRepository;
import com.library.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookIssueRepository bookIssueRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        if (book.getAvailableCopies() == null) {
            book.setAvailableCopies(book.getTotalCopies());
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> searchBooks(String query) {
        return bookRepository.findByTitleContainingOrAuthorContainingOrIsbnContaining(query, query, query);
    }

    @Override
    public List<Book> findBooksByCategory(String categoryName) {
        return bookRepository.findByCategoryName(categoryName);
    }

    @Override
    @Transactional
    public BookIssue borrowBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BookIssue bookIssue = new BookIssue();
        bookIssue.setBook(book);
        bookIssue.setUser(currentUser);
        bookIssue.setIssueDate(LocalDateTime.now());
        bookIssue.setDueDate(LocalDateTime.now().plusDays(14)); // 2 weeks lending period
        bookIssue.setStatus(BookIssue.IssueStatus.ISSUED);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        return bookIssueRepository.save(bookIssue);
    }

    @Override
    @Transactional
    public BookIssue returnBook(Long issueId) {
        BookIssue bookIssue = bookIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Book issue not found"));

        if (bookIssue.getStatus() == BookIssue.IssueStatus.RETURNED) {
            throw new RuntimeException("Book already returned");
        }

        Book book = bookIssue.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        bookIssue.setReturnDate(LocalDateTime.now());
        bookIssue.setStatus(BookIssue.IssueStatus.RETURNED);

        // Calculate fine if returned late
        if (LocalDateTime.now().isAfter(bookIssue.getDueDate())) {
            long daysLate = java.time.Duration.between(bookIssue.getDueDate(), LocalDateTime.now()).toDays();
            bookIssue.setFineAmount(daysLate * 1.0); // $1 per day fine
        }

        return bookIssueRepository.save(bookIssue);
    }

    @Override
    public List<BookIssue> getMyBooks() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookIssueRepository.findByUserOrderByIssueDateDesc(currentUser);
    }
}
