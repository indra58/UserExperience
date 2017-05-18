package com.greencomputingnepal.userexperience.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.greencomputingnepal.userexperience.MainActivity;
import com.greencomputingnepal.userexperience.R;
import com.greencomputingnepal.userexperience.utilities.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    public static final String EMAIL_KEY = "email_key";

    @BindView(R.id.txtEmail)
    TextInputEditText txtEmail;
    @BindView(R.id.txtPassword)
    TextInputEditText txtPassword;

    @BindView(R.id.login_toolbar)
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
        ThemeUtils.changeToTheme(LoginActivity.this, themeName);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences preferences = getSharedPreferences(MainActivity.GLOBAL_DATA, MODE_PRIVATE);

        String email = preferences.getString(LoginActivity.EMAIL_KEY, "");

        if(!TextUtils.isEmpty(email)){
            txtEmail.setText("");
            txtEmail.requestFocus();
        }
    }

    @OnClick(R.id.btnSaveData)
    public void clickLogin(View view){
        attemptLogin();
    }

    // Saving Data in Database
    private void attemptLogin(){
        txtEmail.setError(null);
        txtPassword.setError(null);

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(txtEmail.getText().toString())){
            txtEmail.setError("Provide Email Address");
            focusView = txtEmail;
            cancel = true;
        }
        else{
            if(!isValidEmail(txtEmail.getText().toString())){
                txtEmail.setError("Provide Valid Email");
                cancel = true;
                focusView = txtEmail;
            }
        }

        if(!TextUtils.isEmpty(txtPassword.getText().toString()) && !isPasswordValid(txtPassword.getText().toString())){
            txtPassword.setError("Provide Proper Password not less than 5 character");
            cancel = true;
            focusView = txtPassword;
        }

        if(cancel){
            focusView.requestFocus();
        }
        else {
            ActiveAndroid.beginTransaction();
            try{
                Student student = new Student(txtEmail.getText().toString(), txtPassword.getText().toString());

                if(Student.getDataUsingEmail(txtEmail.getText().toString()) == null ){
                    student.save();
                    Toast.makeText(getApplicationContext(), "Data Saved Successfully..", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), txtEmail.getText().toString() + "  Exists Already...", Toast.LENGTH_SHORT).show();
                }
                ActiveAndroid.setTransactionSuccessful();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                ActiveAndroid.endTransaction();
            }
           /* getIntent().putExtra(EMAIL_KEY, txtEmail.getText().toString());
            setResult(RESULT_OK, getIntent());
            finish();*/
        }
    }

    // TODO get data according to the email of student
    @OnClick(R.id.btnGetData)
    public void getData(){
        Student student = Student.getDataUsingEmail(txtEmail.getText().toString());
        if(student != null){
            Toast.makeText(getApplicationContext(), "Email   :   " + student.getEmail() + "     Password   :   " + student.getPassword(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), txtEmail.getText().toString() + "  Not Found...", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO delete data according to email of student
    @OnClick(R.id.btnDeleteData)
    public void deleteData(){
        if(Student.getDataUsingEmail(txtEmail.getText().toString()) != null){
            Student student = Student.deleteDataUsingEmail(txtEmail.getText().toString());
            if(student == null){
                Toast.makeText(getApplicationContext(), txtEmail.getText().toString() + " Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), txtEmail.getText().toString() + "  Not Found...", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO: delete all datas from table Student
    @OnClick(R.id.btnDeleteAll)
    public void deleteAll(){
        if(Student.getAllData().size() != 0){
            Student student = Student.deleteAll();
            if(student == null) {
                Toast.makeText(getApplicationContext(), "All Data Deleted Successfully..", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "No Data Found...", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: update passweord according to email
    @OnClick(R.id.btnUpdatePassword)
    public void updatePassword(){
        if(Student.getDataUsingEmail(txtEmail.getText().toString()) != null){
            Student.updatePassword(txtEmail.getText().toString(), txtPassword.getText().toString());
            Toast.makeText(getApplicationContext(), "Updated Successfully..", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), txtEmail.getText().toString() + "  Not Found...", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPasswordValid(String password){
        return password.length() > 4;
    }

    private boolean isValidEmail(String email){
        return email.contains("@");
    }
}