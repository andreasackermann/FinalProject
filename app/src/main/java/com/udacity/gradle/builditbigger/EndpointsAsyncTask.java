package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.example.JokeSupplier;
import com.example.andreas.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;



public class EndpointsAsyncTask extends AsyncTask<EndpointsAsyncTask.JokeCallback, Void, String> {
    private static MyApi myApiService = null;
    private JokeCallback jokeCallback;

    @Override
    protected String doInBackground(JokeCallback... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        jokeCallback = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
//            return e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        jokeCallback.deliverJoke(result);
    }

    public interface JokeCallback {

        public void deliverJoke(String joke);

    }
}