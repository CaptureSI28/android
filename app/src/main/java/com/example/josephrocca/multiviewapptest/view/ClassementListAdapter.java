package com.example.josephrocca.multiviewapptest.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.ClassementItem;
import com.example.josephrocca.multiviewapptest.utils.MyColor;

import java.util.HashMap;

/**
 * Created by josephrocca on 22/05/15.
 */
public class ClassementListAdapter extends ArrayAdapter<ClassementItem> {

    private Context context;
    private HashMap<Integer, ClassementItem> allclassement;

    private LayoutInflater mInflater;
    private boolean mNotifyOnChange = true;

    public ClassementListAdapter(Context context, HashMap<Integer, ClassementItem> cla) {
        super(context, R.layout.classement_row);
        this.context = context;
        this.allclassement = cla;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allclassement.size();
    }

    @Override
    public ClassementItem getItem(int position) {
        return allclassement.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*
    @Override
    public int getPosition(ClassementItem item) {
        return allclassement.get(item.getIndice());
    }
    */

    @Override
    public int getViewTypeCount() {
        return 1; //Number of types + 1 !!!!!!!!
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case 1:
                    convertView = mInflater.inflate(R.layout.classement_row,parent, false);
                    holder.indice = (TextView) convertView.findViewById(R.id.cl_row_ind);
                    holder.name = (TextView) convertView.findViewById(R.id.cl_row_name);
                    holder.score = (TextView) convertView.findViewById(R.id.cl_row_score);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ClassementItem it = allclassement.get(position);

        holder.indice.setText(String.valueOf(it.getIndice()));
        holder.indice.setTextColor(MyColor.getTeamColorById(it.getTeam(), false));
        holder.name.setText(it.getName());
        holder.name.setTextColor(MyColor.getTeamColorById(it.getTeam(), false));
        holder.score.setText(String.valueOf(allclassement.get(position).getScore()));
        holder.score.setTextColor(MyColor.getTeamColorById(it.getTeam(), false));
        holder.pos = position;
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }


    //---------------static views for each row-----------//
    static class ViewHolder {

        TextView indice;
        TextView name;
        TextView score;
        int pos; //to store the position of the item within the list
    }
}