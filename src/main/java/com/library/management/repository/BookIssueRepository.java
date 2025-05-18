package com.library.management.repository;

import com.library.management.model.BookIssue;
import com.library.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    List<BookIssue> findByUserOrderByIssueDateDesc(User user);
    List<BookIssue> findByUserAndReturnDateIsNull(User user);
    List<BookIssue> findByDueDateBeforeAndReturnDateIsNull(LocalDateTime date);
    List<BookIssue> findByStatus(BookIssue.IssueStatus status);
}
