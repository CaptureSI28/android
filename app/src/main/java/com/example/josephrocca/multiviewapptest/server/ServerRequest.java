package com.example.josephrocca.multiviewapptest.server;

import android.util.Log;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Team;

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

public class ServerRequest {


    // TODO Modifier l'adresse du serveur pour mettre l'adresse du serveur
    private static String serverAdresse = "http://10.0.3.2:8888/mobile/index.php";

    public static JSONObject getInfosPartie() {
        JSONObject result = new JSONObject();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", "-1");
        // TODO Pour l'instant on récupère tout à chaque fois
        data.put("service", "infos_partie");
        data.put("infos_equipes", "true");
        data.put("infos_joueur", "true");
        data.put("couleur_zones", "true");
        data.put("equipe_zones", "true");
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
                result = new JSONObject(json);
                Log.d(ServerRequest.class.getSimpleName(), result.toString());

                // Update nombre de joueurs par équipe dans le modèle
                try {
                    JSONArray nbJoueursEquipe = result.getJSONArray("nbJoueursEquipes");
                    Log.d("NbJoueursJSON : ", nbJoueursEquipe.toString());
                    for (int i = 0; i < nbJoueursEquipe.length(); i++) {

                        JSONObject tmp = nbJoueursEquipe.getJSONObject(i);
                        int eq = tmp.getInt("equipe");
                        int nb = tmp.getInt("nbJoueurs");
                        Team t = Control.getInstance().getTeams().get(eq);
                        Log.d("Equipe:", eq+" = "+nb+"pts");
                        t.setNbJoueurs(nb);
                    }
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                }
                // Update score équipes dans le modèle
                try {
                    JSONArray scoreEquipes = result.getJSONArray("scoreEquipes");
                    Log.d("scoreEquipes : ", scoreEquipes.toString());
                    for (int i = 0; i < scoreEquipes.length(); i++) {

                        JSONObject tmp = scoreEquipes.getJSONObject(i);
                        Team t = Control.getInstance().getTeams().get(tmp.getInt("equipe"));
                        t.setNbpts(tmp.getInt("score"));
                    }
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                }
                // Update score joueur dans le modèle
                try {
                    int scoreJoueur = result.getInt("scoreJoueur");
                    Log.d("scoreJoueur : ", String.valueOf(scoreJoueur));
                    Control.getInstance().getUser().setPoints(scoreJoueur);
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
    public static boolean fetchGamesList() {
        boolean succes=false;
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
                String succ = jsonObj.getString("success");
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
                    succes=true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return succes;
    }

    // Connexion au CAS
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
            String tbt = casConnexion.execute(login, password).get();
            Log.d("ServerRequest",tbt);

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

    // Créer une partie
    public static boolean createGame (String name, String dateDebut, String dateFin, String password) {
        boolean success=false;
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("session_id", "-1");
        data.put("service", "createNewGame");
        data.put("name", name);
        data.put("debut", dateDebut);
        data.put("fin", dateFin);
        data.put("password", password);

        Log.d("Création de la partie ",name+" ,debut=" + dateDebut+ " ,fin=" + dateFin+ " ,mdp="+password);

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
                Log.d(ServerRequest.class.getSimpleName(), jsonObj.toString());

                String succ = jsonObj.getString("success");
                Log.d("Success=", succ.toString());
                if (succ != null && succ.contains("YES")) {
                    JSONObject new_game = jsonObj.getJSONObject("new_game");

                    int idGame = new_game.getInt("id_partie");
                    String nameGame = new_game.getString("nom");
                    String datedebGame = new_game.getString("date_debut");
                    String datefinGame = new_game.getString("date_fin");
                    String privateGame = new_game.getString("partie_privee");

                    Game newGame = new Game();
                    newGame.setId(idGame);
                    newGame.setName(nameGame);
                    newGame.setDateDebut(datedebGame);
                    newGame.setDateFin(datefinGame);
                    newGame.setPrivate(privateGame.equals("YES"));

                    Control.getInstance().addGame(newGame.getId(),newGame);
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
        data.put("session_id", "-1");
        data.put("service", "joinGame");
        data.put("game_id", String.valueOf(gameId));
        data.put("password", password);
        data.put("team_id", String.valueOf(teamId));


        Log.d("Joindre la partie ",gameId+" ,password=" + password+ " ,teamId=" + teamId);

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
                json=json.substring(json.indexOf("{"), json.lastIndexOf("}")+1);
                JSONObject jsonObj = new JSONObject(json);
                Log.d(ServerRequest.class.getSimpleName(), jsonObj.toString());
                success = jsonObj.getString("success");
                Log.d("Success=",success);
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
    public static boolean flash(String codeNumber) {

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

            if (success != null && success.contains("YES"))
                return true;
            else
                return false;
        } else
            return false;
    }

}