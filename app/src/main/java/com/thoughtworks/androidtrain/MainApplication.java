package com.thoughtworks.androidtrain;

import android.app.Application;

import com.thoughtworks.androidtrain.data.source.TweetRepository;
import com.thoughtworks.androidtrain.data.source.remote.TweetDataSource;
import com.thoughtworks.androidtrain.schedulers.SchedulersProvider;

public class MainApplication extends Application {

    private TweetRepository tweetRepository;
    private TweetDataSource tweetDataSource;
    private SchedulersProvider schedulersProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        initSingletonData();
        System.out.println("MAIN INIT DATA");
    }

    private void initSingletonData() {
        tweetDataSource = new TweetDataSource();
        tweetRepository = new TweetRepository(getApplicationContext(), tweetDataSource);
        schedulersProvider = new SchedulersProvider();
    }

    public TweetDataSource getTweetDataSource() {
        return tweetDataSource;
    }

    public TweetRepository getTweetRepository() {
        return tweetRepository;
    }

    public SchedulersProvider getSchedulersProvider() {
        return schedulersProvider;
    }
}
