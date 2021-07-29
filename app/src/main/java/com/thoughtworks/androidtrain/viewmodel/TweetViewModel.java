package com.thoughtworks.androidtrain.viewmodel;

import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.thoughtworks.androidtrain.MainApplication;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.TweetRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetViewModel extends AndroidViewModel {

    private static final String TAG = "TweetViewModel";

    private final TweetRepository tweetRepository;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Tweet>> liveData = new MutableLiveData<>();

    public MutableLiveData<List<Tweet>> getLiveData() {
        return liveData;
    }

    public TweetViewModel(MainApplication application) {
        super(application);
        tweetRepository = application.getTweetRepository();
    }


    public void fetchData(OnError onError) {
        Log.i(TAG, "Fetch data from view model.");
        Disposable disposable = tweetRepository.fetchTweets()
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
