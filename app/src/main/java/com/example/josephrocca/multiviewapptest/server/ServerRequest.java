package com.example.josephrocca.multiviewapptest.server;

import android.util.Log;
import android.widget.Toast;

import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.server.CasConnexion;
import com.example.josephrocca.multiviewapptest.view.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by josephrocca on 27/04/15.
 */
public class ServerRequest {


    private static String serverAdresse = "http://192.168.56.1:8888/mobile/index.php";
    private static String casAdresse = "https://cas.utc.fr/cas/v1/tickets";

    public static ArrayList<Game> fetchGamesList() {
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

        if (reponse != null) {

            String json = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);
                Log.d(ServerRequest.class.getSimpleName(), jsonObj.toString());

                JSONArray gamestab = jsonObj.getJSONArray("games_list");
                for (int i = 0; i < gamestab.length(); i++) {
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

    public static boolean connectCas(String login, String password) {

        boolean result=false;
        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        HostnameVerifier v = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(v);

        CasConnexion casConnexion = new CasConnexion();
        try {
            String service = "http://localhost";
            String tbt = casConnexion.execute(login, password).get();

            if (tbt.startsWith("TGT")) {
                result=true;

            } else {
                Log.d("Connexion error=", tbt);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return result;
    }

    public static boolean createGame (String name, String dateDebut, String dateFin, String password) {
        // TODO
        return true;
    }

    public static boolean joinGame(Integer gameId, String password, Integer teamId) {

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

        if (reponse != null) {

            String json = null;
            String success = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);
                Log.d(ServerRequest.class.getSimpleName(), jsonObj.toString());

                success = jsonObj.getString("success");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (success != null && success.equals("YES"))
                return true;
            else
                return false;
        } else
            return false;
    }


    public static boolean qrcode(String codeNumber) {

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

        if (reponse != null) {

            String json = null;
            String success = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);
                Log.d(ServerRequest.class.getSimpleName(), jsonObj.toString());

                success = jsonObj.getString("success");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (success != null && success.equals("YES"))
                return true;
            else
                return false;
        } else
            return false;
    }

}
