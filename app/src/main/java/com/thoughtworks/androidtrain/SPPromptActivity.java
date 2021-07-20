package com.thoughtworks.androidtrain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.constants.SPKeys;
import com.thoughtworks.androidtrain.utils.SharePreferenceUtil;

public class SPPromptActivity extends AppCompatActivity {

    Button btnIAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_prompt);
        btnIAgree = findViewById(R.id.btnIsKnown);
        btnIAgree.setOnClickListener(v -> {
            initUI();
            SharePreferenceUtil.writeBoolean(this, SPKeys.IS_KNOWN_KEY, true);
        });
    }

    private void initUI() {
        Intent spMainIntent = new Intent(this, SPMainActivity.class);
        setContentView(R.layout.activity_sp_prompt);
    }
}
