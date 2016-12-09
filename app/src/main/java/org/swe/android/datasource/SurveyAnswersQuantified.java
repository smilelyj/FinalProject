package org.swe.android.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by YongjiLi on 12/3/15.
 */
public class SurveyAnswersQuantified implements Parcelable {

    private String questionString;
    private String questionType;
    private Integer questionNum = 0;

    private Integer numberOfAgreeDisagree = 0;
    private Integer agreeStronglyAgreeTotal = 0;

    private Integer numberOfConfidenceAndAbility = 0;
    private Integer improvedTotal = 0;

    private Integer numberOfRecommendToOthers = 0;
    private Integer yesTotal = 0;

    private Integer numberOfOverall = 0;
    private Integer aOrBTotal = 0;

    private Integer grade3 = 0;
    private Integer grade4 = 0;
    private Integer grade5 = 0;
    private Integer grade6 = 0;
    private Integer gradeTotal = 0;

    private Integer genderFemale = 0;
    private Integer genderMale = 0;
    private Integer genderTotal = 0;

    private Integer raceWhite = 0;
    private Integer raceHispanic = 0;
    private Integer raceBlack = 0;
    private Integer raceAsian = 0;
    private Integer raceHawaiian = 0;
    private Integer raceAlaskan = 0;
    private Integer raceOther = 0;
    private Integer raceTotal = 0;

    private Integer relationMom = 0;
    private Integer relationDad = 0;
    private Integer relationGuardian = 0;
    private Integer relationTroopLeader = 0;
    private Integer relationTeacher = 0;
    private Integer relationOther = 0;
    private Integer relationTotal = 0;

    private Integer chooseYes = 0;
    private Integer chooseNo = 0;
    private Integer yesNoTotal = 0;

    private HashMap<String, List<String>> openEndedQuestions;

    //++++
    private List<String> explanations;
    //+++++

    public SurveyAnswersQuantified(){}

    public SurveyAnswersQuantified(Parcel parcel) {

        questionString = parcel.readString();
        questionType = parcel.readString();
        questionNum = parcel.readInt();

        numberOfAgreeDisagree = parcel.readInt();
        agreeStronglyAgreeTotal = parcel.readInt();

        numberOfConfidenceAndAbility = parcel.readInt();
        improvedTotal = parcel.readInt();

        numberOfRecommendToOthers = parcel.readInt();
        yesTotal = parcel.readInt();

        numberOfOverall = parcel.readInt();
        aOrBTotal = parcel.readInt();

        grade3 = parcel.readInt();
        grade4 = parcel.readInt();
        grade5 = parcel.readInt();
        grade6 = parcel.readInt();
        gradeTotal = parcel.readInt();

        genderFemale = parcel.readInt();
        genderMale = parcel.readInt();
        genderTotal = parcel.readInt();

        raceWhite = parcel.readInt();
        raceHispanic  = parcel.readInt();
        raceBlack  = parcel.readInt();
        raceAsian  = parcel.readInt();
        raceHawaiian  = parcel.readInt();
        raceAlaskan  = parcel.readInt();
        raceOther  = parcel.readInt();
        raceTotal = parcel.readInt();

        relationMom  = parcel.readInt();
        relationDad  = parcel.readInt();
        relationGuardian  = parcel.readInt();
        relationTroopLeader  = parcel.readInt();
        relationTeacher  = parcel.readInt();
        relationOther  = parcel.readInt();
        relationTotal = parcel.readInt();

        chooseYes = parcel.readInt();
        chooseNo = parcel.readInt();
        yesNoTotal = parcel.readInt();

        openEndedQuestions = parcel.readHashMap(SurveyAnswersQuantified.class.getClassLoader());

        parcel.readList(explanations,SurveyAnswersQuantified.class.getClassLoader());
        //parcel.readList(explanations,null);

    }

    public String getQuestionString(){return questionString;}

    public void setQuestionString(String questionString){
        this.questionString = questionString;
    }

    public String getQuestionType(){return questionType;}

    public void setQuestionType(String questionType){
        this.questionType = questionType;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }

    public Integer getNumberOfAgreeDisagree() {
        return numberOfAgreeDisagree;
    }

    public void setNumberOfAgreeDisagree(Integer numberOfAgreeDisagree) {
        this.numberOfAgreeDisagree = numberOfAgreeDisagree;
    }

    public Integer getAgreeStronglyAgreeTotal() {
        return agreeStronglyAgreeTotal;
    }

    public void setAgreeStronglyAgreeTotal(Integer agreeStronglyAgreeTotal) {
        this.agreeStronglyAgreeTotal = agreeStronglyAgreeTotal;
    }

    public Integer getNumberOfConfidenceAndAbility() {
        return numberOfConfidenceAndAbility;
    }

    public void setNumberOfConfidenceAndAbility(Integer numberOfConfidenceAndAbility) {
        this.numberOfConfidenceAndAbility = numberOfConfidenceAndAbility;
    }

