package com.thoughtworks.androidtrain;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.utils.SharePreferenceUtil;

public class SPActivity extends AppCompatActivity {

    private static final String TAG = "SPActivity";
    private boolean userIsKnown;
    private final String IS_KNOWN_KEY = "isKnown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userIsKnown = getUserIsKnown();
        Log.i(TAG, String.valueOf(userIsKnown));
        if (userIsKnown) {
            setContentView(R.layout.activity_sp_main);
        } else {
            setContentView(R.layout.activity_sp_prompt);
        }
    }

    private boolean getUserIsKnown() {
        return SharePreferenceUtil.readBoolean(this, IS_KNOWN_KEY);
    }
}
