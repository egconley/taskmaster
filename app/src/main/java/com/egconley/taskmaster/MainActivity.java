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

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.egconley.taskmaster.content.Task;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {

    String TAG = "egc.main";

    private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

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

    public void onListFragmentInteraction(Task task) {
        Intent goToTaskDetail = new Intent(MainActivity.this, TaskDetail.class);

        goToTaskDetail.putExtra("taskName", task.getTitle());
        goToTaskDetail.putExtra("taskBody", task.getBody());

        Log.v(TAG, task.getTitle());

        startActivity(goToTaskDetail);

    }
}
