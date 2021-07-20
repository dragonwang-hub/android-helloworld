package com.thoughtworks.androidtrain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.constants.SPKeys;
import com.thoughtworks.androidtrain.utils.SharePreferenceUtil;

public class SPPromptActivity extends AppCompatActivity {

    private static final String TAG = "SPPromptActivity";
    Button btnIAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_prompt);
        btnIAgree = findViewById(R.id.btnIsKnown);
        btnIAgree.setOnClickListener(v -> {
            SharePreferenceUtil.writeBoolean(this, SPKeys.IS_KNOWN_KEY, true);
            Log.i(TAG, "Write IS_KNOWN_KEY value to true.");
            boolean b = SharePreferenceUtil.readBoolean(this, SPKeys.IS_KNOWN_KEY, false);
            initUI();
        });
    }

    private void initUI() {
        Intent spMainIntent = new Intent(this, SPMainActivity.class);
        startActivity(spMainIntent);
    }
}
