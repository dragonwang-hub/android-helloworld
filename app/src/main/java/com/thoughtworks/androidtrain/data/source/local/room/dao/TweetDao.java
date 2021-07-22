package com.thoughtworks.androidtrain.data.source.local.room.dao;

import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;

import com.thoughtworks.androidtrain.data.source.local.room.entity.Tweet;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TweetDao {
    @Query("SELECT * FROM tweet")
    Flowable<List<Tweet>> getAllTweets();

    @Insert
    Single<Long> insertTweets(Tweet... tweets);
}
