package com.thoughtworks.androidtrain.data.source.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thoughtworks.androidtrain.data.source.local.room.dao.TweetDao;
import com.thoughtworks.androidtrain.data.source.local.room.entity.Tweet;

@Database(entities = {Tweet.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TweetDao tweetDao();
}
