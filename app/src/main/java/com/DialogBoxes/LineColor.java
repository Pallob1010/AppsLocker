package com.DialogBoxes;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.Adapters.SettingsAdapter;
import com.LockSavingRemoving.SharedPreference;
import com.loop.appslocker.R;


public class LineColor extends Dialog {
    TextView dataTextView, ChoosenText;
    String data[]={"White","Green","Green Light","Blue","Blue Light","Red","Red Light","Yellow","Orange","Purple","Black"};
    Context context;
    String color[]={"#FFFFFF","#008000","#00FF44","#0903E5","#33B5E5","#E51410","#FF4444","#FFDF10","#FFBB33","#AA66CC","#000000"};
    SharedPreference sP;
    CustomAdapter customAdapter;

    public LineColor(Context context, final SharedPreference sP, final SettingsAdapter adapter) {
        super(context);
        this.context = context;
        this.sP = sP;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layoutthree);
        show();
        TextView textView = findViewById(R.id.titleTextView);
        textView.setText("Select Line Color");
        ListView listView = findViewById(R.id.SizeListView);
        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sP.setPatternLineColor(color[position]);
                adapter.notifyDataSetChanged();
                dismiss();

            }
        });
    }


    public class CustomAdapter extends ArrayAdapter<String> {
        public CustomAdapter() {
            super(context, R.layout.singleglobalview, data);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = LayoutInflater.from(context).inflate(R.layout.singleglobalview, null, true);
            dataTextView = rowView.findViewById(R.id.dataTextView);
            ChoosenText = rowView.findViewById(R.id.singleradio);
            dataTextView.setText(data[position]);
            if (sP.patternLineColor().equals(color[position])){
                ChoosenText.setBackgroundResource(R.drawable.selected);
            }

            return rowView;
        }
    }


}
