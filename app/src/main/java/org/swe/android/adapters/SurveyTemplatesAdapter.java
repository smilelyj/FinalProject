package org.swe.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.swe.android.R;
import org.swe.android.activities.ImportSurveyActivity;

import java.util.List;

/**
 * Created by YongjiLi on 2/1/16.
 */
public class SurveyTemplatesAdapter extends BaseAdapter{

    private Context context;
    List<ImportSurveyActivity.tempBody> items;//the list in the adapter


    public SurveyTemplatesAdapter(Context context,List<ImportSurveyActivity.tempBody> list){
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
        ImportSurveyActivity.tempBody item = (ImportSurveyActivity.tempBody)getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_template, null);
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.text_templateId);
            viewHolder.tvIntro = (TextView)convertView.findViewById(R.id.text_templateIntroduction);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvId.setText(item.templateId);
        viewHolder.tvIntro.setText(item.templateIntro);
        return convertView;
    }

    class ViewHolder {
        TextView tvId;
        TextView tvIntro;
    }
}