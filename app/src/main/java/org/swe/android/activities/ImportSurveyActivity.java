package org.swe.android.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.adapters.SurveyTemplatesAdapter;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.SurveyTemplates;
import org.swe.android.helpers.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongjiLi on 1/31/16.
 */
public class ImportSurveyActivity extends AppCompatActivity {

    private ConnectivityManager mConnectivityManager;
    ArrayList list;//the list in the adapter
    private ListView listView;
    private SurveyTemplatesAdapter adapter;
    private String mSurveyId;
    private String mSurveyDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_survey_import);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Set the title in the action bar to the Survey ID
            Intent intent = getIntent();
            mSurveyId = intent.getStringExtra("surveyId");
            mSurveyDescription = intent.getStringExtra("surveyDescription");
            ab.setTitle(getString(R.string.action_bar_title) + " " + mSurveyId);
            ab.setDisplayHomeAsUpEnabled(false);
        }
        // Prevent overlapping fragments by returning if savedInstanceState is not null
        if (savedInstanceState != null) {
            return;
        }

        list = new ArrayList();
        new ReadTempListTask().execute();

        listView = (ListView) findViewById(R.id.listView_template_List);


        final List<tempBody> l;
        l=new ArrayList<tempBody>();

        tempBody template01=new tempBody();
        tempBody template02=new tempBody();
        tempBody template03=new tempBody();

        template01.templateId = getString(R.string.text_templateId_Core_Study_Survey);
        template02.templateId = getString(R.string.text_templateId_Core_Adult_Survey);
        template03.templateId = getString(R.string.text_templateId_Core_Activity_Meter);

        template01.templateIntro = getString(R.string.text_templateIntro_Core_Study_Survey);
        template02.templateIntro = getString(R.string.text_templateIntro_Core_Adult_Survey);
        template03.templateIntro = getString(R.string.text_templateIntro_Core_Activity_Meter);

        l.add(template01);
        l.add(template02);
        l.add(template03);

        adapter = new SurveyTemplatesAdapter(ImportSurveyActivity.this, l);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String templateId = l.get(position).getTemplateId();

                Toast.makeText(getApplicationContext(),
                        templateId,
                        Toast.LENGTH_LONG).show();
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                        new ImportTemplateTask().execute(templateId);
                }
                finish();
            }
        });

        // Set up the buttons
        final Button skipButton = (Button) findViewById(R.id.button_skip_import);

        //Set up the listener for the preview button
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user does not have an internet connection, let them know that one
                // is required to look up the survey
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    // Start the CreateSurveyActivity
                    Intent intent = new Intent(ImportSurveyActivity.this, CreateSurveyActivity.class);
                    intent.putExtra("surveyId", mSurveyId);
                    intent.putExtra("surveyDescription",mSurveyDescription);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private class ReadTempListTask extends AsyncTask<String, Integer, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... params) {
            // Read all of the templates from the database
            return SurveyDataSource.getTempList(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext());
        }

        @Override
        protected void onPostExecute(ArrayList tempList) {
            super.onPostExecute(tempList);
            // If a survey was not found, display a toast to let the user know and to allow
            // them to retry entering the survey ID
            if (tempList == null) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_retry_enter_survey_id,
                        Toast.LENGTH_LONG).show();
            } else {
                list = (ArrayList<String>)tempList.clone();
            }
        }
    }

    private class ImportTemplateTask extends AsyncTask<String, Integer, SurveyTemplates> {
        @Override
        protected SurveyTemplates doInBackground(String... params) {
            // Read the template from the database that has the given template ID
            return SurveyDataSource.getTemplateQuestions(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(),
                    params[0]); // Template ID
        }

        @Override
        protected void onPostExecute(SurveyTemplates template) {
            super.onPostExecute(template);

            // If a template was not found, display a toast to let the user know and to allow
            // them to retry entering the template ID
            if (template == null) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_retry_enter_template_id,
                        Toast.LENGTH_LONG).show();
            } else {
                // Start the CreateSurveyActivity
                template.setTemplateId(mSurveyId);
                Intent intent = new Intent(ImportSurveyActivity.this, CreateSurveyActivity.class);
                intent.putExtra("surveyId", mSurveyId);
                intent.putExtra("surveyDescription", mSurveyDescription);
                intent.putExtra("template", template);
                startActivity(intent);
            }
        }
    }

    public class tempBody {
        public String templateId;
        public String templateIntro;
        public String getTemplateId() {
            return templateId;
        }
        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }
        public String getTemplateIntro() {
            return templateIntro;
        }
        public void setTemplateIntro(String templateIntro) {
            this.templateIntro = templateIntro;
        }
    }
}