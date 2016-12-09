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
import android.widget.AdapterView.OnItemClickListener;

import org.swe.android.R;
import org.swe.android.adapters.SurveyPoolAdapter;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.SurveyParcelable;
import org.swe.android.datasource.User;
import org.swe.android.helpers.NetworkHelper;
import java.util.ArrayList;

/**
 * Created by YongjiLi on 3/29/16.
 */
public class SurveyPoolActivity extends Activity {

    private ListView listView;
    private SurveyPoolAdapter adapter;
    private ConnectivityManager mConnectivityManager;
    private ArrayList<body> l;
    private int mNumberofSurveys = 0;
    protected String[]array;

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

        if (userInfo.getSurveyList() == null){
            mNumberofSurveys = 0;
        } else
        mNumberofSurveys = userInfo.getSurveyList().size();

        if (mNumberofSurveys !=0) {

            l = new ArrayList<body>();
            for (int i = mNumberofSurveys-1; i >= 0; i--) {
                body b = new body();
                b.setSurveyId(userInfo.getSurveyList().get(i).getSurveyId());
                l.add(b);
            }
            listView = (ListView) findViewById(R.id.listView_the_list);
        }

        adapter = new SurveyPoolAdapter(SurveyPoolActivity.this, l);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String surveyId = l.get(position).getSurveyId();

                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    new GetSurveyTask().execute(surveyId);
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
            return SurveyDataSource.getSurvey(SurveyDataSource.DATA_SOURCE,
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
                Intent intent = new Intent(SurveyPoolActivity.this, DeploySurveyActivity.class);
                intent.putExtra("survey", survey);
                startActivity(intent);
            }
        }
    }


}