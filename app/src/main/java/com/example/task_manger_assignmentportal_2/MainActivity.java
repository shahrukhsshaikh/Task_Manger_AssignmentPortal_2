package com.example.task_manger_assignmentportal_2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText taskTitle;
    private EditText taskDescription;
    private ListView taskListView;
    private ArrayList<Task> tasks;
    private ArrayAdapter<Task> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskTitle = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);
        taskListView = findViewById(R.id.taskListView);
        Button addTaskButton = findViewById(R.id.addTaskButton);

        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskListView.setAdapter(adapter);

        addTaskButton.setOnClickListener(v -> addTask());

        taskListView.setOnItemClickListener((parent, view, position, id) -> updateTaskStatus(position));

        taskListView.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteTask(position);
            return true;
        });
    }

    private void addTask() {
        String title = taskTitle.getText().toString().trim();
        String description = taskDescription.getText().toString().trim();

        if (!title.isEmpty() && !description.isEmpty()) {
            Task task = new Task(title, description);
            tasks.add(task);
            adapter.notifyDataSetChanged();
            taskTitle.setText("");
            taskDescription.setText("");
        } else {
            Toast.makeText(this, "Please enter task details", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTaskStatus(int position) {
        Task task = tasks.get(position);
        task.setCompleted(!task.isCompleted());
        adapter.notifyDataSetChanged();
    }

    private void deleteTask(int position) {
        tasks.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
    }
}