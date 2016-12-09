package org.swe.android.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBQuestion;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Casey on 7/18/2015.
 */
public class QuestionParcelable implements Parcelable {
    private String question;
    private List<String> choices;
    private boolean explanationRequired;
    private String questionType;

    public QuestionParcelable(){}
    public QuestionParcelable(Parcel parcel) {
        question = parcel.readString();
        Object[] objects = parcel.readArray(String.class.getClassLoader());
        if (objects != null) {
            choices = Arrays.asList((String[]) Arrays.copyOf(objects, objects.length, String[].class));
        }
        explanationRequired = parcel.readByte() != 0;
        questionType = parcel.readString();
    }

    public QuestionParcelable(AmazonDynamoDBQuestion question) {
        this.question = question.getQuestion();
        if (question.getChoices() != null) {
            this.choices = question.getChoices();
        }
        this.explanationRequired = question.getExplanationRequired();
        this.questionType = question.getQuestionType();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public boolean getExplanationRequired() {
        return explanationRequired;
    }

    public void setExplanationRequired(boolean explanationRequired) {
        this.explanationRequired = explanationRequired;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return "QuestionParcelable{" +
                "question='" + question + '\'' +
                ", choices=" + choices +
                ", explanationRequired=" + explanationRequired +
                ", questionType=" + questionType +
                '}';
    }

    public static final Parcelable.Creator<QuestionParcelable> CREATOR = new Parcelable.Creator<QuestionParcelable>() {
        public QuestionParcelable createFromParcel(Parcel in) {
            return new QuestionParcelable(in);
        }

        public QuestionParcelable[] newArray(int size) {
            return new QuestionParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        if (choices == null) {
            parcel.writeArray(null);
        } else {
            parcel.writeArray(choices.toArray());
        }
        parcel.writeByte((byte) (explanationRequired ? 1 : 0));
        parcel.writeString(questionType);
    }
}
