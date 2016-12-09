package org.swe.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.datasource.ActivationCode;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.SurveyList;
import org.swe.android.datasource.User;
import org.swe.android.helpers.NetworkHelper;

import java.util.ArrayList;

/**
 * Created by YongjiLi on 11/17/15.
 */
public class AdminSignUpActivity extends Activity {

    private Button buttonSignUp;
    private Button buttonCancel;
    private ConnectivityManager mConnectivityManager;
    private String mUserId, mEmail, mPassword, mPasswordAgain;
    private EditText etUserId, etEmail, etPassword, etPasswordAgain, etKey;
    private RadioButton rbCoordinator;
    private User newUser = new User();
    private ActivationCode returnedActivationCode = new ActivationCode();
    private boolean foundActivationCode;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.admin_sign_up);

        etUserId = (EditText) findViewById(R.id.et_sign_up_username);
        etEmail = (EditText) findViewById(R.id.et_sign_up_email);
        etPassword = (EditText) findViewById(R.id.et_sign_up_password);
        etPasswordAgain = (EditText) findViewById(R.id.et_sign_up_password_again);
        etKey = (EditText) findViewById(R.id.et_activation_key);

        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //Set up the radio button
        rbCoordinator = (RadioButton) findViewById(R.id.radioButtonCoordinator);

        addListenerOnButtonCancel();
        addListenerOnButtonSignUp();

    }

    public void addListenerOnButtonSignUp() {

        buttonSignUp = (Button) findViewById(R.id.button_Sign_Up);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    // If we have username and password, run the AsyncTask to get the Username from the database tabel SWE_user
                    if (inputCheck()) {
                        new userNameCheckTask().execute(mUserId);
                    }
                }
            }
        });
    }

    // Get the UserId that the user has entered ID= the string from user input
    private boolean inputCheck() {

        // If the user has not entered a value, show a toast to let them know to enter
        // your Username, otherwise, set the username to the value they have entered
        if (TextUtils.getTrimmedLength(etUserId.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_user_name,
                    Toast.LENGTH_SHORT).show();
            mUserId = null;
            return false;
        } else {
            mUserId = etUserId.getText().toString().trim();
        }

        if (TextUtils.getTrimmedLength(etKey.getText()) != 0) {
            String key = etKey.getText().toString().trim();
            new getActivationKeyTask().execute(key);
        }

        // If the user has not entered a value, show a toast to let them know to enter
        // password, email address, admin_role, or activation code. Otherwise, set the password to the value they have entered
        if (TextUtils.getTrimmedLength(etEmail.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_email,
                    Toast.LENGTH_SHORT).show();
            mEmail = null;
            return false;
        } else {
            mEmail = etEmail.getText().toString().trim();
            if (!isValidEmail(mEmail)) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_please_check_email,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (TextUtils.getTrimmedLength(etPassword.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_password,
                    Toast.LENGTH_SHORT).show();
            mPassword = null;
            return false;
        } else {
            mPassword = etPassword.getText().toString().trim();
        }

        if (TextUtils.getTrimmedLength(etPasswordAgain.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_password_again,
                    Toast.LENGTH_SHORT).show();
            mPasswordAgain = null;
            return false;
        } else {
            mPasswordAgain = etPasswordAgain.getText().toString().trim();
            if (!mPasswordAgain.equals(mPassword)) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_password__does_not_match,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (!rbCoordinator.isChecked()) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_select_coordinator,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        //no activation code
        if (TextUtils.getTrimmedLength(etKey.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_activation_code,
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String key = etKey.getText().toString().trim();
            //Invalid activation code
            new getActivationKeyTask().execute(key);
            if(!foundActivationCode){
                Toast.makeText(getApplicationContext(),
                        R.string.toast_please_enter_activation_code,
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            if (returnedActivationCode.getKey() == null) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_incorrect_activation_key,
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        return true;
    }


    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    private class userNameCheckTask extends AsyncTask<String, Integer, User> {
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
            if (user == null) {
                newUser.setPassword(mPassword);
                newUser.setUserId(mUserId);
                newUser.setEmail(mEmail);
                newUser.setAdminRole("coordinator");
                newUser.setSurveyList(new ArrayList<SurveyList>());
                //returnedActivationCode.setIsNew(false);
                //new updateActivationCodeTask().execute(returnedActivationCode);
                new SignUpTask().execute(newUser);
            } else {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_userid_is_already_existed,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private class SignUpTask extends AsyncTask<User, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(User... user) {
            return SurveyDataSource.insertUser(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), newUser);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_sign_up_success),
                        Toast.LENGTH_LONG).show();

                returnedActivationCode.setKey(null);
                Intent intent = new Intent(AdminSignUpActivity.this, AdminLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_saving_user),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private class getActivationKeyTask extends AsyncTask<String, Integer, ActivationCode> {
        @Override
        protected ActivationCode doInBackground(String... params) {
            // Read the user from the database that has the given username and password
            return SurveyDataSource.getActivationCode(SurveyDataSource.DATA_SOURCE, getApplicationContext(),
                    params[0]); //key
        }

        @Override
        protected void onPostExecute(ActivationCode activationCode) {
            super.onPostExecute(activationCode);

            // If a user info was found, display a toast to let the user know this Id has been used
            // and let the user to retry entering username and password.
            if (activationCode != null) {
                returnedActivationCode.setIsNew(activationCode.getIsNew());
                returnedActivationCode.setKey(activationCode.getKey());
                returnedActivationCode.setDate(activationCode.getDate());
                foundActivationCode = true;
            } else {
                foundActivationCode = false;
            }
        }
    }

    private class updateActivationCodeTask extends AsyncTask<ActivationCode, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(ActivationCode... code) {
            return SurveyDataSource.generateActivationKey(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), returnedActivationCode);
        }
    }

    public void addListenerOnButtonCancel() {

        final Context context = this;
        buttonCancel = (Button) findViewById(R.id.button_Sign_Up_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
}
