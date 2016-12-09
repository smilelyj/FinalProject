package org.swe.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.swe.android.R;
import org.swe.android.activities.DeployedSurveyListActivity;

import java.util.List;

/**
 * Created by YongjiLi on 4/5/16.
 */
public class DeployedSurveyListAdapter extends BaseAdapter {

        private Context context;
        List<DeployedSurveyListActivity.body> items;//the list in the adapter

        public DeployedSurveyListAdapter(Context context,List<DeployedSurveyListActivity.body> list){
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
            DeployedSurveyListActivity.body item = (DeployedSurveyListActivity.body)getItem(position);
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