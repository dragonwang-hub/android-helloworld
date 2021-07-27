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
import com.thoughtworks.androidtrain.data.source.local.room.entity.CommentEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.ImageEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.SenderEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.TweetEntity;
import com.thoughtworks.androidtrain.utils.RawUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        getTweetsFromInternet()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    Type arrayListType = new TypeToken<List<Tweet>>() {
                    }.getType();
                    List<Tweet> tweets = new Gson().fromJson(result, arrayListType);
                    List<Tweet> filterTweets = tweets.stream().filter(tweet ->
                            tweet.getError() == null && tweet.getUnknownError() == null
                    ).collect(Collectors.toList());

                    updateTweetsToDB(filterTweets).subscribeOn(Schedulers.io()).subscribe();

                    Log.i(TAG, "Update tweets to DB, result is " + result);
                });

        return getTweets();
    }

    private OkHttpClient okHttpClient = new OkHttpClient();

    private final String TWEETS_URL = "https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith/tweets";

    private Single<String> getTweetsFromInternet() {
        return Single.create(emitter -> {
            Request request = new Request.Builder().url(TWEETS_URL).build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                Log.i(TAG, "The response is: " + response.body());
                emitter.onSuccess(Objects.requireNonNull(response.body()).string());
            } catch (Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                emitter.onError(throwable);
            }
        });
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

    public Single<Boolean> updateTweetsToDB(List<Tweet> tweets) {
        return Single.create(emitter -> {
            try {
                appDataBase.clearAllTables();

                appDataBase.runInTransaction(() -> {
                    tweets.forEach(tweet -> {
                        TweetEntity tweetEntity = toRoomTweet(tweet);
                        tweetEntity.setSenderId(insertRoomSender(tweet.getSender()));
                        long tweetId = appDataBase.tweetDao().insert(tweetEntity).blockingGet();

                        if (tweet.getImages() != null) {
                            tweet.getImages().forEach(image -> {
                                ImageEntity imageEntity = toRoomImage(image, tweetId);
                                appDataBase.imageDao().insert(imageEntity).blockingGet();
                            });
                        }

                        if (tweet.getComments() != null) {
                            tweet.getComments().forEach(comment -> {
                                CommentEntity commentEntity = toRoomComment(comment, tweetId, insertRoomSender(comment.getSender()));
                                appDataBase.commentDao().insert(commentEntity).blockingGet();
                            });
                        }
                    });
                });
            } catch (Throwable t) {
                emitter.onError(t);
                return;
            }

            emitter.onSuccess(true);
        });
    }

    public Flowable<List<Tweet>> getTweets() {
        return appDataBase.tweetDao().getAll()
                .map(tweetEntities -> {
                    List<SenderEntity> senderEntities = appDataBase.senderDao().getAll().blockingFirst();
                    List<ImageEntity> imageEntities = appDataBase.imageDao().getAll().blockingFirst();
                    List<CommentEntity> commentEntities = appDataBase.commentDao().getAll().blockingFirst();

                    List<Tweet> tweets = new ArrayList<>();
                    for (TweetEntity tweetEntity : tweetEntities) {
                        Tweet tweet = toTweet(tweetEntity);
                        senderEntities.stream().filter(senderEntity1 -> senderEntity1.id == tweetEntity.getSenderId()).map(this::toSender).findFirst().ifPresent(tweet::setSender);
                        tweet.setImages(imageEntities.stream().filter(imageEntity -> imageEntity.getTweetId() == tweetEntity.id).map(imageEntity -> new Image(imageEntity.getUrl())).collect(Collectors.toList()));
                        tweet.setComments(commentEntities.stream().filter(commentEntity -> commentEntity.getTweetId() == tweetEntity.id).map(commentEntity -> new Comment(commentEntity.getContent(), senderEntities.stream().filter(senderEntity -> senderEntity.id == commentEntity.getSenderId()).map(this::toSender).findFirst().orElse(null))).collect(Collectors.toList()));
                        tweets.add(tweet);
                    }
                    return tweets;
                });
    }

    private Tweet toTweet(TweetEntity tweetEntity) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetEntity.getContent());

        return tweet;
    }

    private Sender toSender(SenderEntity senderEntity) {
        Sender sender = new Sender();
        sender.setUserName(senderEntity.getUserName());
        sender.setNick(senderEntity.getNick());
        sender.setAvatar(senderEntity.getAvatar());

        return sender;
    }

    private TweetEntity toRoomTweet(Tweet tweet) {
        TweetEntity tweetEntity = new TweetEntity();
        tweetEntity.setContent(tweet.getContent());
        tweetEntity.id = 0;
        return tweetEntity;
    }

    private long insertRoomSender(Sender sender) {
        SenderEntity senderEntity = toRoomSender(sender);
        return appDataBase.senderDao().insert(senderEntity).blockingGet();
    }

    private SenderEntity toRoomSender(Sender sender) {
        SenderEntity senderEntity = new SenderEntity();
        senderEntity.id = 0;
        senderEntity.setUserName(sender.getUserName());
        senderEntity.setNick(sender.getNick());
        senderEntity.setAvatar(sender.getAvatar());

        return senderEntity;
    }

    private ImageEntity toRoomImage(Image image, long tweetId) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.id = 0;
        imageEntity.setTweetId(tweetId);
        imageEntity.setUrl(image.getUrl());

        return imageEntity;
    }

    private CommentEntity toRoomComment(Comment comment, long tweetId, long senderId) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.id = 0;
        commentEntity.setTweetId(tweetId);
        commentEntity.setSenderId(senderId);
        commentEntity.setContent(comment.getContent());

        return commentEntity;
    }
}
