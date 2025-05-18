// Global variables
let currentUser = null;

// Show/Hide functions
function showLogin() {
    document.getElementById('loginForm').classList.remove('hidden');
    document.getElementById('registerForm').classList.add('hidden');
    document.getElementById('mainContent').classList.add('hidden');
}

function showRegister() {
    document.getElementById('loginForm').classList.add('hidden');
    document.getElementById('registerForm').classList.remove('hidden');
    document.getElementById('mainContent').classList.add('hidden');
}

function showMain() {
    document.getElementById('loginForm').classList.add('hidden');
    document.getElementById('registerForm').classList.add('hidden');
    document.getElementById('mainContent').classList.remove('hidden');
    document.getElementById('userInfo').classList.remove('hidden');
    loadBooks();
    if (currentUser && currentUser.role === 'ROLE_STUDENT') {
        loadMyBooks();
    }
}

// Authentication functions
async function login() {
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            currentUser = data;
            document.getElementById('username').textContent = currentUser.username;
            if (currentUser.role === 'ROLE_ADMIN') {
                document.getElementById('adminPanel').classList.remove('hidden');
            }
            showMain();
        } else {
            alert('Invalid username or password');
        }
    } catch (error) {
        alert('Error during login');
    }
}

async function register() {
    const username = document.getElementById('regUsername').value;
    const password = document.getElementById('regPassword').value;
    const fullName = document.getElementById('regFullName').value;
    const email = document.getElementById('regEmail').value;

    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ 
                username, 
                password, 
                fullName, 
                email,
                role: 'ROLE_STUDENT'
            })
        });

        if (response.ok) {
            alert('Registration successful! Please login.');
            showLogin();
        } else {
            alert('Registration failed');
        }
    } catch (error) {
        alert('Error during registration');
    }
}

function logout() {
    currentUser = null;
    document.getElementById('username').textContent = '';
    document.getElementById('adminPanel').classList.add('hidden');
    showLogin();
}

// Book management functions
async function loadBooks() {
    try {
        const response = await fetch('/api/books');
        const books = await response.json();
        displayBooks(books);
    } catch (error) {
        alert('Error loading books');
    }
}

function displayBooks(books) {
    const tbody = document.getElementById('booksTableBody');
    tbody.innerHTML = '';

    books.forEach(book => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.availableCopies}</td>
            <td>
                ${currentUser.role === 'ROLE_ADMIN' 
                    ? `<button onclick="deleteBook(${book.id})">Delete</button>`
                    : `<button onclick="borrowBook(${book.id})" ${book.availableCopies === 0 ? 'disabled' : ''}>
                        ${book.availableCopies === 0 ? 'Not Available' : 'Borrow'}
                       </button>`
                }
            </td>
        `;
        tbody.appendChild(row);
    });
}

async function addBook() {
    const title = document.getElementById('bookTitle').value;
    const author = document.getElementById('bookAuthor').value;
    const isbn = document.getElementById('bookIsbn').value;
    const totalCopies = document.getElementById('bookCopies').value;
    const category = document.getElementById('bookCategory').value;

    try {
        const response = await fetch('/api/books', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                title,
                author,
                isbn,
                totalCopies,
                availableCopies: totalCopies,
                category
            })
        });

        if (response.ok) {
            alert('Book added successfully');
            loadBooks();
        } else {
            alert('Error adding book');
        }
    } catch (error) {
        alert('Error adding book');
    }
}

async function searchBooks() {
    const query = document.getElementById('searchInput').value;
    try {
        const response = await fetch(`/api/books/search?query=${query}`);
        const books = await response.json();
        displayBooks(books);
    } catch (error) {
        alert('Error searching books');
    }
}

async function borrowBook(bookId) {
    try {
        const response = await fetch(`/api/books/${bookId}/borrow`, {
            method: 'POST'
        });

        if (response.ok) {
            alert('Book borrowed successfully');
            loadBooks();
            loadMyBooks();
        } else {
            alert('Error borrowing book');
        }
    } catch (error) {
        alert('Error borrowing book');
    }
}

async function loadMyBooks() {
    try {
        const response = await fetch('/api/books/mybooks');
        const books = await response.json();
        displayMyBooks(books);
        document.getElementById('myBooks').classList.remove('hidden');
    } catch (error) {
        alert('Error loading borrowed books');
    }
}

function displayMyBooks(books) {
    const tbody = document.getElementById('myBooksTableBody');
    tbody.innerHTML = '';

    books.forEach(issue => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${issue.book.title}</td>
            <td>${new Date(issue.issueDate).toLocaleDateString()}</td>
            <td>${new Date(issue.dueDate).toLocaleDateString()}</td>
            <td>${issue.fineAmount || 0}</td>
            <td>
                <button onclick="returnBook(${issue.id})">Return</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

async function returnBook(issueId) {
    try {
        const response = await fetch(`/api/books/return/${issueId}`, {
            method: 'POST'
        });

        if (response.ok) {
            alert('Book returned successfully');
            loadBooks();
            loadMyBooks();
        } else {
            alert('Error returning book');
        }
    } catch (error) {
        alert('Error returning book');
    }
}

// Initial setup
showLogin();
