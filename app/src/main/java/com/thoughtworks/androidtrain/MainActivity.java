package com.thoughtworks.androidtrain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.thoughtworks.androidtrain.constants.SPKeys;
import com.thoughtworks.androidtrain.utils.SharePreferenceUtil;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_main);

        Log.d(TAG, "MainActivity Creater");

        Button button = findViewById(R.id.ToConstraintButton);
        button.setOnClickListener(v -> openConstraintActivity());

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(v -> openLoginActivity());

        Button pickContact = findViewById(R.id.pickContact);
        pickContact.setOnClickListener(v -> selectContact());

        Button btnFragment = findViewById(R.id.fragment);
        btnFragment.setOnClickListener(v -> openFragmentActivity());

        Button btnRecycler = findViewById(R.id.recyclerView);
        btnRecycler.setOnClickListener(v -> openRecyclerViewActivity());

        Button btnTimer = findViewById(R.id.timer);
        btnTimer.setOnClickListener(v -> openTimerActivity());

        Button btnHandler = findViewById(R.id.handler);
        btnHandler.setOnClickListener(v -> openHandlerActivity());

        Button btnRxJava = findViewById(R.id.rxjava);
        btnRxJava.setOnClickListener(v -> openRxJavaActivity());

        Button btnSP = findViewById(R.id.sharePreference);
        btnSP.setOnClickListener(v -> openSharePreferenceActivity());
    }

    private void openFragmentActivity() {
        Intent fragmentActivity = new Intent(this, FragmentDemoActivity.class);
        startActivity(fragmentActivity);
    }

    private void openLoginActivity() {
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }

    private void openConstraintActivity() {
        Intent intent = new Intent(this, ConstraintActivity.class);
        startActivity(intent);
    }

    static final int REQUEST_SELECT_CONTACT = 1;

    @SuppressLint({"IntentReset", "QueryPermissionsNeeded"})
    private void selectContact() {
        Intent pickContact = new Intent(Intent.ACTION_PICK);
        pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (pickContact.resolveActivity(getPackageManager()) != null) {
            Log.d(TAG, "Start activity for result.");
            startActivityForResult(pickContact, REQUEST_SELECT_CONTACT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "RESULT_CODE:" + resultCode + requestCode);
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            String[] params = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(data.getData(), params, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                cursor.close();
                Log.d(TAG, contactName + " " + phoneNumber);
                Toast.makeText(this, contactName + " " + phoneNumber, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid select, Please check it.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void openRecyclerViewActivity() {
        Intent fragmentActivity = new Intent(this, RecyclerViewActivity.class);
        startActivity(fragmentActivity);
    }


    private void openTimerActivity() {
        Intent timerActivity = new Intent(this, TimerActivity.class);
        startActivity(timerActivity);
    }

    private void openHandlerActivity() {
        Intent handlerActivity = new Intent(this, HandlerActivity.class);
        startActivity(handlerActivity);
    }

    private void openRxJavaActivity() {
        Intent rxJavaActivity = new Intent(this, RxJavaActivity.class);
        startActivity(rxJavaActivity);
    }

    private static boolean userIsKnown;

    private void openSharePreferenceActivity() {
        Intent spActivity;

        userIsKnown = getUserIsKnown();
        Log.i(TAG, String.valueOf(userIsKnown));
        if (userIsKnown) {
            spActivity = new Intent(this, SPPromptActivity.class);
        } else {
            spActivity = new Intent(this, SPMainActivity.class);
        }
        startActivity(spActivity);
    }

    private boolean getUserIsKnown() {
        return SharePreferenceUtil.readBoolean(this, SPKeys.IS_KNOWN_KEY);
    }
}