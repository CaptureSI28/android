package com.example.josephrocca.multiviewapptest.view;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.Zone;
import com.example.josephrocca.multiviewapptest.utils.MyColor;

import java.util.HashMap;

public class FragmentMap extends Fragment {

    static HashMap<Integer, Integer> zoneLayoutId = new HashMap<Integer, Integer>();
    static{
        zoneLayoutId.put(1, R.id.planbfz1);
        zoneLayoutId.put(2, R.id.planbfz2);
        zoneLayoutId.put(3, R.id.planbfz3);
        zoneLayoutId.put(4, R.id.planbfz4);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Ici on simule la récupération de l'étage
        // TODO : le faire vraiment...
        int etage = 0;


        // Pour chaque zone, on allume (ou non) de la couleur corespondante
        ImageView zoneImg;

        for(Integer idxZone : zoneLayoutId.keySet()){
            zoneImg = (ImageView)v.findViewById(zoneLayoutId.get(idxZone));
            int team = Control.getInstance().getZoneByIdx(etage*10+idxZone).getTeam();
            if(team != 0) {
                zoneImg.setColorFilter(MyColor.getTeamColorById(team, false));
                zoneImg.setAlpha(0.5f);
            }
            else{
                zoneImg.setAlpha(0.0f);
            }
        }

        return v;
    }
}