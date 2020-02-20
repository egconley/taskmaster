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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.MutationType;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {

    String TAG = "egc.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("AmplifyGetStarted", "Amplify is all setup and ready to go!");
        } catch (AmplifyException exception) {
            Log.e("AmplifyGetStarted", exception.getMessage());
        }

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
