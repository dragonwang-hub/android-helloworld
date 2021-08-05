package com.thoughtworks.androidtrain.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.androidtrain.data.model.User;
import com.thoughtworks.androidtrain.data.source.UserRepository;
import com.thoughtworks.androidtrain.schedulers.SchedulersProvider;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class UserViewModel extends ViewModel {

    private static final String TAG = "UserViewModel";

    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private SchedulersProvider schedulersProvider;

    private UserRepository userRepository;

    public void init(SchedulersProvider schedulersProvider, UserRepository userRepository) {
        this.schedulersProvider = schedulersProvider;
        this.userRepository = userRepository;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void getUser() {
        Disposable disposable = userRepository.fetchUser()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe(user -> {
                    userMutableLiveData.postValue(user);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
