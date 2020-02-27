package com.egconley.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.ListTeamsQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.HashMap;

import javax.annotation.Nonnull;

public class Settings extends AppCompatActivity {

    String TAG = "egc.settings";

    private AWSAppSyncClient mAWSAppSyncClient;

    ArrayAdapter<String> adapter;
    String selectedTeamID = "";

    HashMap<String, String> teamHashmap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        final Spinner spinner = findViewById(R.id.spinner);
        final String[] spinnerTeams = new String[3];
        ListTeamsQuery teams = ListTeamsQuery.builder().build();

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

        ImageButton saveTeamButton = findViewById(R.id.saveTeam);

        saveTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences p =
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = p.edit();

                String userTeam = spinner.getSelectedItem().toString();

                editor.putString("team", userTeam);
                editor.apply();

                Toast toast = Toast.makeText(Settings.this, "Saved!", Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        ImageButton saveUsernameButton = findViewById(R.id.saveUsername);

        saveUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences p =
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = p.edit();

                EditText enterUsername = findViewById(R.id.enterUsername);
                String username = enterUsername.getText().toString();

                editor.putString("username", username);
                editor.apply();

                Toast toast = Toast.makeText(Settings.this, "Saved!", Toast.LENGTH_SHORT);
                toast.show();
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

        EditText enterUsername = findViewById(R.id.enterUsername);

        if (username!=null) {
            enterUsername.setText(username);
        }

    }
}
