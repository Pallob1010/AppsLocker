package com.Fragments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.Listener.BackListener;
import com.lock.PatternLockView;
import com.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class SetPatternLock extends Fragment implements View.OnClickListener {

    View view;
    PatternLockView patternLockView;
    TextView crateCancel;
    BackListener backListener;
    Context context;
    public SetPatternLock(BackListener backListener, Context context) {
        this.backListener=backListener;
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setpatternlock, container, false);
        patternLockView = view.findViewById(R.id.createPatternLockView);
        crateCancel = view.findViewById(R.id.pattern_cancel);
        crateCancel.setOnClickListener(this);
        patternLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public void onFinish(String pattern) {
                ConfirmEnteredPattern(pattern);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        backListener.Back();
    }


    public void ConfirmEnteredPattern(String createdPattern){
        ConFirmPattern conFirmPattern=new ConFirmPattern(createdPattern,context);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.setPatternRoot, conFirmPattern);
        fragmentTransaction.addToBackStack("conFirmPattern");
        fragmentTransaction.commit();

    }


}
