package com.thoughtworks.androidtrain.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.MainApplication;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.adapter.MomentsRefreshAdapter;
import com.thoughtworks.androidtrain.viewmodel.TweetViewModel;
import com.thoughtworks.androidtrain.viewmodel.UserViewModel;

import java.util.Objects;

public class MomentsActivity extends AppCompatActivity {

    private static final String TAG = "MomentsActivity";

    MomentsRefreshAdapter momentsRefreshAdapter = new MomentsRefreshAdapter();

    private UserViewModel userViewModel;
    private TweetViewModel tweetViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_refresh);
        hideStatusBarAndActionBar();

        initMomentRecyclerView();

        initUserViewModel();
        initTweetViewModel();

        getLiveData();
    }

    private void getLiveData() {
        userViewModel.getUser();
        tweetViewModel.fetchData(throwable -> {
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void initMomentRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.moments_recyclerview);
        recyclerView.setAdapter(momentsRefreshAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initUserViewModel() {
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(UserViewModel.class);
        MainApplication application = (MainApplication) getApplication();
        userViewModel.init(application.getSchedulersProvider(), application.getUserRepository());

        userViewModel.getUserMutableLiveData().observe(this, user -> {
            Log.i(TAG, "User info: " + user.toString());
            momentsRefreshAdapter.setUser(user);
        });
    }

    private void initTweetViewModel() {
        tweetViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(TweetViewModel.class);

        MainApplication application = (MainApplication) getApplication();
        tweetViewModel.init(application.getSchedulersProvider(), application.getTweetRepository());

        tweetViewModel.getLiveData().observe(this, tweets -> {
            Log.i(TAG, "Tweets info is: " + tweets.toString());
            momentsRefreshAdapter.setMoments(tweets);
        });

    }

    private void hideStatusBarAndActionBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}
