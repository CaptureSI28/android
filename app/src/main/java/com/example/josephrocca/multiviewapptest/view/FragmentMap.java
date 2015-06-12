package com.example.josephrocca.multiviewapptest.view;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.Zone;
import com.example.josephrocca.multiviewapptest.server.BitmapWorkerTask;
import com.example.josephrocca.multiviewapptest.utils.MyColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FragmentMap extends Fragment {

    private static String spinnerTxt0 = "Etage 0";
    private static String spinnerTxt1 = "Etage 1";
    private static String spinnerTxt2 = "Etage 2";
    private static String spinnerTxt3 = "Etage 3";
    private static String spinnerTxt4 = "Etage 4";
    private static String spinnerTxt5 = "Etage 5";
    private static String spinnerTxt6 = "Etage 6";

    static HashMap<Integer, Integer> zoneImageId = new HashMap<Integer, Integer>();
    static HashMap<Integer, Integer> zoneLayoutId = new HashMap<Integer, Integer>();
    static HashMap<Integer, ArrayList<Integer>> disablezones = new HashMap<Integer, ArrayList<Integer>>();
    static{

        zoneImageId.put(1, R.drawable.planbfz1);
        zoneImageId.put(2, R.drawable.planbfz2);
        zoneImageId.put(3, R.drawable.planbfz3);
        zoneImageId.put(4, R.drawable.planbfz4);
        zoneImageId.put(5, R.drawable.planbfz5);
        zoneImageId.put(6, R.drawable.planbfz6);
        zoneImageId.put(7, R.drawable.planbfz7);

        zoneLayoutId.put(1, R.id.planbfz1);
        zoneLayoutId.put(2, R.id.planbfz2);
        zoneLayoutId.put(3, R.id.planbfz3);
        zoneLayoutId.put(4, R.id.planbfz4);
        zoneLayoutId.put(5, R.id.planbfz5);
        zoneLayoutId.put(6, R.id.planbfz6);
        zoneLayoutId.put(7, R.id.planbfz7);

        disablezones.put(0, new ArrayList<Integer>(Arrays.asList(1,2,3,4)));
        disablezones.put(3, new ArrayList<Integer>(Arrays.asList(2,3,4)));
        disablezones.put(4, new ArrayList<Integer>(Arrays.asList(2,3,4)));
        disablezones.put(5, new ArrayList<Integer>(Arrays.asList(2,3,4,5)));
        disablezones.put(6, new ArrayList<Integer>(Arrays.asList(2,3,4,5)));
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Spinner etage
        Spinner selecteuretage = (Spinner) v.findViewById(R.id.map_spinner);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add(spinnerTxt0);
        spinnerArray.add(spinnerTxt1);
        spinnerArray.add(spinnerTxt2);
        spinnerArray.add(spinnerTxt3);
        spinnerArray.add(spinnerTxt4);
        spinnerArray.add(spinnerTxt5);
        spinnerArray.add(spinnerTxt6);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) v.findViewById(R.id.map_spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                int etg = Integer.parseInt(item.substring(item.length() - 1, item.length()));
                colorEtage(v, etg);
                disableEtage(v, etg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        colorEtage(v, 0);
        disableEtage(v, 0);

        return v;
    }

    private void colorEtage(View v, int etage){
        // Pour chaque zone, on allume (ou non) de la couleur corespondante
        ImageView zoneImg;

        for(Integer idxZone : zoneLayoutId.keySet()){
            zoneImg = (ImageView)v.findViewById(zoneLayoutId.get(idxZone));
            loadBitmap(zoneImageId.get(idxZone), zoneImg);
            Zone z = Control.getInstance().getZoneByIdx(etage*10+idxZone);
            if(z!=null) {
                int team = z.getTeam();
                if (team != 0) {
                    zoneImg.setColorFilter(MyColor.getTeamColorById(team, false));
                    zoneImg.setAlpha(0.5f);
                } else {
                    zoneImg.setAlpha(0.0f);
                }
            }
            else{
                zoneImg.setAlpha(0.0f);
            }
        }
    }

    private void disableEtage(View v, int etage){
        // Pour chaque zone, on grise les zones inexistantes
        ImageView zoneImg;

        if(disablezones.get(etage)!=null) {
            for(Integer idxZone : disablezones.get(etage)) {
                zoneImg = (ImageView)v.findViewById(zoneLayoutId.get(idxZone));
                zoneImg.setColorFilter(MyColor.white());
                zoneImg.setAlpha(0.75f);
            }
        }
    }

    public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resId);
    }
}