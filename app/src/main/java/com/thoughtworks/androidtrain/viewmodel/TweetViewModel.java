package com.thoughtworks.androidtrain.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.TweetRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetViewModel extends AndroidViewModel {

    private static final String TAG = "TweetViewModel";

    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public TweetViewModel(Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<Tweet>> liveData = new MutableLiveData<>();

    public void fetchData(OnError onError) {
        TweetRepository tweetRepository = new TweetRepository(context);
        Log.i(TAG, "Fetch data from view model.");
        Disposable disposable = tweetRepository.fetchTweets(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tweets -> liveData.postValue(tweets), onError::onError);
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
