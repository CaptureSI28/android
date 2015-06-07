package com.example.josephrocca.multiviewapptest.server;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.ClassementItem;
import com.example.josephrocca.multiviewapptest.model.Flash;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.model.Team;
import com.example.josephrocca.multiviewapptest.model.Zone;
import com.example.josephrocca.multiviewapptest.utils.Util;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import android.os.StrictMode;

public class ServerRequest {


    private static String serverAdresse = "http://si28.riccioli.fr/mobile/";
    // private static String serverAdresse = "http://192.168.1.43:8888/server/mobile/";
    // private static String serverAdresse = "http://10.0.3.2:8888/mobile/";

    public static JSONObject getInfosPartie() {
        JSONObject result = new JSONObject();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
        data.put("service", "infos_partie");
        data.put("game_id", String.valueOf(Control.getInstance().getCurrentGame().getId()));
        data.put("infos_equipes", "true");
        data.put("infos_joueur", "true");
        data.put("couleur_zones", "true");
        data.put("equipe_zones", "true");
        data.put("historique_flashs", "true");
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

                Game currentGame = Control.getInstance().getCurrentGame();

                json = EntityUtils.toString(reponse.getEntity());
                result = new JSONObject(json);
                // Update joueurs dans le modèle
                try {
                    JSONArray players = result.getJSONArray("listeJoueursPartie");
                    for (int j = 0; j < players.length(); j++) {
                        JSONObject playerJson = players.getJSONObject(j);
                        String pseudo = playerJson.getString("login");
                        int teamId = Integer.valueOf(playerJson.getString("equipe"));

                        Control.getInstance().addPlayer(new Player(pseudo, 0, teamId, ""));
                        currentGame.addPlayer(Control.getInstance().getPlayerByLogin(pseudo), teamId);
                    }
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                }
                // Update score équipes dans le modèle
                try {
                    JSONArray scoreEquipes = result.getJSONArray("scoreEquipes");
                    for (int i = 0; i < scoreEquipes.length(); i++) {

                        JSONObject tmp = scoreEquipes.getJSONObject(i);
                        Team t = currentGame.getTeams().get(tmp.getInt("equipe"));
                        t.setNbpts(tmp.getInt("score"));
                    }
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                }
                // Update score joueur dans le modèle
                try {
                    int scoreJoueur = result.getInt("scoreJoueur");
                    Control.getInstance().getUser().setPoints(scoreJoueur);
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                }
                // Update zones dans le modèle
                try {
                    JSONArray equipesZones = result.getJSONArray("equipesZones");
                    for (int i = 0; i < equipesZones.length(); i++) {

                        JSONObject tmp = equipesZones.getJSONObject(i);
                        int eq = tmp.getInt("equipe");
                        int zo = tmp.getInt("zone");
                        Zone modifZone = Control.getInstance().getZoneByIdx(zo);
                        if (modifZone != null)
                            modifZone.setTeam(eq);

                    }
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                }
                // Update historique dans le modèle
                try {
                    JSONArray historique = result.getJSONArray("historique");
                    ArrayList<Flash> f = new ArrayList<Flash>();
                    for (int i = 0; i < historique.length(); i++) {

                        JSONObject tmp = historique.getJSONObject(i);
                        String dateString = tmp.getString("date_flash");
                        int equipe = tmp.getInt("equipe");
                        int zone = tmp.getInt("zone");
                        String login = tmp.getString("login");
                        int nbpoints = tmp.getInt("nbpoints");
                        Date date = Util.getDateFromString(dateString);
                        f.add(new Flash(date, Control.getInstance().getPlayerByLogin(login), Control.getInstance().getZoneByIdx(zone), nbpoints));
                    }
                    Control.getInstance().getCurrentGame().setFlashs(f);
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Récupère la liste des parties en cours
    public static boolean fetchZonesList() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        boolean succes = false;

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
        data.put("service", "fetchZone");
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
                String succ = jsonObj.getString("success");
                if (succ != null && succ.contains("YES")) {
                    JSONArray zonesTab = jsonObj.getJSONArray("zones_list");
                    for (int i = 0; i < zonesTab.length(); i++) {
                        JSONObject tmp = zonesTab.getJSONObject(i);

                        int id = tmp.getInt("id_zone");
                        String name = tmp.getString("nom_zone");
                        Control.getInstance().addZone(new Zone(id, name));
                    }
                    succes = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return succes;
    }

    public static int getTeamIdByPlayer(int id_equipe, String login_joueur) {
        int equipe = 0;
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
        data.put("service", "equipe_joueur");
        data.put("login_joueur", String.valueOf(login_joueur));
        data.put("id_equipe", String.valueOf(id_equipe));
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


        JSONObject result = new JSONObject();
        if (reponse != null) {
            String json = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);
                String equipeString = jsonObj.getString("equipe");
                if (equipeString != null)
                    equipe = Integer.valueOf(equipeString);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return equipe;
    }

    // Récupère la liste des parties en cours
    public static boolean fetchGamesList() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        boolean succes = false;
        ArrayList<Game> games = new ArrayList<Game>();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
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
                String succ = jsonObj.getString("success");
                if (succ != null && succ.contains("YES")) {
                    JSONArray gamestab = jsonObj.getJSONArray("games_list");
                    for (int i = 0; i < gamestab.length(); i++) {
                        JSONObject tmp = gamestab.getJSONObject(i);

                        Game g = new Game();
                        g.setId(Integer.parseInt(tmp.getString("id_partie")));
                        g.setName(tmp.getString("nom"));
                        g.setDateDebut(Util.getDateFromString(tmp.getString("date_debut")));
                        g.setDateFin(Util.getDateFromString(tmp.getString("date_fin")));
                        g.setPrivate(tmp.getString("partie_privee").equals("YES"));
                        g.setCreator(tmp.getString("createur"));

                        // Get liste players
                        JSONArray players = tmp.getJSONArray("players");
                        for (int j = 0; j < players.length(); j++) {
                            JSONObject playerJson = players.getJSONObject(j);
                            String pseudo = playerJson.getString("login");
                            int teamId = Integer.valueOf(playerJson.getString("equipe"));

                            Control.getInstance().addPlayer(new Player(pseudo, 0, teamId, ""));
                            g.addPlayer(Control.getInstance().getPlayerByLogin(pseudo), teamId);
                        }

                        Control.getInstance().addGame(g.getId(), g);
                    }
                    succes = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return succes;
    }

    private static String getTicket(String tbt, String service, Context c) {
        try {
            RequestTicket rt = new RequestTicket(c);
            return rt.execute(tbt, service).get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static String loginCas(String ticket, String service, String cas_service, Context c) {
        try {
            LoginCas lc = new LoginCas();
            return lc.execute(c.getResources().getString(R.string.url), ticket, service, cas_service).get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    // Connexion au CAS
    public static boolean connectCas(String login, String password, Context c) {

        boolean result = false;
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
            String tbt = casConnexion.execute(login, password).get();

            if (tbt != null && tbt.startsWith("TGT")) {
                String ticket = getTicket(tbt, c.getResources().getString(R.string.cas_service), c);
                if (ticket != null && ticket.startsWith("ST")) {
                    String res = loginCas(ticket, c.getResources().getString(R.string.service), c.getResources().getString(R.string.cas_service), c);
                    if (res != null) {
                        String session_id;
                        try {
                            JSONObject resJson = new JSONObject(res);
                            session_id = resJson.getString("session_id");
                            // TODO Calculer le nb de points du joueurs
                            Control.getInstance().setUser(new Player(login, 0, 1, session_id));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        result = true; // success
                    } else {
                        Log.d("Connexion error login=", "");
                    }
                } else {
                    Log.d("Connexion error ticket=", "");
                }
            } else {
                Log.d("Connexion error=", "Error Connexion");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return result;
    }

    // Créer une partie
    public static boolean createGame(String name, String dateDebut, String dateFin, String password) {
        boolean success = false;
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
        data.put("service", "createNewGame");
        data.put("name", name);
        data.put("debut", dateDebut);
        data.put("fin", dateFin);
        data.put("password", password);

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

            // TODO Catcher l'erreur en retour pour afficher un message d'erreur
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);

                String succ = jsonObj.getString("success");
                if (succ != null && succ.contains("YES")) {
                    JSONObject new_game = jsonObj.getJSONObject("new_game");

                    int idGame = new_game.getInt("id_partie");
                    String nameGame = new_game.getString("nom");
                    Date datedebGame = Util.getDateFromString(new_game.getString("date_debut"));
                    Date datefinGame = Util.getDateFromString(new_game.getString("date_fin"));
                    String privateGame = new_game.getString("partie_privee");

                    Game newGame = new Game();
                    newGame.setId(idGame);
                    newGame.setName(nameGame);
                    newGame.setDateDebut(datedebGame);
                    newGame.setDateFin(datefinGame);
                    newGame.setPrivate(privateGame.equals("YES"));
                    newGame.setCreator(Control.getInstance().getUser().getLogin());

                    Control.getInstance().addGame(newGame.getId(), newGame);
                    Control.getInstance().setCurrentGame(newGame.getId());
                    success = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    // Rejoindre une partie
    public static boolean joinGame(Integer gameId, String password, Integer teamId) {

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
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
                json = json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1);
                JSONObject jsonObj = new JSONObject(json);
                success = jsonObj.getString("success");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (success != null && success.contains("YES"))
                return true;
            else
                return false;
        } else
            return false;
    }


    // Flasher un qrcode
    public static String flash(String codeNumber) {
        String result = "fail";

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
        data.put("service", "flash");
        data.put("qrcode", String.valueOf(codeNumber));
        data.put("id_game", String.valueOf(Control.getInstance().getCurrentGame().getId()));

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
            String bonus = null;
            try {
                json = EntityUtils.toString(reponse.getEntity());
                JSONObject jsonObj = new JSONObject(json);
                success = jsonObj.getString("success");
                bonus = jsonObj.getString("bonus");
                if (bonus != null && bonus.contains("YES"))
                    result = "bonus";
                else if (success != null && success.contains("YES"))
                    result = "success";
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    // Recuperation de classements
    public static HashMap<Integer, ClassementItem> getClassement(String typC) {

        HashMap<Integer, ClassementItem> classement = new HashMap<Integer, ClassementItem>();
        ArrayList<ClassementItem> classementTemp = new ArrayList<ClassementItem>();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", Control.getInstance().getUser().getSession_id());
        data.put("service", "classements");
        data.put("classement", typC);
        data.put("game_id", String.valueOf(Control.getInstance().getCurrentGame().getId()));
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
                String succ = jsonObj.getString("success");
                Log.d("Success=", succ.toString());
                if (succ != null && succ.contains("true")) {

                    Object js = jsonObj.get("classement");

                    for (int i = 0; i < Control.getInstance().getCurrentGame().getPlayersList().size(); i++) {

                        JSONObject tmp;
                        if (js instanceof JSONArray)
                            tmp = jsonObj.getJSONArray("classement").getJSONObject(i);
                        else
                            tmp = jsonObj.getJSONObject("classement").getJSONObject(String.valueOf(i));

                        int place = tmp.getInt("place");
                        int score = tmp.getInt("score");
                        int team = tmp.getInt("team");
                        String name = tmp.getString("login");

                        classementTemp.add(new ClassementItem(i, name, team, score));
                    }
                    Collections.sort(classementTemp);
                    for (int i = 0; i < classementTemp.size(); i++) {
                        classementTemp.get(i).setIndice(i + 1);
                        classement.put(i + 1, classementTemp.get(i));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return classement;
    }

}
