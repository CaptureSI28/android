package com.example.josephrocca.multiviewapptest;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by josephrocca on 27/04/15.
 */
public class ServerRequest {


    private static String serverAdresse="http://192.168.1.21:8888/server/mobile/index.php";



    public static ArrayList<Game> fetchGamesList(){
        ArrayList<Game> games = new ArrayList<Game>();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", "-1");
        data.put("service", "fetchGamesList");
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
        asyncHttpPost.execute(serverAdresse);

        HttpResponse reponse = null;
        try {
            reponse = asyncHttpPost.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(reponse!=null) {

            String json = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);

                JSONArray gamestab = jsonObj.getJSONArray("games_list");
                for(int i=0; i<gamestab.length(); i++){
                    JSONObject tmp = gamestab.getJSONObject(i);

                    Game g = new Game();
                    g.setId(Integer.parseInt(tmp.getString("id_partie")));
                    g.setName(tmp.getString("nom"));
                    g.setDateDeb(tmp.getString("date_debut"));
                    g.setDateFin(tmp.getString("date_fin"));
                    g.setIsPrivate(tmp.getString("partie_privee").equals("YES"));
                    games.add(g);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return games;
    }



    public static boolean joinGame(Integer gameId, String password, Integer teamId){

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", "-1");
        data.put("service", "joinGame");
        data.put("game_id", String.valueOf(gameId));
        data.put("password", password);
        data.put("team_id", String.valueOf(teamId));

        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
        asyncHttpPost.execute(serverAdresse);

        HttpResponse reponse = null;
        try {
            reponse = asyncHttpPost.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(reponse!=null) {

            String json = null;
            String success = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);
                success = jsonObj.getString("success");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(success!=null && success.equals("YES"))
                return true;
            else
                return false;
        }
        else
            return false;
    }



    public static boolean qrcode(String codeNumber){

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", "-1");
        data.put("service", "flash");
        data.put("qrcode", String.valueOf(codeNumber));

        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
        asyncHttpPost.execute(serverAdresse);

        HttpResponse reponse = null;
        try {
            reponse = asyncHttpPost.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(reponse!=null) {

            String json = null;
            String success = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);
                success = jsonObj.getString("success");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(success!=null && success.equals("YES"))
                return true;
            else
                return false;
        }
        else
            return false;
    }

}
