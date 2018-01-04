package com.example.administrator.myapplication;

import android.app.Activity;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ActivityCollector {
    public static LinkedList<Activity> activities = new LinkedList<Activity>();
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    public static void finishAll()
    {
        for(Activity activity:activities)
        {
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }
}