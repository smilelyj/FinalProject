package org.swe.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.swe.android.R;
import org.swe.android.activities.SurveyResultsActivity.resultsBody;

import java.util.List;

/**
 * Created by YongjiLi on 11/3/15.
 */

public class SurveyResultsAdapter extends BaseAdapter {

    private Context context;
    List<resultsBody> items;//the list in the adapter


    public SurveyResultsAdapter(Context context,List<resultsBody> list){
        this.context = context;
        this.items = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override //only used
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return items.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        resultsBody item = (resultsBody)getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_survey_results, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.text_question);
            viewHolder.tvQtype = (TextView) convertView.findViewById(R.id.text_question_type);
            viewHolder.tvResults = (TextView) convertView.findViewById(R.id.text_choice);
            //viewHolder.progressBar = (FrameLayout) convertView.findViewById(R.id.frameLayout_progress_bar);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll_open_ended_questions);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(item.question);
        viewHolder.tvQtype.setText(item.question_type);
        viewHolder.tvResults.setText(item.text_choice);
        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvQtype;
        TextView tvResults;
        LinearLayout ll;
        FrameLayout progressBar;
    }
}