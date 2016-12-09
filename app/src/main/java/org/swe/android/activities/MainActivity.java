package org.swe.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
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
import org.swe.android.helpers.NetworkHelper;
public class MainActivity extends Activity {

    private ConnectivityManager mConnectivityManager;
    private String mSurveyId;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_main);

        // Set up Take Survey button
        final Button takeSurveyButton = (Button) findViewById(R.id.button_take_survey);

        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

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
                    setSurveyId();

                    // If we have a survey ID, run the AsyncTask to get the survey from the database
                    if (mSurveyId != null) {
                        new GetSurveyTask().execute(mSurveyId);

                    }
                }
                // Set up the Connectivity Manager
                mConnectivityManager = (ConnectivityManager) getApplicationContext().
                        getSystemService(Context.CONNECTIVITY_SERVICE);
            }
        });

        // Set up Admin login button
        addListenerOnButtonAdmin();
    }

    // Get the survey ID that the user has entered
    private void setSurveyId() {
        EditText editText = (EditText) findViewById(R.id.edit_text);

        // If the user has not entered a value, show a toast to let them know to enter
        // a survey ID, otherwise, set the survey ID to the value they have entered
        if (TextUtils.getTrimmedLength(editText.getText()) == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.toast_please_enter_survey_id,
                    Toast.LENGTH_SHORT).show();
            mSurveyId = null;
        } else {
            mSurveyId = editText.getText().toString().trim();
        }
    }

    private class GetSurveyTask extends AsyncTask<String, Integer, SurveyParcelable> {
        @Override
        protected SurveyParcelable doInBackground(String... params) {
            // Read the survey from the database that has the given survey ID
            return SurveyDataSource.getDeployedSurvey(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(),
                    params[0]); // Survey ID
        }

        @Override
        protected void onPostExecute(SurveyParcelable survey) {
            super.onPostExecute(survey);

            // If a survey was not found, display a toast to let the user know and to allow
            // them to retry entering the survey ID
            if (survey == null) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_retry_enter_survey_id,
                        Toast.LENGTH_LONG).show();
            } else {
                // Start the SurveyActivity
                Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                intent.putExtra("survey", survey);
                startActivity(intent);
            }
        }
    }

    public void addListenerOnButtonAdmin() {

        final Context context = this;

        button = (Button) findViewById(R.id.admin_login);

        button.setOnClickListener(new OnClickListener() {

            //@Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, AdminLoginActivity.class);
                startActivity(intent);

            }

        });

    }
}
