package com.thoughtworks.androidtrain.data.source;

import android.util.Log;

import com.google.gson.Gson;
import com.thoughtworks.androidtrain.data.model.User;
import com.thoughtworks.androidtrain.data.source.remote.UserProfileDataSource;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class UserRepository {

    private static final String TAG = "UserRepository";

    private final UserProfileDataSource userProfileDataSource;

    public UserRepository(UserProfileDataSource userProfileDataSource) {
        this.userProfileDataSource = userProfileDataSource;
    }

    public Single<User> fetchUser() {
        return Single.create(emitter -> {
                    String json = userProfileDataSource.fetchUserFromAPI().blockingGet();
                    Log.i(TAG, "User json: " + json);
                    emitter.onSuccess(new Gson().fromJson(json, User.class));
                }
        );
    }
}
