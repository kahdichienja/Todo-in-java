package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todo.Adapter.TodoAdapter;
import com.example.todo.Model.TodoModel;
import com.example.todo.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    private RecyclerView tasksRecyclerView;

    private TodoAdapter tasksAdapter;
    private DatabaseHandler db;
    private FloatingActionButton fab;

    private List<TodoModel> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();


        taskList = new ArrayList<>();



        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        tasksAdapter = new TodoAdapter(db,this);
        tasksRecyclerView.setAdapter(tasksAdapter);



        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
//        Adding Dammy task to the view.

//        TodoModel task = new TodoModel();
//        task.setTask("Loading jetpack composed after this");
//        task.setStatus(0);
//        task.setId(1);
//
//
//
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
////setter
//        tasksAdapter.setTasks(taskList);
//        end add dammy

        taskList = db.getAllTask();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTask();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }

}