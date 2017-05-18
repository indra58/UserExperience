package com.greencomputingnepal.userexperience.contact;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.greencomputingnepal.userexperience.R;
import com.greencomputingnepal.userexperience.utilities.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactActivity extends AppCompatActivity {

    @BindView(R.id.contact_toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtName)
    TextInputEditText txtName;
    @BindView(R.id.txtContactNo)
    TextInputEditText txtContactNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Object of SharedPreferences to get the values
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        String themeName = settings.getString(getString(R.string.preference_list_theme_colors), "");

        if(themeName.equalsIgnoreCase("")){
            themeName = "0";
        }
        ThemeUtils.changeToTheme(ContactActivity.this, themeName);

        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.btnSaveData)
    public void saveData(){
        if(TextUtils.isEmpty(txtName.getText().toString())){
            txtName.setError("Provide Data");
            return;
        }
        else if (TextUtils.isEmpty(txtContactNo.getText().toString())){
            txtContactNo.setError("Provide Contact No");
            return;
        }
        else if(!isValidContactNo()){
            txtContactNo.setError("Contact No not less than 10 number");
            return;
        }

        if(Contact.getDataFromName(txtName.getText().toString()) == null){
            ActiveAndroid.beginTransaction();
            try{
                Contact contact = new Contact(txtName.getText().toString(), txtContactNo.getText().toString());
                contact.save();
                Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                ActiveAndroid.setTransactionSuccessful();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                ActiveAndroid.endTransaction();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Name Already Found. Try Another", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnGetData)
    public void getData(){
        Contact contact = Contact.getDataFromName(txtName.getText().toString());

        if(contact != null){
            Toast.makeText(getApplicationContext(), "Name   :" + contact.getName() + "    Contact No   :" + contact.getContactno(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnDeleteData)
    public void deleteData(){

        if(Contact.getDataFromName(txtName.getText().toString()) != null){
            if(Contact.deleteSingleData(txtName.getText().toString()) == null){
                Toast.makeText(getApplicationContext(), "Data Deleted..", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Data Not Deleted. Try Again", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Data Not Found..", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnDeleteAll)
    public void deleteAllData(){
        if(Contact.getAllData().size() != 0 ){
            if(Contact.deleteAll() == null){
                Toast.makeText(getApplicationContext(), "All Deleted..", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Data Not Deleted..", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Data Not Found..", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnUpdateContact)
    public void updateContact(){

        if(TextUtils.isEmpty(txtName.getText().toString()) || TextUtils.isEmpty(txtContactNo.getText().toString())){
            Toast.makeText(getApplicationContext(), "Provide Data", Toast.LENGTH_SHORT).show();
        }
        else {
            if(Contact.getDataFromName(txtName.getText().toString()) != null){
                Contact.updateContact(txtName.getText().toString(), txtContactNo.getText().toString());
                Toast.makeText(getApplicationContext(), "Updated Data", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Data Not Found..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidContactNo(){
        return txtContactNo.getText().toString().length() > 9;
    }
}
