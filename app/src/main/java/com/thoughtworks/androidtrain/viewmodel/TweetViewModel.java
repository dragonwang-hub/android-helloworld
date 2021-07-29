package com.thoughtworks.androidtrain.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.thoughtworks.androidtrain.MainApplication;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.TweetRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetViewModel extends AndroidViewModel {

    private static final String TAG = "TweetViewModel";

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Tweet>> liveData = new MutableLiveData<>();

    public TweetViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<List<Tweet>> getLiveData() {
        return liveData;
    }


    public void fetchData(OnError onError) {
        Log.i(TAG, "Fetch data from view model.");
        Disposable disposable = ((MainApplication)getApplication())
                .getTweetRepository()
                .fetchTweets()
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
