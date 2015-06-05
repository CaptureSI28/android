package com.example.josephrocca.multiviewapptest.server;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AsyncHttpPost extends AsyncTask<String, String, HttpResponse> {
    private HashMap<String, String> mData = null;// post data

    /**
     * constructor
     */
    public AsyncHttpPost(HashMap<String, String> data) {
        mData = data;
    }

    /**
     * background
     */
    @Override
    protected HttpResponse doInBackground(String... params) {

        HttpResponse response = null;
        String str = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(params[0]);// in this case, params[0] is URL
        try {
            // set up post data
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            Iterator<String> it = mData.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                nameValuePair.add(new BasicNameValuePair(key, mData.get(key)));
            }

            post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            response = client.execute(post);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
