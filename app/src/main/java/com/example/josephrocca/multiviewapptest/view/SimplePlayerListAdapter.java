package com.example.josephrocca.multiviewapptest.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.ClassementItem;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.utils.MyColor;

import java.util.ArrayList;
import java.util.HashMap;

public class SimplePlayerListAdapter extends ArrayAdapter<Player> {

    private Context context;
    private ArrayList<Player> allplayer;

    private LayoutInflater mInflater;
    private boolean mNotifyOnChange = true;

    public SimplePlayerListAdapter(Context context, ArrayList<Player> p) {
        super(context, R.layout.classement_row);
        this.context = context;
        this.allplayer = p;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allplayer.size();
    }

    @Override
    public Player getItem(int position) {
        return allplayer.get(position);
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
                    convertView = mInflater.inflate(R.layout.simple_player_row,parent, false);
                    holder.playerlogin = (TextView) convertView.findViewById(R.id.player_row_login);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Player player = allplayer.get(position);
        holder.playerlogin.setText(String.valueOf(player.getLogin()));
        holder.playerlogin.setTextColor(MyColor.getTeamColorById(Control.getInstance().getCurrentGame().getTeamIdByLogin(player.getLogin()), false));
        holder.pos = position;
        return convertView;
    }

    public void setList(ArrayList<Player> newplayer){
        allplayer = newplayer;
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

        TextView playerlogin;
        int pos; //to store the position of the item within the list
    }
}