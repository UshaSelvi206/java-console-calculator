package scheduler;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Comparable<Task>, Serializable {
    private String title;
    private String priority;
    private LocalDate deadline;

    public Task(String title, String priority, LocalDate deadline) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
    }

    public String getTitle() { return title; }
    public String getPriority() { return priority; }
    public LocalDate getDeadline() { return deadline; }

    @Override
    public int compareTo(Task other) {
        return this.deadline.compareTo(other.deadline);
    }

    @Override
    public String toString() {
        return title + " (" + priority + ") - " + deadline;
    }
}
