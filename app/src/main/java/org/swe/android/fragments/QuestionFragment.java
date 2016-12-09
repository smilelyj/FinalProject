package org.swe.android.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.swe.android.R;

/**
 * Created by caseylee on 7/26/15.
 */
public class QuestionFragment extends ListFragment {
    private String mQuestion;
    private String mChoice;
    private String mExplanation;
    private String mQuestionType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get the arguments bundle
        Bundle bundle = getArguments();

        // Set up the fragment values
        mQuestion = bundle.getString("question");

        // Obtain the view
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView question = (TextView) view.findViewById(R.id.question);
        question.setText(mQuestion);

        if (bundle.getStringArrayList("choices") != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_list_item_1, bundle.getStringArrayList("choices"));

            setListAdapter(adapter);
        }

        // Obtain the explanation EditText
        EditText explanation = (EditText) view.findViewById(R.id.explanation);

        if (!bundle.getBoolean("explanationRequired")) {
            // Hide the view if an explanation is not required
            view.findViewById(R.id.explanation).setVisibility(View.GONE);
        } else {
            if (bundle.getStringArrayList("choices") != null) {
                explanation.setHint(getString(R.string.hint_explanation));
            } else {
                explanation.setHint(getString(R.string.hint_answer));
            }

            explanation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mExplanation = editable.toString();
                }
            });
        }

        // Set the question type
        mQuestionType = bundle.getString("questionType");

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mChoice = (String) getListAdapter().getItem(position);
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public String getmChoice() {
        return mChoice;
    }

    public String getmExplanation() {
        return mExplanation;
    }

    public String getmQuestionType() {
        return mQuestionType;
    }
}
