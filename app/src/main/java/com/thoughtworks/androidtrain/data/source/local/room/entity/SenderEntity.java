package com.thoughtworks.androidtrain.data.source.local.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "senders")
public class SenderEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    private String userName;

    @ColumnInfo(name = "nick")
    private String nick;

    @ColumnInfo(name = "avatar")
    private String avatar;
}
