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


public class LineSize extends Dialog {
    TextView dataTextView, ChoosenText;
    String data[] = {"1 px", "2 px", "3 px", "4 px", "5 px", "6 px", "7 px", "8 px", "9 px", "10 px","11 px","12 px","13 px","14 px","15 px","16 px","17 px","18 px","19 px","20 px"};
    Context context;
    SharedPreference sP;
    CustomAdapter customAdapter;

    public LineSize(Context context, final SharedPreference sP, final SettingsAdapter adapter) {
        super(context);
        this.context = context;
        this.sP = sP;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layoutthree);
        show();
        TextView textView = findViewById(R.id.titleTextView);
        textView.setText("Select Line Size");
        ListView listView = findViewById(R.id.SizeListView);
        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sP.setPatternLineSize(position + 1);
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

            convertView= LayoutInflater.from(context).inflate(R.layout.singleglobalview, null, true);
            dataTextView = convertView.findViewById(R.id.dataTextView);
            ChoosenText = convertView.findViewById(R.id.singleradio);
            dataTextView.setText(data[position]);
            if (sP.patternLineSize()==position+1) {
                ChoosenText.setBackgroundResource(R.drawable.selected);
            }
            return convertView;
        }
    }


}
