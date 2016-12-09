package org.swe.android.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.datasource.QuestionParcelable;
import org.swe.android.datasource.QuestionTypes;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.SurveyList;
import org.swe.android.datasource.SurveyParcelable;
import org.swe.android.datasource.SurveyTemplates;
import org.swe.android.datasource.User;
import org.swe.android.helpers.NetworkHelper;

import java.util.ArrayList;

/**
 * Created by YongjiLi on 10/8/15.
 */

public class CreateSurveyActivity extends AppCompatActivity {

    private ConnectivityManager mConnectivityManager;
    //private int mNumberOfQuestions;
    private String mCurrentQuestionString;
    private int mCurrentQuestion = 0;

    public SurveyParcelable mSurveyParcelable = new SurveyParcelable();
    public SurveyParcelable tempParcelable = new SurveyParcelable();

    private String mCurrentQuestionType;
    private QuestionParcelable mQuestionParcelable;
    private String mSurveyId;

    private User mUser;
    private String mUserId;
    private String mSurveyDescription;
    private TextView textViewChoices;
    private TextView QuestionNumber;

    private SurveyTemplates template  = new SurveyTemplates();
    private QuestionTypes questionTypes = new QuestionTypes();
    public static final String PREFS_NAME = "LoginPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_survey_create);
        clearFragmentQuestionCreate();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Set the title in the action bar to the Survey ID
            Intent intent = getIntent();
            mSurveyId = intent.getStringExtra("surveyId");
            template = intent.getExtras().getParcelable("template");
            mSurveyDescription = intent.getStringExtra("surveyDescription");


            if (mSurveyParcelable.getQuestions() == null){
                mSurveyParcelable.setQuestions(new ArrayList<QuestionParcelable>());
                // Set the survey ID and owner in the new survey
                mSurveyParcelable.setSurveyId(mSurveyId);
                mSurveyParcelable.setSurveyDescription(mSurveyDescription);

                //Retrieve Data from SharedPreferences
                SharedPreferences settings;
                String userId;
                settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
                userId = settings.getString("mUserId", null); //2
                mSurveyParcelable.setOwner(userId);
                mSurveyParcelable.setSurveyDescription(mSurveyDescription);
                mUserId = userId;
            }

           // if(template.getQuestions()!=null)
            if (intent.getExtras().getParcelable("template")!= null)
            {
                mSurveyParcelable.setQuestions(template.getQuestions());
            }
            //mSurveyParcelable = getIntent().getExtras().getParcelable("templates");

