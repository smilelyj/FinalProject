package org.swe.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.swe.android.R;
import org.swe.android.activities.DeploySurveyActivity;

import java.util.List;

/**
 * Created by YongjiLi on 4/4/16.
 */
public class DeploySurveyAdapter extends BaseAdapter {

    private Context context;
    List<DeploySurveyActivity.body> items;//the list in the adapter

    public DeploySurveyAdapter(Context context,List<DeploySurveyActivity.body> list){
        this.context = context;
        this.items = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
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
        DeploySurveyActivity.body item = (DeploySurveyActivity.body)getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_preview, null);

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvQtype = (TextView) convertView.findViewById(R.id.tvQuestionType);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(item.question);
        viewHolder.tvQtype.setText(item.questiontype);

        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvQtype;
        ImageView ivCountryLogo;

    }
}