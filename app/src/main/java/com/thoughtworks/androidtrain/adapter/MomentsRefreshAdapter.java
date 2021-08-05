package com.thoughtworks.androidtrain.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.data.model.User;

import org.jetbrains.annotations.NotNull;

public class MomentsRefreshAdapter extends RecyclerView.Adapter {

    private User userProfile = new User();

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setUser(User user) {
        userProfile = user;
        notifyDataSetChanged();
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

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
