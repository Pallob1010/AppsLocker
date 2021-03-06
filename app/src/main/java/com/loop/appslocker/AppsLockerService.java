package com.loop.appslocker;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.LockSavingRemoving.SharedPreference;
import com.lock.PatternLockView;
import java.util.ArrayList;
import java.util.List;

public class AppsLockerService extends Service {
    public static String currentApp = "";
    public static String previousApp = "";
    final Handler handler = new Handler();
    Dialog dialog;
    SharedPreference sharedPreference;
    List<String> pakageName;
    long t = 300;
    ActivityManager activityManager;
    List<ActivityManager.RunningTaskInfo> taskInfo;
    WindowManager windowManager;
    ImageView imageView;
    WindowManager.LayoutParams params;
    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                if (dialog != null) {
                    Home();
                }
            }
        }
    };
    private Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sharedPreference = new SharedPreference(context);
        pakageName = new ArrayList<>();
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        imageView = new ImageView(this);
        imageView.setVisibility(View.GONE);

        params.gravity = Gravity.TOP | Gravity.CENTER;
        params.x = ((getApplicationContext().getResources().getDisplayMetrics().widthPixels) / 2);
        params.y = ((getApplicationContext().getResources().getDisplayMetrics().heightPixels) / 2);
        windowManager.addView(imageView, params);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, t);
                if (isAppsRunning()) {

                    if (imageView != null) {

                        if (!currentApp.matches(previousApp)) {
                            showUnlockDialog();
                            previousApp = currentApp;
                        }
                    }
                } else {
                    hideUnlockDialog();
                }
            }

        }, t);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    void showUnlockDialog() {
        showDialog();
    }

    void hideUnlockDialog() {
        previousApp = "";
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(broadcastReceiver, intentFilter);
        return START_STICKY;
    }

    public boolean isAppsRunning() {
        pakageName = sharedPreference.getLockedListedApp();

        if (Build.VERSION.SDK_INT >= 21) {
            String mpackageName = activityManager.getRunningAppProcesses().get(0).processName;
            if (pakageName.contains(mpackageName)) {
                currentApp = mpackageName;
                return true;
            }
        } else {
            taskInfo= activityManager.getRunningTasks(10);
            if (taskInfo.size() > 0) {
                ComponentName componentInfo = taskInfo.get(0).topActivity;
                if (pakageName.contains(componentInfo.getPackageName())) {
                    currentApp = componentInfo.getPackageName();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void showDialog() {
        if (context == null) {
            context = getApplicationContext();
        }

        dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.popuplock);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        PatternLockView patternLockView = dialog.findViewById(R.id.patternlockview);

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK | event.getAction() == KeyEvent.ACTION_UP) {
                    Home();
                }

                return true;
            }
        });

    }

    public void Home() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        dialog.dismiss();
    }

}