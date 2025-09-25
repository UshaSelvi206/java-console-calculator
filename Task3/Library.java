package Task3;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private int maxBooksPerUser = 5;

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public Optional<Book> findBookById(String id) {
        return Optional.ofNullable(books.get(id));
    }

    public Optional<User> findUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    // issue a book for 'days' days; returns true on success or throws exception on failure
    public synchronized void issueBook(String bookId, String userId, int days) {
        Book book = books.get(bookId);
        if (book == null) throw new IllegalArgumentException("Book not found: " + bookId);
        User user = users.get(userId);
        if (user == null) throw new IllegalArgumentException("User not found: " + userId);

        if (user.getBorrowedCount() >= maxBooksPerUser) {
            throw new IllegalStateException("User has reached maximum borrowed books (" + maxBooksPerUser + ").");
        }
        if (book.isIssued()) {
            throw new IllegalStateException("Book is already issued.");
        }

        book.issueTo(userId, days);
        user.borrowBook(bookId);
    }

    public synchronized void returnBook(String bookId, String userId) {
        Book book = books.get(bookId);
        if (book == null) throw new IllegalArgumentException("Book not found: " + bookId);
        User user = users.get(userId);
        if (user == null) throw new IllegalArgumentException("User not found: " + userId);

        if (!book.isIssued()) throw new IllegalStateException("Book is not issued currently.");
        if (!Objects.equals(book.getIssuedToUserId(), userId)) {
            throw new IllegalStateException("Book is issued to a different user.");
        }

        // optional: calculate fine
        LocalDate today = LocalDate.now();
        LocalDate due = book.getDueDate();
        if (due != null && today.isAfter(due)) {
            long daysLate = ChronoUnit.DAYS.between(due, today);
            double fine = daysLate * 1.0; // Rs.1 per day (example)
            System.out.printf("Book is %d day(s) late. Fine: %.2f%n", daysLate, fine);
        }

        book.returnedBy(userId);
        user.returnBook(bookId);
    }

    public List<Book> searchByTitle(String titleQuery) {
        String q = titleQuery.toLowerCase();
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Book> listAvailableBooks() {
        return books.values().stream().filter(b -> !b.isIssued()).collect(Collectors.toList());
    }

    public List<Book> listIssuedBooks() {
        return books.values().stream().filter(Book::isIssued).collect(Collectors.toList());
    }

    // helper to print summary
    public void printSummary() {
        System.out.println("Library summary:");
        System.out.println("Total books: " + books.size());
        System.out.println("Total users: " + users.size());
    }
}

