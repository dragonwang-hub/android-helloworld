package com.thoughtworks.androidtrain.data.source.remote;

import android.util.Log;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserProfileDataSource {
    private static final String TAG = "UserProfileDataSource";

    private OkHttpClient okHttpClient = new OkHttpClient();

    private final String USER_PROFILE_URL = "https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith";

    public Single<String> fetchUserFromAPI() {
        return Single.create(emitter -> {
            Request request = new Request.Builder().url(USER_PROFILE_URL).build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                Log.i(TAG, "The user profile response is:" + response.body());
                emitter.onSuccess(Objects.requireNonNull(response.body()).string());
            } catch (Throwable throwable) {
                Log.i(TAG, "Fetch user info failed, the message is: " + throwable.getMessage());
                emitter.onError(throwable);
            }
        });
    }
}
