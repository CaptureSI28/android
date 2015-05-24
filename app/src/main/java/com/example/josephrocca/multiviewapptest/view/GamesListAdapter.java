package com.example.josephrocca.multiviewapptest.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.ClassementItem;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.utils.MyColor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by josephrocca on 22/05/15.
 */
public class GamesListAdapter extends ArrayAdapter<Game> {

    private Context context;
    private ArrayList<Game> allgames;

    private LayoutInflater mInflater;
    private boolean mNotifyOnChange = true;

    public GamesListAdapter(Context context, ArrayList<Game> g) {
        super(context, R.layout.fetchgame_row);
        this.context = context;
        this.allgames = g;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allgames.size();
    }

    @Override
    public Game getItem(int position) {
        return allgames.get(position);
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
                    convertView = mInflater.inflate(R.layout.fetchgame_row,parent, false);
                    holder.title = (TextView) convertView.findViewById(R.id.games_row_title);
                    holder.nbp = (TextView) convertView.findViewById(R.id.games_row_nbp);
                    holder.lockic = (ImageView) convertView.findViewById(R.id.games_row_lockic);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Game it = allgames.get(position);

        holder.title.setText(it.getName());
        holder.nbp.setText(String.valueOf(it.getPlayers().size()));
        if(!it.getIsPrivate()) {
            holder.lockic.setVisibility(View.INVISIBLE);
        }
        holder.pos = position;
        return convertView;
    }

    public void setList(ArrayList<Game> newclassement){
        allgames = newclassement;
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

        TextView title;
        TextView nbp;
        ImageView lockic;
        int pos; //to store the position of the item within the list
    }
}