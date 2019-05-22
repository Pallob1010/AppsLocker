package expandablerecyclerview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;

import LockSavingRemoving.SharedPreference;
import spark.loop.appslocker.R;

public class AppsViewHolder extends ChildViewHolder implements View.OnClickListener {

    private TextView AppsName,AppsIcon,AppsState;
    SharedPreference sharedPreference;
    Context context;
    Apps apps;
    ArrayList<String>lockListedApp;
    public AppsViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        sharedPreference=new SharedPreference(context);
        lockListedApp=sharedPreference.getLockedListedApp();
        AppsName=itemView.findViewById(R.id.systemAppsName);
        AppsIcon=itemView.findViewById(R.id.systemAppsIcon);
        itemView.setOnClickListener(this);
    }

    public void Bind(Apps apps){
        this.apps=apps;
        AppsName.setText(apps.getappsName(apps.getPackageName()));
        AppsIcon.setBackground(apps.getappsIcon(apps.getPackageName()));
        if (lockListedApp.contains(apps.getappsName(apps.getPackageName()))){
            AppsState.setBackgroundResource(R.drawable.locked_apps);
        }
    }

    @Override
    public void onClick(View v) {



    }
}
