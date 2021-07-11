package com.thoughtworks.androidtrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_main);

        Log.d(TAG, "MainActivity Creater");

        Button button = (Button) findViewById(R.id.ToConstraintButton);
        button.setOnClickListener(v -> openConstraintActivity());

        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(v -> openLoginActivity());

        Button pickContact = (Button) findViewById(R.id.pickContact);
        pickContact.setOnClickListener(v -> selectContact());

        Button btnFragment = (Button) findViewById(R.id.fragment);
        btnFragment.setOnClickListener(v->openFragmentActivity());
    }

    private void openFragmentActivity() {
        Intent fragmentActivity = new Intent(this, FragmentDemoActivity.class)
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

            if (cursor != null && cursor.moveToFirst()){
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
}