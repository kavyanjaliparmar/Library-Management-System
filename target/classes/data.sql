-- Insert Admin User
INSERT INTO users (username, password, full_name, email, role) 
VALUES ('admin', '$2a$10$Pn2T3.RdoKz7PFx9TpNxYO8j2h4gGp8jPC1Aj8Ey3hta/hgHnUEqW', 'System Administrator', 'admin@library.com', 'ROLE_ADMIN');

-- Insert Sample Students
INSERT INTO users (username, password, full_name, email, role)
VALUES 
('john.doe', '$2a$10$Pn2T3.RdoKz7PFx9TpNxYO8j2h4gGp8jPC1Aj8Ey3hta/hgHnUEqW', 'John Doe', 'john.doe@example.com', 'ROLE_STUDENT'),
('jane.smith', '$2a$10$Pn2T3.RdoKz7PFx9TpNxYO8j2h4gGp8jPC1Aj8Ey3hta/hgHnUEqW', 'Jane Smith', 'jane.smith@example.com', 'ROLE_STUDENT');

-- Insert Categories
INSERT INTO categories (name, description)
VALUES 
('Fiction', 'Literary works created from the imagination'),
('Science Fiction', 'Fiction based on imagined future scientific or technological advances'),
('Fantasy', 'Fiction featuring magical and supernatural elements'),
('Romance', 'Fiction focused on romantic love stories');

-- Insert Sample Books
INSERT INTO books (title, author, isbn, description, total_copies, available_copies, category_id, location, publication_year)
VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'A story of the fabulously wealthy Jay Gatsby', 5, 5, 1, 'Shelf A1', 1925),
('To Kill a Mockingbird', 'Harper Lee', '9780446310789', 'The story of racial injustice in a small Southern town', 3, 3, 1, 'Shelf A2', 1960),
('1984', 'George Orwell', '9780451524935', 'A dystopian social science fiction novel', 4, 4, 2, 'Shelf B1', 1949),
('The Hobbit', 'J.R.R. Tolkien', '9780547928227', 'A fantasy novel about the adventures of Bilbo Baggins', 3, 3, 3, 'Shelf B2', 1937),
('Pride and Prejudice', 'Jane Austen', '9780141439518', 'A romantic novel of manners', 2, 2, 4, 'Shelf C1', 1813);

-- Insert Sample Book Issues
INSERT INTO book_issues (user_id, book_id, issue_date, due_date, status)
VALUES 
(2, 1, '2025-05-01 10:00:00', '2025-05-15 10:00:00', 'RETURNED'),
(3, 2, '2025-05-10 14:30:00', '2025-05-24 14:30:00', 'ISSUED');

-- Update available copies for issued books
UPDATE books SET available_copies = available_copies - 1 WHERE id = 2;
