package org.swe.android.datasource.amazondynamodb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import org.swe.android.datasource.QuestionParcelable;
import org.swe.android.datasource.SurveyParcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casey on 7/17/2015.
 */
@DynamoDBTable(tableName = "survey")
public class AmazonDynamoDBSurvey {
    private String surveyId;
    private String owner;
    private String surveyDescription;
    private List<AmazonDynamoDBQuestion> questions;

    public AmazonDynamoDBSurvey(){
        super();
    }
    public AmazonDynamoDBSurvey(SurveyParcelable surveyParcelable) {
        super();
        this.surveyId = surveyParcelable.getSurveyId();
        this.owner = surveyParcelable.getOwner();
        this.questions = new ArrayList<>();
        for (QuestionParcelable questionParcelable : surveyParcelable.getQuestions()) {
            this.questions.add(new AmazonDynamoDBQuestion(questionParcelable));
        }
    }

    @DynamoDBHashKey(attributeName = "survey_id")
    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    @DynamoDBAttribute(attributeName = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }

    @DynamoDBAttribute(attributeName = "questions")
    public List<AmazonDynamoDBQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AmazonDynamoDBQuestion> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "surveyId='" + surveyId + '\'' +
                "owner='" + owner + '\'' +
                "surveyDescription='" + surveyDescription + '\'' +
                ", questions=" + questions +
                '}';
    }
}
