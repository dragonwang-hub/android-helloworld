package com.thoughtworks.androidtrain.data.source.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thoughtworks.androidtrain.data.source.local.room.dao.CommentDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.ImageDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.SenderDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.TweetDao;
import com.thoughtworks.androidtrain.data.source.local.room.entity.CommentEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.ImageEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.SenderEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.TweetEntity;

@Database(entities = {TweetEntity.class, SenderEntity.class, ImageEntity.class, CommentEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TweetDao tweetDao();

    public abstract SenderDao senderDao();

    public abstract ImageDao imageDao();

    public abstract CommentDao commentDao();
}
