package com.thoughtworks.androidtrain.data.source.remote;

import android.util.Log;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TweetDataSource {

    private static final String TAG = "TweetDataSource";
    // network core code
    private OkHttpClient okHttpClient = new OkHttpClient();

    private final String TWEETS_URL = "https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith/tweets";


    public Single<String> fetchTweetsFromAPI() {
        return Single.create(emitter -> {
            Request request = new Request.Builder().url(TWEETS_URL).build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                Log.i(TAG, "The response is: " + response.body());
                emitter.onSuccess(Objects.requireNonNull(response.body()).string());
            } catch (Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                emitter.onError(throwable);
            }
        });
    }
}
