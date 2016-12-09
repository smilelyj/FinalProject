package org.swe.android.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBDeployedSurvey;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBQuestion;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBSurvey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casey on 7/17/2015.
 */
public class SurveyParcelable implements Parcelable {
    private String surveyId;
    private String owner;
    private String surveyDescription;
    private List<QuestionParcelable> questions;
    public SurveyParcelable(){
    }
    public SurveyParcelable(Parcel parcel) {
        surveyId = parcel.readString();
        owner = parcel.readString();
        surveyDescription = parcel.readString();
        questions = new ArrayList<>();
        parcel.readTypedList(questions, QuestionParcelable.CREATOR);
    }

    public SurveyParcelable(AmazonDynamoDBSurvey survey) {
        this.surveyId = survey.getSurveyId();
        this.owner = survey.getOwner();
        this.surveyDescription = survey.getSurveyDescription();
        if (survey.getQuestions() != null) {
            this.questions = new ArrayList<>();

            if (survey.getQuestions().size() > 0) {
                for (AmazonDynamoDBQuestion question : survey.getQuestions()) {
                    this.questions.add(new QuestionParcelable(question));
                }
            }
        }
    }
    public SurveyParcelable(AmazonDynamoDBDeployedSurvey survey) {
        this.surveyId = survey.getSurveyId();
        this.owner = survey.getOwner();
        this.surveyDescription = survey.getSurveyDescription();
        if (survey.getQuestions() != null) {
            this.questions = new ArrayList<>();

            if (survey.getQuestions().size() > 0) {
                for (AmazonDynamoDBQuestion question : survey.getQuestions()) {
                    this.questions.add(new QuestionParcelable(question));
                }
            }
        }
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getOwner(){
        return owner;
    }
    public void setOwner (String owner){
        this.owner = owner;
    }


    public  String getSurveyDescription() {return surveyDescription;}
    public void setSurveyDescription (String surveyDescription) {this.surveyDescription = surveyDescription;}

    public List<QuestionParcelable> getQuestions() {
        return questions;
    }
    public void setQuestions(List<QuestionParcelable> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SurveyParcelable{" +
                "surveyId='" + surveyId + '\'' +
                "owner='" + owner + '\'' +
                "surveyDescription" + surveyDescription + '\'' +
                ", questions=" + questions +
                '}';
    }

    public static final Parcelable.Creator<SurveyParcelable> CREATOR = new Parcelable.Creator<SurveyParcelable>() {
        public SurveyParcelable createFromParcel(Parcel in) {
            return new SurveyParcelable(in);
        }
        public SurveyParcelable[] newArray(int size) {
            return new SurveyParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(surveyId);
        parcel.writeString(owner);
        parcel.writeString(surveyDescription);
        parcel.writeTypedList(questions);
    }

}
