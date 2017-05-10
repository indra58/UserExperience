package com.greencomputingnepal.userexperience.utilities;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

/**
 * Created by i7 on 5/9/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration configuration = new Configuration.Builder(this).setDatabaseName("Student.db").setDatabaseVersion(1).create();
        ActiveAndroid.initialize(configuration);
    }
}
