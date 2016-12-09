package org.swe.android.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.adapters.DeploySurveyAdapter;
import org.swe.android.datasource.QuestionParcelable;
import org.swe.android.datasource.QuestionTypes;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.SurveyList;
import org.swe.android.datasource.SurveyParcelable;
import org.swe.android.datasource.User;
import org.swe.android.helpers.NetworkHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by YongjiLi on 4/4/16.
 */
public class DeploySurveyActivity extends AppCompatActivity {
    private ConnectivityManager mConnectivityManager;
    private ListView listView;
    private DeploySurveyAdapter adapter;
    // private Button btnEdit;
    ArrayList<body> l;
    private SurveyParcelable newSurveyParcelable = new SurveyParcelable();
    private int mNumberofQuestions = 0;
    protected String[]array;
    protected String[] arrayQtype;
    public SurveyParcelable survey = new SurveyParcelable();

    public QuestionParcelable newQuestion = new QuestionParcelable();

    private QuestionTypes questionTypes = new QuestionTypes();
    public static final String PREFS_NAME = "LoginPrefs";
    private String mUserId;
    private String surveyPoolId, deployedSurveyId;
    private User mUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_survey_deploy);
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        survey = getIntent().getExtras().getParcelable("survey");

        ActionBar ab = getSupportActionBar();
        if (ab != null && survey != null) {
            // Set the title in the action bar to the Survey ID
            ab.setTitle(getString(R.string.action_bar_title) + " " + survey.getSurveyId());
            ab.setDisplayHomeAsUpEnabled(false);
        }

        assert survey != null;
        mNumberofQuestions = survey.getQuestions().size();

        initData();
        adapter = new DeploySurveyAdapter(DeploySurveyActivity.this, l);
        listView.setAdapter(adapter);

        Button btnDeploy = (Button)findViewById(R.id.button_deploy_survey);
        btnDeploy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If the user does not have an internet connection, let them know that one
                // is required to submit the survey
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    // add an alert dialog
                    // Deploy the survey
                    deploySurveyDialog(v);
                }


            }
        });

        Button btnDone=(Button)findViewById(R.id.button_deploy_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Close the Activity
                finish();
            }
        });
    }

    private void deploySurveyDialog(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_dialog_deploy_the_survey);
        //builder.setMessage(R.string.simple_dialog_deploy_the_survey);

        //set onClickListener
        builder.setPositiveButton(R.string.button_postive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences settings;
                settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
                mUserId = settings.getString("mUserId", null); //2


                deployedSurveyId = survey.getSurveyId()+new SimpleDateFormat("yyMMdd").format(new Date()) +
                        Short.toString((short) System.currentTimeMillis());//

                surveyPoolId = survey.getSurveyId();

                survey.setSurveyId(deployedSurveyId);

                new InsertDeployedSurveyToUserTask().execute(mUserId);

                new InsertDeployedSurveyTask().execute(survey);
                //Close the Activity
                showDeployedSurveyIdDialog(view);


            }
        });
        builder.setNegativeButton(R.string.button_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.toast_negative_not_deployed, Toast.LENGTH_SHORT).show();
            }
        });

        //Dialog is cancelable
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void showDeployedSurveyIdDialog(View view) {AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("The deployed survey Id is: "+ deployedSurveyId);
        //builder.setMessage(deployedSurveyId);

        //set onClickListener
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        //Dialog is cancelable
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void initData() {//Initialize
        array = new String[mNumberofQuestions];
        arrayQtype = new String[mNumberofQuestions];
        for(int n=0;n<mNumberofQuestions;n++){
            array[n] = survey.getQuestions().get(n).getQuestion();
        }

        for(int n=0;n<mNumberofQuestions;n++){
            arrayQtype[n] = survey.getQuestions().get(n).getQuestionType();
        }

        l=new ArrayList<body>();
        for(int i=0;i<mNumberofQuestions;i++){
            body b=new body();
            b.question=array[i];
            b.questiontype=arrayQtype[i];
            l.add(b);
        }
        listView = (ListView) findViewById(R.id.listView_survey_list);
    }
    public class body{//a class to restore the data of adapter
        public String question;
        public String questiontype;
        public String getQuestion() {
            return question;
        }
        public void setQuestion(String question) {
            this.question = question;
        }
        public String getQuestiontype() {
            return questiontype;
        }
        public void setQuestiontype(String questiontype) {
            this.questiontype = questiontype;
        }
    }


    private class InsertDeployedSurveyToUserTask extends AsyncTask<String, Integer, User> {

        @Override
        protected User doInBackground(String... surveyParcelable) {

            return SurveyDataSource.getUserInfo(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), mUserId);
        }

        @Override
        protected void onPostExecute(User user) {

            if( user.getSurveyList() == null){
                user.setSurveyList(new ArrayList<SurveyList>());
            }
            int numOfSurveyList = user.getSurveyList().size();

            if (numOfSurveyList != 0){
                for (int i = 0; i<numOfSurveyList; i++){
                   if(Objects.equals(user.getSurveyList().get(i).getSurveyId(), surveyPoolId)){
                       if(user.getSurveyList().get(i).getDeployedSurveyId() == null){
                           user.getSurveyList().get(i).setDeployedSurveyId(new ArrayList<String>());
                           //ArrayList<String> d = new ArrayList<String>();
                           //d.add(deployedSurveyId);
                           user.getSurveyList().get(i).getDeployedSurveyId().add(deployedSurveyId);
                       }else {
                           user.getSurveyList().get(i).getDeployedSurveyId().add(deployedSurveyId);
                       }
                   }
                }
            }
            mUser = user;
            new userUpdateTask().execute(mUser);
        }
    }

    //   private class InsertSurveyTask extends AsyncTask<SurveyResults, Integer, Boolean> {
    private class InsertDeployedSurveyTask extends AsyncTask<SurveyParcelable, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(SurveyParcelable... surveyParcelable) {

            return SurveyDataSource.insertDeployedSurvey(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), survey);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_survey_deploy_success),
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_saving_answers),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private class userUpdateTask extends AsyncTask<User, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(User... surveyParcelable) {

            return SurveyDataSource.insertUser(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), mUser);
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
        }
    }
}