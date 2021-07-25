package com.thoughtworks.androidtrain.data.source;

import com.thoughtworks.androidtrain.data.model.Comment;
import com.thoughtworks.androidtrain.data.model.Image;
import com.thoughtworks.androidtrain.data.model.Sender;
import com.thoughtworks.androidtrain.data.source.local.room.entity.CommentEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.ImageEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.SenderEntity;

public class ModelToEntity {

    static public SenderEntity senderToSenderEntity(Sender sender) {
        SenderEntity senderEntity = new SenderEntity();
        senderEntity.setUserName(sender.getUserName());
        senderEntity.setNick(sender.getNick());
        senderEntity.setAvatar(sender.getAvatar());
        return senderEntity;
    }

    static public CommentEntity commentToCommentEntity(Comment comment) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(comment.getContent());
        return commentEntity;
    }

    static public ImageEntity imageToImageEntity(Image image) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUrl(image.getUrl());
        return imageEntity;
    }
}
