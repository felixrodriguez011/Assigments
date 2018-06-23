package com.example.felix.dialogapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
implements MyDialogFragment.OnMyDialogConfirmedListener,
DialogExit.OnMyDialogConfirmedListener,
        SignInDialogFragment.OnCredentialEnteredListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = new Intent(this, MyService.class);
        startService(intent);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        DialogExit newFragment = DialogExit.newInstance();
        newFragment.show(getSupportFragmentManager(), "ExitApp");


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

    @Override
    public void onDialogConfirmed() {
        Toast.makeText(this, "App Closed", Toast.LENGTH_LONG).show();
        finish();
    }

    public void showDialog(View view) {
        MyDialogFragment newFragment = MyDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "confirmDeleteArtPiece");
    }

    public void signInDialog(View view) {
        SignInDialogFragment newFragment = SignInDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "signInDialog");
    }

    @Override
    public void authenticate(String username) {
        if(username.equals("felix")){
            Toast.makeText(this, "Authenticated, Welcome", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Wrong username", Toast.LENGTH_LONG).show();
        }

    }
}
