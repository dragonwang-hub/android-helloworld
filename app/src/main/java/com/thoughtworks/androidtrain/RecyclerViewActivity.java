package com.thoughtworks.androidtrain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.adapter.MomentsAdapter;

public class RecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initRecyclerView();
    }

    private void initRecyclerView() {
        // by id get the recycler view component
        RecyclerView momentsRecyclerView = findViewById(R.id.recyclerView);

        MomentsAdapter momentsAdapter = new MomentsAdapter();
        // initial data
        momentsAdapter.setMoments();

        momentsRecyclerView.setAdapter(momentsAdapter);
        momentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
