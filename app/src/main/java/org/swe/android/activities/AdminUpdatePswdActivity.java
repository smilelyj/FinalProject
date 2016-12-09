package org.swe.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.User;
import org.swe.android.helpers.NetworkHelper;

/**
 * Created by YongjiLi on 11/15/15.
 */
public class AdminUpdatePswdActivity extends Activity{

    Button buttonUpdatePassword;
    Button buttonCancel;
    private ConnectivityManager mConnectivityManager;
    private String mUserId,mEmail,mNewPassword,mNewPasswordAgain;
    private EditText etUserId,etEmail,etNewPassword, etNewPasswordAgain;
    User userWithNewPassword = new User();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.admin_update_pswd);

        etUserId = (EditText) findViewById(R.id.edittext_username);
        etEmail = (EditText) findViewById(R.id.edittext_email);
        etNewPassword = (EditText) findViewById(R.id.edittext_new_password);
        etNewPasswordAgain = (EditText) findViewById(R.id.edittext_new_password_again);

        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        addListenerOnButtonCancel();
        addListenerOnButtonUpdatePassword();

    }

    public void addListenerOnButtonUpdatePassword() {
        buttonUpdatePassword = (Button) findViewById(R.id.button_update);
        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    // If we have username and password, run the AsyncTask to get the Username from
                    // the database table SWE_user


                    inputCheck();
                    if (mUserId != null && mEmail != null  &&
                            mNewPassword != null && mNewPasswordAgain != null &&
                            mNewPassword.equals(mNewPasswordAgain) ) {
                        new idAndEmailCheckTask().execute(mUserId);
                    }
                }
            }
        });
    }

    // Get the UserId that the user has entered ID= the string from user input
    private void inputCheck() {

        // If the user has not entered a value, show a toast to let them know to enter
        // your Username, otherwise, set the username to the value they have entered
        if (TextUtils.getTrimmedLength(etUserId.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_user_name,
                    Toast.LENGTH_SHORT).show();
            mUserId = null;
        }
        else {
            mUserId = etUserId.getText().toString().trim();
        }

        // If the user has not entered a value, show a toast to let them know to enter
        // password, otherwise, set the password to the value they have entered
        if (TextUtils.getTrimmedLength(etEmail.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_email,
                    Toast.LENGTH_SHORT).show();
            mEmail = null;
        }
        else {
            mEmail = etEmail.getText().toString().trim();
        }

        if (TextUtils.getTrimmedLength(etNewPassword.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_password,
                    Toast.LENGTH_SHORT).show();
            mNewPassword = null;
        }
        else {
            mNewPassword = etNewPassword.getText().toString().trim();
        }

        if (TextUtils.getTrimmedLength(etNewPasswordAgain.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_password_again,
                    Toast.LENGTH_SHORT).show();
            mNewPasswordAgain = null;
        }
        else {
            mNewPasswordAgain = etNewPasswordAgain.getText().toString().trim();
            if(!mNewPasswordAgain.equals(mNewPassword)){
                Toast.makeText(getApplicationContext(),
                        R.string.toast_password__does_not_match,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class idAndEmailCheckTask extends AsyncTask<String, Integer, User> {
        @Override
        protected User doInBackground(String... params) {
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
                if(user.getUserId().equals(mUserId) && user.getEmail().equals(mEmail)){
                    userWithNewPassword.setPassword(mNewPassword);
                    userWithNewPassword.setUserId(user.getUserId());
                    userWithNewPassword.setEmail(user.getEmail());
                    userWithNewPassword.setAdminRole(user.getAdminRole());
                    userWithNewPassword.setSurveyList(user.getSurveyList());
                    userWithNewPassword.setRegion(user.getRegion());

                    new passwordUpdateTask().execute(userWithNewPassword);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_please_check_login_info,
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_userid_does_not_exist,
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    private class passwordUpdateTask extends AsyncTask<User, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(User... user) {
            return SurveyDataSource.insertUser(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), userWithNewPassword);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_update_pswd_success),
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AdminUpdatePswdActivity.this, AdminLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_saving_answers),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void addListenerOnButtonCancel() {

        final Context context = this;
        buttonCancel = (Button) findViewById(R.id.button_update_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
            finish();
            }
        });
    }


}