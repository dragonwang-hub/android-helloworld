package com.thoughtworks.androidtrain.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.fragment.AndroidDescription;
import com.thoughtworks.androidtrain.fragment.JavaDescription;
import com.thoughtworks.androidtrain.utils.FragmentUI;

public class FragmentDemoActivity extends AppCompatActivity {

    public static final String TAG = "FragmentDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "on Create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        switchFragmentDescription(R.id.btnAndroid,new AndroidDescription());
        switchFragmentDescription(R.id.btnJava,new JavaDescription());
    }

    private void switchFragmentDescription(int btnId, Fragment fragment) {
        Button btnAndroidDescription = findViewById(btnId);
        btnAndroidDescription.setOnClickListener(v -> {
            FragmentUI.replaceFragment(getSupportFragmentManager(), fragment);
        });
    }
}
