package com.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.LockSavingRemoving.SharedPreference;
import com.lock.PinLockView;
import com.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class ConFirmPinLock extends Fragment {

    View view;
    Context context;
    String createdPin;
    PinLockView pinLockView;
    String pin="";
    TextView showPin;
    SharedPreference preference;
    public ConFirmPinLock(String createdPin, Context context) {
        this.context=context;
        this.createdPin=createdPin;
        preference=new SharedPreference(context);
    }

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.confirmpin,container,false);
        pinLockView=view.findViewById(R.id.confirmPinLockView);
        showPin=view.findViewById(R.id.pinConfirmShow);
        pinLockView.setCallBack(new PinLockView.CallBack() {
            @Override
            public void onNumberClicked(String number) {
                pin += number;
                if (pin.length()<=createdPin.length()){
                    showPin.setText(pin);
                    if(pin.length()==createdPin.length()){
                        if (pin.equals(createdPin)){
                            preference.savePasswordPin(createdPin);
                            Toast.makeText(context, "Pin Created", Toast.LENGTH_SHORT).show();
                            clearApps(0);
                        }else {
                            Toast.makeText(context, "Pin Mismatch", Toast.LENGTH_SHORT).show();
                            pin="";
                        }

                    }
                }
            }

            @Override
            public void onArrowClicked() {
                clearApps(1);
            }

            @Override
            public void onCrossClicked() {
                showPin.setText("");
                pin="";

            }
        });
        return view;
    }


    public void clearApps(int j) {
        for (int i = j; i < getFragmentManager().getBackStackEntryCount(); i++) {
            getFragmentManager().popBackStack();
        }

    }
}
