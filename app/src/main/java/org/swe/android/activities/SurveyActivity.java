package org.swe.android.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.swe.android.R;
import org.swe.android.datasource.Answer;
import org.swe.android.datasource.QuestionParcelable;
import org.swe.android.datasource.SurveyDataSource;
import org.swe.android.datasource.SurveyParcelable;
import org.swe.android.datasource.SurveyResults;
import org.swe.android.fragments.QuestionFragment;
import org.swe.android.helpers.NetworkHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SurveyActivity extends AppCompatActivity {
    private ConnectivityManager mConnectivityManager;
    private int mNumberOfQuestions;
    private int mCurrentQuestion = 0;
    DecimalFormat twoDecimalFormat = new DecimalFormat("##.##");
    double i =1.00;
    private SurveyResults mResults = new SurveyResults();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_survey);

        // Get the survey extra that was sent to this activity
        final SurveyParcelable survey = getIntent().getExtras().getParcelable("survey");

        ActionBar ab = getSupportActionBar();
        if (ab != null && survey != null) {
            // Set the title in the action bar to the Survey ID
            ab.setTitle(getString(R.string.action_bar_title) + " " + survey.getSurveyId());
            ab.setDisplayHomeAsUpEnabled(false);

            // Set the survey ID in the results
            mResults.setSurveyId(survey.getSurveyId());

            // Set the number of questions in the survey
            mNumberOfQuestions = survey.getQuestions().size();

        }

        // Check that the activity is using the layout with the fragment_container
        if (findViewById(R.id.fragment_container) != null) {
            // Prevent overlapping fragments by returning if savedInstanceState is not null
            if (savedInstanceState != null) {
                return;
            }

            QuestionFragment firstFragment = createQuestionFragment(
                    survey.getSurveyId(),
                    survey.getQuestions().get(mCurrentQuestion).getQuestion(),
                   //newQuestion.getChoices() != null ? new ArrayList<>(newQuestion.getChoices()) : null,
                    survey.getQuestions().get(mCurrentQuestion).getChoices() != null ? new ArrayList<>(survey.getQuestions().get(mCurrentQuestion).getChoices()) : null,

                    //new ArrayList<>(survey.getQuestions().get(mCurrentQuestion).getChoices()),
                    survey.getQuestions().get(mCurrentQuestion).getExplanationRequired(),
                    survey.getQuestions().get(mCurrentQuestion).getQuestionType()
            );

            getSupportFragmentManager().beginTransaction().add(
                    R.id.fragment_container, firstFragment, getString(R.string.fragment_question_name)).commit();

            // Set up the buttons
            final Button nextButton = (Button) findViewById(R.id.button_next);
            final Button submitButton = (Button) findViewById(R.id.button_submit);

            // Set up the progress bar
            Double formatPercentage = Double.valueOf(twoDecimalFormat.format(
                     1.00 / mNumberOfQuestions));
            double percentage_survey = formatPercentage * 100;

            setUpProgressBar(R.id.progress_survey_status, percentage_survey);

            // Set up the listener for the next button
            nextButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Add the current question's answer to the results before going to the next question
                    QuestionFragment currentQuestion = (QuestionFragment) getSupportFragmentManager().
                            findFragmentByTag(getString(R.string.fragment_question_name));

                    addResult(currentQuestion);

                    // Increment to to the next question
                    mCurrentQuestion++;
                    i++;
                    Double formatPercentage = Double.valueOf(twoDecimalFormat.format(
                            i / mNumberOfQuestions));
                    double percentage_survey = formatPercentage * 100;

                    setUpProgressBar(R.id.progress_survey_status, percentage_survey);
                    // If the new question is the last one in the survey, hide the next button
                    // and display the submit button
                    if (mCurrentQuestion == mNumberOfQuestions - 1) {
                        nextButton.setVisibility(View.GONE);
                        submitButton.setVisibility(View.VISIBLE);
                    }
                    // Get the new question
                    QuestionParcelable newQuestion = survey.getQuestions().get(mCurrentQuestion);

                    // Set up a new fragment with the new question
                    QuestionFragment questionFragment = createQuestionFragment(
                            survey.getSurveyId(),
                            newQuestion.getQuestion(),
                            newQuestion.getChoices() != null ? new ArrayList<>(newQuestion.getChoices()) : null,
                            newQuestion.getExplanationRequired(),
                            newQuestion.getQuestionType()
                    );

                    // Display the fragment with the new question
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fragment_container, questionFragment,
                            getString(R.string.fragment_question_name)).commit();

                }
            });

            // Set up the Connectivity Manager
            mConnectivityManager = (ConnectivityManager) getApplicationContext().
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Set up the listener for the submit button
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // If the user does not have an internet connection, let them know that one
                    // is required to submit the survey
                    if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_internet_connection_required,
                                Toast.LENGTH_LONG).show();
                    } else {
                        // Add the current question's answer to the results before submitting
                        QuestionFragment currentQuestion = (QuestionFragment) getSupportFragmentManager().
                                findFragmentByTag(getString(R.string.fragment_question_name));

                        addResult(currentQuestion);

                        // Insert the results of the survey into the database
                        new InsertSurveyResultsTask().execute(mResults);
                    }
                }
            });
        }
    }

    private void setUpProgressBar(int resourceId, Double percentage) {
        ProgressBar pb = (ProgressBar) findViewById(resourceId).findViewById(R.id.progress_bar);
        TextView tvPercentage = (TextView) findViewById(resourceId).findViewById(R.id.percentage);
        percentage = Double.valueOf(twoDecimalFormat.format(percentage));
        pb.setProgress(percentage.intValue());
        tvPercentage.setText(percentage.toString() + "% completed");
    }

    private QuestionFragment createQuestionFragment(String surveyId, String question,
                                                    ArrayList<String> choices,
                                                    boolean explanationRequired,
                                                    String questionType) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("surveyId", surveyId);
        bundle.putString("question", question);
        if (choices != null) {
            bundle.putStringArrayList("choices", choices);
        }
        bundle.putBoolean("explanationRequired", explanationRequired);
        bundle.putString("questionType", questionType);
        questionFragment.setArguments(bundle);

        return questionFragment;
    }

    private void addResult(QuestionFragment questionFragment) {
        if (mResults.getAnswers() == null) {
            mResults.setAnswers(new ArrayList<Answer>());
        }

        Answer answer = new Answer();
        answer.setQuestion(questionFragment.getmQuestion());
        answer.setChoice(questionFragment.getmChoice());
        answer.setExplanation(questionFragment.getmExplanation());
        answer.setQuestionNum(mCurrentQuestion+1);
        answer.setQuestionType(questionFragment.getmQuestionType());

        mResults.getAnswers().add(mCurrentQuestion, answer);
    }

    private class InsertSurveyResultsTask extends AsyncTask<SurveyResults, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(SurveyResults... surveyResults) {
            return SurveyDataSource.insertSurveyResults(SurveyDataSource.DATA_SOURCE,
                    getApplicationContext(), mResults);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_survey_success),
                        Toast.LENGTH_LONG).show();
                finish();
                /*
                Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                */
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_saving_answers),
                        Toast.LENGTH_LONG).show();
            }
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
                    .setTitle("Do you want to quit?")
                    .setPositiveButton("Yes", listener)
                    .setNegativeButton("No", listener)
                    .show();
        }
        return false;
    }
}