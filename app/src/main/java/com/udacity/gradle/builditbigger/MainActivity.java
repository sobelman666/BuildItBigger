package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sobelman.and.jokedisplay.JokeDisplayActivity;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.JokeCallback {

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * IdlingResource accessor. Only used for testing AsyncTask joke retrieval.
     *
     * @return a SimpleIdling resource if in test configuration, null otherwise.
     */
    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        SimpleIdlingResource idlingResource = getIdlingResource();
        new EndpointsAsyncTask(idlingResource).execute(this);
    }

    /**
     * Implementaion of JokeCallback interface method. Called by AsyncTask after joke retrieval.
     *
     * @param joke the joke retrieved from the GCE endpoint.
     */
    @Override
    public void onTellJoke(String joke) {
        // start joke display activity, passing joke as an intent extra
        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.EXTRA_JOKE, joke);
        startActivity(intent);
    }
}
