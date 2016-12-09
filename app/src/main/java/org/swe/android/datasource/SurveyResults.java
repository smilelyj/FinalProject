package org.swe.android.datasource;

import java.util.List;

/**
 * Created by caseylee on 7/26/15.
 */
public class SurveyResults {
    private String surveyId;
    private List<Answer> answers;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "SurveyResults{" +
                "surveyId='" + surveyId + '\'' +
                ", answers=" + answers +
                '}';
    }
}