    public Integer getImprovedTotal() {
        return improvedTotal;
    }

    public void setImprovedTotal(Integer improvedTotal) {
        this.improvedTotal = improvedTotal;
    }

    public Integer getNumberOfRecommendToOthers() {
        return numberOfRecommendToOthers;
    }

    public void setNumberOfRecommendToOthers(Integer numberOfRecommendToOthers) {
        this.numberOfRecommendToOthers = numberOfRecommendToOthers;
    }

    public Integer getYesTotal() {
        return yesTotal;
    }

    public void setYesTotal(Integer yesTotal) {
        this.yesTotal = yesTotal;
    }

    public Integer getNumberOfOverall() {
        return numberOfOverall;
    }

    public void setNumberOfOverall(Integer numberOfOverall) {
        this.numberOfOverall = numberOfOverall;
    }

    public Integer getaOrBTotal() {
        return aOrBTotal;
    }

    public void setaOrBTotal(Integer aOrBTotal) {
        this.aOrBTotal = aOrBTotal;
    }

    public Integer getGrade3(){return grade3;}
    public void setGrade3(Integer grade3) {
        this.grade3 = grade3;
    }

    public Integer getGrade4(){return grade4;}
    public void setGrade4(Integer grade4) {
        this.grade4 = grade4;
    }

    public Integer getGrade5(){return grade5;}
    public void setGrade5(Integer grade5) {
        this.grade5 = grade5;
    }

    public Integer getGrade6(){return grade6;}
    public void setGrade6(Integer grade6) {
        this.grade6 = grade6;
    }

    public Integer getGradeTotal(){return gradeTotal;}
    public void setGradeTotal(Integer gradeTotal) {
        this.gradeTotal = gradeTotal;
    }


    public Integer getGenderFemale(){return genderFemale;}
    public void setGenderFemale(Integer genderFemale) {
        this.genderFemale = genderFemale;
    }

    public Integer getGenderMale(){return genderMale;}
    public void setGenderMale(Integer genderMale) {
        this.genderMale = genderMale;
    }

    public Integer getGenderTotal(){return genderTotal;}
    public void setGenderTotal(Integer genderTotal) {
        this.genderTotal = genderTotal;
    }

    public Integer getRaceWhite(){return raceWhite;}
    public void setRaceWhite(Integer raceWhite) {
        this.raceWhite = raceWhite;
    }
    public Integer getRaceHispanic(){return raceHispanic;}
    public void setRaceHispanic(Integer raceHispanic) {
        this.raceHispanic = raceHispanic;
    }
    public Integer getRaceBlack(){return raceBlack;}
    public void setRaceBlack(Integer raceBlack) {
        this.raceBlack = raceBlack;
    }
    public Integer getRaceAsian(){return raceAsian;}
    public void setRaceAsian(Integer raceAsian) {
        this.raceAsian = raceAsian;
    }
    public Integer getRaceHawaiian(){return raceHawaiian;}
    public void setRaceHawaiian(Integer raceHawaiian) {
        this.raceAsian = raceHawaiian;
    }
    public Integer getRaceAlaskan(){return raceAlaskan;}
    public void setRaceAlaskan(Integer raceAlaskan) {
        this.raceAlaskan = raceAlaskan;
    }
    public Integer getRaceOther(){return raceOther;}
    public void setRaceOther(Integer raceOther) {
        this.raceOther = raceOther;
    }
    public Integer getRaceTotal(){return raceTotal;}
    public void setRaceTotal(Integer raceTotal) {
        this.raceTotal = raceTotal;
    }

    public Integer getRelationMom(){return relationMom;}
    public void setRelationMom(Integer relationMom) {
        this.relationMom = relationMom;
    }public Integer getRelationDad(){return relationDad;}
    public void setRelationDad(Integer relationDad) {
        this.relationDad = relationDad;
    }
    public Integer getRelationGuardian(){return relationGuardian;}
    public void setRelationGuardian(Integer relationGuardian) {
        this.relationGuardian = relationGuardian;
    }
    public Integer getRelationTroopLeader(){return relationTroopLeader;}
    public void setRelationTroopLeader(Integer relationTroopLeader) {
        this.relationTroopLeader = relationTroopLeader;
    }public Integer getRelationTeacher(){return relationTeacher;}
    public void setRelationTeacher(Integer relationTeacher) {
        this.relationTeacher = relationTeacher;
    }
    public Integer getRelationOther(){return relationOther;}
    public void setRelationOther(Integer relationOther) {
        this.relationOther = relationOther;
    }

    public Integer getRelationTotal(){return relationTotal;}
    public void setRelationTotal(Integer relationTotal) {
        this.relationTotal = relationTotal;
    }
    public Integer getChooseYes(){return chooseYes;}
    public void setChooseYes(Integer chooseYes) {
        this.chooseYes = chooseYes;
    }

    public Integer getChooseNo(){return chooseNo;}
    public void setChooseNo(Integer chooseNo) {
        this.chooseNo = chooseNo;
    }

