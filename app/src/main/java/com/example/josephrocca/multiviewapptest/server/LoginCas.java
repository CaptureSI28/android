package com.example.josephrocca.multiviewapptest.server;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginCas extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = PermanentHttpClient.getInstance().getNewHttpClient();

        HttpPost httppost = new HttpPost(
                params[0]);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("cas_ticket", params[1]
                .toString()));
        nameValuePairs.add(new BasicNameValuePair("service", params[2]
                .toString()));
        nameValuePairs.add(new BasicNameValuePair("cas_service", params[3]
                .toString()));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response;
            response = httpclient.execute(httppost);
            PermanentHttpClient.getInstance().setCookie(response.getHeaders("Cookie"));
            String rep = EntityUtils.toString(response.getEntity());
            Log.d("rep", rep);
            return rep;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }


}
