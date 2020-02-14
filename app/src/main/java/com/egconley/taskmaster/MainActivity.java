package com.egconley.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    String TAG = "egc.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettings = new Intent(MainActivity.this, Settings.class);

                startActivity(goToSettings);
            }
        });

        Button addTaskButton = findViewById(R.id.addTaskButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddTask = new Intent(MainActivity.this, AddATask.class);
                startActivity(goToAddTask);
            }
        });

        Button allTasksButton = findViewById(R.id.allTasksButton);

        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllTasks = new Intent(MainActivity.this, AllTasks.class);
                startActivity(goToAllTasks);
            }
        });

//        final Button task1Button = findViewById(R.id.task1Button);
//
//        task1Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToTaskDetail = new Intent(MainActivity.this, TaskDetail.class);
//
//                goToTaskDetail.putExtra("taskName", task1Button.getText().toString());
//
//                Log.v(TAG, task1Button.getText().toString());
//
//                startActivity(goToTaskDetail);
//            }
//        });
//
//        final Button task2Button = findViewById(R.id.task2Button);
//
//        task2Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToTaskDetail = new Intent(MainActivity.this, TaskDetail.class);
//                goToTaskDetail.putExtra("taskName", task2Button.getText().toString());
//
//                Log.v(TAG, task2Button.getText().toString());
//
//                startActivity(goToTaskDetail);
//            }
//        });
//
//        final Button task3Button = findViewById(R.id.task3Button);
//
//        task3Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToTaskDetail = new Intent(MainActivity.this, TaskDetail.class);
//
//                goToTaskDetail.putExtra("taskName", task3Button.getText().toString());
//
//                Log.v(TAG, task3Button.getText().toString());
//
//                startActivity(goToTaskDetail);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "resumed");

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("username", null);

        TextView myTasksHeader = findViewById(R.id.myTasksHeader);

        String myTasksHeaderText = username + "\'s Tasks";

        if (username!=null) {
            myTasksHeader.setText(myTasksHeaderText);
        }

    }

}
