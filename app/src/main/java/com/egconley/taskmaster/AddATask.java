package com.egconley.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateTaskMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import type.CreateTaskInput;

public class AddATask extends AppCompatActivity {

    String TAG = "egc.addATask";

    private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        EditText newTaskTitle = findViewById(R.id.addTaskName);
        EditText newTaskBody = findViewById(R.id.addTaskBody);

        CreateTaskInput input = CreateTaskInput.builder()
                .title(newTaskTitle.getText().toString())
                .body(newTaskBody.getText().toString())
                .state("new")
                .build();

        mAWSAppSyncClient.mutate(CreateTaskMutation.builder().input(input).build()).enqueue(
                new GraphQLCall.Callback<CreateTaskMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<CreateTaskMutation.Data> response) {
                        Log.e(TAG, response.data().toString());
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e(TAG, "create task mutation failure!  :(");
                    }
                }
        );
        Button button3 = findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(AddATask.this, "Submitted!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
