package com.thoughtworks.androidtrain.data.source.local.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tweet {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "sender")
    public String sender;

    @ColumnInfo(name = "comment")
    public String comment;

    @ColumnInfo(name = "image")
    public String image;
}
