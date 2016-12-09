package org.swe.android.datasource.amazondynamodb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;

import org.swe.android.datasource.SurveyList;

import java.util.List;

/**
 * Created by YongjiLi on 4/2/16.
 */
@DynamoDBDocument
public class AmazonDynamoDBSurveyList {
    private String surveyId;
    private List<String> deployedSurveyId;
    //++++++++++++++++++++++++++++++++++++++++++++++++
    public AmazonDynamoDBSurveyList (){
        super ();
    }
    public AmazonDynamoDBSurveyList(SurveyList surveyList) {
        super();
        this.surveyId = surveyList.getSurveyId();
        this.deployedSurveyId = surveyList.getDeployedSurveyId();
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++

    @DynamoDBAttribute(attributeName = "survey_id")
    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    @DynamoDBAttribute(attributeName = "deployed_survey_id")
    public List<String> getDeployedSurveyId() {
        return deployedSurveyId;
    }

    public void setDeployedSurveyId(List<String> deployedSurveyId) {
        this.deployedSurveyId = deployedSurveyId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "survey_id='" + surveyId + '\'' +
                ", deployedSurveyId=" + deployedSurveyId +
                '}';
    }
}
