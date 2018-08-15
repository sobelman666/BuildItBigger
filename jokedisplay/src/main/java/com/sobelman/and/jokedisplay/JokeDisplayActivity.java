package com.sobelman.and.jokedisplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * An activity for displaying jokes in a TextView. Displays a joke provided in an intent extra
 * or an error message if no joke was provided.
 */
public class JokeDisplayActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "com.sobelman.and.jokedisplay.extraJoke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        TextView jokeTextView = findViewById(R.id.tv_joke);

        Intent callingIntent = getIntent();
        if (callingIntent != null && callingIntent.hasExtra(EXTRA_JOKE)) {
            String joke = callingIntent.getStringExtra(EXTRA_JOKE);
            jokeTextView.setText(joke);
        } else {
            jokeTextView.setText(R.string.msg_no_joke);
        }
    }
}
