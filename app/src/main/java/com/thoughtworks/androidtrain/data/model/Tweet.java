package com.thoughtworks.androidtrain.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tweet {
    private String content;
    private List<Image> images;
    private Sender sender;
    private List<Comment> comments;
    private String error;
    @SerializedName("unknown error")
    private String unknownError;

    public Tweet() {

    }

    public Tweet(String content, List<Image> images, Sender sender, List<Comment> comments) {
        this.content = content;
        this.images = images;
        this.sender = sender;
        this.comments = comments;
    }

    public String getContent() {
        return content;
    }

    public Tweet setContent(String content) {
        this.content = content;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public Tweet setImages(List<Image> images) {
        this.images = images;
        return this;
    }

    public Sender getSender() {
        return sender;
    }

    public Tweet setSender(Sender sender) {
        this.sender = sender;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Tweet setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUnknownError() {
        return unknownError;
    }

    public void setUnknownError(String unknownError) {
        this.unknownError = unknownError;
    }

    @Override
    public String toString() {
        return "content " + content + " \n";
    }
}
