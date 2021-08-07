package com.thoughtworks.androidtrain.data.model;

import org.jetbrains.annotations.NotNull;

public class Comment {
    private String content;
    private Sender sender;

    public Comment(String content, Sender sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Sender getSender() {
        return sender;
    }

    public Comment setSender(Sender sender) {
        this.sender = sender;
        return this;
    }

    @Override
    public @NotNull String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", sender=" + sender.getNick() +
                '}';
    }
}
