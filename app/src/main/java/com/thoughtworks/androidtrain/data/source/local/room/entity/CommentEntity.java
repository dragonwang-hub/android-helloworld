package com.thoughtworks.androidtrain.data.source.local.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "comments",
        foreignKeys = {
                @ForeignKey(entity = SenderEntity.class, parentColumns = "id", childColumns = "sender_id"),
                @ForeignKey(entity = TweetEntity.class, parentColumns = "id", childColumns = "tweet_id")
        }
)
public class CommentEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "sender_id", index = true)
    public int senderId;

    @ColumnInfo(name = "tweet_id", index = true)
    public int tweetId;
}
