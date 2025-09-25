package Task3;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String id;
    private String name;
    private final List<String> borrowedBookIds = new ArrayList<>();

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<String> getBorrowedBookIds() { return borrowedBookIds; }

    public void borrowBook(String bookId) {
        borrowedBookIds.add(bookId);
    }

    public void returnBook(String bookId) {
        borrowedBookIds.remove(bookId);
    }

    public int getBorrowedCount() {
        return borrowedBookIds.size();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s — borrowed: %d", id, name, borrowedBookIds.size());
    }
}

