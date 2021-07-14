package com.thoughtworks.androidtrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.data.model.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MomentsAdapter extends RecyclerView.Adapter<MomentsAdapter.MomentsViewHolder> {

    private List<Tweet> moments = new ArrayList<>();

    @Override
    public MomentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater from = LayoutInflater.from(context);

        // item activity
        View momentView = from.inflate(R.layout.moments_itew_view, parent, false);

        return new MomentsViewHolder(momentView);
    }

    @Override
    public void onBindViewHolder(MomentsAdapter.MomentsViewHolder holder, int position) {
        holder.nickName.setText(moments.get(position).getSender().getNick());
        holder.content.setText(Optional.ofNullable(moments.get(position).getContent()).orElse("Non-Content"));
    }

    @Override
    public int getItemCount() {
        return moments.size();
    }

    public void setMoments(List<Tweet> validMoments) {
        moments.addAll(validMoments);
    }

    static class MomentsViewHolder extends RecyclerView.ViewHolder {
        TextView nickName;
        TextView content;

        public MomentsViewHolder(View viewItem) {
            super(viewItem);
            // get component
            this.nickName = viewItem.findViewById(R.id.nickName);
            this.content = viewItem.findViewById(R.id.content);
        }
    }
}
