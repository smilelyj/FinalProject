package org.swe.android.datasource.amazondynamodb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import org.swe.android.datasource.SurveyList;
import org.swe.android.datasource.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongjiLi on 11/14/15.
 */
@DynamoDBTable(tableName = "SWE_user")
public class AmazonDynamoDBUser {
    private String UserId;
    private String password;
    private String email;
    private String AdminRole;
    private String region;
    private List<AmazonDynamoDBSurveyList> SurveyList;

    public AmazonDynamoDBUser(){super();}

    public AmazonDynamoDBUser(User user) {
        super();
        this.UserId = user.getUserId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.AdminRole = user.getAdminRole();
        this.region = user.getRegion();
        this.SurveyList = new ArrayList<>();
        for (SurveyList surveyList : user.getSurveyList()) {
            this.SurveyList.add(new AmazonDynamoDBSurveyList(surveyList));
        }

    }

    @DynamoDBHashKey(attributeName = "user_id")
    public String getUserId() {
        return UserId;
    }
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "admin_role")
    public String getAdminRole() {
        return AdminRole;
    }

    public void setAdminRole(String AdminRole) {
        this.AdminRole = AdminRole;
    }

    @DynamoDBAttribute(attributeName = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @DynamoDBAttribute(attributeName = "survey_list")
    public List<AmazonDynamoDBSurveyList> getSurveyList() {
        return SurveyList;
    }

    public void setSurveyList(List<AmazonDynamoDBSurveyList> surveyList) {
        this.SurveyList = surveyList;
    }

    @Override
    public String toString() {
        return "AmazonDynamoDBUser{" +
                "UserId='" + UserId + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", AdminRole='" + AdminRole + '\'' +
                ", Region='" + region + '\'' +
                ", SurveyList=" + SurveyList +
                '}';
    }
}
