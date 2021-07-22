package com.thoughtworks.androidtrain;

import android.app.Application;

import androidx.room.Room;

import com.thoughtworks.androidtrain.data.source.local.room.AppDataBase;

public class HelloApplication extends Application {

    private AppDataBase roomDB;

    @Override
    public void onCreate() {
        super.onCreate();
        initRoomDB();
    }

    public AppDataBase getRoomDB() {
        return roomDB;
    }

    private void initRoomDB() {
        roomDB = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "hello_room_db").build();
    }
}
