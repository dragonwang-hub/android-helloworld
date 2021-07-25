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
    public int id;

    @ColumnInfo(name = "sender_id", index = true)
    public int senderId;

    @ColumnInfo(name = "comment")
    public String comment;
}
