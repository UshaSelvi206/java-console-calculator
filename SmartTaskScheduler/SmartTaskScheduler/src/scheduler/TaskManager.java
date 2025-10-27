package scheduler;

import java.io.*;
import java.time.LocalDate;
import java.util.PriorityQueue;

public class TaskManager {
    private PriorityQueue<Task> tasks = new PriorityQueue<>();

    public void addTask(String title, String priority, LocalDate deadline) {
        tasks.add(new Task(title, priority, deadline));
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public PriorityQueue<Task> getTasks() {
        return tasks;
    }

    public void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tasks.dat"))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tasks.dat"))) {
            tasks = (PriorityQueue<Task>) ois.readObject();
        } catch (Exception e) {
            System.out.println("No saved tasks found.");
        }
    }
}
