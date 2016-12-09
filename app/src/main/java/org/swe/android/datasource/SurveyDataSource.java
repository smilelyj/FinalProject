package org.swe.android.datasource;

import android.content.Context;

import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBActivationCode;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBAnswer;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBDataSource;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBDeployedSurvey;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBSurvey;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBSurveyResults;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBTemplates;
import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casey on 7/17/2015.
 */

public class SurveyDataSource {

    public static final String SOURCE_AMAZON_DYNAMODB = "AMAZON_DYNAMODB";
    public static final String DATA_SOURCE = SOURCE_AMAZON_DYNAMODB;

    // Survey Question Types
    public static final String QUESTION_TYPE_OVERALL = "Overall";
    public static final String QUESTION_TYPE_AGREE_DISAGREE = "Agree/Disagree";
    public static final String QUESTION_TYPE_CONFIDENCE_AND_ABILITY = "Confidence and Ability";
    public static final String QUESTION_TYPE_RECOMMEND_TO_OTHERS = "Recommend to Others";
    public static final String QUESTION_TYPE_OPEN_ENDED = "Open-Ended";

    public static final String QUESTION_TYPE_DEMO_GRADE = "Demographic-Grade";
    public static final String QUESTION_TYPE_DEMO_AGE = "Demographic-Age";
    public static final String QUESTION_TYPE_DEMO_GENDER = "Demographic-Gender";
    public static final String QUESTION_TYPE_DEMO_RACE = "Demographic-Race";
    public static final String QUESTION_TYPE_RELATIONSHIP = "Relationship";
    public static final String QUESTION_TYPE_YES_NO = "Yes/No";
    
