package Task3;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();

        // sample data
        lib.addBook(new Book("B001", "Clean Code", "Robert C. Martin"));
        lib.addBook(new Book("B002", "Effective Java", "Joshua Bloch"));
        lib.addBook(new Book("B003", "Java: The Complete Reference", "Herbert Schildt"));

        lib.registerUser(new User("U001", "Alice"));
        lib.registerUser(new User("U002", "Bob"));

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. List available books");
            System.out.println("2. List issued books");
            System.out.println("3. Issue book");
            System.out.println("4. Return book");
            System.out.println("5. Search by title");
            System.out.println("6. Register user");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine().trim());
            try {
                switch (choice) {
                    case 1:
                        List<Book> avail = lib.listAvailableBooks();
                        avail.forEach(System.out::println);
                        break;
                    case 2:
                        List<Book> issued = lib.listIssuedBooks();
                        issued.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Book ID: "); String bid = sc.nextLine().trim();
                        System.out.print("User ID: "); String uid = sc.nextLine().trim();
                        System.out.print("Days to issue (default 14): ");
                        String ds = sc.nextLine().trim();
                        int days = ds.isEmpty() ? 14 : Integer.parseInt(ds);
                        lib.issueBook(bid, uid, days);
                        System.out.println("Issued successfully.");
                        break;
                    case 4:
                        System.out.print("Book ID: "); String rb = sc.nextLine().trim();
                        System.out.print("User ID: "); String ru = sc.nextLine().trim();
                        lib.returnBook(rb, ru);
                        System.out.println("Returned successfully.");
                        break;
                    case 5:
                        System.out.print("Title query: "); String q = sc.nextLine().trim();
                        lib.searchByTitle(q).forEach(System.out::println);
                        break;
                    case 6:
                        System.out.print("New User ID: "); String nid = sc.nextLine().trim();
                        System.out.print("Name: "); String name = sc.nextLine().trim();
                        lib.registerUser(new User(nid, name));
                        System.out.println("User registered.");
                        break;
                    case 0:
                        System.out.println("Bye!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}

