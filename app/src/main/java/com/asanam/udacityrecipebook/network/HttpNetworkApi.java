package com.asanam.udacityrecipebook.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpNetworkApi implements NetworkApi {
    @Override
    public void get(String url, final Callback apiCallback) {

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... downloadUrl) {

                try {
                    URL url = new URL(downloadUrl[0]);//Create Download URl
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                    c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                    c.connect();//connect the URL Connection

                    //If Connection response is not OK then show Logs
                    if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        return null;
                    }

                    InputStream inputStream = c.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream)));
                    StringBuffer buffer = new StringBuffer();
                    String line = null;
                    while((line = br.readLine()) != null) {
                        buffer.append(line);
                    }
                    br.close();

                    return buffer.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String jsonResponse) {
                Log.i(HttpNetworkApi.class.getSimpleName(), "jsonResponse : "+jsonResponse);
                apiCallback.onSuccess(jsonResponse);
            }
        }.execute(url);

        return;
    }
}
