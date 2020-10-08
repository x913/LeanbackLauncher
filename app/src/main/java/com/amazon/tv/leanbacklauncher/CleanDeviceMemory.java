package com.amazon.tv.leanbacklauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class CleanDeviceMemory extends Activity {

    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // killing background processes
                PackageManager pm = getPackageManager();
                List<ApplicationInfo> installedPackages = pm.getInstalledApplications(0);
                ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                for (ApplicationInfo applicationInfo : installedPackages) {
                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)
                        continue;
                    if(getApplicationContext().getPackageName().equalsIgnoreCase(applicationInfo.packageName))
                        continue;
                    mActivityManager.killBackgroundProcesses(applicationInfo.packageName);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.memory_has_been_cleared), Toast.LENGTH_SHORT)
                                .show();

                        finish();
                    }
                });
            }
        });
        mThread.start();
    }

}