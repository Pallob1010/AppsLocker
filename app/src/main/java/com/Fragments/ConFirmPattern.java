package com.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.LockSavingRemoving.SharedPreference;
import com.lock.PatternLockView;
import com.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class ConFirmPattern extends Fragment implements View.OnClickListener {

    View view;
    PatternLockView patternLockView;
    TextView Back;
    String recievedPattern, ConFirmPattern;
    SharedPreference preference;
    public ConFirmPattern(String recievedPattern, Context context) {
        this.recievedPattern = recievedPattern;
        preference=new SharedPreference(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.confirmpattern, container, false);
        patternLockView = view.findViewById(R.id.confirmPatternLockView);
        Back = view.findViewById(R.id.confirm_back);
        Back.setOnClickListener(this);
        patternLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public void onFinish(String conFirmPattern) {

                if(recievedPattern.equals(conFirmPattern)){
                    preference.savePasswordPattern(recievedPattern);
                    Toast.makeText(getContext(), "Pattern Created", Toast.LENGTH_SHORT).show();
                    clearApps(0);
                }else {
                    Toast.makeText(getContext(), "Pattern Doesn't Match", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        clearApps(1);
    }

    public void clearApps(int j) {
        for (int i = j; i < getFragmentManager().getBackStackEntryCount(); i++) {
            getFragmentManager().popBackStack();
        }

    }


}
