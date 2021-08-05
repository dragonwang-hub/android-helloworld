package com.thoughtworks.androidtrain.data.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName(value = "userName", alternate = "username")
    private String userName;
    private String nick;
    private String avatar;
    @SerializedName(value = "profileImage", alternate = "profile-image")
    private String profileImage;

    public User() {

    }

    public User(String userName, String nick, String avatar, String profileImage) {
        this.userName = userName;
        this.nick = nick;
        this.avatar = avatar;
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
