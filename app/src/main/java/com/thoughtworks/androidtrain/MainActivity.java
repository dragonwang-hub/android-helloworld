package com.thoughtworks.androidtrain;

import androidx.appcompat.app.AppCompatActivity;

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
        pickContact.setOnClickListener(v -> selectContact());
    }

    private void openLoginActivity() {
        Intent openLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(openLoginActivity);
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