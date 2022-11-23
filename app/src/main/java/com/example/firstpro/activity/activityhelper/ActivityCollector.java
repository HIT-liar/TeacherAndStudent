package com.example.firstpro.activity.activityhelper;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishOneActivity(String ActivityName){
        for(Activity activity : activities){
            String name = activity.getClass().getName();
            if(name.equals(ActivityName)){
                if(activity.isFinishing()){
                    activities.remove(activity);
                }else {
                    activity.finish();
                }
            }
        }
    }

    public static void finishOtherActivity(String ActivityName){
        for(Activity activity : activities){
            String name = activity.getClass().getName();
            if(!name.equals(ActivityName)){
                if(activity.isFinishing()){
                    activities.remove(activity);
                }else {
                    activity.finish();
                }
            }
        }
    }

    public static void finishAll(){
        for(Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }

}
