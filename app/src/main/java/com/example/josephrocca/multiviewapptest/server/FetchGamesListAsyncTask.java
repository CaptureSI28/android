package com.example.josephrocca.multiviewapptest.server;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.model.Game;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class FetchGamesListAsyncTask extends AsyncTask<String, Integer, String>{

    AsynkTaskResultsStringListener listener;

    public void setOnResultsListener(AsynkTaskResultsStringListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String succ="";
        ArrayList<Game> games = new ArrayList<Game>();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
        data.put("service", "fetchGamesList");
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
        asyncHttpPost.execute((String)params[0]);

        HttpResponse reponse = null;
        try {
            reponse = asyncHttpPost.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (reponse != null) {

            String json = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);
                Log.d(ServerRequest.class.getSimpleName(), jsonObj.toString());
                succ = jsonObj.getString("success");
                Log.d("Success=", succ.toString());
                if (succ != null && succ.contains("YES")) {
                    JSONArray gamestab = jsonObj.getJSONArray("games_list");
                    for (int i = 0; i < gamestab.length(); i++) {
                        Log.d("Nombre de parties ", String.valueOf(gamestab.length()));
                        JSONObject tmp = gamestab.getJSONObject(i);

                        Game g = new Game();
                        g.setId(Integer.parseInt(tmp.getString("id_partie")));
                        g.setName(tmp.getString("nom"));
                        g.setDateDebut(tmp.getString("date_debut"));
                        g.setDateFin(tmp.getString("date_fin"));
                        g.setPrivate(tmp.getString("partie_privee").equals("YES"));
                        Control.getInstance().addGame(g.getId(), g);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return succ;
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(listener !=null)
            listener.onResultsSucceeded(result);
    }
}
