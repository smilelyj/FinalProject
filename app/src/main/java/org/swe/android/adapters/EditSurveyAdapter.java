package org.swe.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.swe.android.R;
import org.swe.android.activities.EditSurveyActivity.body;

import java.util.List;


/**
 * Created by YongjiLi on 11/3/15.
 */

public class EditSurveyAdapter extends BaseAdapter {

    private Context context;
    List<body> items;//the list in the adapter

    public EditSurveyAdapter(Context context,List<body> list){
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


    public void removeItem(int arg0) {//delete the item in the listview
        items.remove(arg0);
        this.notifyDataSetChanged();
    }
    public void notRemoveItem(int arg0) {//delete the item in the listview
       // items.remove(arg0);
        this.notifyDataSetChanged();
    }

    //@Override
    public void remove(final int arg0) {
        items.remove(arg0);
        this.notifyDataSetChanged();
        /*Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(R.string.button_postive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg0) {
                // do some custom delete code (e.g delete datamodel)
               removeItem(arg0+1);
            }

        });

        builder.setNegativeButton(R.string.button_negative_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // call notifyDataSetChanged() on your adapter otherwise it will be gone!
                notRemoveItem(arg0);
            }
        });
        builder.setTitle(R.string.simple_dialog_delete_a_question);
        builder.setMessage(context.getString(R.string.simple_dialog_delete_a_question));
        builder.show(); */
    }

    public void insert(body item, int arg0) {//delete the item in the listview
        items.add(arg0, item);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        body item = (body)getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_preview, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvQtype = (TextView) convertView.findViewById(R.id.tvQuestionType);
            //viewHolder.ivCountryLogo = (ImageView) convertView.findViewById(R.id.ivCountryLogo);
            viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.click_remove_id);
            viewHolder.ivDragHandle = (ImageView) convertView.findViewById(R.id.drag_handle_id);
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
        ImageView ivDelete;
        ImageView ivDragHandle;
    }
}