package com.thoughtworks.androidtrain.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.thoughtworks.androidtrain.R;

public class FragmentUI {
    static public void replaceFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPage, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
