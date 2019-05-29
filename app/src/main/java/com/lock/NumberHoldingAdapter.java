package com.lock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loop.appslocker.R;

public class NumberHoldingAdapter extends RecyclerView.Adapter {
    Context context;
    String data[] = {"1", "2", "3","4", "5", "6", "7","8", "9", "10", "0", "11"};
    PinLockView.CallBack callBack;
    public NumberHoldingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        switch (i) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.singlenumber, viewGroup, false);
                return new NumberViewHolder(view);
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.singlearrowbutton, viewGroup, false);
                return new ArrowViewHolder(view);
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.singlecrossbutton, viewGroup, false);
                return new CrossViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {

        switch (data[i]) {
            case "10":
                ((ArrowViewHolder)viewHolder).arrowSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (callBack!=null){
                           callBack.onArrowClicked();
                       }
                    }
                });
                break;
            case "11":
                ((CrossViewHolder)viewHolder).crossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callBack!=null){
                            callBack.onCrossClicked();
                        }
                    }
                });
                break;
            default:
                ((NumberViewHolder)viewHolder).numberButton.setText(data[i]);
                ((NumberViewHolder)viewHolder).numberButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callBack!=null){
                            callBack.onNumberClicked(data[i]);
                        }
                    }
                });
                break;

        }


    }

    public void addListener(PinLockView.CallBack callBack){
            this.callBack=callBack;
    }

    @Override
    public int getItemViewType(int position) {
        switch (data[position]) {
            case "10":
                return 1;
            case "11":
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {
        Button numberButton;
        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            numberButton=itemView.findViewById(R.id.numberButton);

        }
    }

    public class ArrowViewHolder extends RecyclerView.ViewHolder {
        ImageButton arrowSwitch;
        public ArrowViewHolder(@NonNull View itemView) {
            super(itemView);
            arrowSwitch=itemView.findViewById(R.id.arrowbutton);
        }
    }

    public class CrossViewHolder extends RecyclerView.ViewHolder {

        ImageButton crossButton;
        public CrossViewHolder(@NonNull View itemView) {
            super(itemView);
            crossButton=itemView.findViewById(R.id.crossButton);
        }

    }

}
