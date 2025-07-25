package prototype;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/** The main class to start the to-do app.
 *
 * @author Ubaid Irfan
 * @version 1.0
 *
 * simple to-do app that allows users to add and delete tasks through GUI.
 *
 * I used ChatGPT to help me understand how java swing works.
 * AI was used in 2 iterations to give me an idea of what to make as a simple prototype to learn swing,
 * and to help with structuring the code.*/
public class Application
{
    /** initialising the app window */
    private JFrame frame;

    /** initialising a text field to add tasks. */
    private JTextField addTaskField;

    /** initialising a model to store list of tasks displayed in the UI. */
    private DefaultListModel<Task> taskListModel;

    /** Jlist is used to display a list of items (each item is of type: Task) in a GUI
     * <type> = <Task> means that the list contains elements of type = Task (user specified class)
     * Task class has 'name' field overriding toString to return task name, which is what will be displayed in the Jlist.*/
    private JList<Task> taskList;

    /** jlist just shows data in ui, internal list for actually storing the tasks, useful for extensions like editing, deleting tasks */
    private List<Task> tasks;

    /**
     * Constructor initializes the GUI and sets up event listeners.
     * Creates and configures the main frame, input fields, buttons, and task list.
     */
    public Application() {

        // arraylist to store tasks as size is dynamic, meaning tasks can be added and removed
        tasks = new ArrayList<>();


        //creates the jframe to display the app. it is titled "Task Manager"
        //all components of the app are added to this frame, e.g. buttons, text fields, task list
        frame = new JFrame("Task Manager");

        //to terminate program when x button is clicked (window is closed)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //sets size of the window
        frame.setSize(500, 500);

        //a panel to hold the task input field and add button
        JPanel inputPanel = new JPanel(new GridLayout(3, 1));

        //adds a label to the panel
        inputPanel.add(new JLabel(" Task:"));

        //adds a text field to the panel
        addTaskField = new JTextField();

        //adds a placeholder text to the text field saying do the dishes in italics
        addTaskField.setFont(addTaskField.getFont().deriveFont(Font.ITALIC));
        addTaskField.setText("Add a task, e.g. Do the dishes");
        inputPanel.add(addTaskField);

        //adds add button to panel
        JButton addButton = new JButton("Add");
        inputPanel.add(addButton);

        //initialises the task list model to store the tasks
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel); //creates a jlist to display the tasks
        //adds the panel to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        //adds the task list to the center of the frame in a scrollable area (for if there are many tasks)
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

        //panel to hold the delete button
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //adds action listeners to the buttons
        //action listener for add btn to specify what happens when it is clicked (addTask method is called)
        addButton.addActionListener(this::addTask);
        //action listener for delete btn to specify what happens when it is clicked (deleteTask method is called)
        deleteButton.addActionListener(e -> deleteTask(taskList.getSelectedIndex()));

        //to make jframe appear on screen
        frame.setVisible(true);
    }

    /**
     * Adds a new task to the list if the input is not empty,
     * or the default placeholder text.
     *
     * @param e The event triggered by clicking the "Add" button.
     */
    private void addTask(ActionEvent e) {
        String name = addTaskField.getText();

        //if name = placeholder text, task is not added, popup shows
        if (name.equals("Add a task, e.g. Do the dishes")) {
            //popup to inform user to type a task
            JOptionPane.showMessageDialog(frame, "Type the task you want to add.");
            //if addTaskField has text in it, add task to the list
        } else if (!name.isEmpty()) {
            Task task = new Task(name);
            tasks.add(task);
            taskListModel.addElement(task);
            addTaskField.setText("");
            //nothing happens except popup to tell user to type something in task field.
        } else {
            JOptionPane.showMessageDialog(frame, "Task field cannot be empty."); //popup
        }
    }

    /**
     * Deletes a task from the list if a valid index is selected.
     *
     * @param index The index of the task to be deleted.
     */
    private void deleteTask(int index) {
        if (index >= 0) {
            tasks.remove(index);
            taskListModel.remove(index);
        } else {
            JOptionPane.showMessageDialog(frame, "Select a task to delete.");
        }
    }

    /**
     * The main method starts the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Application();
    }
}