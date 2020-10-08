package com.amazon.tv.leanbacklauncher.apps;

import java.util.Arrays;
import java.util.List;

public class UninstallBlackListApp {
    private static List<String> apps = Arrays.asList(
            "com.liskovsoft.videomanager.beta",
            "com.liskovsoft.videomanager.kids",
            "com.smartup.tv",
            "ru.ivi.client",
            "tv.tvip.tvapp.tvipmedia",
            "dkc.video.hdbox",
            "org.cyanogenmod.appdrawer",
            "com.example.android.apis");

    public static Boolean isAppInList(String packageName) {
        return apps.contains(packageName);
    }

}
