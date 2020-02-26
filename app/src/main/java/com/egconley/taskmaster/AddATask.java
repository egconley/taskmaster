package com.egconley.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateTaskMutation;
import com.amazonaws.amplify.generated.graphql.ListTeamsQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Nonnull;

import type.CreateTaskInput;

public class AddATask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String TAG = "egc.addATask";

    private AWSAppSyncClient mAWSAppSyncClient;
    RecyclerView recyclerView;
    ArrayAdapter<String> adapter;
    String selectedTeamID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        final Spinner spinner = findViewById(R.id.spinner);
        final String[] spinnerTeams = new String[3];
        ListTeamsQuery teams = ListTeamsQuery.builder().build();
        final HashMap<String, String> teamHashmap = new HashMap<>();
        mAWSAppSyncClient.query(teams).enqueue(new GraphQLCall.Callback<ListTeamsQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<ListTeamsQuery.Data> response) {
                int idx = 0;
                for (ListTeamsQuery.Item team : response.data().listTeams().items()) {
                    teamHashmap.put(team.name(), team.id());
                    spinnerTeams[idx] = team.name();
                    idx++;
                }

                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerTeams);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Handler threadHandler = new Handler(Looper.getMainLooper()) {
                    public void handleMessage(Message msg) {
                        spinner.setAdapter(adapter);
//                        spinner.setOnItemSelectedListener(AddATask.this);
                    }
                };
                threadHandler.obtainMessage().sendToTarget();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.i("ran", "failed to pull from db");
            }
        });

        Button button3 = findViewById(R.id.addTaskButton);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newTaskTitle = findViewById(R.id.addTaskName);
                EditText newTaskBody = findViewById(R.id.addTaskBody);


                String selectTeam = spinner.getSelectedItem().toString();

                CreateTaskInput input = CreateTaskInput.builder()
                        .title(newTaskTitle.getText().toString())
                        .body(newTaskBody.getText().toString())
                        .state("new")
                        .taskTeamId(teamHashmap.get(selectTeam))
                        .build();

                mAWSAppSyncClient.mutate(CreateTaskMutation.builder().input(input).build()).enqueue(
                        new GraphQLCall.Callback<CreateTaskMutation.Data>() {
                            @Override
                            public void onResponse(@Nonnull Response<CreateTaskMutation.Data> response) {
                                Log.i(TAG, response.data().toString());
                            }

                            @Override
                            public void onFailure(@Nonnull ApolloException e) {
                                Log.i(TAG, "create task mutation failure!  :(");
                            }
                        }
                );
                finish();
                Toast toast = Toast.makeText(AddATask.this, "Submitted!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
