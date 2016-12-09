package org.swe.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.swe.android.R;
import org.swe.android.activities.SurveyPoolActivity;

import java.util.List;

/**
 * Created by YongjiLi on 4/2/16.
 */
public class SurveyPoolAdapter extends BaseAdapter {

    private Context context;
    List<SurveyPoolActivity.body> items;//the list in the adapter

    public SurveyPoolAdapter(Context context,List<SurveyPoolActivity.body> list){
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
        SurveyPoolActivity.body item = (SurveyPoolActivity.body)getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_done_item, null);
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.listView_item_surveyId);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvId.setText(item.surveyId);
        return convertView;
    }

    class ViewHolder {
        TextView tvId;
    }
}