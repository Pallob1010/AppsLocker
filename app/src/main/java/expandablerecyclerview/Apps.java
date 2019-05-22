package expandablerecyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import LockSavingRemoving.Constants;
import spark.loop.appslocker.R;

public class Apps implements Parcelable {


    public static final Creator<Apps> CREATOR = new Creator<Apps>() {
        @Override
        public Apps createFromParcel(Parcel in) {
            return new Apps(in);
        }

        @Override
        public Apps[] newArray(int size) {
            return new Apps[size];
        }
    };
    ApplicationInfo applicationInfo;
    Context context;
    ArrayList<Apps> systemApps = new ArrayList<>();
    ArrayList<Apps> installedApps = new ArrayList<>();
    private String packageName;

    public Apps(Context context) {
        this.context=context;
        getAllApps();

    }

    public Apps(String packageName, Context context) {
        this.packageName = packageName;
        this.context = context;
    }

    public Apps(Context context, ArrayList<String> system, ArrayList<String> installed) {

        this.context = context;
        mapMyList(system, installed);

    }

    protected Apps(Parcel in) {
        packageName = in.readString();
    }

    public String getPackageName() {
        return packageName;
    }

    public ArrayList<Apps> systemAppsCaller() {
        return systemApps;
    }

    public ArrayList<Apps> installedAppsCaller() {
        return installedApps;
    }

    public void getAllApps() {
        systemApps.add(new Apps(Constants.Package_Installer,context));
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (isSystemPackage(resolveInfo)) {
                systemApps.add(new Apps(activityInfo.applicationInfo.packageName,context));
            } else {
                installedApps.add(new Apps(activityInfo.applicationInfo.packageName,context));

            }
        }
    }

    public boolean isSystemPackage(ResolveInfo resolveInfo) {

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public void mapMyList(ArrayList<String> system, ArrayList<String> installed) {
        for (int i = 0; i < system.size(); i++) {
            systemApps.add(new Apps(system.get(i), context));
        }
        for (int j = 0; j < installed.size(); j++) {
            installedApps.add(new Apps(installed.get(j), context));
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageName);
    }


    public Drawable getappsIcon(String packagename) {


        Drawable drawable;

        try {
            drawable = context.getPackageManager().getApplicationIcon(packagename);

        } catch (PackageManager.NameNotFoundException e) {
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher);
        }
        return drawable;
    }

    public String getappsName(String packagename) {
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

}
