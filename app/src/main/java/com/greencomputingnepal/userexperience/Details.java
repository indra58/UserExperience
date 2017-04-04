package com.greencomputingnepal.userexperience;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.greencomputingnepal.userexperience.model.DataItem;
import com.greencomputingnepal.userexperience.utilities.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Details extends AppCompatActivity {

    @BindView(R.id.item_id)
    TextView itemId;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_description)
    TextView itemDescription;
    @BindView(R.id.item_category)
    TextView itemCategory;
    @BindView(R.id.item_sortposition)
    TextView itemSortPosition;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.item_image)
    TextView itemImage;

    @BindView(R.id.details_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Object of SharedPreferences to get the values
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        String themeName = settings.getString(getString(R.string.preference_list_theme_colors), "");

        if(themeName.equalsIgnoreCase("")){
            themeName = "0";
        }

        ThemeUtils.changeToTheme(Details.this, themeName);

        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DataItem dataItem = getIntent().getExtras().getParcelable(DataItemAdapter.ITEM_KEY);

        if (dataItem != null) {
            itemId.setText(dataItem.getItemId());
            itemName.setText(dataItem.getItemName());
            itemDescription.setText(dataItem.getDescription());
            itemCategory.setText(dataItem.getCategory());
            itemSortPosition.setText(String.valueOf(dataItem.getSortPosition()));
            itemPrice.setText(String.valueOf(dataItem.getPrice()));
            itemImage.setText(dataItem.getImage());
        }
        else {
            Toast.makeText(getApplicationContext(), "Not Received Data", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnOk)
    public void clickOk(View view){
        Toast.makeText(getApplicationContext(), "Toast Message", Toast.LENGTH_SHORT).show();
    }
}
