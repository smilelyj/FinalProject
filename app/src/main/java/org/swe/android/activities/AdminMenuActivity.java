package org.swe.android.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.SurveyParcelable;
import org.swe.android.datasource.User;
import org.swe.android.helpers.NetworkHelper;

import java.util.Objects;

/**
 * Created by YongjiLi on 9/23/15.
 */
public class AdminMenuActivity extends Activity {

    private ConnectivityManager mConnectivityManager;
    private String mSurveyId;
    public static final String PREFS_NAME = "LoginPrefs";
    private String mUserId;
    private String mAdminRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.admin_menu);

        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        final Button createSurveyButton = (Button) findViewById(R.id.button_create_survey);
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        createSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user does not have an internet connection, let them know that one
                // is required to look up the survey
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    // Set the survey ID based on what the user has entered
                    setSurveyId();
                    // If we have a survey ID, run the AsyncTask to check the survey from the database
                    if (mSurveyId != null) {
                        new CreateSurveyCheckTask().execute(mSurveyId);
                    }
                }
            }
        });


        SharedPreferences settings;
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        mUserId = settings.getString("mUserId", null); //2
        mAdminRole = settings.getString("mAdminRole",null);


        // Set up Take Survey button
        final Button takeSurveyButton = (Button) findViewById(R.id.button_take_survey_admin);





        takeSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user does not have an internet connection, let them know that one
                // is required to look up the survey
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    // Set the survey ID based on what the user has entered
                    //setSurveyId();

                    // If we have a survey ID, run the AsyncTask to get the survey from the database
                   // if (mSurveyId != null) {
                        new GetDeployedSurveyListFromTakeTask().execute(mUserId);
                   // }
                }
            }
        });
        addListenerOnButtonResults();
        addListenerOnButtonDepoly();
        addListenerOnButtonDone();
        addListenerOnButtonActivation();
    }

    // Get the survey ID that the user has entered ID= the string from user input + date + time
    private void setSurveyId() {
        EditText editText = (EditText) findViewById(R.id.editTextAdmin);

        // If the user has not entered a value, show a toast to let them know to enter
        // a survey ID, otherwise, set the survey ID to the value they have entered
        if (TextUtils.getTrimmedLength(editText.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_survey_id,
                    Toast.LENGTH_SHORT).show();
            mSurveyId = null;
        }
        else {
            mSurveyId = editText.getText().toString().trim();
        }
    }

    protected void addListenerOnButtonResults() {
        final Context context = this;

        Button viewResultsButton = (Button) findViewById(R.id.button_view_results_admin);

        viewResultsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // If the user does not have an internet connection, let them know that one
                // is required
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    new GetDeployedSurveyListFromViewTask().execute(mUserId);

                }
            }
        });
    }
    protected void addListenerOnButtonDepoly() {
        final Context context = this;

        Button surveyDeployButton = (Button) findViewById(R.id.button_deploy_survey);

        surveyDeployButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // If the user does not have an internet connection, let them know that one
                // is required
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    new GetSurveyPoolTask().execute(mUserId);

                }
            }
        });
    }
    protected void addListenerOnButtonDone() {

        final Context context = this;

        Button buttonDone = (Button) findViewById(R.id.button_done);

        buttonDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Log out and clear SharedPreference Data
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("mUserId");
                editor.commit();
                finish();

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void addListenerOnButtonActivation() {
        final Context context = this;
        final Button activationCodeButton = (Button) findViewById(R.id.button_activation_code);
        if (!Objects.equals(mAdminRole, "admin")){
            activationCodeButton.setVisibility(View.GONE);
        }
        else {
            activationCodeButton.setVisibility(View.VISIBLE);
        }
        activationCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // If the user does not have an internet connection, let them know that one
                // is required
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    // Start the ActivationActivity
                    Intent i = new Intent(AdminMenuActivity.this, ActivationCodeGeneratorActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private class CreateSurveyCheckTask extends AsyncTask<String, Integer, SurveyParcelable> {
       @Override
       protected SurveyParcelable  doInBackground(String... params) {
            // Read the survey from the database that has the given survey ID
            return SurveyDataSource.getSurvey(SurveyDataSource.DATA_SOURCE,getApplicationContext(),
                    params[0]); // Survey ID

        }

       @Override
        protected void onPostExecute(SurveyParcelable survey) {
            super.onPostExecute(survey);

           // If a survey was found, display a toast to let the user know this Id has been used
           // and let the user to retry entering another survey ID.
            if (survey == null) {
                //Start the CreateSurveyActivity
                Intent intent = new Intent(AdminMenuActivity.this, ImportSurveyActivity.class);
                intent.putExtra("surveyId", mSurveyId);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_retry_enter_a_new_survey_id,
                        Toast.LENGTH_LONG).show();

            }
        }
    }


    private class GetSurveyPoolTask extends AsyncTask<String, Integer, User> {
        @Override
        protected User doInBackground(String... params) {
            // Read the surveys from the database whose owner is the given userID
            return SurveyDataSource.getUserInfo(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(),
                    mUserId); // User ID
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            // If a survey was not found, display a toast to let the user know and to allow
            // them to retry entering the survey ID
            if (user.getSurveyList().size()==0) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_no_survey_in_survey_pool,
                        Toast.LENGTH_LONG).show();
            }
            else {
                // Start the SurveyActivity
                Intent i = new Intent(AdminMenuActivity.this, SurveyPoolActivity.class);
                i.putExtra("UserInfo", user);
                startActivity(i);
            }
        }
    }

    private class GetDeployedSurveyListFromTakeTask extends AsyncTask<String, Integer, User> {
        @Override
        protected User doInBackground(String... params) {
            // Read the surveys from the database whose owner is the given userID
            return SurveyDataSource.getUserInfo(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(),
                    mUserId); // User ID
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            // If a survey was not found, display a toast to let the user know and to allow
            // them to retry entering the survey ID
            if (user.getSurveyList().size() == 0) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_no_survey_in_survey_pool,
                        Toast.LENGTH_LONG).show();
            } else {
                //Restore the userinfo
                // Start the SurveyActivity
                Intent i = new Intent(AdminMenuActivity.this, DeployedSurveyListActivity.class);
                i.putExtra("UserInfo", user);
                String tempFlag = "takeSurvey";
                i.putExtra("takeOrViewFlag", tempFlag);
                startActivity(i);
            }
        }
    }
    private class GetDeployedSurveyListFromViewTask extends AsyncTask<String, Integer, User> {
        @Override
        protected User doInBackground(String... params) {
            // Read the surveys from the database whose owner is the given userID
            return SurveyDataSource.getUserInfo(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(),
                    mUserId); // User ID
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            // If a survey was not found, display a toast to let the user know and to allow
            // them to retry entering the survey ID
            if (user.getSurveyList().size()== 0) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_no_survey_in_survey_pool,
                        Toast.LENGTH_LONG).show();
            } else {
                //Restore the userinfo
                // Start the SurveyActivity
                Intent i = new Intent(AdminMenuActivity.this, DeployedSurveyListActivity.class);
                i.putExtra("UserInfo", user);
                String tempFlag = "viewResults";
                i.putExtra("takeOrViewFlag", tempFlag);
                startActivity(i);
            }
        }
    }
}