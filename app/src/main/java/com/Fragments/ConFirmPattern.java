package com.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lock.PatternLockView;
import com.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class ConFirmPattern extends Fragment implements View.OnClickListener {

    View view;
    PatternLockView patternLockView;
    Button Back, Confirm;
    String recievedPattern, ConFirmPattern;

    public ConFirmPattern(String recievedPattern) {
        this.recievedPattern = recievedPattern;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.confirmpattern, container, false);
        patternLockView = view.findViewById(R.id.confirmPatternLockView);
        Back = view.findViewById(R.id.pattern_back);
        Confirm = view.findViewById(R.id.pattern_confirm);
        Confirm.setOnClickListener(this);
        Back.setOnClickListener(this);
        patternLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public void onFinish(String conFirmPattern) {
                ConFirmPattern = conFirmPattern;
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pattern_confirm:
                if (recievedPattern.equals(ConFirmPattern)) {
                    Toast.makeText(getContext(), "Pattern Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Mismatch", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.pattern_back:
                clearApps();
                break;
        }

    }

    public void clearApps() {
        for (int i = 1; i < getFragmentManager().getBackStackEntryCount(); i++) {
            getFragmentManager().popBackStack();
        }
    }

}
