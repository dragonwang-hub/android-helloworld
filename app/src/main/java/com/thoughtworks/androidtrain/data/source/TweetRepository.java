package com.thoughtworks.androidtrain.data.source;


import android.content.Context;
import android.util.Log;

import androidx.annotation.RawRes;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.thoughtworks.androidtrain.utils.RawUtil;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscription;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetRepository implements DataSource {

    private static final String TAG = "TweetRepository";

    private final AppDataBase appDataBase;

    private final Context context;

    public TweetRepository(Context context) {
        this.appDataBase = Room.databaseBuilder(context,
                AppDataBase.class, "hello_room_db").build();
        this.context = context;
    }

    @Override
    public Flowable<List<Tweet>> fetchTweets(@RawRes int id) {
        updateTweetsToDB(getValidTweetsFromRaw(id))
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    Log.i(TAG, "Update tweets to DB, result is " + result);
                });

        return getTweets();
    }

    private Flowable<List<Tweet>> getTweets() {
        List<SenderEntity> senderEntities = appDataBase.senderDao().getAll().blockingFirst();
        List<CommentEntity> commentEntities = appDataBase.commentDao().getAll().blockingFirst();
        List<ImageEntity> imageEntities = appDataBase.imageDao().getAll().blockingFirst();

        return appDataBase.tweetDao().getAll().map(tweetEntities -> {
            List<Tweet> tweets = new ArrayList<>();

            Log.d(TAG, "SenderEntity list is: " + senderEntities.toString());
            Log.d(TAG, "CommentEntity list is: " + commentEntities.toString());
            Log.d(TAG, "ImageEntity list is: " + imageEntities.toString());

            tweetEntities.forEach(tweetEntity -> {
                Tweet tweet = new Tweet();
                senderEntities.stream()
                        .filter(entity -> tweetEntity.getSenderId() == entity.id)
                        .map(EntityToModel::senderEntityToSender)
                        .findFirst().ifPresent(tweet::setSender);

                List<Comment> comments = commentEntities.stream()
                        .filter(entity -> entity.getTweetId() == tweetEntity.id)
                        .map(entity -> EntityToModel.commentEntityToComment(entity, senderEntities))
                        .collect(Collectors.toList());
                List<Image> images = imageEntities.stream()
                        .filter(entity -> entity.getTweetId() == tweetEntity.id)
                        .map(EntityToModel::imageEntityToImage)
                        .collect(Collectors.toList());

                tweet.setContent(tweetEntity.getContent());
                tweet.setComments(comments);
                tweet.setImages(images);
                Log.d(TAG, "The tweet is " + tweet.toString());
                tweets.add(tweet);
            });
            return tweets;
        });
    }

    private Single<Boolean> updateTweetsToDB(List<Tweet> tweets) {
        return Single.create(emitter -> {
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

                        if (tweet.getComments() != null) {
                            tweet.getComments().forEach(comment -> {
                                CommentEntity commentEntity = ModelToEntity.commentToCommentEntity(comment);
                                commentEntity.setContent(comment.getContent());
                                commentEntity.setTweetId(tweetId);
                                // set comment sender
                                Long commentSenderId = insertToSenderDao(senderDao, comment.getSender());
                                commentEntity.setSenderId(commentSenderId);
                                commentDao.insert(commentEntity).blockingGet();
                            });
                        }

                        if (tweet.getImages() != null) {
                            tweet.getImages().forEach(image -> {
                                ImageEntity imageEntity = ModelToEntity.imageToImageEntity(image);
                                imageEntity.setTweetId(tweetId);
                                imageDao.insert(imageEntity).blockingGet();
                            });
                        }
                    });
                });
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                emitter.onError(e);
                return;
            }
            emitter.onSuccess(true);
        });
    }

    @NotNull
    private Long insertToSenderDao(SenderDao senderDao, Sender sender) {
        SenderEntity senderEntity = ModelToEntity.senderToSenderEntity(sender);
        return senderDao.insert(senderEntity).blockingGet();
    }

    private List<Tweet> getValidTweetsFromRaw(@RawRes int id) {
        String json = RawUtil.readFileToString(context, id);
        Log.i(TAG, json);

        Type arrayListType = new TypeToken<List<Tweet>>() {
        }.getType();
        List<Tweet> tweets = new Gson().fromJson(json, arrayListType);
        return tweets.stream().filter(tweet ->
                tweet.getError() == null && tweet.getUnknownError() == null
        ).collect(Collectors.toList());
    }
}
