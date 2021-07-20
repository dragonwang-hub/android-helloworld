package com.thoughtworks.androidtrain;

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
import com.thoughtworks.androidtrain.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";

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

        List<Tweet> tweets = getTweetsFromJson();
        List<Tweet> moments = filterValidTweets(tweets);
        // initial data
        momentsAdapter.setMoments(moments);
    }

    private List<Tweet> filterValidTweets(List<Tweet> tweets) {
        return tweets.stream().filter(tweet ->
                tweet.getError() == null && tweet.getUnknownError() == null
        ).collect(Collectors.toList());
    }

    private List<Tweet> getTweetsFromJson() {
        String fileName = "tweets.json";
        String json = JsonUtil.getJson(fileName, getApplicationContext());
        Log.i(TAG, json);

        Type arrayListType = new TypeToken<List<Tweet>>() {
        }.getType();
        return new Gson().fromJson(json, arrayListType);
    }

}
