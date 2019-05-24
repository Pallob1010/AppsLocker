package com.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.LockSavingRemoving.Apps;
import com.loop.appslocker.R;
import java.util.ArrayList;
import java.util.Arrays;


public class CustomRecyclerView extends RecyclerView.Adapter<CustomRecyclerView.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    View view;
    ArrayList<Apps>data;
    public SparseBooleanArray booleanArray;

    public CustomRecyclerView(Context context) {
        this.context = context;
        data=new ArrayList<>();
        inflater = LayoutInflater.from(context);
        booleanArray = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public CustomRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = inflater.inflate(R.layout.singleappsview, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerView.MyViewHolder myViewHolder, int i) {
        myViewHolder.keep(i);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void getUser(ArrayList<Apps>data){
        this.data=data;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Name, Number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void keep(int position) {

            if (!booleanArray.get(position, false)) {

            } else {

            }

        }

        @Override
        public void onClick(View v) {

            int adaptePosition = getAdapterPosition();
            if (!booleanArray.get(adaptePosition, false)) {

                booleanArray.put(adaptePosition, true);
            } else {

                booleanArray.put(adaptePosition, false);
            }

        }
    }






}