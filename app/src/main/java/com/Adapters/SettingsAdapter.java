package com.Adapters;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.DialogBoxes.LineSize;
import com.DialogBoxes.PatternSize;
import com.DialogBoxes.PinLockSize;
import com.LockSavingRemoving.SharedPreference;
import com.loop.appslocker.R;

public class SettingsAdapter extends ArrayAdapter<String>{
    Context context;
    SharedPreference preference;
    TextView settingsTitle,settingsColor,settingsOption;
    CheckBox checkBox;
    String data[];
    View view;
    public SettingsAdapter(Context context, String[] data, SharedPreference sP) {
    super(context,R.layout.singlesettingsview,data);
        this.context = context;
        this.data=data;
        preference = sP;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.length;
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        view=LayoutInflater.from(context).inflate(R.layout.singlesettingsview,parent,false);
        settingsTitle=view.findViewById(R.id.settingsTitle);
        settingsColor=view.findViewById(R.id.colorField);
        settingsOption=view.findViewById(R.id.settingsOptions);
        printf(position);
        return view;
    }

    public void printf(int position){
        switch (position){
            case 0:
                settingsTitle.setText(data[position]);
                settingsOption.setText(String.valueOf(preference.patternSize())+" x "+String.valueOf(preference.patternSize()));
                break;
            case 1:
                settingsTitle.setText(data[position]);
                settingsOption.setText(String.valueOf(preference.pinLockSize()));
                break;
            case 2:
                settingsTitle.setText(data[position]);
                settingsOption.setText(String.valueOf(preference.patternLineSize())+" px");
                break;
            case 3:
                settingsTitle.setText(data[position]);
                settingsColor.setBackgroundResource(preference.patternDot());

                break;
            case 4:
                settingsTitle.setText(data[position]);
                settingsColor.setBackgroundResource(preference.patternHighlighted());
                break;
            case 5:
                settingsTitle.setText(data[position]);
            settingsColor.setBackgroundColor(Color.parseColor(preference.patternLineColor()));
                break;
            case 6:
                settingsTitle.setText(data[position]);
                break;
            case 7:
                settingsTitle.setText(data[position]);
                settingsOption.setText(preference.patternVisibility());
                break;


        }
    }

}


