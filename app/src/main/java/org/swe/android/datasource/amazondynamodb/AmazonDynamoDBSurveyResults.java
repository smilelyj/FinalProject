package org.swe.android.datasource.amazondynamodb;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import org.swe.android.datasource.Answer;
import org.swe.android.datasource.SurveyResults;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caseylee on 7/25/15.
 */
@DynamoDBTable(tableName = "survey_results")
public class AmazonDynamoDBSurveyResults {
    private String surveyId;
    private String rangeKey;
    private List<AmazonDynamoDBAnswer> answers;

    public AmazonDynamoDBSurveyResults() {

    }

    public AmazonDynamoDBSurveyResults(Context context, SurveyResults surveyResults) {
        this.surveyId = surveyResults.getSurveyId();
        this.rangeKey = new SimpleDateFormat("yyyyMMdd").format(new Date()) +
                Long.toString(System.currentTimeMillis())// +
               // TelephonyManagerHelper.getDeviceId(context)
               ;
        this.answers = new ArrayList<>();
        for (Answer answer : surveyResults.getAnswers()) {
            this.answers.add(new AmazonDynamoDBAnswer(answer));
        }
    }

    @DynamoDBHashKey(attributeName = "survey_id")
    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    @DynamoDBRangeKey(attributeName = "range_key")
    public String getRangeKey() {
        return rangeKey;
    }

    public void setRangeKey(String rangeKey) {
        this.rangeKey = rangeKey;
    }

    @DynamoDBAttribute(attributeName = "answers")
    public List<AmazonDynamoDBAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AmazonDynamoDBAnswer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "AmazonDynamoDBSurveyResults{" +
                "surveyId='" + surveyId + '\'' +
                ", answers=" + answers +
                '}';
    }
}
