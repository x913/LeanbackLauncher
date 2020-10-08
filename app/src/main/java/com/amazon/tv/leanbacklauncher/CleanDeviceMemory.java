package com.amazon.tv.leanbacklauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class CleanDeviceMemory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CleanDeviceMemory","onCreate");
        //Context context = getApplicationContext();
        //CharSequence text = "%USERNAME% Mydak !";
        //int duration = Toast.LENGTH_SHORT;

        //Toast toast = Toast.makeText(context, text, duration);
        //toast.show();
        showClean();
        finish();
    }

    private void showClean(){
        new killProcessesThread().start();
    }

    public class killProcessesThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000L);
                    killBackgroundProcesses();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    continue;
                }
                break;
            }
        }
    }

    private void killBackgroundProcesses() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    Thread.sleep(100L);
                    ActivityManager.RunningAppProcessInfo apinfo = list.get(i);
                    String[] pkgList = apinfo.pkgList;

                    if (apinfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                        // Process.killProcess(apinfo.pid);
                        for (int j = 0; j < pkgList.length; j++) {
                            am.killBackgroundProcesses(pkgList[j]);
                            myHander.sendEmptyMessage(0);
                        }
                    }
                } catch (InterruptedException localInterruptedException) {
                    localInterruptedException.printStackTrace();
                }
            }

        } myHander.sendEmptyMessage(1);
    }

    Handler myHander = new Handler() {
        public void handleMessage(final Message message) {
            switch (message.what) {
                default: {}
                case 1: {
                    Context context = getApplicationContext();
                    CharSequence text = "Очищено !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        }
    };
}