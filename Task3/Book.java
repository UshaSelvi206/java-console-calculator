package Task3;
import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private final String id;
    private String title;
    private String author;
    private boolean issued;
    private String issuedToUserId; // user ID who currently has the book
    private LocalDate issueDate;
    private LocalDate dueDate;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return issued; }
    public String getIssuedToUserId() { return issuedToUserId; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }

    // issue book to userId for 'days' days
    public synchronized void issueTo(String userId, int days) {
        if (issued) throw new IllegalStateException("Book already issued to " + issuedToUserId);
        this.issued = true;
        this.issuedToUserId = userId;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(days);
    }

    // return book by userId
    public synchronized void returnedBy(String userId) {
        if (!issued) throw new IllegalStateException("Book is not issued.");
        if (!Objects.equals(this.issuedToUserId, userId)) {
            throw new IllegalStateException("Book is issued to different user (" + this.issuedToUserId + ").");
        }
        this.issued = false;
        this.issuedToUserId = null;
        this.issueDate = null;
        this.dueDate = null;
    }

    @Override
    public String toString() {
        String status = issued ? String.format("Issued to %s (due %s)", issuedToUserId, dueDate) : "Available";
        return String.format("[%s] %s by %s — %s", id, title, author, status);
    }
}

