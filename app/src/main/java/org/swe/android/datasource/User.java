package org.swe.android.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBSurveyList;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongjiLi on 11/14/15.
 */

public class User implements Parcelable {

    private String UserId;
    private String password;
    private String email;
    private String AdminRole;
    private String region;
    private List<SurveyList> surveyLists;

    public User(){}
    public User(Parcel parcel) {
        UserId = parcel.readString();
        password = parcel.readString();
        email = parcel.readString();
        AdminRole = parcel.readString();
        region = parcel.readString();
        surveyLists = new ArrayList<>();
        parcel.readTypedList(surveyLists, SurveyList.CREATOR);
    }

    public User(AmazonDynamoDBUser user) {
        this.UserId = user.getUserId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.AdminRole = user.getAdminRole();
        this.region = user.getRegion();
        if (user.getSurveyList() != null) {
            this.surveyLists = new ArrayList<>();

            if (user.getSurveyList().size() > 0) {
                for (AmazonDynamoDBSurveyList surveyList : user.getSurveyList()) {
                    this.surveyLists.add(new SurveyList(surveyList));
                }
            }
        }
    }



    public String getUserId() {
        return UserId;
    }
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminRole() {
        return AdminRole;
    }
    public void setAdminRole(String AdminRole) {
        this.AdminRole = AdminRole;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public List<SurveyList> getSurveyList() {
        return surveyLists;
    }
    public void setSurveyList(List<SurveyList> surveyList) {
        this.surveyLists = surveyList;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId='" + UserId + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", AdminRole='" + AdminRole + '\'' +
                ", Region='" + region + '\'' +
                ", SurveyList='" + surveyLists + '\'' +
                '}';
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(UserId);
        parcel.writeString(password);
        parcel.writeString(email);
        parcel.writeString(AdminRole);
        parcel.writeString(region);
        parcel.writeTypedList(surveyLists);
    }


}

