package com.thoughtworks.androidtrain;

import android.app.Application;

import com.thoughtworks.androidtrain.data.source.TweetRepository;
import com.thoughtworks.androidtrain.data.source.remote.TweetDataSource;

public class MainApplication extends Application {

    private TweetRepository tweetRepository;
    private TweetDataSource tweetDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        initSingletonData();
        System.out.println("MAIN INIT DATA");
    }

    private void initSingletonData() {
        tweetDataSource = new TweetDataSource();
        tweetRepository = new TweetRepository(getApplicationContext(), tweetDataSource);
    }

    public TweetDataSource getTweetDataSource() {
        return tweetDataSource;
    }

    public TweetRepository getTweetRepository() {
        return tweetRepository;
    }
}