            ab.setTitle(getString(R.string.action_bar_title) + " " + mSurveyId);
            ab.setDisplayHomeAsUpEnabled(false);
            // Set the number of questions in the survey

        }

        mCurrentQuestion = mSurveyParcelable.getQuestions().size();

        QuestionNumber = (TextView) findViewById(R.id.textview_question_number);
        QuestionNumber.setText("Question " + (mCurrentQuestion+1) + ":");

        textViewChoices = (TextView) findViewById(R.id.textview_create_question);
            // Prevent overlapping fragments by returning if savedInstanceState is not null
            if (savedInstanceState != null) {
                return;
            }

        Toast.makeText(getApplicationContext(),
                R.string.toast_question_type_spinner_reminder,
                Toast.LENGTH_LONG).show();

        // Set up the spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner_question_type);
        // Set up the resource
        String[] mItems = getResources().getStringArray(R.array.question_type);
        // Set up an Adapter and then build a connection with the resource
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //connect Adapter to the spinner layout
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] question_type = getResources().getStringArray(R.array.question_type);
                Toast.makeText(getApplicationContext(), "Your choice is:" + question_type[pos], Toast.LENGTH_SHORT).show();
                mCurrentQuestionType = question_type[pos];

                    switch (mCurrentQuestionType){
                        case "Overall":{
                            textViewChoices.setText("A\nB\nC\nD\nF");
                        }
                        break;

                        case "Agree/Disagree":{
                            textViewChoices.setText("Strongly Agree\nAgree\nNot Sure\nDisagree\nStrongly Disagree");
                        }
                        break;

                        case "Confidence and Ability":{
                            textViewChoices.setText("Improved\nStay the Same\nGot Worse\nI Don't Know");
                        }
                        break;

                        case "Recommend to Others":{
                            textViewChoices.setText("Yes\nMaybe\nNo (please explain)");
                        }
                        break;
                        case "Open-Ended":{
                            textViewChoices.setText("input the answer");
                        }
                        break;

                        case "Demographic-Age":{
                            textViewChoices.setText("input his/her age");
                        }
                        break;

                        case "Demographic-Grade":{
                            textViewChoices.setText("3rd grade\n4th grade\n5th grade\n6th grade");
                        }
                        break;

                        case "Demographic-Gender":{
                            textViewChoices.setText("female\nmale");
                        }
                        break;

                        case "Demographic-Race":{
                            textViewChoices.setText("White or European American\nHispanic, Latino, or Spanish\nBlack or African-American\nAsian American\nNative Hawaiian or Pacific Islander\nNative American or Alaskan Native\nOther");
                        }
                        break;

                        case "Relationship":{
                            textViewChoices.setText("Mother\nFather\nGuardian\nTroop Leader\nTeacher\nOther");
                        }
                        case"Yes/No":{
                            textViewChoices.setText("Yes\nNo");
                        }
                        break;

                        default:
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });

            // Set up the buttons
            final Button nextQuestionButton = (Button) findViewById(R.id.button_next_question);
            final Button editButton = (Button) findViewById(R.id.button_edit_the_survey);
            final Button saveButton = (Button) findViewById(R.id.button_save_the_survey);
            final Button previewTheSurveyButton = (Button) findViewById(R.id.button_preview_the_survey);

            //Set up the listener for the preview_the_survey button

            previewTheSurveyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrentQuestion > 1) {
                        Intent intent = new Intent(CreateSurveyActivity.this, PreviewActivity.class);
                        intent.putExtra("survey", mSurveyParcelable);
                        startActivityForResult(intent, 2);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_add_question_required,
                                Toast.LENGTH_LONG).show();
                    }
                }
            });


            //Set up the listener for the preview button
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // If the user does not have an internet connection, let them know that one
                    // is required to look up the survey
                    if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_internet_connection_required,
                                Toast.LENGTH_LONG).show();
                    } else {
                        if (mCurrentQuestion > 0) {
                            // Start the EditSurveyActivity
                            Intent intent = new Intent(CreateSurveyActivity.this, EditSurveyActivity.class);
                            intent.putExtra("survey", mSurveyParcelable);
                            startActivityForResult(intent, 1);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    R.string.toast_add_question_required,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            // Set up the listener for the next button
            nextQuestionButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                // Add the current question's answer to the results before going to the next question
                    EditText editTextQuestion = (EditText) findViewById(R.id.edittext_create_question);

                    if(TextUtils.getTrimmedLength(editTextQuestion.getText()) == 0 | mCurrentQuestionType == null) {
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_please_check_survey_question,
                                Toast.LENGTH_SHORT).show();
                        mCurrentQuestionString = null;
                    }else{
                        mCurrentQuestionString = editTextQuestion.getText().toString().trim();
                        if (mSurveyParcelable.getQuestions() == null) {
                            mSurveyParcelable.setQuestions(new ArrayList<QuestionParcelable>());
                        }
                        addQuestion();

                        // Increment to to the next question
                        QuestionNumber.setText("Question " + (mCurrentQuestion + 1) + ":");

                        // Get the new question
                        clearFragmentQuestionCreate();
                    }
                }
            });

            // Set up the Connectivity Manager
            mConnectivityManager = (ConnectivityManager) getApplicationContext().
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Set up the listener for the submit button
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // If the user does not have an internet connection, let them know that one
                    // is required to submit the survey
                    if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_internet_connection_required,
                                Toast.LENGTH_LONG).show();
                    } else if(mCurrentQuestion < 2){
                        Toast.makeText(getApplicationContext(), R.string.toast_survey_few_questions, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        // Add the current question's answer to the results before submitting
                        // add an alert dialog
                        // Insert the results of the survey into the database
                        showCreateSuccessfulDialog(v);
                    }
                }
            });

    }
    //receive the new survey from preview activity
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){

        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                mSurveyParcelable =  data.getParcelableExtra("new_survey");
                mCurrentQuestion = data.getIntExtra("newQuestionNumber",0);
                QuestionNumber.setText("Question " + (mCurrentQuestion + 1) + ":");
            }
        }

        if (requestCode == 2){
            if (resultCode == Activity.RESULT_OK){
                mSurveyParcelable =  data.getParcelableExtra("survey_after_preview");
                mCurrentQuestion = data.getIntExtra("QuestionNumber",0);
                QuestionNumber.setText("Question " + (mCurrentQuestion + 1) + ":");
            }
        }
    }

    private void showCreateSuccessfulDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_dialog_save_the_survey);
        //builder.setMessage(R.string.dialog_message);

        //set onClickListener
        builder.setPositiveButton(R.string.button_postive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText eTQuestion = (EditText) findViewById(R.id.edittext_create_question);

                if (TextUtils.getTrimmedLength(eTQuestion.getText()) != 0){
                    addQuestion();
                }
                new InsertSurveyToUserTask().execute(mSurveyId);
                new InsertSurveyTask().execute(mSurveyParcelable);

            }
        });
        builder.setNegativeButton(R.string.button_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.toast_negative_not_saved, Toast.LENGTH_SHORT).show();
            }
        });

        //Dialog is cancelable
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    protected void clearFragmentQuestionCreate(){
        EditText eTQuestion = (EditText) findViewById(R.id.edittext_create_question);
        eTQuestion.setText("");
    }

    private void addQuestion() {

        //+++++++++++++++
        EditText eTquestion = (EditText) findViewById(R.id.edittext_create_question);
        mCurrentQuestionString = eTquestion.getText().toString().trim();
        eTquestion.setText(mCurrentQuestionString);

        QuestionParcelable newQuestion = new QuestionParcelable();
        newQuestion.setQuestion(mCurrentQuestionString);
        newQuestion.setQuestionType(mCurrentQuestionType);
        switch (mCurrentQuestionType){
            case "Overall":{
                newQuestion.setQuestionType("Overall");
                newQuestion.setChoices(questionTypes.getqTypeOverall());
                newQuestion.setExplanationRequired(false);
            }
            break;

            case "Agree/Disagree":{
                newQuestion.setQuestionType("Agree/Disagree");
                newQuestion.setChoices(questionTypes.getqTypeAgreeDisagree());
                newQuestion.setExplanationRequired(false);
            }
            break;

            case "Confidence and Ability":{
                newQuestion.setQuestionType("Confidence and Ability");
                newQuestion.setChoices(questionTypes.getqTypeConfidenceAndAbility());
                newQuestion.setExplanationRequired(false);
            }
            break;

            case "Recommend to Others":{
                newQuestion.setQuestionType("Recommend to Others");
                newQuestion.setChoices(questionTypes.getqTypeRecommendToOthers());
                newQuestion.setExplanationRequired(true);
            }
            break;

            case "Open-Ended":{
                newQuestion.setQuestionType("Open-Ended");
                newQuestion.setExplanationRequired(true);
            }
            break;

            case "Demographic-Age":{
                newQuestion.setQuestionType("Demographic-Age");
                newQuestion.setExplanationRequired(true);
            }
            break;

            case "Demographic-Grade":{
                newQuestion.setQuestionType("Demographic-Grade");
                newQuestion.setChoices(questionTypes.getqTypeDemoGrade());
                newQuestion.setExplanationRequired(false);
            }
            break;

            case "Demographic-Gender":{
                newQuestion.setQuestionType("Demographic-Gender");
                newQuestion.setChoices(questionTypes.getqTypeDemoSex());
                newQuestion.setExplanationRequired(false);
            }
            break;

            case "Demographic-Race":{
                newQuestion.setQuestionType("Demographic-Race");
                newQuestion.setChoices(questionTypes.getqTypeDemoEthnic());
                newQuestion.setExplanationRequired(true);
            }
            break;

            case "Relationship":{
                newQuestion.setQuestionType("Relationship");
                newQuestion.setChoices(questionTypes.getqTypeDemoRelationship());
                newQuestion.setExplanationRequired(true);
            }
            case "Yes/No":{
                newQuestion.setQuestionType("Yes/No");
                newQuestion.setChoices(questionTypes.getqTypeYesNo());
                newQuestion.setExplanationRequired(false);
            }
            break;
            default:
                break;
        }
        mSurveyParcelable.getQuestions().add(mCurrentQuestion,newQuestion);
        mCurrentQuestion++;

        QuestionTypes questionTypes = new QuestionTypes();
        questionTypes.getqTypeAgreeDisagree();
    }


    private class InsertSurveyToUserTask extends AsyncTask<String, Integer, User> {

        @Override
        protected User doInBackground(String... surveyParcelable) {

            return SurveyDataSource.getUserInfo(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), mUserId);
        }

        @Override
        protected void onPostExecute(User user) {

            SurveyList surveyPool = new SurveyList();
            surveyPool.setSurveyId(mSurveyId);
            if( user.getSurveyList() == null){
                user.setSurveyList(new ArrayList<SurveyList>());
            }

            user.getSurveyList().add(user.getSurveyList().size(),surveyPool);
            mUser = user;
            new userUpdateTask().execute(mUser);

        }
    }

 //   private class InsertSurveyTask extends AsyncTask<SurveyResults, Integer, Boolean> {
       private class InsertSurveyTask extends AsyncTask<SurveyParcelable, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(SurveyParcelable... surveyParcelable) {

            return SurveyDataSource.insertSurvey(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), mSurveyParcelable);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_survey_success),
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(CreateSurveyActivity.this, AdminMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SurveyId", mSurveyId);
                startActivity(intent);

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

    //add clickLister on buttonBack
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "Yes" quit creating a survey
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "No" stay on this layout
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            new AlertDialog.Builder(this)
                    .setTitle("Warning: Do you want to quit?")
                    .setPositiveButton("Yes", listener)
                    .setNegativeButton("No", listener)
                    .show();
        }
        return false;
    }

}