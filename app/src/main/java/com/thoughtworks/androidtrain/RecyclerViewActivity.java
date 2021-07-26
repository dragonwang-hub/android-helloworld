package com.thoughtworks.androidtrain;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.androidtrain.adapter.MomentsAdapter;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.TweetRepository;
import com.thoughtworks.androidtrain.data.source.local.room.AppDataBase;
import com.thoughtworks.androidtrain.utils.JsonUtil;
import com.thoughtworks.androidtrain.utils.RawUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        hideStatusBarAndActionBar();
        initRecyclerView();
    }

    private void hideStatusBarAndActionBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void initRecyclerView() {
        // by id get the recycler view component
        RecyclerView momentsRecyclerView = findViewById(R.id.recyclerView);

        MomentsAdapter momentsAdapter = new MomentsAdapter();

        momentsRecyclerView.setAdapter(momentsAdapter);
        momentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TweetRepository tweetRepository = new TweetRepository(getApplicationContext());

        Disposable subscribe = tweetRepository.fetchTweets(R.raw.tweets)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.i(TAG, "Fetch tweets result:" + result);
                    momentsAdapter.setMoments(filterValidTweets(result));
                });

//        Disposable subscribe = Observable.just(getTweetsFromJson())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result -> {
//                    Log.i(TAG,"RxJava get file result:" + result);
//                    momentsAdapter.setMoments(filterValidTweets(result));
//                });
        compositeDisposable.add(subscribe);
    }

    private List<Tweet> filterValidTweets(List<Tweet> tweets) {
        return tweets.stream().filter(tweet ->
                tweet.getError() == null && tweet.getUnknownError() == null
        ).collect(Collectors.toList());
    }

    private List<Tweet> getTweetsFromJson() {
//        use assets
//        String fileName = "tweets.json";
//        String json = JsonUtil.getJson(fileName, getApplicationContext());

//        use raw
        String json = RawUtil.readFileToString(getApplicationContext(), R.raw.tweets);
        Log.i(TAG, json);

        Type arrayListType = new TypeToken<List<Tweet>>() {
        }.getType();
        return new Gson().fromJson(json, arrayListType);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Clear disposable, on destroy step.");
        compositeDisposable.clear();
        super.onDestroy();
    }
}
