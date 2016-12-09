package org.swe.android.activities;

/**
 * Created by YongjiLi on 9/21/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.User;
import org.swe.android.helpers.NetworkHelper;

public class AdminLoginActivity extends Activity{

    Button button;
    Button buttonSignUp;
    private ConnectivityManager mConnectivityManager;
    private String mUserId;
    private String mPassword;
    private String mAdminRole;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.admin_login);

        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        addListenerOnButtonCancel();
        addListenerOnButtonLogin();
        addListenerOnTextViewForgotPassword();
        addListenerOnButtonSignUp();

        Button helpButton = (Button) findViewById(R.id.button_help);
        helpButton.setVisibility(View.GONE);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user does not have an internet connection, let them know that one
                EditText ed1 = (EditText) findViewById(R.id.textUsername);
                EditText ed2 = (EditText) findViewById(R.id.textPassword);
                ed1.setText(R.string.admin);
                ed2.setText(R.string.admin);
            }
        });
    }

    public void addListenerOnButtonLogin() {

        button = (Button) findViewById(R.id.button_admin_login);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    // If we have username and password, run the AsyncTask to get the Username from the database tabel SWE_user
                    setUserId();
                    if (mPassword != null && mUserId != null) {
                        new GetUserTask().execute(mUserId);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_user_input_check,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Get the UserId that the user has entered ID= the string from user input
    private void setUserId() {
        EditText editText = (EditText) findViewById(R.id.textUsername);

        // If the user has not entered a value, show a toast to let them know to enter
        // your Username, otherwise, set the username to the value they have entered
        if (TextUtils.getTrimmedLength(editText.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_user_name,
                    Toast.LENGTH_SHORT).show();
            mUserId = null;
        }
        else {
            mUserId = editText.getText().toString().trim();
        }

        EditText editTextPassword = (EditText) findViewById(R.id.textPassword);

        // If the user has not entered a value, show a toast to let them know to enter
        // password, otherwise, set the password to the value they have entered
        if (TextUtils.getTrimmedLength(editText.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_password,
                    Toast.LENGTH_SHORT).show();
            mPassword = null;
        }
        else {
            mPassword = editTextPassword.getText().toString().trim();
        }
    }

    private class GetUserTask extends AsyncTask<String, Integer, User> {
            @Override
            protected User  doInBackground(String... params) {
                // Read the user from the database that has the given username and password
                return SurveyDataSource.getUserInfo(SurveyDataSource.DATA_SOURCE, getApplicationContext(),
                        params[0]); // Survey ID
            }
            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                // If a user info was found, display a toast to let the user know this Id has been used
                // and let the user to retry entering username and password.
                if (user != null) {
                    if(user.getUserId().equals(mUserId) && user.getPassword().equals(mPassword)){
                        // Start the AdminMenuActivity and store the user credentials
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        mAdminRole = user.getAdminRole();
                        editor.putString("mUserId", mUserId);
                        editor.putString("mAdminRole",mAdminRole);
                        editor.commit();
                        Intent intent = new Intent(AdminLoginActivity.this, AdminMenuActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_please_check_login_info,
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_please_check_login_info,
                            Toast.LENGTH_LONG).show();

                }
            }
        }

    public void addListenerOnButtonCancel() {

        final Context context = this;

        button = (Button) findViewById(R.id.button_cancel);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }

        });
    }

    public void addListenerOnButtonSignUp() {

        final Context context = this;

        buttonSignUp = (Button) findViewById(R.id.button_Sign_up);

        buttonSignUp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, AdminSignUpActivity.class);
                startActivity(intent);
            }

        });
    }

    public void addListenerOnTextViewForgotPassword() {
        final Context context = this;
        TextView tvForgotPassword = (TextView) findViewById(R.id.textview_forgot_password);
        tvForgotPassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, AdminUpdatePswdActivity.class);
                startActivity(intent);
            }

        });
    }
}