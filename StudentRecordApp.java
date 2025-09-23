import java.util.*;

public class StudentRecordApp {
    // --- Student model ---
    static class Student {
        private int id;
        private String name;
        private double marks;

        public Student(int id, String name, double marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getMarks() { return marks; }

        public void setName(String name) { this.name = name; }
        public void setMarks(double marks) { this.marks = marks; }

        @Override
        public String toString() {
            return String.format("%-5d | %-20s | %-6.2f", id, name, marks);
        }
    }

    // --- Manager (CRUD ops) ---
    static class StudentManager {
        private final List<Student> students = new ArrayList<>();

        public boolean addStudent(Student s) {
            if (findStudentById(s.getId()) != null) return false; // duplicate id
            students.add(s);
            return true;
        }

        public List<Student> getAllStudents() { return students; }

        public Student findStudentById(int id) {
            for (Student s : students) if (s.getId() == id) return s;
            return null;
        }

        public boolean updateStudent(int id, String newName, Double newMarks) {
            Student s = findStudentById(id);
            if (s == null) return false;
            if (newName != null) s.setName(newName);
            if (newMarks != null) s.setMarks(newMarks);
            return true;
        }

        public boolean deleteStudent(int id) {
            Student s = findStudentById(id);
            if (s == null) return false;
            return students.remove(s);
        }
    }

    // --- CLI ---
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        System.out.println("=== Student Record Management System ===");
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> { running = false; System.out.println("Exiting. Goodbye!"); }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
    }

    private static void addStudent() {
        int id = readInt("Enter student ID: ");
        if (manager.findStudentById(id) != null) {
            System.out.println("A student with that ID already exists.");
            return;
        }
        String name = readLine("Enter name: ");
        double marks = readDouble("Enter marks: ");
        manager.addStudent(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {
        List<Student> list = manager.getAllStudents();
        if (list.isEmpty()) { System.out.println("No students available."); return; }
        System.out.println(String.format("%-5s | %-20s | %-6s", "ID", "Name", "Marks"));
        System.out.println("-------------------------------------------------");
        for (Student s : list) System.out.println(s);
    }

    private static void updateStudent() {
        int id = readInt("Enter ID of student to update: ");
        Student s = manager.findStudentById(id);
        if (s == null) { System.out.println("No student found with ID " + id); return; }
        System.out.println("Current -> " + s);
        String newName = readLine("Enter new name (press Enter to keep): ");
        String marksStr = readLine("Enter new marks (press Enter to keep): ");
        Double newMarks = null;
        if (!marksStr.isBlank()) {
            try { newMarks = Double.parseDouble(marksStr.trim()); }
            catch (NumberFormatException e) { System.out.println("Invalid marks entered. Update cancelled."); return; }
        }
        boolean ok = manager.updateStudent(id, newName.isBlank() ? null : newName, newMarks);
        System.out.println(ok ? "Update successful." : "Update failed.");
    }

    private static void deleteStudent() {
        int id = readInt("Enter ID of student to delete: ");
        Student s = manager.findStudentById(id);
        if (s == null) { System.out.println("No student with ID " + id); return; }
        System.out.println("Found -> " + s);
        String confirm = readLine("Type 'yes' to confirm delete: ");
        if (confirm.equalsIgnoreCase("yes")) {
            manager.deleteStudent(id);
            System.out.println("Student deleted.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // --- Input helpers (robust parsing) ---
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try { return Integer.parseInt(line); }
            catch (NumberFormatException e) { System.out.println("Please enter a valid integer."); }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try { return Double.parseDouble(line); }
            catch (NumberFormatException e) { System.out.println("Please enter a valid number (e.g. 78.5)."); }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
