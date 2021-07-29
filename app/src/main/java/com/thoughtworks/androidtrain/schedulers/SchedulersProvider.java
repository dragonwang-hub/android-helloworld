package com.thoughtworks.androidtrain.schedulers;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SchedulersProvider {
    public Scheduler io() {
        return Schedulers.io();
    }

    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
