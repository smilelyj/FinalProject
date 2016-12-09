package org.swe.android.activities;
/**
 * Created by YongjiLi on 10/27/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.RemoveListener;

import org.swe.android.R;
import org.swe.android.adapters.EditSurveyAdapter;
import org.swe.android.datasource.QuestionParcelable;
import org.swe.android.datasource.QuestionTypes;
import org.swe.android.datasource.SurveyParcelable;

import java.util.ArrayList;

public class EditSurveyActivity extends Activity {

    private DragSortListView listView;
    private EditSurveyAdapter adapter;
   // private Button btnEdit;
    ArrayList<body> l;
    private SurveyParcelable newSurveyParcelable = new SurveyParcelable();
    private int mNumberofQuestions = 0;
    protected String[]array;
    protected String[] arrayQtype;
    public SurveyParcelable survey = new SurveyParcelable();

    public QuestionParcelable newQuestion = new QuestionParcelable();

    private QuestionTypes questionTypes = new QuestionTypes();

    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        body item = (body)adapter.getItem(from);//get the listview adapter
                        adapter.removeItem(from);
                        adapter.insert(item, to);
                    }
                }
            };
    //delete an item when clicking the button in the left
    private RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    adapter.remove(which);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adjust the soft-keyboard automatically,put this line before setContentView
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_preview_survey);

        survey = getIntent().getExtras().getParcelable("survey");

        assert survey != null;
       mNumberofQuestions = survey.getQuestions().size();

        initData();

        //get the Dragsort listview and set up an adapter
        listView.setDropListener(onDrop);
        listView.setRemoveListener(onRemove);

        adapter = new EditSurveyAdapter(EditSurveyActivity.this, l);
        listView.setAdapter(adapter);
        listView.setDragEnabled(true); //set it is enabled to drag

        Button btnDone=(Button)findViewById(R.id.button_preview_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateData();
                int newQuestionNumber = newSurveyParcelable.getQuestions().size();
                //Use Intent to return data
                Intent intent = new Intent();
                intent.putExtra("new_survey", newSurveyParcelable);
                intent.putExtra("newQuestionNumber", newQuestionNumber);
                setResult(Activity.RESULT_OK, intent);
                //Close the Activity
                finish();
            }
        });
    }

    private void updateData() {
        newSurveyParcelable = new SurveyParcelable();
        newSurveyParcelable.setSurveyId(survey.getSurveyId());
        newSurveyParcelable.setOwner(survey.getOwner());
        if (newSurveyParcelable.getQuestions() == null) {
            newSurveyParcelable.setQuestions(new ArrayList<QuestionParcelable>());
        }
        for (int i = 0; i < l.size();i++){
            newQuestion = new QuestionParcelable();
            newQuestion.setQuestion(l.get(i).getQuestion());
            newQuestion.setQuestionType(l.get(i).getQuestiontype());
            addChoicesAndExplainRequired(l.get(i).getQuestiontype());
            newSurveyParcelable.getQuestions().add(i,newQuestion);
        }
    }
    private void addChoicesAndExplainRequired (String questiontype) {
        switch (questiontype) {
            case "Overall":{
                newQuestion.setQuestionType("Overall");
                //newQuestion.setChoices(qTypeOverall);
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

        //ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
        l=new ArrayList<body>();
        for(int i=0;i<mNumberofQuestions;i++){
            body b=new body();
            b.question=array[i];
            b.questiontype=arrayQtype[i];
            l.add(b);
        }
        listView = (DragSortListView) findViewById(R.id.dslvList);
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
}