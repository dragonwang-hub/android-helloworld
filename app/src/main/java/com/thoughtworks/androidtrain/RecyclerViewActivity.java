package com.thoughtworks.androidtrain;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.adapter.MomentsAdapter;
import com.thoughtworks.androidtrain.viewmodel.TweetViewModel;

import java.util.Objects;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";
    private TweetViewModel tweetViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        hideStatusBarAndActionBar();
        initRecyclerView();

        tweetViewModel.fetchData(throwable -> {
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });
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

        tweetViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory((MainApplication)this.getApplication()))
                .get(TweetViewModel.class);

        tweetViewModel.getLiveData().observe(this, tweets -> {
            Log.i(TAG, "Fetch tweets result:" + tweets);
            momentsAdapter.setMoments(tweets);
        });
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Clear disposable, on destroy step.");
        super.onDestroy();
    }
}
