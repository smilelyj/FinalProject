package org.swe.android.datasource.amazondynamodb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;

import org.swe.android.datasource.QuestionParcelable;

import java.util.List;

/**
 * Created by Casey on 7/18/2015.
 */
@DynamoDBDocument
public class AmazonDynamoDBQuestion {
    private String question;
    private List<String> choices;
    private boolean explanationRequired;
    private String questionType;
    //++++++++++++++++++++++++++++++++++++++++++++++++
    public AmazonDynamoDBQuestion (){
        super ();
    }
    public AmazonDynamoDBQuestion(QuestionParcelable questionParcelable) {
        super();
        this.question = questionParcelable.getQuestion();
        this.choices = questionParcelable.getChoices();
        this.explanationRequired = questionParcelable.getExplanationRequired();
        this.questionType = questionParcelable.getQuestionType();
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++

    @DynamoDBAttribute(attributeName = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @DynamoDBAttribute(attributeName = "choices")
    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    @DynamoDBAttribute(attributeName = "explanation_required")
    public boolean getExplanationRequired() {
        return explanationRequired;
    }

    public void setExplanationRequired(boolean explanationRequired) {
        this.explanationRequired = explanationRequired;
    }

    @DynamoDBAttribute(attributeName = "question_type")
    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", choices=" + choices +
                ", explanationRequired=" + explanationRequired +
                ", questionType=" + questionType +
                '}';
    }
}
