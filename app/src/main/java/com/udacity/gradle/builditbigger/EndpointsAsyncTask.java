package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * AsyncTask subclass for retrieving jokes from a locally-installed GCE endpoint. Uses
 * IdlingResource for testing. Based on template found here:
 *
 * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend
 */
public class EndpointsAsyncTask extends AsyncTask<EndpointsAsyncTask.JokeCallback, Void, String> {
    public static final String ERROR_PREFIX = "***Error: ";
    private SimpleIdlingResource mIdlingResource;

    private static MyApi myApiService = null;
    private JokeCallback mCallback;

    public interface JokeCallback {
        void onTellJoke(String joke);
    }

    public EndpointsAsyncTask(@Nullable SimpleIdlingResource idlingResource) {
        if (idlingResource != null) mIdlingResource = idlingResource;
    }

    @Override
    protected void onPreExecute() {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
    }

    @Override
    protected String doInBackground(JokeCallback... params) {
        if (myApiService == null) {
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

        mCallback = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return ERROR_PREFIX + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (mCallback != null) {
            mCallback.onTellJoke(s);
            if (mIdlingResource != null) {
                mIdlingResource.setIdleState(true);
            }
        }
    }
}
