package com.egconley.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.Set;

public class TaskDetail extends AppCompatActivity {

    String TAG = "egc.taskDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "resumed");

        // https://stackoverflow.com/questions/5968896/listing-all-extras-of-an-intent
        Bundle extras = getIntent().getExtras();

        Set<String> ks = extras.keySet();
        Iterator<String> iterator = ks.iterator();
        while (iterator.hasNext()) {
            Log.d(TAG + "KEY", iterator.next());
        }

        //        String taskName = getIntent().getStringExtra("taskName");
        String taskName = extras.getString("taskName");
        TextView taskTitle = findViewById(R.id.taskDetailTitle);
        taskTitle.setText(taskName);

        //        String taskBodyContent = getIntent().getStringExtra("taskBody");
        String taskBodyContent = extras.getString("taskBody");
        Log.d(TAG, taskBodyContent);
        TextView taskBody = findViewById(R.id.taskDetailBody);
        taskBody.setText(taskBodyContent);
    }

}
