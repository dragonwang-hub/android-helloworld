package com.thoughtworks.androidtrain.data.source.local.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tweets",
        foreignKeys = @ForeignKey(entity = SenderEntity.class, parentColumns = "id", childColumns = "sender_id")
)
public class TweetEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "sender_id", index = true)
    private long senderId;

    @ColumnInfo(name = "comment")
    private String comment;

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
