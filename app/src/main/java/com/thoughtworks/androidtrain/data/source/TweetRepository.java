package com.thoughtworks.androidtrain.data.source;


import android.util.Log;

import com.thoughtworks.androidtrain.data.model.Comment;
import com.thoughtworks.androidtrain.data.model.Image;
import com.thoughtworks.androidtrain.data.model.Sender;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.local.room.AppDataBase;
import com.thoughtworks.androidtrain.data.source.local.room.dao.CommentDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.ImageDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.SenderDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.TweetDao;
import com.thoughtworks.androidtrain.data.source.local.room.entity.CommentEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.ImageEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.SenderEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.TweetEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;

public class TweetRepository implements DataSource {

    private static final String TAG = "TweetRepository";

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
                        .filter(entity -> tweetEntity.getSenderId() == entity.id)
                        .findFirst()
                        .orElse(null);
                List<Comment> comments = commentEntities.stream()
                        .filter(entity -> entity.getTweetId() == tweetEntity.id)
                        .map(entity -> EntityToModel.commentEntityToComment(entity, senderEntities))
                        .collect(Collectors.toList());
                List<Image> images = imageEntities.stream()
                        .filter(entity -> entity.getTweetId() == tweetEntity.id)
                        .map(EntityToModel::imageEntityToImage)
                        .collect(Collectors.toList());

                tweet.setContent(tweetEntity.getContent());
                tweet.setSender(EntityToModel.senderEntityToSender(senderEntity));
                tweet.setComments(comments);
                tweet.setImages(images);
                tweets.add(tweet);
            });
            return tweets;
        });
    }

    private boolean updateTweetsToDB(List<Tweet> tweets) {
        appDataBase.clearAllTables();

        TweetDao tweetDao = appDataBase.tweetDao();
        SenderDao senderDao = appDataBase.senderDao();
        CommentDao commentDao = appDataBase.commentDao();
        ImageDao imageDao = appDataBase.imageDao();

        try {
            appDataBase.runInTransaction(() -> {
                tweets.forEach(tweet -> {
                    Long senderId = insertToSenderDao(senderDao, tweet.getSender());

                    TweetEntity tweetEntity = new TweetEntity();
                    tweetEntity.setContent(tweet.getContent());
                    tweetEntity.setSenderId(senderId);
                    Long tweetId = tweetDao.insert(tweetEntity).blockingGet();

                    tweet.getComments().forEach(comment -> {
                        CommentEntity commentEntity = ModelToEntity.commentToCommentEntity(comment);
                        commentEntity.setContent(comment.getContent());
                        commentEntity.setTweetId(tweetId);
                        // set comment sender
                        Long commentSenderId = insertToSenderDao(senderDao, comment.getSender());
                        commentEntity.setSenderId(commentSenderId);
                        commentDao.insert(commentEntity);
                    });

                    tweet.getImages().forEach(image -> {
                        ImageEntity imageEntity = ModelToEntity.imageToImageEntity(image);
                        imageEntity.setTweetId(tweetId);
                        imageDao.insert(imageEntity);
                    });
                });
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }

        return true;
    }

    @NotNull
    private Long insertToSenderDao(SenderDao senderDao, Sender sender) {
        SenderEntity senderEntity = ModelToEntity.senderToSenderEntity(sender);
        return senderDao.insert(senderEntity).blockingGet();
    }
}
