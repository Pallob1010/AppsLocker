package com.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Listener.BackListener;
import com.LockSavingRemoving.SharedPreference;
import com.lock.PinLockView;
import com.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class SetPinLock extends Fragment {

    Context context;
    BackListener backListener;
    View view;
    private String pin ="";
    SharedPreference preference;
    TextView showPin;

    public SetPinLock(BackListener backListener, Context context) {
        this.context = context;
        this.backListener = backListener;
        preference=new SharedPreference(context);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setpinlock, container, false);
        showPin=view.findViewById(R.id.createPinShow);
        PinLockView pinLockView = view.findViewById(R.id.createPinLockView);
        pinLockView.setCallBack(new PinLockView.CallBack() {
            @Override
            public void onNumberClicked(String number) {
                pin += number;
                if (pin.length()<=preference.pinLockSize()){
                    showPin.setText(pin);
                    if(pin.length()==preference.pinLockSize()){
                        ConFirmPinLock(pin);
                        pin="";
                    }
                }
            }

            @Override
            public void onArrowClicked() {
                backListener.Back();
            }

            @Override
            public void onCrossClicked() {
                showPin.setText("");
                pin="";

            }
        });
        return view;
    }


    public void ConFirmPinLock(String createdPin){
        ConFirmPinLock conFirmPin=new ConFirmPinLock(createdPin,context);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.setPinRoot, conFirmPin);
        fragmentTransaction.addToBackStack("conFirmPin");
        fragmentTransaction.commit();
        showPin.setText("");

    }

}
