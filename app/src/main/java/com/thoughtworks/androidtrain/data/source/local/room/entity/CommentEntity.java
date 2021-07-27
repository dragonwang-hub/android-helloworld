package com.thoughtworks.androidtrain.data.source.local.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "comments",
        foreignKeys = {
                @ForeignKey(entity = SenderEntity.class, parentColumns = "id", childColumns = "sender_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = TweetEntity.class, parentColumns = "id", childColumns = "tweet_id", onDelete = ForeignKey.CASCADE)
        }
)
public class CommentEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "sender_id", index = true)
    private long senderId;

    @ColumnInfo(name = "tweet_id", index = true)
    private long tweetId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }
}
