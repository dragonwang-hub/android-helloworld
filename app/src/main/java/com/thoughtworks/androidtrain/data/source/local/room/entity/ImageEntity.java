package com.thoughtworks.androidtrain.data.source.local.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "images",
        foreignKeys = @ForeignKey(entity = TweetEntity.class, parentColumns = "id", childColumns = "tweet_id")
)
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "tweet_id")
    public long tweetId;
}
