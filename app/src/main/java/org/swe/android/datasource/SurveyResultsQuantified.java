package org.swe.android.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongjiLi on 12/3/15.
 */
public class SurveyResultsQuantified implements Parcelable{
    private String surveyId;
    private List<SurveyAnswersQuantified> answersQuantified;

    public SurveyResultsQuantified(){
    }
    public SurveyResultsQuantified(Parcel parcel) {
        surveyId = parcel.readString();
        answersQuantified = new ArrayList<>();
        parcel.readTypedList(answersQuantified, SurveyAnswersQuantified.CREATOR);
    }
    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }
    public List<SurveyAnswersQuantified> getAnswersQuantified() {
        return answersQuantified;
    }

    public void setAnswersQuantified(List<SurveyAnswersQuantified> answersQuantified) {
        this.answersQuantified = answersQuantified;
    }

    @Override
    public String toString() {
        return "SurveyResultsQuantified{" +
                "surveyId='" + surveyId + '\'' +
                ", answers=" + answersQuantified +
                '}';
    }

    public static final Parcelable.Creator<SurveyResultsQuantified> CREATOR = new Parcelable.Creator<SurveyResultsQuantified>() {
        public SurveyResultsQuantified createFromParcel(Parcel in) {
            return new SurveyResultsQuantified(in);
        }

        public SurveyResultsQuantified[] newArray(int size) {
            return new SurveyResultsQuantified[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(surveyId);
        parcel.writeTypedList(answersQuantified);
    }
}
