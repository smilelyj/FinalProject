package org.swe.android.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBQuestion;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBTemplates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongjiLi on 1/26/16.
 */
public class SurveyTemplates implements Parcelable {
    private String templateId;
    private String templateIntroduction;
    private List<QuestionParcelable> questions;
    public SurveyTemplates(){
    }
    public SurveyTemplates(Parcel parcel) {
        templateId = parcel.readString();
        templateIntroduction = parcel.readString();
        questions = new ArrayList<>();
        parcel.readTypedList(questions, QuestionParcelable.CREATOR);
    }

    public SurveyTemplates(AmazonDynamoDBTemplates amazonDynamoDBTemplates) {
        this.templateId = amazonDynamoDBTemplates.getTemplateId();
        this.templateIntroduction = amazonDynamoDBTemplates.getTemplateId();

        if (amazonDynamoDBTemplates.getQuestions() != null) {
            this.questions = new ArrayList<>();

            if (amazonDynamoDBTemplates.getQuestions().size() > 0) {
                for (AmazonDynamoDBQuestion question : amazonDynamoDBTemplates.getQuestions()) {
                    this.questions.add(new QuestionParcelable(question));
                }
            }
        }
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateIntroduction(){
        return templateIntroduction;
    }

    public void setTemplateIntroduction(String templateIntroduction){
        this.templateIntroduction = templateIntroduction;
    }

    public List<QuestionParcelable> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionParcelable> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SurveyTemplates{" +
                "templateId='" + templateId + '\'' +
                "templateIntroduction" + templateIntroduction + '\'' +
                ", questions=" + questions +
                '}';
    }

    public static final Parcelable.Creator<SurveyTemplates> CREATOR = new Parcelable.Creator<SurveyTemplates>() {
        public SurveyTemplates createFromParcel(Parcel in) {
            return new SurveyTemplates(in);
        }

        public SurveyTemplates[] newArray(int size) {
            return new SurveyTemplates[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(templateId);
        parcel.writeString(templateIntroduction);
        parcel.writeTypedList(questions);
    }
}

