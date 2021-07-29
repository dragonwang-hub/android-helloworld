package com.thoughtworks.androidtrain.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.androidtrain.MainApplication;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.TweetRepository;
import com.thoughtworks.androidtrain.schedulers.SchedulersProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetViewModel extends ViewModel {

    private static final String TAG = "TweetViewModel";

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Tweet>> liveData = new MutableLiveData<>();

    private SchedulersProvider schedulersProvider;

    private TweetRepository tweetRepository;

    public void init(SchedulersProvider schedulersProvider, TweetRepository tweetRepository) {
        this.schedulersProvider = schedulersProvider;
        this.tweetRepository = tweetRepository;
    }

    public MutableLiveData<List<Tweet>> getLiveData() {
        return liveData;
    }


    public void fetchData(OnError onError) {
        Log.i(TAG, "Fetch data from view model.");
        Disposable disposable = tweetRepository
                .fetchTweets()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe(tweets -> liveData.postValue(tweets), onError::onError);
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
