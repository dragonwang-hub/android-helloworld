package com.thoughtworks.androidtrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_main);

        Log.d(TAG, "MainActivity Creater");

        Button button = (Button) findViewById(R.id.ToConstraintButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConstraintActivity();
            }
        });

        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        Button pickContact = (Button) findViewById(R.id.pickContact);
        pickContact.setOnClickListener(v -> pickContactActivity());
    }

    private void pickContactActivity() {
        Intent pickContactActivity = new Intent(this,PickContactActivity.class);
        startActivity(pickContactActivity);
    }

    private void openLoginActivity() {
        Intent openLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(openLoginActivity);
    }

    private void openConstraintActivity() {
        Intent intent = new Intent(this, ConstraintActivity.class);
        startActivity(intent);
    }
}