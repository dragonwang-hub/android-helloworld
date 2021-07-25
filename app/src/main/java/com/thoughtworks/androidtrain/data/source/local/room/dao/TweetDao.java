package com.thoughtworks.androidtrain.data.source.local.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thoughtworks.androidtrain.data.source.local.room.entity.TweetEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TweetDao {
    @Query("SELECT * FROM tweets")
    Flowable<List<TweetEntity>> getAllTweets();

    @Insert
    Single<Long> insertTweets(TweetEntity... tweets);
}
