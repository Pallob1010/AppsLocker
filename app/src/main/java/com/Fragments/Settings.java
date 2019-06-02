package com.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.Adapters.SettingsAdapter;
import com.DialogBoxes.LineColor;
import com.DialogBoxes.LineSize;
import com.DialogBoxes.PatternDot;
import com.DialogBoxes.PatternHighlighted;
import com.DialogBoxes.PatternSize;
import com.DialogBoxes.PinLockSize;
import com.LockSavingRemoving.SharedPreference;
import com.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class Settings extends Fragment {
    View view;
    String data[] = {"Pattern Size", "Pin Length", "Line Width", "Pattern Dot", "Pattern HighLighted", "Line Color", "Pattern Background", "Pattern Visibility"};
    SettingsAdapter adapter;
    SharedPreference sP;
    Context context;
    ListView listView;
    public Settings(Context context, SharedPreference sP) {
        this.context = context;
        this.sP = sP;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings, container, false);
        listView = view.findViewById(R.id.settingsListView);
        adapter = new SettingsAdapter(context, data, sP);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CallerFunction(position);
            }
        });


        return view;
    }

    public void CallerFunction(int position) {
        switch (position) {

            case 0:
                new PatternSize(context, sP, adapter);
                break;
            case 1:
                new PinLockSize(context, sP, adapter);
                break;
            case 2:
                new LineSize(context, sP, adapter);
                break;
            case 3:
                new PatternDot(context, sP, adapter);
                break;
            case 4:
                new PatternHighlighted(context, sP, adapter);
                break;
            case 5:
                new LineColor(context, sP, adapter);


        }
    }

}
