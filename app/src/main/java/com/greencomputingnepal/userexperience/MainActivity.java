package com.greencomputingnepal.userexperience;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.greencomputingnepal.userexperience.model.DataItem;
import com.greencomputingnepal.userexperience.sample.SampleDataProvider;
import com.greencomputingnepal.userexperience.utilities.ThemeUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1001;
    public static final String GLOBAL_DATA = "global_data";

    List<DataItem> dataItems = SampleDataProvider.dataItemList;

    DataItemAdapter dataItemAdapter;

    @BindView(R.id.txt_username)
    TextView txtUsername;

    @BindView(R.id.rv_items)
    RecyclerView recyclerView;

    @BindView(R.id.home_toolbar)
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
        ThemeUtils.changeToTheme(MainActivity.this, themeName); // Change Theme as per user  choice

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        Collections.sort(dataItems, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });

        boolean grid = settings.getBoolean(getString(R.string.preference_display_grid), false);
        boolean show_hide = settings.getBoolean(getString(R.string.preference_display_show_hide_username), false);
        String username = settings.getString(getString(R.string.preference_display_name), "Guest");

        if(show_hide){
            txtUsername.setText(username);
        }

        if(grid){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        dataItemAdapter = new DataItemAdapter(this, dataItems);
        recyclerView.setAdapter(dataItemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                return true;

            case R.id.menu_settings:
                Intent settingsIntent = new Intent(this, PreferenceActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String email = data.getStringExtra(LoginActivity.EMAIL_KEY);
            Toast.makeText(getApplicationContext(), "Signed in as " + email, Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = getSharedPreferences(GLOBAL_DATA, MODE_PRIVATE).edit();
            editor.putString(LoginActivity.EMAIL_KEY, email);
            editor.apply();
        }
    }
}