package com.thoughtworks.androidtrain.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.thoughtworks.androidtrain.R;

public class AndroidDescription extends Fragment {

    public static final String TAG = "AndroidDescription";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG,"Create the android description fragment.");
        return inflater.inflate(R.layout.fragment_android, container, false);
    }
}
