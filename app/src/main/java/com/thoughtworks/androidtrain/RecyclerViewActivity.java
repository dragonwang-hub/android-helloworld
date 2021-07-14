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
import com.thoughtworks.androidtrain.util.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

        ArrayList<Tweet> tweets = getTweetsFromJson();
        List<Tweet> moments = filterValidTweets(tweets);
        // initial data
        momentsAdapter.setMoments(moments);

        momentsRecyclerView.setAdapter(momentsAdapter);
        momentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Tweet> filterValidTweets(ArrayList<Tweet> tweets) {
        return tweets.stream().filter(tweet ->
            tweet.getError() == null && tweet.getUnknownError() == null
        ).collect(Collectors.toList());
    }

    private ArrayList<Tweet> getTweetsFromJson() {
        String fileName = "tweets.json";
        String json = JsonUtil.getJson(fileName, getApplicationContext());
        Log.i(TAG, json);

        Type arrayListType = new TypeToken<ArrayList<Tweet>>() {
        }.getType();
        return new Gson().fromJson(json, arrayListType);
    }

}
