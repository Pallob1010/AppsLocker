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


public class PatternHighlighted extends Dialog {
    TextView dataTextView, ChoosenText;
    int backgrounds[] = {R.drawable.white_highlight,R.drawable.green_high_lighted,R.drawable.blue_highlighted,R.drawable.red_highlighted,R.drawable.black_highlight};
    String data[]={"White","Green","Blue","Red","Black"};

    Context context;
    SharedPreference sP;
    CustomAdapter customAdapter;

    public PatternHighlighted(Context context, final SharedPreference sP, final SettingsAdapter adapter) {
        super(context);
        this.context = context;
        this.sP = sP;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layoutthree);
        show();
        TextView textView = findViewById(R.id.titleTextView);
        textView.setText("Select Highlighted State");
        ListView listView = findViewById(R.id.SizeListView);
        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sP.setPatternHighLighted(backgrounds[position]);
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
            if (sP.patternHighlighted()==backgrounds[position]) {
                ChoosenText.setBackgroundResource(R.drawable.selected);
            }
            return rowView;
        }
    }


}
