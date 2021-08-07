package com.thoughtworks.androidtrain.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.data.model.Comment;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MomentsRefreshAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MomentsRefreshAdapter";

    private User userProfile = new User();

    private List<Tweet> moments = new ArrayList<>();

    private SimpleAdapter commentsAdapter;

    private final int TYPE_MOMENT = 0;
    private final int TYPE_USER = 1;

    private int userProfileCount = 1;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_USER;
        }
        return TYPE_MOMENT;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater from = LayoutInflater.from(context);

        Log.d(TAG, "viewType:" + viewType);
        switch (viewType) {
            case TYPE_USER:
                View userView = from.inflate(R.layout.moments_refresh_header_view, parent, false);
                return new HeaderViewHolder(userView);
            case TYPE_MOMENT:
                View momentView = from.inflate(R.layout.moments_refresh_item_view, parent, false);
                return new MomentsViewHolder(momentView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (MomentsRefreshAdapter.HeaderViewHolder) holder;
            headerViewHolder.userNickName.setText(userProfile.getNick());
            Glide.with(holder.itemView.getContext()).load(userProfile.getAvatar()).into(headerViewHolder.userAvatar);
            Log.i(TAG, "User profile image: " + userProfile.getProfileImage());
            Log.i(TAG, "User getAvatar: " + userProfile.getAvatar());

            Glide.with(holder.itemView.getContext()).load(userProfile.getProfileImage()).into(headerViewHolder.userProfileImage);
        } else {
            MomentsViewHolder momentsViewHolder = (MomentsRefreshAdapter.MomentsViewHolder) holder;
            momentsViewHolder.nickName.setText(Optional.ofNullable(moments.get(position).getSender().getNick()).orElse("Nick Name Miss"));
            momentsViewHolder.content.setText(Optional.ofNullable(moments.get(position).getContent()).orElse("Non-Content"));
            Glide.with(holder.itemView.getContext()).load(moments.get(position).getSender().getAvatar()).into(momentsViewHolder.avatar);

            List<Comment> comments = moments.get(position).getComments();
            SimpleAdapter commentAdapter = getSimpleAdapter(comments, momentsViewHolder.itemView.getContext());

            momentsViewHolder.comments.setAdapter(commentAdapter);
        }

    }

    @NotNull
    private SimpleAdapter getSimpleAdapter(List<Comment> comments, Context context) {
        List<HashMap<String, Object>> commentsData = comments.stream().map(comment -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("comment_sender", comment.getSender().getNick());
            map.put("comment_content", comment.getContent());
            return map;
        }).collect(Collectors.toList());

        return new SimpleAdapter(context, commentsData, R.layout.moment_comments_item,
                new String[]{"comment_sender", "comment_content"}, new int[]{R.id.comment_sender, R.id.comment_content});
    }

    @Override
    public int getItemCount() {
        return moments.size();
    }

    public void setUser(User user) {
        userProfile = user;
        notifyDataSetChanged();
    }

    public void setMoments(List<Tweet> validMoments) {
        moments.clear();
        moments.addAll(validMoments);
        notifyDataSetChanged();
    }

    public void setCommentsAdapter(SimpleAdapter simpleAdapter) {
        this.commentsAdapter = simpleAdapter;
        notifyDataSetChanged();
    }

    static class MomentsViewHolder extends RecyclerView.ViewHolder {
        TextView nickName;
        TextView content;
        ImageView avatar;
        ListView comments;

        public MomentsViewHolder(View viewItem) {
            super(viewItem);
            // get component
            this.nickName = viewItem.findViewById(R.id.sender_nickName);
            this.content = viewItem.findViewById(R.id.sender_content);
            this.avatar = viewItem.findViewById(R.id.sender_avatar);
            this.comments = viewItem.findViewById(R.id.moment_comments);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView userProfileImage;
        TextView userNickName;
        ImageView userAvatar;

        public HeaderViewHolder(View userItem) {
            super(userItem);

            this.userProfileImage = userItem.findViewById(R.id.profile_image);
            this.userNickName = userItem.findViewById(R.id.user_nickname);
            this.userAvatar = userItem.findViewById(R.id.user_avatar);
        }
    }
}
