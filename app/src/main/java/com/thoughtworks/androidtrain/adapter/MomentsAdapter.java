package com.thoughtworks.androidtrain.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.data.model.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MomentsAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MomentsAdapter";

    private final List<Tweet> moments = new ArrayList<>();

    private final int TYPE_MOMENT = 0;
    private final int TYPE_FOOTER = 1;

    private int footerCount = 1;

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = moments.size();
        if (position == dataItemCount) {
            return TYPE_FOOTER;
        }
        return TYPE_MOMENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater from = LayoutInflater.from(context);

        Log.d(TAG, "viewType:" + viewType);
        switch (viewType) {
            case TYPE_MOMENT:
                // moment item activity
                View momentView = from.inflate(R.layout.moments_itew_view, parent, false);
                return new MomentsViewHolder(momentView);
            case TYPE_FOOTER:
                // footer item activity
                View footerView = from.inflate(R.layout.footer_itew_view, parent, false);
                return new FooterViewHolder(footerView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).footer.setText("IT IS END!");
        } else {
            ((MomentsViewHolder) holder).nickName.setText(Optional.ofNullable(moments.get(position).getSender().getNick()).orElse("Nick Name Miss"));
            ((MomentsViewHolder) holder).content.setText(Optional.ofNullable(moments.get(position).getContent()).orElse("Non-Content"));
            ImageView avatar = ((MomentsViewHolder) holder).avatar;
            Glide.with(holder.itemView.getContext()).load(moments.get(position).getSender().getAvatar()).into(avatar);
        }
    }

    @Override
    public int getItemCount() {
        return moments.size() + footerCount;
    }

    public void setMoments(List<Tweet> validMoments) {
        moments.addAll(validMoments);
    }

    static class MomentsViewHolder extends RecyclerView.ViewHolder {
        TextView nickName;
        TextView content;
        ImageView avatar;

        public MomentsViewHolder(View viewItem) {
            super(viewItem);
            // get component
            this.nickName = viewItem.findViewById(R.id.nickName);
            this.content = viewItem.findViewById(R.id.content);
            this.avatar = viewItem.findViewById(R.id.avatar);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footer;

        public FooterViewHolder(View itemView) {
            super(itemView);
            this.footer = itemView.findViewById(R.id.footer);
        }
    }
}
