package com.thoughtworks.androidtrain.data.source;


import com.thoughtworks.androidtrain.data.model.Comment;
import com.thoughtworks.androidtrain.data.model.Image;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.local.room.AppDataBase;
import com.thoughtworks.androidtrain.data.source.local.room.entity.CommentEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.ImageEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.SenderEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;

public class TweetRepository implements DataSource {

    private final AppDataBase appDataBase;

    public TweetRepository(AppDataBase appDataBase) {
        this.appDataBase = appDataBase;
    }

    @Override
    public Flowable<List<Tweet>> fetchTweets() {
        return getTweets();
    }

    private Flowable<List<Tweet>> getTweets() {
        List<SenderEntity> senderEntities = appDataBase.senderDao().getAll().blockingFirst();
        List<CommentEntity> commentEntities = appDataBase.commentDao().getAll().blockingFirst();
        List<ImageEntity> imageEntities = appDataBase.imageDao().getAll().blockingFirst();

        return appDataBase.tweetDao().getAll().map(tweetEntities -> {
            List<Tweet> tweets = new ArrayList<>();

            tweetEntities.forEach(tweetEntity -> {
                Tweet tweet = new Tweet();
                SenderEntity senderEntity = senderEntities.stream()
                        .filter(entity -> tweetEntity.senderId == entity.id)
                        .findFirst()
                        .orElse(null);
                List<Comment> comments = commentEntities.stream()
                        .filter(entity -> entity.tweetId == tweetEntity.id)
                        .map(entity -> EntityToModel.commentEntityToComment(entity, senderEntities))
                        .collect(Collectors.toList());
                List<Image> images = imageEntities.stream()
                        .filter(entity -> entity.tweetId == tweetEntity.id)
                        .map(EntityToModel::imageEntityToImage)
                        .collect(Collectors.toList());

                tweet.setContent(tweetEntity.comment);
                tweet.setSender(EntityToModel.senderEntityToSender(senderEntity));
                tweet.setComments(comments);
                tweet.setImages(images);
                tweets.add(tweet);
            });
            return tweets;
        });
    }
}
