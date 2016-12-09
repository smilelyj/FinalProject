package org.swe.android.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBSurveyList;

import java.util.Arrays;
import java.util.List;

/**
 * Created by YongjiLi on 4/2/16.
 */
public class SurveyList implements Parcelable {
    private String SurveyId;
    private List<String> deployedSurveyId;


    public SurveyList(){}
    public SurveyList(Parcel parcel) {
        SurveyId = parcel.readString();
        Object[] objects = parcel.readArray(String.class.getClassLoader());
        if (objects != null) {
            deployedSurveyId = Arrays.asList((String[]) Arrays.copyOf(objects, objects.length, String[].class));
        }
    }

    public SurveyList(AmazonDynamoDBSurveyList surveyList) {
        this.SurveyId = surveyList.getSurveyId();
        if (surveyList.getDeployedSurveyId() != null) {
            this.deployedSurveyId = surveyList.getDeployedSurveyId();
        }
    }

    public String getSurveyId() {
        return SurveyId;
    }

    public void setSurveyId(String SurveyId) {
        this.SurveyId = SurveyId;
    }

    public List<String> getDeployedSurveyId() {
        return deployedSurveyId;
    }

    public void setDeployedSurveyId(List<String> setDeployedSurveyId) {
        this.deployedSurveyId = setDeployedSurveyId;
    }


    @Override
    public String toString() {
        return "SurveyList{" +
                "SurveyId='" + SurveyId + '\'' +
                ", deployedSurveyId=" + deployedSurveyId +
                '}';
    }

    public static final Parcelable.Creator<SurveyList> CREATOR = new Parcelable.Creator<SurveyList>() {
        public SurveyList createFromParcel(Parcel in) {
            return new SurveyList(in);
        }

        public SurveyList[] newArray(int size) {
            return new SurveyList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(SurveyId);
        if (deployedSurveyId == null) {
            parcel.writeArray(null);
        } else {
            parcel.writeArray(deployedSurveyId.toArray());
        }
    }
}

