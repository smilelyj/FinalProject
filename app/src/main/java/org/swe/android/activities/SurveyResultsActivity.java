package org.swe.android.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.swe.android.R;
import org.swe.android.adapters.SurveyResultsAdapter;
import org.swe.android.datasource.SurveyResultsQuantified;
import org.swe.android.helpers.NetworkHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurveyResultsActivity extends AppCompatActivity {

    private int mNumberofResults = 0;
    private ListView listView;
    private SurveyResultsAdapter adapter;
    List<resultsBody> l;//the list in the adapter
    private ConnectivityManager mConnectivityManager;
    //Assuming your rootView is called mRootView like so
    private View mRootView;

    private static final String QUESTION_TYPE_OVERALL = "Overall";
    private static final String QUESTION_TYPE_AGREE_DISAGREE = "Agree/Disagree";
    private static final String QUESTION_TYPE_CONFIDENCE_AND_ABILITY = "Confidence and Ability";
    private static final String QUESTION_TYPE_RECOMMEND_TO_OTHERS = "Recommend to Others";
    private static final String QUESTION_TYPE_OPEN_ENDED = "Open-Ended";
    private static final String QUESTION_TYPE_GRADE = "Demographic-Grade";
    private static final String QUESTION_TYPE_GENDER = "Demographic-Gender";
    private static final String QUESTION_TYPE_RACE = "Demographic-Race";
    private static final String QUESTION_TYPE_RELATIONSHIP = "Relationship";
    private static final String QUESTION_TYPE_YESNO = "Yes/No";


    DecimalFormat twoDecimalFormat = new DecimalFormat("##.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_view_survey_results);

        mRootView = findViewById(android.R.id.content);

        // Get the surveyResults extra that was sent to this activity
        final SurveyResultsQuantified  results =  getIntent().getExtras().getParcelable("results");

        assert results != null;
        // Set up the action bar title using the survey ID
        ActionBar ab = getSupportActionBar();
        if (ab != null && results.getSurveyId() !=null) {
            ab.setTitle(getString(R.string.action_bar_title) + " " + results.getSurveyId());
        }
        mNumberofResults = results.getAnswersQuantified().size();
        l=new ArrayList<resultsBody>();

        //ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));

        int totalNumber = 0;
        Double tempPercentage;
        Double tempChoiceA;
        Double tempChoiceB;
        Double tempChoiceC;
        Double tempChoiceD;
        Double tempChoiceE;
        Double tempChoiceF;
        Double tempChoiceG;

        Double percentage;
        Double ChoiceA, ChoiceB, ChoiceC, ChoiceD, ChoiceE, ChoiceF, ChoiceG;

        for(int i=0;i<mNumberofResults;i++){
            resultsBody b=new resultsBody();
            //answersQuantified = results.getAnswersQuantified().get(i);

            //new ArrayList(survey.getQuestions().get(mCurrentQuestion).getChoices()),
            //survey.getQuestions().get(mCurrentQuestion).getExplanationRequired(),
            //survey.getQuestions().get(mCurrentQuestion).getQuestionType()
            b.question=results.getAnswersQuantified().get(i).getQuestionString();
            String questionType = results.getAnswersQuantified().get(i).getQuestionType();
            b.question_type=("Question type: " +results.getAnswersQuantified().get(i).getQuestionType());
            switch (questionType) {
                case QUESTION_TYPE_OVERALL:
                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getNumberOfOverall();
                  //  setUpTextView(R.id.text_choice, R.string.text_a_or_b, totalNumber);
                    // Set up the progress bar
                    tempPercentage = results.getAnswersQuantified().get(i).getaOrBTotal().doubleValue() /
                            results.getAnswersQuantified().get(i).getNumberOfOverall().doubleValue()*100;
                    percentage = Double.valueOf(twoDecimalFormat.format(tempPercentage));
                    b.text_choice = (percentage.toString() + "% of users that answered 'A' or 'B' out of " + totalNumber + " questions");

                    // setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;
                case QUESTION_TYPE_AGREE_DISAGREE:

                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getNumberOfAgreeDisagree();


                    //setUpTextView(R.id.text_choice, R.string.text_agree_strongly_agree, totalNumber);
                    // Set up the progress bar
                    tempPercentage = results.getAnswersQuantified().get(i).getAgreeStronglyAgreeTotal().doubleValue() /
                            results.getAnswersQuantified().get(i).getNumberOfAgreeDisagree().doubleValue()*100;
                    percentage = Double.valueOf(twoDecimalFormat.format(tempPercentage));
                    b.text_choice = (percentage.toString() + "% of users that answered 'Agree' or 'Strongly Agree' out of " + totalNumber + " questions");

                   // setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;
                case QUESTION_TYPE_CONFIDENCE_AND_ABILITY:
                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getNumberOfConfidenceAndAbility();
                    //setUpTextView(R.id.text_choice, R.string.text_improved, totalNumber);
                    // Set up the progress bar
                    tempPercentage = results.getAnswersQuantified().get(i).getImprovedTotal().doubleValue() /
                            results.getAnswersQuantified().get(i).getNumberOfConfidenceAndAbility().doubleValue()*100;
                    percentage = Double.valueOf(twoDecimalFormat.format(tempPercentage));
                    b.text_choice = (percentage.toString() + "% of users that answered 'Improved' out of " + totalNumber + " questions");

                    // setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;
                case QUESTION_TYPE_RECOMMEND_TO_OTHERS:
                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getNumberOfRecommendToOthers();
                    //setUpTextView(R.id.text_choice, R.string.text_yes, totalNumber);
                    // Set up the progress bar
                    tempPercentage = results.getAnswersQuantified().get(i).getYesTotal().doubleValue() /
                            results.getAnswersQuantified().get(i).getNumberOfRecommendToOthers().doubleValue()*100;
                    percentage = Double.valueOf(twoDecimalFormat.format(tempPercentage));
                    b.text_choice = (percentage.toString() + "% of users that answered 'Yes' out of " + totalNumber + " questions");

                    //   setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;
                case QUESTION_TYPE_GRADE:
                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getGradeTotal();
                    //setUpTextView(R.id.text_choice, R.string.text_yes, totalNumber);
                    // Set up the progress bar
                    tempChoiceA = results.getAnswersQuantified().get(i).getGrade3().doubleValue() /
                            results.getAnswersQuantified().get(i).getGradeTotal().doubleValue()*100;
                    tempChoiceB = results.getAnswersQuantified().get(i).getGrade4().doubleValue() /
                            results.getAnswersQuantified().get(i).getGradeTotal().doubleValue()*100;
                    tempChoiceC = results.getAnswersQuantified().get(i).getGrade5().doubleValue() /
                            results.getAnswersQuantified().get(i).getGradeTotal().doubleValue()*100;
                    tempChoiceD = results.getAnswersQuantified().get(i).getGrade6().doubleValue() /
                            results.getAnswersQuantified().get(i).getGradeTotal().doubleValue()*100;

                    ChoiceA = Double.valueOf(twoDecimalFormat.format(tempChoiceA));
                    ChoiceB = Double.valueOf(twoDecimalFormat.format(tempChoiceB));
                    ChoiceC = Double.valueOf(twoDecimalFormat.format(tempChoiceC));
                    ChoiceD = Double.valueOf(twoDecimalFormat.format(tempChoiceD));
                    b.text_choice = (ChoiceA.toString() + "% of users answered '3rd grade',\n" +
                                     ChoiceB.toString() + "% of users answered '4th grade',\n" +
                                     ChoiceC.toString() + "% of users answered '5th grade',\n" +
                                     ChoiceD.toString() + "% of users answered '6th grade'\n" +
                            " out of " + totalNumber + " questions");

                    //setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;

                case QUESTION_TYPE_GENDER:
                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getGenderTotal();
                    //setUpTextView(R.id.text_choice, R.string.text_yes, totalNumber);
                    // Set up the progress bar
                    tempChoiceA = results.getAnswersQuantified().get(i).getGenderFemale().doubleValue() /
                            results.getAnswersQuantified().get(i).getGenderTotal().doubleValue()*100;
                    tempChoiceB = results.getAnswersQuantified().get(i).getGenderMale().doubleValue() /
                            results.getAnswersQuantified().get(i).getGenderTotal().doubleValue()*100;

                    ChoiceA = Double.valueOf(twoDecimalFormat.format(tempChoiceA));
                    ChoiceB = Double.valueOf(twoDecimalFormat.format(tempChoiceB));
                    b.text_choice = (ChoiceA.toString() + "% of users answered 'Female',\n" +
                                     ChoiceB.toString() + "% of users answered 'Male',\n" +
                            " out of " + totalNumber + " questions");
                    //setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;

                case QUESTION_TYPE_RACE:
                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getRaceTotal();
                    //setUpTextView(R.id.text_choice, R.string.text_yes, totalNumber);
                    // Set up the progress bar
                    tempChoiceA = results.getAnswersQuantified().get(i).getRaceWhite().doubleValue() /
                            results.getAnswersQuantified().get(i).getRaceTotal().doubleValue()*100;
                    tempChoiceB = results.getAnswersQuantified().get(i).getRaceHispanic().doubleValue() /
                            results.getAnswersQuantified().get(i).getRaceTotal().doubleValue()*100;
                    tempChoiceC = results.getAnswersQuantified().get(i).getRaceBlack().doubleValue() /
                            results.getAnswersQuantified().get(i).getRaceTotal().doubleValue()*100;
                    tempChoiceD = results.getAnswersQuantified().get(i).getRaceAsian().doubleValue() /
                            results.getAnswersQuantified().get(i).getRaceTotal().doubleValue()*100;
                    tempChoiceE = results.getAnswersQuantified().get(i).getRaceHawaiian().doubleValue() /
                            results.getAnswersQuantified().get(i).getRaceTotal().doubleValue()*100;
                    tempChoiceF = results.getAnswersQuantified().get(i).getRaceAlaskan().doubleValue() /
                            results.getAnswersQuantified().get(i).getRaceTotal().doubleValue()*100;
                    tempChoiceG = results.getAnswersQuantified().get(i).getRaceOther().doubleValue() /
                            results.getAnswersQuantified().get(i).getRaceTotal().doubleValue()*100;

                    ChoiceA = Double.valueOf(twoDecimalFormat.format(tempChoiceA));
                    ChoiceB = Double.valueOf(twoDecimalFormat.format(tempChoiceB));
                    ChoiceC = Double.valueOf(twoDecimalFormat.format(tempChoiceC));
                    ChoiceD = Double.valueOf(twoDecimalFormat.format(tempChoiceD));
                    ChoiceE = Double.valueOf(twoDecimalFormat.format(tempChoiceE));
                    ChoiceF = Double.valueOf(twoDecimalFormat.format(tempChoiceF));
                    ChoiceG = Double.valueOf(twoDecimalFormat.format(tempChoiceG));
                    b.text_choice = (
                            ChoiceA.toString() + "% of users answered 'White or European American',\n" +
                            ChoiceB.toString() + "% of users answered 'Hispanic, Latino, or Spanish',\n" +
                            ChoiceC.toString() + "% of users answered 'Black or African-American',\n" +
                            ChoiceD.toString() + "% of users answered 'Asian American'\n" +
                            ChoiceE.toString() + "% of users answered 'Native Hawaiian or Pacific Islander',\n" +
                            ChoiceF.toString() + "% of users answered 'Native American or Alaskan Native',\n" +
                            ChoiceG.toString() + "% of users answered 'Other'\n" +
                            " out of " + totalNumber + " questions");

                    //   setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;

                case QUESTION_TYPE_RELATIONSHIP:
                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getRelationTotal();
                    //setUpTextView(R.id.text_choice, R.string.text_yes, totalNumber);
                    // Set up the progress bar
                    tempChoiceA = results.getAnswersQuantified().get(i).getRelationMom().doubleValue() /
                            results.getAnswersQuantified().get(i).getRelationTotal().doubleValue()*100;
                    tempChoiceB = results.getAnswersQuantified().get(i).getRelationDad().doubleValue() /
                            results.getAnswersQuantified().get(i).getRelationTotal().doubleValue()*100;
                    tempChoiceC = results.getAnswersQuantified().get(i).getRelationGuardian().doubleValue() /
                            results.getAnswersQuantified().get(i).getRelationTotal().doubleValue()*100;
                    tempChoiceD = results.getAnswersQuantified().get(i).getRelationTroopLeader().doubleValue() /
                            results.getAnswersQuantified().get(i).getRelationTotal().doubleValue()*100;
                    tempChoiceE = results.getAnswersQuantified().get(i).getRelationTeacher().doubleValue() /
                            results.getAnswersQuantified().get(i).getRelationTotal().doubleValue()*100;
                    tempChoiceF = results.getAnswersQuantified().get(i).getRelationOther().doubleValue() /
                            results.getAnswersQuantified().get(i).getRelationTotal().doubleValue()*100;

                    ChoiceA = Double.valueOf(twoDecimalFormat.format(tempChoiceA));
                    ChoiceB = Double.valueOf(twoDecimalFormat.format(tempChoiceB));
                    ChoiceC = Double.valueOf(twoDecimalFormat.format(tempChoiceC));
                    ChoiceD = Double.valueOf(twoDecimalFormat.format(tempChoiceD));
                    ChoiceE = Double.valueOf(twoDecimalFormat.format(tempChoiceE));
                    ChoiceF = Double.valueOf(twoDecimalFormat.format(tempChoiceF));

                    b.text_choice = (ChoiceA.toString() + "% of users answered 'Mother',\n" +
                                    ChoiceB.toString() + "% of users answered 'Dad',\n" +
                                    ChoiceC.toString() + "% of users answered 'Guardian',\n" +
                                    ChoiceD.toString() + "% of users answered 'Troop Leader'\n" +
                                    ChoiceE.toString() + "% of users answered 'Teacher',\n" +
                                    ChoiceF.toString() + "% of users answered 'Other'\n" +
                                    " out of " + totalNumber + " questions");

                    //   setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;

                case QUESTION_TYPE_YESNO:
                    //set up textview
                    totalNumber = results.getAnswersQuantified().get(i).getYesNoTotal();
                    //setUpTextView(R.id.text_choice, R.string.text_yes, totalNumber);
                    // Set up the progress bar
                    tempChoiceA = results.getAnswersQuantified().get(i).getChooseYes().doubleValue() /
                            results.getAnswersQuantified().get(i).getYesNoTotal().doubleValue()*100;
                    tempChoiceB = results.getAnswersQuantified().get(i).getChooseNo().doubleValue() /
                            results.getAnswersQuantified().get(i).getYesNoTotal().doubleValue()*100;

                    ChoiceA = Double.valueOf(twoDecimalFormat.format(tempChoiceA));
                    ChoiceB = Double.valueOf(twoDecimalFormat.format(tempChoiceB));
                    b.text_choice = (ChoiceA.toString() + "% of users answered 'Yes',\n" +
                            ChoiceB.toString() + "% of users answered 'No',\n" +
                            " out of " + totalNumber + " questions");
                    //   setUpProgressBar(R.id.frameLayout_progress_bar, percentage);

                    break;
                /*
                case QUESTION_TYPE_OPEN_ENDED:

                    LinearLayout ll = (LinearLayout) findViewById(R.id.ll_open_ended_questions);

                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    @SuppressWarnings("unchecked")
                    HashMap<String, List<String>> tempMap =  results.getAnswersQuantified().get(i).getOpenEndedQuestions();

                    Iterator iterator = results.getAnswersQuantified().get(i).getOpenEndedQuestions().entrySet().iterator();


                    while (iterator.hasNext()) {
                        View view = inflater.inflate(R.layout.text_bold, ll, false);
                        Map.Entry pair = (Map.Entry) iterator.next();

                        TextView text = (TextView) view.findViewById(R.id.text);
                        text.setText(pair.getKey().toString());

                        // Add the view to the list
                        ll.addView(view);

                        for (String s : results.getAnswersQuantified().get(i).getOpenEndedQuestions().get(pair.getKey().toString())) {
                            view = inflater.inflate(R.layout.text_regular, ll, false);

                            // Create the view that contains the question and its answer
                            text = (TextView) view.findViewById(R.id.text);
                            text.setText("- " + s);

                            // Add the view to the list
                            ll.addView(view);
                        }
                        iterator.remove(); // avoids a ConcurrentModificationException
                    }

                    break;

                */
                default:
                    break;

            }
            l.add(b);
        }
        listView = (ListView) findViewById(R.id.listView_results);


        adapter = new SurveyResultsAdapter(SurveyResultsActivity.this, l);

        listView.setAdapter(adapter);
        // Set up the Connectivity Manager
        mConnectivityManager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);


        // Set up Take Survey button
        final Button pdfButton = (Button) findViewById(R.id.button_generate_pdf_file);
        //pdfButton.setVisibility(View.GONE);
        pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user does not have an internet connection, let them know that one
                // is required to look up the survey
                if (!NetworkHelper.hasInternetConnection(mConnectivityManager)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_internet_connection_required,
                            Toast.LENGTH_LONG).show();
                } else {
                    //First Check if the external storage is writable
                    String state = Environment.getExternalStorageState();
                    if (!Environment.MEDIA_MOUNTED.equals(state)) {
                        Toast.makeText(getApplicationContext(),R.string.external_storage_is_not_writable,
                                Toast.LENGTH_SHORT).show();
                    }else {
                        //Create a directory for your PDF
                        File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOCUMENTS), "AutoSWE");
                        if (!pdfDir.exists()){
                            pdfDir.mkdir();
                        }

                        //Then take the screen shot
                        Bitmap screen; View v1 = mRootView.getRootView();
                        v1.setDrawingCacheEnabled(true);
                        screen = Bitmap.createBitmap(v1.getDrawingCache());
                        v1.setDrawingCacheEnabled(false);

                        //Now create the name of your PDF file that you will generate
                        File pdfFile = new File(pdfDir, "myPdfFile.pdf");

                        try {
                            Document  document = new Document();

                            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                            document.open();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            addImage(document,byteArray);
                            document.close();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void setUpTextView(int tvResourceId, int stringResourceId, int numberOfQuestions) {
        TextView tv = (TextView) findViewById(tvResourceId);
        tv.setText(String.format(getResources().getString(stringResourceId), numberOfQuestions));
    }


    private void setUpProgressBar(int resourceId, Double percentage) {
        ProgressBar pb = (ProgressBar) findViewById(resourceId).findViewById(R.id.progress_bar);
        TextView tvPercentage = (TextView) findViewById(resourceId).findViewById(R.id.percentage);
        pb.setProgress(percentage.intValue());
        tvPercentage.setText(percentage.toString() + "%");
    }

    private static void addImage(Document document,byte[] byteArray)
    {
        Image image = null;
        try
        {
            image = Image.getInstance(byteArray);
        }
        catch (BadElementException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // image.scaleAbsolute(150f, 150f);
        try
        {
            document.add(image);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class resultsBody{//to restore the class in the adapter
        public String question;
        public String question_type;
        public String text_choice;
        public HashMap openEndedQuestions;
        ProgressBar pb;

        public String getQuestion() {
            return question;
        }
        public void setQuestion(String question) {
            this.question = question;
        }
        public String getQuestiontype() {
            return question_type;
        }
        public void setQuestiontype(String questiontype) {
            this.question_type = questiontype;
        }
    }
}
