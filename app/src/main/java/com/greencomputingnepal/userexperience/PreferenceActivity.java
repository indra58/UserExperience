package com.greencomputingnepal.userexperience;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.greencomputingnepal.userexperience.utilities.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreferenceActivity extends AppCompatActivity {

    private static final String TAG = "PreferenceActivity";

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    @BindView(R.id.settings_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Object of SharedPreferences to get the values
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        String themeName = settings.getString(getString(R.string.preference_list_theme_colors), "");

        if(themeName.equalsIgnoreCase("")){
            themeName = "Default";
        }

        ThemeUtils.changeToTheme(PreferenceActivity.this, themeName);

        setContentView(R.layout.activity_preference);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.layout_framelayout, new SettingsFragment())
                .commit();

        final Activity activity = this;

        onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equalsIgnoreCase(getString(R.string.preference_list_theme_colors))){

                    activity.finish();
                    activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    activity.startActivity(new Intent(activity, activity.getClass()));
                }
            }
        };
        settings.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public static class SettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        navigateUpTo(new Intent(this, MainActivity.class));
    }
}