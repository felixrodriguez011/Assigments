package com.example.felix.mhmoviedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView title = findViewById(R.id.MovieTitle);
        title.setText( getIntent().getStringExtra("MovieTitle"));
    }
}
