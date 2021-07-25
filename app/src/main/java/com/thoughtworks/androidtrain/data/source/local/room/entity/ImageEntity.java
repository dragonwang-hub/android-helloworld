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
    private String url;

    @ColumnInfo(name = "tweet_id")
    private long tweetId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }
}
