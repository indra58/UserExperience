package com.greencomputingnepal.userexperience.utilities;

import android.app.Activity;

import com.greencomputingnepal.userexperience.R;

/**
 * Created by i7 on 3/30/2017.
 */

public class ThemeUtils {

    public static void changeToTheme(Activity activity, String themeName) {
        switch (themeName) {
            default:
            case "0":
                activity.setTheme(R.style.AppTheme);
                break;
            case "1":
                activity.setTheme(R.style.Red);
                break;
            case "2":
                activity.setTheme(R.style.Brown);
                break;
            case "3":
                activity.setTheme(R.style.Indigo);
                break;
        }
    }
}
