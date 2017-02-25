package com.example.jokeactivityandlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TellJokeActivity extends AppCompatActivity {

    public static final String KEY_JOKE = "JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String joke = getIntent().getStringExtra(KEY_JOKE);
        setContentView(R.layout.activity_tell_joke);
        TextView textView = (TextView)findViewById(R.id.textview_joke);
        textView.setText(joke);
    }
}
