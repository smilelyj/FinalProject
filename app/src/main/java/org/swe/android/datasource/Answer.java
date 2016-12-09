package org.swe.android.datasource;

/**
 * Created by caseylee on 7/26/15.
 */

//Create a mapping class: Answer.
public class Answer {
    private String question;
    private String choice;
    private String explanation;
    private String questionType;
    private int questionNum;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "question='" + question + '\'' +
                ", choice='" + choice + '\'' +
                ", explanation='" + explanation + '\'' +
                ", questionType='" + questionType + '\'' +
                ", questionNum='" + questionNum + '\'' +
                '}';
    }
}