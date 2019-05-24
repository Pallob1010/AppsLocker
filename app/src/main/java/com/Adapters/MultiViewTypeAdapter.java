package com.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.LockSavingRemoving.Apps;
import com.LockSavingRemoving.Constants;
import com.loop.appslocker.R;

import java.util.ArrayList;

public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    Context context;
    int total_types;
    ArrayList<Apps> data;

    public MultiViewTypeAdapter(ArrayList<Apps> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.appscategories, parent, false);
                return new TitleTypeViewHolder(view);
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.singleappsview, parent, false);
                return new AppsTypeViewHolder(view);
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.appscategories, parent, false);
                return new TitleTypeViewHolder(view);
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.singleappsview, parent, false);
                return new AppsTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (data.get(position).getType()) {
            case 0:
                return Constants.SYSTEM_TITLE;
            case 1:
                return Constants.SYSTEM_APPS;
            case 2:
                return Constants.INSTALLED_TITLE;
            case 3:
                return Constants.INSTALLED_APPS;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

            Apps apps = data.get(listPosition);
            if (apps != null) {
                switch (apps.getType()) {
                    case Constants.SYSTEM_TITLE:
                        ((TitleTypeViewHolder)holder).appsHeader.setText(apps.getPackageName());
                        break;
                    case Constants.SYSTEM_APPS:
                        ((AppsTypeViewHolder)holder).printAll(apps);
                        break;
                    case Constants.INSTALLED_TITLE:
                        ((TitleTypeViewHolder)holder).appsHeader.setText(apps.getPackageName());
                        break;
                    case Constants.INSTALLED_APPS:
                        ((AppsTypeViewHolder)holder).printAll(apps);
                        break;

                }
            }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TitleTypeViewHolder extends RecyclerView.ViewHolder {

         TextView appsHeader;

        public TitleTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            appsHeader = itemView.findViewById(R.id.appsCategorieTextView);
        }
    }

    public static class AppsTypeViewHolder extends RecyclerView.ViewHolder {
        TextView appsName,appsIcon,appsLockState;

        public AppsTypeViewHolder(View itemView) {
            super(itemView);
            appsName=itemView.findViewById(R.id.systemAppsName);
            appsIcon=itemView.findViewById(R.id.systemAppsIcon);
            appsLockState=itemView.findViewById(R.id.systemAppsLockState);
        }
        public void printAll(Apps apps){
            appsName.setText(apps.getappsName(apps.getPackageName()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                appsIcon.setBackground(apps.getappsIcon(apps.getPackageName()));
            }else {
                appsIcon.setBackgroundDrawable(apps.getappsIcon(apps.getPackageName()));
            }

        }


    }

}
