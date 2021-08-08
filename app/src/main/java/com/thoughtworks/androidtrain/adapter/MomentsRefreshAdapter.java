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
import com.thoughtworks.androidtrain.components.ExpandableHeightGridView;
import com.thoughtworks.androidtrain.components.ExpandableHeightListView;
import com.thoughtworks.androidtrain.data.model.Comment;
import com.thoughtworks.androidtrain.data.model.Image;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MomentsRefreshAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MomentsRefreshAdapter";

    private User userProfile = new User();

    private List<Tweet> moments = new ArrayList<>();

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
            int momentPosition = position - 1;
            MomentsViewHolder momentsViewHolder = (MomentsRefreshAdapter.MomentsViewHolder) holder;
            momentsViewHolder.nickName.setText(Optional.ofNullable(moments.get(momentPosition).getSender().getNick()).orElse("Nick Name Miss"));
            momentsViewHolder.content.setText(Optional.ofNullable(moments.get(momentPosition).getContent()).orElse("Non-Content"));
            Glide.with(holder.itemView.getContext()).load(moments.get(momentPosition).getSender().getAvatar()).into(momentsViewHolder.avatar);

            // images
            List<Image> images = moments.get(momentPosition).getImages();
            SimpleAdapter imagesAdapter = getImagesAdapter(images, momentsViewHolder.itemView.getContext());
            momentsViewHolder.images.setExpanded(true);
            momentsViewHolder.images.setAdapter(imagesAdapter);

            // comments
            List<Comment> comments = moments.get(momentPosition).getComments();
            SimpleAdapter commentsAdapter = getCommentsAdapter(comments, momentsViewHolder.itemView.getContext());
            momentsViewHolder.comments.setExpanded(true);
            momentsViewHolder.comments.setAdapter(commentsAdapter);
        }

    }

    @NonNull
    private SimpleAdapter getImagesAdapter(List<Image> images, Context context) {
        List<HashMap<String, Object>> imagesData = new ArrayList<>();
        images.forEach(image -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("image_item", image.getUrl());
            imagesData.add(map);
        });
        Log.i(TAG, "imagesData is: " + imagesData.size());
        SimpleAdapter imagesAdapter = new SimpleAdapter(context, imagesData, R.layout.moment_image_item,
                new String[]{"image_item"}, new int[]{R.id.image_item}
        );
        imagesAdapter.setViewBinder((view, data, textRepresentation) -> {
            if (view.getId() == R.id.image_item) {
                ImageView imageView = (ImageView) view;
                Glide.with(context).load(data).into(imageView);
                return true;
            }
            return false;
        });
        return imagesAdapter;
    }

    @NotNull
    private SimpleAdapter getCommentsAdapter(List<Comment> comments, Context context) {

        List<HashMap<String, String>> commentsData = new ArrayList<>();
        comments.forEach(comment -> {
            Log.i(TAG, "Comment is: " + comment.toString());
            HashMap<String, String> map = new HashMap<>();
            map.put("comment_sender", comment.getSender().getNick());
            map.put("comment_content", comment.getContent());
            commentsData.add(map);
        });
        Log.i(TAG, "commentsData is: " + commentsData.size());
        return new SimpleAdapter(context, commentsData, R.layout.moment_comments_item,
                new String[]{"comment_sender", "comment_content"}, new int[]{R.id.comment_sender, R.id.comment_content});
    }

    @Override
    public int getItemCount() {
        return moments.size() + userProfileCount;
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

    static class MomentsViewHolder extends RecyclerView.ViewHolder {
        TextView nickName;
        TextView content;
        ImageView avatar;
        ExpandableHeightListView comments;
        ExpandableHeightGridView images;

        public MomentsViewHolder(View viewItem) {
            super(viewItem);
            // get component
            this.nickName = viewItem.findViewById(R.id.sender_nickName);
            this.content = viewItem.findViewById(R.id.sender_content);
            this.avatar = viewItem.findViewById(R.id.sender_avatar);
            this.comments = viewItem.findViewById(R.id.moment_comments);
            this.images = viewItem.findViewById(R.id.moment_images);
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
