package com.thoughtworks.androidtrain.data.source;

import com.thoughtworks.androidtrain.data.source.remote.UserProfileDataSource;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserRepository {

    private final UserProfileDataSource userProfileDataSource;

    public UserRepository(UserProfileDataSource userProfileDataSource) {
        this.userProfileDataSource = userProfileDataSource;
    }

    public Single<String> fetchUser() {
        return userProfileDataSource.fetchUserFromAPI()
                .subscribeOn(Schedulers.io());
    }
}
