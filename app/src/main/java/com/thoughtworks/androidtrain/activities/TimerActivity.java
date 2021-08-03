package com.thoughtworks.androidtrain.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.R;

import java.util.stream.IntStream;

@SuppressLint({"SetTextI18n", "StaticFieldLeak"})
public class TimerActivity extends AppCompatActivity {

    private static final String TAG = "TimerActivity";
    Button startTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        startTimer = findViewById(R.id.btnStartTimer);
        startTimer.setOnClickListener(v -> {
            // Async Task execute()
            TimerAysncTask timerAysncTask = new TimerAysncTask();
            timerAysncTask.execute();
        });
    }

    class TimerAysncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startTimer.setClickable(false);
            Log.i(TAG, "Start Timer Task.");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            IntStream.rangeClosed(0, 10).forEach(item -> {
                publishProgress(item);
                Log.i(TAG, "Publish progress Timer Task." + item);
                SystemClock.sleep(1000);
            });
            return "Task Done.";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            startTimer.setText(values[0].toString());
            Log.i(TAG, "Update the button text.");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            startTimer.setText("Start");
            startTimer.setClickable(true);
            Log.i(TAG, "On post execute " + result);
        }
    }
}
