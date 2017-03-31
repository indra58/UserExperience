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
            case "Default":
                activity.setTheme(R.style.AppTheme);
                break;
            case "Red":
                activity.setTheme(R.style.Red);
                break;
            case "Brown":
                activity.setTheme(R.style.Brown);
                break;
            case "Indigo":
                activity.setTheme(R.style.Indigo);
                break;
        }
    }
}
