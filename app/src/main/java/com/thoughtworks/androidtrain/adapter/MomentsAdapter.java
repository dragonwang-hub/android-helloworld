package com.thoughtworks.androidtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MomentsAdapter extends RecyclerView.Adapter<MomentsAdapter.MomentsViewHolder> {

    // TODO String change to obj `moment`
    ArrayList<String> moments;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater from = LayoutInflater.from(context);

        // item activity
        View momentView = from.inflate(R.layout.moments_itew_view, parent, false);

        return new MomentsViewHolder(momentView);
    }

    @Override
    public void onBindViewHolder(MomentsAdapter.MomentsViewHolder holder, int position) {
        MomentsViewHolder momentsViewHolder = (MomentsViewHolder) holder;

        // TODO set text as `position`
        momentsViewHolder.name.setText("xxxx");
    }

    @Override
    public int getItemCount() {
        return moments.size();
    }

    public void setMoments() {
        //TODO add the data to list
    }

    static class MomentsViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MomentsViewHolder(View viewItem) {
            super(viewItem);
            // get component
            this.name = viewItem.findViewById(R.id.nickName);
        }
    }
}
