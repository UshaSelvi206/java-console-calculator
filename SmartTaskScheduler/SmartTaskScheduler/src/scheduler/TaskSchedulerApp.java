package scheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class TaskSchedulerApp {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.loadTasks();

        JFrame frame = new JFrame("Smart Task Scheduler");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        DefaultListModel<Task> listModel = new DefaultListModel<>();
        manager.getTasks().forEach(listModel::addElement);

        JList<Task> taskList = new JList<>(listModel);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField titleField = new JTextField();
        JTextField priorityField = new JTextField();
        JTextField dateField = new JTextField("YYYY-MM-DD");

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Priority:"));
        panel.add(priorityField);
        panel.add(new JLabel("Deadline:"));
        panel.add(dateField);

        JButton addButton = new JButton("Add Task");
        JButton deleteButton = new JButton("Delete Task");
        panel.add(addButton);
        panel.add(deleteButton);

        frame.add(panel, BorderLayout.NORTH);

        addButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String priority = priorityField.getText();
                LocalDate date = LocalDate.parse(dateField.getText());
                manager.addTask(title, priority, date);
                listModel.clear();
                manager.getTasks().forEach(listModel::addElement);
                manager.saveTasks();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });

        deleteButton.addActionListener(e -> {
            Task selected = taskList.getSelectedValue();
            if (selected != null) {
                manager.deleteTask(selected);
                listModel.removeElement(selected);
                manager.saveTasks();
            }
        });

        frame.setVisible(true);
    }
}
