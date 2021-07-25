package com.thoughtworks.androidtrain.data.source.local.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thoughtworks.androidtrain.data.source.local.room.entity.CommentEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface CommentDao {
    @Query("SELECT * FROM comments")
    Flowable<List<CommentEntity>> getAll();

    @Insert
    Single<Long> insert(CommentEntity commentEntity);
}
