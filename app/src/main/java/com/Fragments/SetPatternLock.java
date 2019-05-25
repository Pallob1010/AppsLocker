package com.Fragments;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Listener.BackFragmentListener;
import com.Listener.BackListener;
import com.lock.PatternLockView;
import com.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class SetPatternLock extends Fragment implements View.OnClickListener {

    View view;
    PatternLockView patternLockView;
    TextView createNext, crateCancel;
    String createdPattern="";
    BackListener backListener;
    public SetPatternLock(BackListener backListener) {
        this.backListener=backListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setpatternlock, container, false);
        patternLockView = view.findViewById(R.id.createPatternLockView);
        createNext = view.findViewById(R.id.pattern_next);
        crateCancel = view.findViewById(R.id.pattern_cancel);
        createNext.setOnClickListener(this);
        crateCancel.setOnClickListener(this);
        patternLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public void onFinish(String pattern) {
             createdPattern=pattern;

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pattern_next:
                if(createdPattern.equals("")){
                    Toast.makeText(getContext(), "Draw Pattern First !", Toast.LENGTH_SHORT).show();
                }else {
                    ConfirmEnteredPattern(createdPattern);
                }
                break;
            case R.id.pattern_cancel:
               backListener.Back();
                break;
        }

    }


    public void ConfirmEnteredPattern(String createdPattern){
        ConFirmPattern conFirmPattern=new ConFirmPattern(createdPattern);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.setPatternRoot, conFirmPattern);
        fragmentTransaction.addToBackStack("conFirmPattern");
        fragmentTransaction.commit();
        this.createdPattern="";

    }


}
