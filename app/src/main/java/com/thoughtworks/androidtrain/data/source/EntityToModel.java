package com.thoughtworks.androidtrain.data.source;

import com.thoughtworks.androidtrain.data.model.Comment;
import com.thoughtworks.androidtrain.data.model.Image;
import com.thoughtworks.androidtrain.data.model.Sender;
import com.thoughtworks.androidtrain.data.source.local.room.entity.CommentEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.ImageEntity;
import com.thoughtworks.androidtrain.data.source.local.room.entity.SenderEntity;

import java.util.List;

public class EntityToModel {

    static public Sender senderEntityToSender(SenderEntity senderEntity) {
        return new Sender(senderEntity.getUserName(), senderEntity.getNick(), senderEntity.getAvatar());
    }

    static public Comment commentEntityToComment(CommentEntity commentEntity, List<SenderEntity> senderEntityList) {
        SenderEntity senderEntity = senderEntityList.stream().filter(item ->
                item.id == commentEntity.getSenderId()
        ).findFirst().orElse(null);
        return new Comment(commentEntity.getContent(), senderEntityToSender(senderEntity));
    }

    static public Image imageEntityToImage(ImageEntity imageEntity) {
        return new Image(imageEntity.getUrl());
    }
}
