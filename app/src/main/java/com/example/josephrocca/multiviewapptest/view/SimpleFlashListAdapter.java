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
import com.example.josephrocca.multiviewapptest.model.Flash;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.utils.MyColor;
import com.example.josephrocca.multiviewapptest.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SimpleFlashListAdapter extends ArrayAdapter<Flash> {

    private Context context;
    private ArrayList<Flash> allflashs;

    private LayoutInflater mInflater;
    private boolean mNotifyOnChange = true;

    public SimpleFlashListAdapter(Context context, ArrayList<Flash> f) {
        super(context, R.layout.flash_row);

        this.context = context;
        this.allflashs = f;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allflashs.size();
    }

    @Override
    public Flash getItem(int position) {
        return allflashs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public int getViewTypeCount() {
        return 1;
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
                    convertView = mInflater.inflate(R.layout.flash_row, parent, false);
                    holder.date = (TextView) convertView.findViewById(R.id.historique_date);
                    holder.player = (TextView) convertView.findViewById(R.id.historique_player);
                    holder.zone = (TextView) convertView.findViewById(R.id.historique_zone);
                    holder.nbpoints = (TextView) convertView.findViewById(R.id.historique_points);
                    holder.pts = (TextView) convertView.findViewById(R.id.pts);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Flash it = allflashs.get(position);
        int teamColor = MyColor.getTeamColorById(Control.getInstance().getCurrentGame().getTeamIdByLogin(it.getPlayer().getLogin()), false);

        holder.player.setText(String.valueOf(it.getPlayer().getLogin()));
        holder.player.setTextColor(teamColor);
        holder.date.setText(Util.getStringFromDate(it.getDate_flash()));
        holder.zone.setText(String.valueOf(it.getZone().getName()));
        holder.nbpoints.setText("+"+String.valueOf(it.getNbpoints()));
        holder.nbpoints.setTextColor(teamColor);
        holder.pts.setTextColor(teamColor);
        holder.pos = position;
        return convertView;
    }

    public void setList(ArrayList<Flash> newflash) {
        allflashs = newflash;
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

        TextView date;
        TextView player;
        TextView zone;
        TextView nbpoints;
        TextView pts;
        int pos; //to store the position of the item within the list
    }
}