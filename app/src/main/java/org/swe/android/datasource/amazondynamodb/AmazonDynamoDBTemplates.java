package org.swe.android.datasource.amazondynamodb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import org.swe.android.datasource.QuestionParcelable;
import org.swe.android.datasource.SurveyTemplates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongjiLi on 1/26/16.
 */
@DynamoDBTable(tableName = "survey_templates")
public class AmazonDynamoDBTemplates {
    private String templateId;
    private String templateIntroduction;
    private List<AmazonDynamoDBQuestion> questions;

    public AmazonDynamoDBTemplates(){
        super();
    }
    public AmazonDynamoDBTemplates(SurveyTemplates surveyTemplates) {
        super();
        this.templateId = surveyTemplates.getTemplateId();
        this.templateIntroduction = surveyTemplates.getTemplateIntroduction();
        this.questions = new ArrayList<>();
        for (QuestionParcelable questionParcelable : surveyTemplates.getQuestions()) {
            this.questions.add(new AmazonDynamoDBQuestion(questionParcelable));
        }
    }

    @DynamoDBHashKey(attributeName = "templateId")
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @DynamoDBAttribute(attributeName = "templateIntroduction")
    public String getTemplateIntroduction() {
        return templateIntroduction;
    }

    public void setTemplateIntroduction(String templateIntroduction) {
        this.templateIntroduction = templateIntroduction;
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
                "templateId='" + templateId + '\'' +
                "templateIntroduction" + templateIntroduction + '\'' +
                ", questions=" + questions +
                '}';
    }
}
