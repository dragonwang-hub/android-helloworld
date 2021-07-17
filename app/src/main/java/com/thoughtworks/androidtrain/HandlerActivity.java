package com.thoughtworks.androidtrain;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HandlerActivity extends AppCompatActivity {

    private static final String TAG = "HandlerActivity";

    private final int MSG_HANDLER_ONE = 1;
    private final int MSG_HANDLER_TWO = 2;

    private Handler subThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        Button btnHandlerOne = findViewById(R.id.btnHandleOne);
        btnHandlerOne.setOnClickListener(v -> {
            subThreadHandler.sendEmptyMessage(MSG_HANDLER_ONE);
        });

        Button btnHandlerTwo = findViewById(R.id.btnHandleTwo);
        btnHandlerTwo.setOnClickListener(v -> {
            subThreadHandler.sendEmptyMessage(MSG_HANDLER_TWO);
        });

        initHandlerThread();

    }

    private void initHandlerThread() {

        // create the  HandlerThread instance
        HandlerThread handlerThread = new HandlerThread("Handler_Thread");
        // start handler thread
        handlerThread.start();
        // get handlerthread looper for create handler of handlerthread
        Looper looper = handlerThread.getLooper();

        // create handler
        subThreadHandler = new Handler(looper) {
            public void handleMessage(Message message) {
                Log.i(TAG, "subThreadHandler" + Thread.currentThread());
                switch (message.what) {
                    case MSG_HANDLER_ONE:
                        // do something on handlerthread
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                                "IT IS HANDLER ONE BUTTON EVENT!",
                                Toast.LENGTH_SHORT).show()
                        );
                        break;
                    case MSG_HANDLER_TWO:
                        // do something on handlerthread
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                                "IT IS HANDLER TWO BUTTON EVENT!",
                                Toast.LENGTH_SHORT).show()
                        );
                        break;
                    default:
                        break;
                }
            }
        };


    }


}
