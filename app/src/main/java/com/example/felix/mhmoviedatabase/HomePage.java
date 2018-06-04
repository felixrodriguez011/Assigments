package com.example.felix.mhmoviedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    ImageButton Movie1;
    ImageButton Movie2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Movie1 = (ImageButton) findViewById(R.id.Movie1);
        Movie2 = (ImageButton) findViewById(R.id.Movie2);


        Movie1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V)
            {
                Intent intentLoadNewActivity = new Intent(HomePage.this, MovieDetails.class);
                intentLoadNewActivity.putExtra("MovieTitle", "DeadPool");
                startActivity(intentLoadNewActivity);
            }
        });

        Movie2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V)
            {
                Intent intentLoadNewActivity = new Intent(HomePage.this, MovieDetails.class);
                intentLoadNewActivity.putExtra("MovieTitle", "X-Men");
                startActivity(intentLoadNewActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
}
