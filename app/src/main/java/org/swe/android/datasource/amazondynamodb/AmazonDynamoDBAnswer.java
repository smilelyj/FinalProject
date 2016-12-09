package org.swe.android.datasource.amazondynamodb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;

import org.swe.android.datasource.Answer;

/**
 * Created by caseylee on 7/25/15.
 */
@DynamoDBDocument
public class AmazonDynamoDBAnswer {
    private String question;
    private String choice;
    private String explanation;
    private String questionType;
    private int questionNum;

    public AmazonDynamoDBAnswer() {

    }

    public AmazonDynamoDBAnswer(Answer answer) {
        this.question = answer.getQuestion();
        this.choice = answer.getChoice();
        this.explanation = answer.getExplanation();
        this.questionType = answer.getQuestionType();
        this.questionNum = answer.getQuestionNum();
    }

    @DynamoDBAttribute(attributeName = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @DynamoDBAttribute(attributeName = "choice")
    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    @DynamoDBAttribute(attributeName = "explanation")
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @DynamoDBAttribute(attributeName = "question_type")
    public String  getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @DynamoDBAttribute(attributeName = "question_num")
    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    @Override
    public String toString() {
        return "AmazonDynamoDBAnswer{" +
                "question='" + question + '\'' +
                ", choice='" + choice + '\'' +
                ", explanation='" + explanation + '\'' +
                ", questionType='" + questionType + '\'' +
                ", questionNum='" + questionNum + '\'' +
                '}';
    }
}
