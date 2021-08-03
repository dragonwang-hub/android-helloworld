package com.thoughtworks.androidtrain.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.R;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.security.interfaces.DSAKey;
import java.util.stream.IntStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaActivity";

    Button btnRxjava;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        btnRxjava = findViewById(R.id.btnRxJava);
        btnRxjava.setOnClickListener(v -> initRxTimer());
    }

    private void initRxTimer() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                    IntStream.rangeClosed(0, 10)
                            .forEachOrdered(i -> {
                                emitter.onNext(i);
                                SystemClock.sleep(1000);
                            });
                    emitter.onComplete();
                }
        ).map(integer -> "This is Number: " + integer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable d) {
                                   Log.i(TAG, "On subscribe, set disposable.");
                                   btnRxjava.setClickable(false);
                                   compositeDisposable.add(d);
                               }

                               @Override
                               public void onNext(@NotNull String str) {
                                   Log.i(TAG, "Current string: " + str);
                                   btnRxjava.setText(str);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   Log.d(TAG, t.getMessage());
                               }

                               @SuppressLint("SetTextI18n")
                               @Override
                               public void onComplete() {
                                   Log.i(TAG, "on Completed.");
                                   btnRxjava.setText("Restart RxJava");
                                   btnRxjava.setClickable(true);
                               }
                           }
                );

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Clear disposable, on destroy step.");
        compositeDisposable.clear();
        super.onDestroy();
    }
}
