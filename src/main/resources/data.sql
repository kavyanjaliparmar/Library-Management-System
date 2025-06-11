-- Insert Admin User
INSERT INTO users (username, password, full_name, email, role)
SELECT 'admin', '$2a$10$/8vmgF9fNqKJdrMWAiXE3Or344bdkXuSETqI8J6k.cOtFIaxXgbYy', 'System Administrator', 'admin@library.com', 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@library.com');

-- Insert Sample Students
INSERT INTO users (username, password, full_name, email, role)
SELECT 'john.doe', '$2a$10$Pn2T3.RdoKz7PFx9TpNxYO8j2h4gGp8jPC1Aj8Ey3hta/hgHnUEqW', 'John Doe', 'john.doe@example.com', 'ROLE_STUDENT'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'john.doe@example.com');

INSERT INTO users (username, password, full_name, email, role)
SELECT 'jane.smith', '$2a$10$Pn2T3.RdoKz7PFx9TpNxYO8j2h4gGp8jPC1Aj8Ey3hta/hgHnUEqW', 'Jane Smith', 'jane.smith@example.com', 'ROLE_STUDENT'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'jane.smith@example.com');

-- Insert Categories
INSERT INTO categories (name, description)
SELECT 'Fiction', 'Fiction books including novels and short stories'
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Fiction');

INSERT INTO categories (name, description)
SELECT 'Non-Fiction', 'Non-fiction books including biographies and educational books'
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Non-Fiction');

INSERT INTO categories (name, description)
SELECT 'Science', 'Science books including physics, chemistry, and biology'
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Science');

-- Insert Sample Books
INSERT INTO books (title, author, isbn, description, total_copies, available_copies, category_id, location, publication_year)
SELECT 'The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 'A story of the fabulously wealthy Jay Gatsby', 5, 5,
       (SELECT id FROM categories WHERE name = 'Fiction'), 'Shelf A1', 1925
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '978-0743273565');

INSERT INTO books (title, author, isbn, description, total_copies, available_copies, category_id, location, publication_year)
SELECT 'To Kill a Mockingbird', 'Harper Lee', '978-0446310789', 'The story of racial injustice and the loss of innocence', 3, 3,
       (SELECT id FROM categories WHERE name = 'Fiction'), 'Shelf A2', 1960
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '978-0446310789');

INSERT INTO books (title, author, isbn, description, total_copies, available_copies, category_id, location, publication_year)
SELECT 'A Brief History of Time', 'Stephen Hawking', '978-0553380163', 'A book about theoretical physics', 2, 2,
       (SELECT id FROM categories WHERE name = 'Science'), 'Shelf B1', 1988
WHERE NOT EXISTS (SELECT 1 FROM books WHERE isbn = '978-0553380163');

-- Insert Sample Book Issues
INSERT INTO book_issues (user_id, book_id, issue_date, due_date, status)
SELECT 2, 1, '2025-05-01 10:00:00', '2025-05-15 10:00:00', 'RETURNED'
WHERE NOT EXISTS (SELECT 1 FROM book_issues WHERE user_id = 2 AND book_id = 1);

INSERT INTO book_issues (user_id, book_id, issue_date, due_date, status)
SELECT 3, 2, '2025-05-10 14:30:00', '2025-05-24 14:30:00', 'ISSUED'
WHERE NOT EXISTS (SELECT 1 FROM book_issues WHERE user_id = 3 AND book_id = 2);

-- Update available copies for issued books
UPDATE books SET available_copies = available_copies - 1 
WHERE id = 2 AND available_copies > 0;
