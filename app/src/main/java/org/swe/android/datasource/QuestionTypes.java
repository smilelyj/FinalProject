package org.swe.android.datasource;

import java.util.Arrays;
import java.util.List;

/**
 * Created by YongjiLi on 2/29/16.
 */
public class QuestionTypes {
    private List<String> qTypeOverall = Arrays.asList("A", "B", "C", "D", "F");

    private List<String> qTypeAgreeDisagree = Arrays.asList(
            "Strongly Agree", "Agree", "Not Sure", "Disagree", "Strongly Disagree"
    );

    private List<String> qTypeConfidenceAndAbility = Arrays.asList(
            "Improved", "Stay the Same", "Got Worse", "I Don't Know"
    );

    private List<String> qTypeRecommendToOthers = Arrays.asList(
            "Yes", "Maybe", "No (please explain)"
    );
    private List<String> qTypeOpenEnded = Arrays.asList();

    private List<String> qTypeDemoAge = Arrays.asList();

    private List<String> qTypeDemoGrade = Arrays.asList(
            "3rd grade", "4th grade", "5th grade", "6th grade"
    );

    private List<String> qTypeDemoSex = Arrays.asList(
            "female", "male"
    );

    private List<String> qTypeDemoEthnic = Arrays.asList(
            "White or European American",
            "Hispanic, Latino, or Spanish",
            "Black or African-American",
            "Asian American",
            "Native Hawaiian or Pacific Islander",
            "Native American or Alaskan Native",
            "Other"
    );

    private List<String> qTypeDemoRelationship = Arrays.asList(
            "Mother",
            "Father",
            "Guardian",
            "Troop Leader",
            "Teacher",
            "Other"
    );

    private List<String> qTypeYesNo = Arrays.asList(
            "Yes",
            "No"
    );


    public List<String> getqTypeOverall() {
        return qTypeOverall;
    }
    public void setqTypeOverall(List<String> choices) {
        this.qTypeOverall = qTypeOverall;
    }

    public List<String> getqTypeAgreeDisagree() {
        return qTypeAgreeDisagree;
    }
    public void setqTypeAgreeDisagree(List<String> qTypeAgreeDisagree) {
        this.qTypeAgreeDisagree = qTypeAgreeDisagree;
    }

    public List<String> getqTypeConfidenceAndAbility() {
        return qTypeConfidenceAndAbility;
    }
    public void setqTypeConfidenceAndAbility(List<String> qTypeConfidenceAndAbility) {
        this.qTypeConfidenceAndAbility = qTypeConfidenceAndAbility;
    }

    public List<String> getqTypeRecommendToOthers() {
        return qTypeRecommendToOthers;
    }
    public void setqTypeRecommendToOthers(List<String> qTypeRecommendToOthers) {
        this.qTypeRecommendToOthers = qTypeRecommendToOthers;
    }

    public List<String> getqTypeOpenEnded() {
        return getqTypeOpenEnded();
    }
    public void setqTypeOpenEnded(List<String> qTypeOpenEnded) {
        this.qTypeOpenEnded = qTypeOpenEnded;
    }

    public List<String> getqTypeDemoAge() {
        return qTypeDemoAge;
    }
    public void setqTypeDemoAge(List<String> qTypeDemoAge) {
        this.qTypeDemoAge = qTypeDemoAge;
    }

    public List<String> getqTypeDemoGrade() {
        return qTypeDemoGrade;
    }
    public void setqTypeDemoGrade(List<String> qTypeDemoGrade) {
        this.qTypeDemoGrade = qTypeDemoGrade;
    }

    public List<String> getqTypeDemoSex() {
        return qTypeDemoSex;
    }
    public void setqTypeDemoSex(List<String> qTypeDemoSex) {
        this.qTypeDemoSex = qTypeDemoSex;
    }

    public List<String> getqTypeDemoEthnic() {
        return qTypeDemoEthnic;
    }
    public void setqTypeDemoEthnic(List<String> qTypeDemoEthnic) {
        this.qTypeDemoEthnic = qTypeDemoEthnic;
    }

    public List<String> getqTypeDemoRelationship() {
        return qTypeDemoRelationship;
    }
    public void setqTypeDemoRelationship(List<String> qTypeDemoRelationship) {
        this.qTypeDemoRelationship = qTypeDemoRelationship;
    }

    public List<String> getqTypeYesNo() {
        return qTypeYesNo;
    }
    public void setqTypeYesNo(List<String> qTypeYesNo) {
        this.qTypeYesNo = qTypeYesNo;
    }
}
