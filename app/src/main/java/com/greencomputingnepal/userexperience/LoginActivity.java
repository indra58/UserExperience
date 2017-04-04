package com.greencomputingnepal.userexperience;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

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
            txtEmail.setText(email);
            txtPassword.requestFocus();
        }
    }

    @OnClick(R.id.btnLogin)
    public void clickLogin(View view){
        attemptLogin();
    }

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
            getIntent().putExtra(EMAIL_KEY, txtEmail.getText().toString());
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }

    private boolean isPasswordValid(String password){
        return password.length() > 4;
    }

    private boolean isValidEmail(String email){
        return email.contains("@");
    }
}