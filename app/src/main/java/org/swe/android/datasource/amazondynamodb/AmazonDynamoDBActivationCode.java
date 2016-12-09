package org.swe.android.datasource.amazondynamodb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import org.swe.android.datasource.ActivationCode;

/**
 * Created by YongjiLi on 4/19/16.
 */
@DynamoDBTable(tableName = "activation_code")
public class AmazonDynamoDBActivationCode {
    private String key;
    private boolean isNew;
    private String date;

    public AmazonDynamoDBActivationCode (){
        super ();
    }
    public AmazonDynamoDBActivationCode(ActivationCode activationCode) {
        super();
        this.key = activationCode.getKey();
        this.isNew = activationCode.getIsNew();
        this.date = activationCode.getDate();
    }

    @DynamoDBHashKey(attributeName = "activation_key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @DynamoDBAttribute(attributeName = "is_new")
    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @DynamoDBAttribute(attributeName = "date")
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ActivationCode{" +
                "key='" + key + '\'' +
                ", isNew='" + isNew + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
