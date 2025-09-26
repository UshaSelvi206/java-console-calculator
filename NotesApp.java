import java.io.*;
import java.util.*;

public class NotesApp {
    private static final String NOTES_FILE = "notes.txt";
    private static final String LOG_FILE = "notes_error.log";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ensureFileExists(NOTES_FILE);
        while (true) {
            System.out.println("\n== Notes App ==");
            System.out.println("1. Add note");
            System.out.println("2. List notes");
            System.out.println("3. Search notes");
            System.out.println("4. Delete note");
            System.out.println("5. Edit note");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addNote(); break;
                case "2": listNotes(); break;
                case "3": searchNotes(); break;
                case "4": deleteNote(); break;
                case "5": editNote(); break;
                case "6": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void ensureFileExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            log(e);
            System.out.println("Could not create notes file: " + e.getMessage());
        }
    }

    private static void addNote() {
        System.out.print("Enter note (single line): ");
        String note = scanner.nextLine().trim();
        if (note.isEmpty()) {
            System.out.println("Empty note not saved.");
            return;
        }
        String entry = System.currentTimeMillis() + "|" + note;
        try (FileWriter fw = new FileWriter(NOTES_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(entry);
            System.out.println("Note saved.");
        } catch (IOException e) {
            log(e);
            System.out.println("Failed to save note: " + e.getMessage());
        }
    }

    private static List<String> readAllNotes() {
        List<String> lines = new ArrayList<>();
        try (FileReader fr = new FileReader(NOTES_FILE);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            log(e);
            System.out.println("Failed to read notes: " + e.getMessage());
        }
        return lines;
    }

    private static void listNotes() {
        List<String> lines = readAllNotes();
        if (lines.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }
        int idx = 1;
        for (String l : lines) {
            String display = l;
            String[] parts = l.split("\\|", 2);
            if (parts.length == 2) {
                try {
                    long ts = Long.parseLong(parts[0]);
                    display = String.format("[%s] %s", formatTs(ts), parts[1]);
                } catch (NumberFormatException ex) {
                    display = parts[1];
                }
            }
            System.out.println(idx++ + ". " + display);
        }
    }

    private static String formatTs(long ts) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(ts));
    }

    private static void searchNotes() {
        System.out.print("Enter keyword: ");
        String kw = scanner.nextLine().toLowerCase();
        List<String> lines = readAllNotes();
        boolean found = false;
        int idx = 1;
        for (String l : lines) {
            String text = l;
            String[] parts = l.split("\\|", 2);
            if (parts.length == 2) text = parts[1];
            if (text.toLowerCase().contains(kw)) {
                System.out.println(idx + ". " + text);
                found = true;
            }
            idx++;
        }
        if (!found) System.out.println("No matches.");
    }

    private static void deleteNote() {
        List<String> lines = readAllNotes();
        if (lines.isEmpty()) { System.out.println("No notes to delete."); return; }
        listNotes();
        System.out.print("Enter note number to delete: ");
        String s = scanner.nextLine();
        int num;
        try { num = Integer.parseInt(s); }
        catch (NumberFormatException e) { System.out.println("Invalid number."); return; }
        if (num < 1 || num > lines.size()) { System.out.println("Out of range."); return; }
        lines.remove(num - 1);
        writeAllNotes(lines);
        System.out.println("Deleted.");
    }

    private static void editNote() {
        List<String> lines = readAllNotes();
        if (lines.isEmpty()) { System.out.println("No notes to edit."); return; }
        listNotes();
        System.out.print("Enter note number to edit: ");
        String s = scanner.nextLine();
        int num;
        try { num = Integer.parseInt(s); }
        catch (NumberFormatException e) { System.out.println("Invalid number."); return; }
        if (num < 1 || num > lines.size()) { System.out.println("Out of range."); return; }
        String old = lines.get(num - 1);
        String[] parts = old.split("\\|", 2);
        String oldText = parts.length == 2 ? parts[1] : old;
        System.out.println("Old: " + oldText);
        System.out.print("Enter new text: ");
        String newText = scanner.nextLine().trim();
        if (newText.isEmpty()) { System.out.println("Empty - canceled."); return; }
        lines.set(num - 1, System.currentTimeMillis() + "|" + newText);
        writeAllNotes(lines);
        System.out.println("Edited.");
    }

    private static void writeAllNotes(List<String> lines) {
        try (FileWriter fw = new FileWriter(NOTES_FILE, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (String l : lines) out.println(l);
        } catch (IOException e) {
            log(e);
            System.out.println("Failed to write notes: " + e.getMessage());
        }
    }

    private static void log(Exception e) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            e.printStackTrace(pw);
            pw.println("----");
        } catch (IOException ex) {
            System.err.println("Logging failed: " + ex.getMessage());
        }
    }
}

