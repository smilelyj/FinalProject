package org.swe.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.adapters.DeployedSurveyListAdapter;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.SurveyParcelable;
import org.swe.android.datasource.SurveyResultsQuantified;
import org.swe.android.datasource.User;
import org.swe.android.helpers.NetworkHelper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by YongjiLi on 4/5/16.
 */
public class DeployedSurveyListActivity extends Activity {

    private ListView listView;
    private ConnectivityManager mConnectivityManager;

    private ArrayList<body> l;

    protected String[]array;
    private String takeOrViewFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.list_done);
        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        final User userInfo = getIntent().getExtras().getParcelable("UserInfo");
        takeOrViewFlag = getIntent().getExtras().getString("takeOrViewFlag");

        assert userInfo != null;
        int mNumberofSurveys;
        if (userInfo.getSurveyList() == null){
            mNumberofSurveys = 0;
        } else {
            mNumberofSurveys = userInfo.getSurveyList().size();
        }
        l = new ArrayList<body>();
        //reverse order
        if (mNumberofSurveys > 0) {
            for (int i = mNumberofSurveys - 1; i >= 0 ; i--) {
                if( userInfo.getSurveyList().get(i).getDeployedSurveyId() == null){
                    userInfo.getSurveyList().get(i).setDeployedSurveyId(new ArrayList<String>());
                }

                int numOfDeployedSurvey = userInfo.getSurveyList().get(i).getDeployedSurveyId().size();
                if (numOfDeployedSurvey > 0) {
                    for(int j = numOfDeployedSurvey-1; j >= 0;j--){
                        body b = new body();
                        b.setSurveyId(userInfo.getSurveyList().get(i).getDeployedSurveyId().get(j));
                        l.add(b);
                    }

                }
            }
            listView = (ListView) findViewById(R.id.listView_the_list);
        }

        DeployedSurveyListAdapter adapter = new DeployedSurveyListAdapter(DeployedSurveyListActivity.this, l);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String surveyId = l.get(position).getSurveyId();

                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    if (Objects.equals(takeOrViewFlag, "takeSurvey")) {
                        new GetSurveyTask().execute(surveyId);
                    }
                    if (Objects.equals(takeOrViewFlag, "viewResults")) {
                        new GetSurveyResultsTask().execute(surveyId);
                    }
                }
            }
        });

        Button btnDone=(Button)findViewById(R.id.button_list_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Close the Activity
                finish();
            }
        });
    }

    public class body{//a class to restore the data of adapter
        public String surveyId;
        public String getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(String surveyId) {
            this.surveyId = surveyId;
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
                Intent intent = new Intent(DeployedSurveyListActivity.this, SurveyActivity.class);
                intent.putExtra("survey", survey);
                startActivity(intent);
            }
        }
    }

    private class GetSurveyResultsTask extends AsyncTask<String, Integer, SurveyResultsQuantified> {
        @Override
        protected SurveyResultsQuantified doInBackground(String... params) {
            // Read the survey from the database that has the given survey ID
            return SurveyDataSource.getSurveyResults(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(),
                    params[0]); // Survey ID
        }

        @Override
        protected void onPostExecute(SurveyResultsQuantified surveyResultsQuantified) {
            super.onPostExecute(surveyResultsQuantified);

            // If survey results were not found, display a toast to let the user know and to allow
            // them to retry entering the survey ID
            if (surveyResultsQuantified == null) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_retry_enter_survey_id,
                        Toast.LENGTH_LONG).show();
            } else {
                // Create the intent to start the SurveyResultsActivity
                Intent data = new Intent(DeployedSurveyListActivity.this, SurveyResultsActivity.class);

                data.putExtra("results",surveyResultsQuantified);
                startActivity(data);
            }
        }
    }


}