    public Integer getYesNoTotal(){return yesNoTotal;}
    public void setYesNoTotal(Integer yesNoTotal) {
        this.yesNoTotal = yesNoTotal;
    }


    public HashMap<String, List<String>> getOpenEndedQuestions() {
        return openEndedQuestions;
    }

    public void setOpenEndedQuestions(HashMap<String, List<String>> openEndedQuestions) {
        this.openEndedQuestions = openEndedQuestions;
    }



    public List<String> getExplanations() {
        return explanations;
    }
    public void setExplanations(List<String> explanations) {
        this.explanations = explanations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "SurveyAnswersQuantified{" +
                "questionString='" + questionString + '\'' +
                "questionType='" + questionType + '\'' +
                "questionNum='" + questionNum + '\'' +
                "numberOfAgreeDisagree='" + numberOfAgreeDisagree + '\'' +
                "agreeStronglyAgreeTotal='" + agreeStronglyAgreeTotal + '\'' +
                "numberOfConfidenceAndAbility='" + numberOfConfidenceAndAbility + '\'' +
                "improvedTotal='" + improvedTotal + '\'' +
                "numberOfRecommendToOthers='" + numberOfRecommendToOthers + '\'' +
                "yesTotal='" + yesTotal + '\'' +
                ", numberOfOverall=" + numberOfOverall +
                ", aOrBTotal=" + aOrBTotal +
                ", grade3=" + grade3 +
                ", grade4=" + grade4 +
                ", grade5=" + grade5 +
                ", grade6=" + grade6 +
                ", gradeTotal=" + gradeTotal +
                ", genderFemale=" + genderFemale +
                ", genderMale=" + genderMale +
                ", genderTotal=" + genderTotal +
                ", raceWhite=" + raceWhite +
                ", raceHispanic=" + raceHispanic +
                ", raceBlack=" + raceBlack +
                ", raceAsian=" + raceAsian +
                ", raceHawaiian=" + raceHawaiian +
                ", raceAlaskan=" + raceAlaskan +
                ", raceOther=" + raceOther +
                ", raceTotal=" + raceTotal +
                ", relationMom=" + relationMom +
                ", relationDad=" + relationDad +
                ", relationGuardian=" + relationGuardian +
                ", relationTroopLeader=" + relationTroopLeader +
                ", relationTeacher=" + relationTeacher +
                ", relationOther=" + relationOther +
                ", relationTotal=" + relationTotal +
                ", chooseYes" + chooseYes +
                ", chooseNo=" + chooseNo +
                ", yesNoTotal=" + yesNoTotal +
               // ", openEndedQuestions=" + openEndedQuestions +
                ", explanations=" + explanations +
                '}';
    }
    public static final Parcelable.Creator<SurveyAnswersQuantified> CREATOR = new Parcelable.Creator<SurveyAnswersQuantified>() {
        public SurveyAnswersQuantified createFromParcel(Parcel in) {
            return new SurveyAnswersQuantified(in);
        }

        public SurveyAnswersQuantified[] newArray(int size) {
            return new SurveyAnswersQuantified[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(questionString);
        parcel.writeString(questionType);
        parcel.writeInt(questionNum);
        parcel.writeInt(numberOfAgreeDisagree);
        parcel.writeInt(agreeStronglyAgreeTotal);
        parcel.writeInt(numberOfConfidenceAndAbility);
        parcel.writeInt(improvedTotal);
        parcel.writeInt(numberOfRecommendToOthers);
        parcel.writeInt(yesTotal);
        parcel.writeInt(numberOfOverall);
        parcel.writeInt(aOrBTotal);
        parcel.writeInt(grade3);
        parcel.writeInt(grade4);
        parcel.writeInt(grade5);
        parcel.writeInt(grade6);
        parcel.writeInt(gradeTotal);
        parcel.writeInt(genderFemale);
        parcel.writeInt(genderMale);
        parcel.writeInt(genderTotal);
        parcel.writeInt(raceWhite);
        parcel.writeInt(raceHispanic);
        parcel.writeInt(raceBlack);
        parcel.writeInt(raceAsian);
        parcel.writeInt(raceHawaiian);
        parcel.writeInt(raceAlaskan);
        parcel.writeInt(raceOther);
        parcel.writeInt(raceTotal);
        parcel.writeInt(relationMom);
        parcel.writeInt(relationDad);
        parcel.writeInt(relationGuardian);
        parcel.writeInt(relationTroopLeader);
        parcel.writeInt(relationTeacher);
        parcel.writeInt(relationOther);
        parcel.writeInt(relationTotal);
        parcel.writeInt(chooseYes);
        parcel.writeInt(chooseNo);
        parcel.writeInt(yesNoTotal);

        if (openEndedQuestions == null) {
            parcel.writeList(null);
        } else {
            parcel.writeString(openEndedQuestions.toString());
        }

        parcel.writeList(explanations);

    }

}