    // Get the survey from the appropriate data source that corresponds to the survey ID
    public static SurveyParcelable getDeployedSurvey(String source, Context context, String surveyId) {
        SurveyParcelable surveyParcelable = null;

        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBDeployedSurvey amazonSurvey = AmazonDynamoDBDataSource.getDeployedSurvey(context, surveyId);

                // If a survey was found, convert it to a SurveyParcelable that can be passed to the SurveyActivity
                if (amazonSurvey != null) {
                    surveyParcelable = new SurveyParcelable(amazonSurvey);
                }
                break;
            default:
                surveyParcelable = null;
                break;
        }
        return surveyParcelable;
    }

    // Get the survey from the appropriate data source that corresponds to the survey ID
    public static SurveyParcelable getSurvey(String source, Context context, String surveyId) {
        SurveyParcelable surveyParcelable = null;

        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBSurvey amazonSurvey = AmazonDynamoDBDataSource.getSurvey(context, surveyId);

                // If a survey was found, convert it to a SurveyParcelable that can be passed to the SurveyActivity
                if (amazonSurvey != null) {
                    surveyParcelable = new SurveyParcelable(amazonSurvey);
                }
                break;
            default:
                surveyParcelable = null;
                break;
        }
        return surveyParcelable;
    }
    // Get the survey results from the appropriate data source that corresponds to the survey ID
    public static ArrayList getSurveyPool(String source, Context context, String userId) {
        ArrayList <String> surveyPool = new ArrayList<String>();
        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                // Get the results from Amazon
                List<AmazonDynamoDBSurvey> amazonDynamoDBSurveys;
                amazonDynamoDBSurveys = AmazonDynamoDBDataSource.getSurveyPool(context, userId);
                int mNumberOfSurveys = amazonDynamoDBSurveys.size();
                for(int mCurrentSurveyId = 0; mCurrentSurveyId<mNumberOfSurveys; mCurrentSurveyId++){
                    surveyPool.add(mCurrentSurveyId, amazonDynamoDBSurveys.get(mCurrentSurveyId).getOwner());
                }
        }

        return surveyPool;
    }

    // Get the survey from the appropriate data source that corresponds to the survey ID
    public static SurveyTemplates getTemplateQuestions(String source, Context context, String templateId) {
        SurveyTemplates surveyTemplate = null;

        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBTemplates amazonDynamoDBTemplate = AmazonDynamoDBDataSource.getTemplate(context, templateId);

                // If a survey was found, convert it to a SurveyParcelable that can be passed to the SurveyActivity
                if (amazonDynamoDBTemplate != null) {
                    surveyTemplate = new SurveyTemplates(amazonDynamoDBTemplate);
                }
                break;
            default:
                surveyTemplate = null;
                break;
        }
        return surveyTemplate;
    }
    //++++++++++++++++++
    // Get the user from the appropriate data source that corresponds to the user ID
    public static User getUserInfo(String source, Context context, String UserId) {
        User user = null;

        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBUser amazonUser = AmazonDynamoDBDataSource.getUserId(context, UserId);
                // If a survey was found, convert it to a SurveyParcelable that can be passed to the SurveyActivity
                if (amazonUser != null) {
                    user = new User(amazonUser);
                }
                break;
            default:
                user = null;
                break;
        }
        return user;
    }
    // Get the user from the appropriate data source that corresponds to the user ID
    public static ActivationCode getActivationCode(String source, Context context, String key) {
        ActivationCode Code = null;

        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBActivationCode amazonDynamoDBActivationCode = AmazonDynamoDBDataSource.getCode(context, key);
                // If a survey was found, convert it to a SurveyParcelable that can be passed to the SurveyActivity
                if (amazonDynamoDBActivationCode != null) {
                    Code = new ActivationCode(amazonDynamoDBActivationCode);
                }
                break;
            default:
                Code = null;
                break;
        }
        return Code;
    }

    // Insert survey into the appropriate data source
    public static Boolean insertSurvey(String source, Context context, SurveyParcelable surveyParcelable) {
        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBDataSource.insertSurvey(context, surveyParcelable);
                break;
            default:
                break;
        }
        return true;
    }
    // Insert deployed survey into the appropriate data source
    public static Boolean insertDeployedSurvey(String source, Context context, SurveyParcelable surveyParcelable) {
        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBDataSource.insertDeployedSurvey(context, surveyParcelable);
                break;
            default:
                break;
        }
        return true;
    }
    // Insert survey into the appropriate data source
    public static Boolean insertSurvey2(String source, Context context, SurveyTemplates surveyParcelable) {
        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBDataSource.insertSurvey2(context, surveyParcelable);
                break;
            default:
                break;
        }
        return true;
    }
    //++++++++++++++++++++++
    public static Boolean insertUser(String source, Context context, User user) {
        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBDataSource.insertUser(context, user);
                break;
            default:
                break;
        }
        return true;
    }

    public static Boolean generateActivationKey(String source, Context context, ActivationCode activationCode) {
        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBDataSource.generateActivationKey(context, activationCode);
                break;
            default:
                break;
        }
        return true;
    }


    // Insert results into the appropriate data source
    public static Boolean insertSurveyResults(String source, Context context, SurveyResults surveyResults) {
        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                AmazonDynamoDBDataSource.insertSurveyResults(context, surveyResults);
                break;
            default:
                break;
        }
        return true;
    }

    //Get the templates list form the appropriate template table

    public static ArrayList getTempList(String source, Context context) {
        SurveyTemplates surveyTemplates = new SurveyTemplates();
        ArrayList tempList = new ArrayList();
        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                // Get all the templates from AmazonDynamoDB
                List<AmazonDynamoDBTemplates> amazonDynamoDBTemplates;
                amazonDynamoDBTemplates = AmazonDynamoDBDataSource.getTemplates(context);

                // Initialize the quantified survey results if results were found
                if (amazonDynamoDBTemplates.size() > 0) {
                    surveyTemplates = new SurveyTemplates();
                } else {
                    break;
                }
                for (AmazonDynamoDBTemplates templates : amazonDynamoDBTemplates) {
                   tempList.add(templates.getTemplateId());
                }

        }
        return tempList;

    }
    // Get the survey results from the appropriate data source that corresponds to the survey ID
    public static SurveyResultsQuantified getSurveyResults(String source, Context context, String surveyId) {
        SurveyResultsQuantified surveyResultsQuantified = new SurveyResultsQuantified();

        switch (source) {
            case SOURCE_AMAZON_DYNAMODB:
                // Get the results from Amazon
                List<AmazonDynamoDBSurveyResults> amazonSurveyResults;
                amazonSurveyResults = AmazonDynamoDBDataSource.getSurveyResults(context, surveyId);

                // Initialize the quantified survey results if results were found
                if (amazonSurveyResults.size() > 0) {
                    surveyResultsQuantified = new SurveyResultsQuantified();
                } else {
                    break;
                }

                // Set the survey ID
                surveyResultsQuantified.setSurveyId(surveyId);
                //++++++++++++++++++++++++++++
                //Create the SurveyResultsQuantified Class
                SurveyParcelable oldSurvey = new SurveyParcelable();
                oldSurvey = SurveyDataSource.getDeployedSurvey(SurveyDataSource.DATA_SOURCE, context, surveyId);

                int mCurrentQuestion = 0;
                int mNumberOfQuestions = oldSurvey.getQuestions().size();
                if (surveyResultsQuantified.getAnswersQuantified() == null) {
                    surveyResultsQuantified.setAnswersQuantified(new ArrayList<SurveyAnswersQuantified>());
                }
                for(mCurrentQuestion = 0; mCurrentQuestion<mNumberOfQuestions; mCurrentQuestion++){
                    SurveyAnswersQuantified surveyAnswersQuantified = new SurveyAnswersQuantified();
                    surveyAnswersQuantified.setQuestionNum(mCurrentQuestion);
                    surveyAnswersQuantified.setQuestionString(oldSurvey.getQuestions().get(mCurrentQuestion).getQuestion());
                    surveyAnswersQuantified.setQuestionType(oldSurvey.getQuestions().get(mCurrentQuestion).getQuestionType());
                    surveyResultsQuantified.getAnswersQuantified().add(mCurrentQuestion, surveyAnswersQuantified);
                }
                //++++++++++++++++++++++++++++
                // Set up the quantified survey results
                for (AmazonDynamoDBSurveyResults results : amazonSurveyResults) {
                    for (AmazonDynamoDBAnswer answer : results.getAnswers()) {
                        // We only want to quantify the results if the user has answered the question

                        switch (answer.getQuestionType()) {
                            case QUESTION_TYPE_OVERALL:
                                if (answer.getChoice() != null) {
                                    // Increment the number of overall questions
                                    answer.getQuestionNum();
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setNumberOfOverall(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getNumberOfOverall() + 1
                                    );

                                    // If they answered A or B, increment the number of A or B's selected
                                    if (answer.getChoice().equalsIgnoreCase("A") ||
                                            answer.getChoice().equalsIgnoreCase("B")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setaOrBTotal(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getaOrBTotal() + 1
                                        );
                                    }
                                }
                                break;
                            case QUESTION_TYPE_AGREE_DISAGREE:
                                if (answer.getChoice() != null) {
                                    // Increment the number of agree/disagree questions
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setNumberOfAgreeDisagree(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getNumberOfAgreeDisagree() + 1
                                    );

                                    // If they answered Agree or Strongly Agree, increment the number
                                    if (answer.getChoice().equalsIgnoreCase("AGREE") ||
                                            answer.getChoice().equalsIgnoreCase("STRONGLY AGREE")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setAgreeStronglyAgreeTotal(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getAgreeStronglyAgreeTotal() + 1
                                        );
                                    }
                                }
                                break;
                            case QUESTION_TYPE_CONFIDENCE_AND_ABILITY:
                                if (answer.getChoice() != null) {
                                    // Increment the number of confidence and ability questions
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setNumberOfConfidenceAndAbility(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getNumberOfConfidenceAndAbility() + 1
                                    );

                                    // If they answered Improved, increment the number
                                    if (answer.getChoice().equalsIgnoreCase("IMPROVED")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setImprovedTotal(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getImprovedTotal() + 1
                                        );
                                    }
                                }
                                break;
                            case QUESTION_TYPE_RECOMMEND_TO_OTHERS:
                                if (answer.getChoice() != null) {
                                    // Increment the number of recommend to others questions
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setNumberOfRecommendToOthers(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getNumberOfRecommendToOthers() + 1
                                    );

                                    // If they answered Yes, increment the number
                                    if (answer.getChoice().equalsIgnoreCase("YES")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setYesTotal(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getYesTotal() + 1
                                        );
                                    }
                                }
                                break;

                            case QUESTION_TYPE_DEMO_GRADE:
                                if (answer.getChoice() != null) {
                                    // Increment the number of recommend to others questions
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setGradeTotal(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getGradeTotal() + 1
                                    );

                                    // If they answered 3rd,4th,5th,6th, increment the number
                                    if (answer.getChoice().equalsIgnoreCase("3rd grade")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setGrade3(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getGrade4() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("4th grade")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setGrade4(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getGrade4() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("5th grade")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setGrade5(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getGrade5() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("6th grade")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setGrade6(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getGrade6() + 1
                                        );
                                    }
                                }
                                break;

                            case QUESTION_TYPE_DEMO_GENDER:
                                if (answer.getChoice() != null) {
                                    // Increment the number of recommend to others questions
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setGenderTotal(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getGenderTotal() + 1
                                    );

                                    // If they answered female or male, increment the number
                                    if (answer.getChoice().equalsIgnoreCase("female")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setGenderFemale(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getGenderFemale() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("male")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setGenderMale(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getGenderMale() + 1
                                        );
                                    }
                                }
                                break;
                            case QUESTION_TYPE_DEMO_RACE:
                                if (answer.getChoice() != null) {
                                    // Increment the number of recommend to others questions
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setRaceTotal(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRaceTotal() + 1
                                    );

                                    // record the choice separately;
                                    if (answer.getChoice().equalsIgnoreCase("White or European American")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRaceWhite(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRaceWhite() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Hispanic, Latino, or Spanish")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRaceHispanic(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRaceHispanic() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Black or African-American")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRaceBlack(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRaceBlack() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Native Hawaiian or Pacific Islander")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRaceHawaiian(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRaceHawaiian() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Native American or Alaskan Native")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRaceAlaskan(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRaceAlaskan() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Other")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRaceOther(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRaceOther() + 1
                                        );
                                    }
                                }
                                break;
                            case QUESTION_TYPE_RELATIONSHIP:
                                if (answer.getChoice() != null) {
                                    // Increment the number of recommend to others questions
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setRelationTotal(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRelationTotal() + 1
                                    );

                                    // record the choice separately;
                                    if (answer.getChoice().equalsIgnoreCase("Mother")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRelationMom(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRelationMom() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Father")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRelationDad(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRelationDad() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Guardian")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRelationGuardian(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRelationGuardian() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Troop Leader")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRelationTroopLeader(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRelationTroopLeader() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Teacher")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRelationTeacher(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRelationTeacher() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("Other")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setRelationOther(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getRelationOther() + 1
                                        );
                                    }
                                }
                                break;
                            case QUESTION_TYPE_YES_NO:
                                if (answer.getChoice() != null) {
                                    // Increment the number of recommend to others questions
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).setYesNoTotal(
                                            surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getYesNoTotal() + 1
                                    );

                                    // If they answered female or male, increment the number
                                    if (answer.getChoice().equalsIgnoreCase("Yes")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setChooseYes(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getChooseYes() + 1
                                        );
                                    }
                                    if (answer.getChoice().equalsIgnoreCase("No")) {
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setChooseNo(
                                                surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getChooseNo() + 1
                                        );
                                    }
                                }
                                break;

                            /*
                            case QUESTION_TYPE_OPEN_ENDED:
                                // Initialize the HashMap if it hasn't been done already
                                if (surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).getOpenEndedQuestions() == null) {
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).setOpenEndedQuestions(new HashMap<String, List<String>>());
                                }

                                // If this particular question is not in our HashMap yet, add it
                                if (!surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).getOpenEndedQuestions().containsKey(
                                        answer.getQuestion()
                                )) {
                                    // Create a list to hold the explanations
                                    List<String> explanations = new ArrayList<>();
                                    explanations.add(answer.getExplanation());

                                    // Add the question and the open-ended answer to the HashMap
                                    surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum()-1).getOpenEndedQuestions().put(
                                            answer.getQuestion(),
                                            explanations
                                    );
                                } else {
                                    // If the user answered the question
                                    if (answer.getExplanation() != null) {
                                        // For the given question, add the open-ended answer to the HashMap
                                        surveyResultsQuantified.getAnswersQuantified().get(answer.getQuestionNum() - 1).getOpenEndedQuestions().
                                                get(answer.getQuestion()).
                                                add(answer.getExplanation());
                                    }
                                }


                                break;
                                */
                            default:
                                break;
                        }
                    }
                }
                break;
            default:
                surveyResultsQuantified = null;
                break;
        }

        return surveyResultsQuantified;
    }
}
