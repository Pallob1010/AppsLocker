package com.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Listener.BackListener;
import com.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class SetPinLock extends Fragment {

    Context context;
    BackListener backListener;
    View view;

    public SetPinLock(BackListener backListener, Context context) {
        this.context = context;
        this.backListener = backListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setpinlock, container, false);
        return view;
    }
}
