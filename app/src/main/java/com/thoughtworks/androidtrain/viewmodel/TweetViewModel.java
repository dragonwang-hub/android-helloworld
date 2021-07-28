package com.thoughtworks.androidtrain.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.TweetRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetViewModel extends ViewModel {

    private final Context context;

    public TweetViewModel(Context context) {
        super();
        this.context = context;
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<Tweet>> liveData = new MutableLiveData<>();

    public void fetchData(OnError onError) {
        TweetRepository tweetRepository = new TweetRepository(context);

        Disposable disposable = tweetRepository.fetchTweets(0)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Tweet>>() {
                    @Override
                    public void accept(List<Tweet> tweets) throws Throwable {
                        liveData.postValue(tweets);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        onError.onError(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
