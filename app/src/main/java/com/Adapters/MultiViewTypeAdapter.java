package com.Adapters;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.LockSavingRemoving.Apps;
import com.LockSavingRemoving.Constants;
import com.LockSavingRemoving.SharedPreference;
import com.loop.appslocker.R;
import java.util.ArrayList;

public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Apps> data;
    ApplicationInfo applicationInfo;
    PackageManager packageManager;
    SharedPreference sP;
    public MultiViewTypeAdapter(ArrayList<Apps> data, Context context,SharedPreference sP) {
        this.data = data;
        this.context = context;
        this.sP=sP;
        packageManager = context.getPackageManager();

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
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (data.get(position).getType()) {
            case Constants.SYSTEM_TITLE:
                return 0;

            case Constants.SYSTEM_APPS:
                return 1;

            case Constants.INSTALLED_TITLE:
                return 0;

            case Constants.INSTALLED_APPS:
                return 1;

            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        Apps apps = data.get(position);
        if (apps != null) {
            switch (apps.getType()) {
                case Constants.SYSTEM_TITLE:
                    ((TitleTypeViewHolder) holder).appsHeader.setText(apps.getPackageName());
                    break;
                case Constants.SYSTEM_APPS:
                    ((AppsTypeViewHolder) holder).printAll(apps);
                    ((AppsTypeViewHolder) holder).Bind(position);
                    break;
                case Constants.INSTALLED_TITLE:
                    ((TitleTypeViewHolder) holder).appsHeader.setText(apps.getPackageName());
                    break;
                case Constants.INSTALLED_APPS:
                    ((AppsTypeViewHolder) holder).printAll(apps);
                    ((AppsTypeViewHolder) holder).Bind(position);
                    break;

            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TitleTypeViewHolder extends RecyclerView.ViewHolder {

        TextView appsHeader;

        public TitleTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            appsHeader = itemView.findViewById(R.id.appsCategorieTextView);
        }
    }

    public class AppsTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView appsName, appsIcon, appsLockState;

        public AppsTypeViewHolder(View itemView) {
            super(itemView);
            appsName = itemView.findViewById(R.id.systemAppsName);
            appsIcon = itemView.findViewById(R.id.systemAppsIcon);
            appsLockState = itemView.findViewById(R.id.systemAppsLockState);
            itemView.setOnClickListener(this);
        }

        public void printAll(Apps apps) {
            appsName.setText(getAppsName(apps.getPackageName()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                appsIcon.setBackground(getAppsIcon(apps.getPackageName()));
            } else {
                appsIcon.setBackgroundDrawable(getAppsIcon(apps.getPackageName()));
            }

        }

        public void Bind(int position) {

            if (sP.getState().get(position)) {
                appsLockState.setBackgroundResource(R.drawable.locked_apps);
            } else {
                appsLockState.setBackgroundResource(R.drawable.transparent);
            }

        }

        public Drawable getAppsIcon(String packagename) {


            Drawable drawable;

            try {
                drawable = context.getPackageManager().getApplicationIcon(packagename);

            } catch (PackageManager.NameNotFoundException e) {
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher);
            }
            return drawable;
        }

        public String getAppsName(String packagename) {
            String Name = "";

            try {
                applicationInfo = context.getPackageManager().getApplicationInfo(packagename, 0);
                if (applicationInfo != null) {
                    Name = (String) context.getPackageManager().getApplicationLabel(applicationInfo);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return Name;
        }


        @Override
        public void onClick(View v) {
            int adaptePosition = getAdapterPosition();
            if (!sP.getState().get(adaptePosition)) {
                appsLockState.setBackgroundResource(R.drawable.locked_apps);
                sP.changeStateTo(true,adaptePosition);
                sP.addAppToLockedList(data.get(adaptePosition).getPackageName());
            } else {
                appsLockState.setBackgroundResource(R.drawable.transparent);
                sP.changeStateTo(false,adaptePosition);
                sP.removeAppFromLockedList(data.get(adaptePosition).getPackageName());
            }



        }
    }




}
