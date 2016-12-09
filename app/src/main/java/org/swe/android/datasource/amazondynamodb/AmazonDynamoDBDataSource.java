package org.swe.android.datasource.amazondynamodb;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import org.swe.android.datasource.ActivationCode;
import org.swe.android.datasource.SurveyParcelable;
import org.swe.android.datasource.SurveyResults;
import org.swe.android.datasource.SurveyTemplates;
import org.swe.android.datasource.User;

import java.util.List;

/**
 * Created by Casey on 7/19/2015.
 */

public class AmazonDynamoDBDataSource {
//  private static final String IDENTITY_POOL_ID = "us-east-1:d284ad23-15fd-4e62-97b6-4074439068e8";// Casey Lee's id
    private static final String IDENTITY_POOL_ID = "us-east-1:9f09dd97-6e4d-470b-9629-2dd11b732cbb";// Yongji Li's id
    //New developer also needs to set permissions in IAM console, see also: developer's guide of dynamodb for android.
    public static AmazonDynamoDBSurvey getSurvey(Context context, String surveyId) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the Survey class to the
        // survey table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Return the Survey object that is mapped from the AmazonDynamoDB database
        return mapper.load(AmazonDynamoDBSurvey.class, surveyId);
    }

    public static AmazonDynamoDBDeployedSurvey getDeployedSurvey(Context context, String surveyId) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the Survey class to the
        // survey table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Return the Survey object that is mapped from the AmazonDynamoDB database
        return mapper.load(AmazonDynamoDBDeployedSurvey.class, surveyId);
    }
    public static AmazonDynamoDBTemplates getTemplate(Context context, String templateId) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the Survey class to the
        // survey table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Return the Survey object that is mapped from the AmazonDynamoDB database
        return mapper.load(AmazonDynamoDBTemplates.class, templateId);
    }
    //++++++++++++++++++++++++++
    public static AmazonDynamoDBUser getUserId(Context context, String UserId) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the Survey class to the
        // survey table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Return the Survey object that is mapped from the AmazonDynamoDB database
        return mapper.load(AmazonDynamoDBUser.class, UserId);
    }

    //+++++++++++++++++++++++++++
    public static AmazonDynamoDBActivationCode getCode(Context context, String code) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the Survey class to the
        // survey table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Return the Survey object that is mapped from the AmazonDynamoDB database
        return mapper.load(AmazonDynamoDBActivationCode.class, code);
    }

    public static void insertSurveyResults(Context context, SurveyResults surveyResults) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the SurveyResults class to the
        // survey_results table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Create the survey results object for Amazon
        AmazonDynamoDBSurveyResults results = new AmazonDynamoDBSurveyResults(context, surveyResults);

        // Save the results in Amazon Dynamo DB
        mapper.save(results);
    }

    public static void insertSurvey(Context context, SurveyParcelable surveyParcelable) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the SurveyResults class to the
        // survey_results table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);


        // Create the survey object for Amazon
        AmazonDynamoDBSurvey creation = new AmazonDynamoDBSurvey(surveyParcelable);

        // Save the results in Amazon Dynamo DB
        mapper.save(creation);
    }
    public static void insertDeployedSurvey(Context context, SurveyParcelable surveyParcelable) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the SurveyResults class to the
        // survey_results table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);


        // Create the survey object for Amazon
        AmazonDynamoDBDeployedSurvey creation = new AmazonDynamoDBDeployedSurvey(surveyParcelable);

        // Save the results in Amazon Dynamo DB
        mapper.save(creation);
    }

    public static void insertSurvey2(Context context, SurveyTemplates surveyParcelable) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the SurveyResults class to the
        // survey_results table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Create the survey object for Amazon
        AmazonDynamoDBTemplates creation = new AmazonDynamoDBTemplates(surveyParcelable);

        // Save the results in Amazon Dynamo DB
        mapper.save(creation);
    }

    public static void insertUser(Context context, User user) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the User class to the
        // User table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Create the User object for Amazon
        AmazonDynamoDBUser creation = new AmazonDynamoDBUser(user);
        // Save the User in Amazon Dynamo DB
        mapper.save(creation);
    }

    public static void generateActivationKey(Context context, ActivationCode activationCode) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the User class to the
        // User table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        // Create the User object for Amazon
        AmazonDynamoDBActivationCode creation = new AmazonDynamoDBActivationCode(activationCode);
        // Save the User in Amazon Dynamo DB
        mapper.save(creation);
    }

    public static List<AmazonDynamoDBTemplates> getTemplates(Context context) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the SurveyResults class to the
        // survey_results table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        AmazonDynamoDBSurveyResults tempIdKey = new AmazonDynamoDBSurveyResults();

        //DynamoDBQueryExpression<AmazonDynamoDBTemplates> queryExpression =
        //        new DynamoDBQueryExpression<AmazonDynamoDBTemplates>();

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<AmazonDynamoDBTemplates> result = mapper.scan(AmazonDynamoDBTemplates.class, scanExpression);
        // Do something with result.


        return mapper.scan(AmazonDynamoDBTemplates.class, scanExpression);
    }


    public static List<AmazonDynamoDBSurvey> getSurveyPool(Context context, String userId) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the SurveyResults class to the
        // survey_results table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        AmazonDynamoDBSurvey surveyPoolKey = new AmazonDynamoDBSurvey();
        surveyPoolKey.setOwner(userId);

        AmazonDynamoDBSurvey surveyKey = new AmazonDynamoDBSurvey();
        DynamoDBQueryExpression<AmazonDynamoDBSurvey> queryExpression =
                new DynamoDBQueryExpression<AmazonDynamoDBSurvey>().
                        withHashKeyValues(surveyKey)
                        .addExpressionAttributeNamesEntry
                        ("owner",userId);

        return mapper.query(AmazonDynamoDBSurvey.class, queryExpression);
    }
    public static List<AmazonDynamoDBSurveyResults> getSurveyResults(Context context, String surveyId) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(context);

        // Create a DynamoDBMapper object to be used for mapping the SurveyResults class to the
        // survey_results table in AmazonDynamoDB
        DynamoDBMapper mapper = getMapper(credentialsProvider);

        AmazonDynamoDBSurveyResults surveyResultsKey = new AmazonDynamoDBSurveyResults();
        surveyResultsKey.setSurveyId(surveyId);

        DynamoDBQueryExpression<AmazonDynamoDBSurveyResults> queryExpression =
                new DynamoDBQueryExpression<AmazonDynamoDBSurveyResults>().
                withHashKeyValues(surveyResultsKey);

        return mapper.query(AmazonDynamoDBSurveyResults.class, queryExpression);
    }

    private static CognitoCachingCredentialsProvider getCredentialsProvider(Context context) {
        return new CognitoCachingCredentialsProvider(
                context, // Context
                IDENTITY_POOL_ID,
                Regions.US_EAST_1 // Region
        );
    }

    private static AmazonDynamoDB getAmazonDynamoDB(CognitoCachingCredentialsProvider credentialsProvider) {
        return new AmazonDynamoDBClient(credentialsProvider);
    }

    private static DynamoDBMapper getMapper(CognitoCachingCredentialsProvider credentialsProvider) {
        AmazonDynamoDB dynamo = getAmazonDynamoDB(credentialsProvider);
        return new DynamoDBMapper(dynamo);
    }
}
