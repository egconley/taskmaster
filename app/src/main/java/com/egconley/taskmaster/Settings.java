package com.egconley.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    String TAG = "egc.settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
