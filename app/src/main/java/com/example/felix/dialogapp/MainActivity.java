package com.example.felix.dialogapp;

import android.content.Context;
import android.os.AsyncTask;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
implements MyDialogFragment.OnMyDialogConfirmedListener,
DialogExit.OnMyDialogConfirmedListener,
        SignInDialogFragment.OnCredentialEnteredListener {
    private static final String prefs = "userPrefs";
    private static final String FILENAME = "Reminders";

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

        //Reading from file
        FileInputStream fin = null;
        BufferedReader bufferedReader = null;
        StringBuilder temp = new StringBuilder();
        try {
            fin = openFileInput(FILENAME);
            bufferedReader = new BufferedReader(new InputStreamReader(fin));
            String line;
            while( (line = bufferedReader.readLine()) != null){
                temp.append(line).append("\n");
            }
            bufferedReader.close();
            fin.close();
            Toast toast = Toast.makeText(this, "Reminder:\n" + temp.toString(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
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

    @Override
    public void authenticate(String username) {
        if (username.equals("felix")) {
            Toast.makeText(this, "Authenticated, Welcome", Toast.LENGTH_LONG).show();

            //Writing to shared preferences
            SharedPreferences sharedPref = this.getSharedPreferences(prefs, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("userName", username);
            editor.commit();

            sayHello(username);
        } else {
            Toast.makeText(this, "Wrong username", Toast.LENGTH_LONG).show();
        }



    }

    public void signInDialog(View view) {
        //Reading from shared preferences
        SharedPreferences sharedPref = this.getSharedPreferences(prefs, Context.MODE_PRIVATE);
        //Reading the username from the shared preferences
        String savedUserName = sharedPref.getString("userName", null);
        if (savedUserName == null) {
            SignInDialogFragment newFragment = SignInDialogFragment.newInstance();
            newFragment.show(getSupportFragmentManager(), "signInDialog");
        } else {
            sayHello(savedUserName);
        }


    }

    private void sayHello(String userName) {
        Toast toast = Toast.makeText(this, "Welcome " + userName + "!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 10, 10);
        toast.show();
    }

    public void addReminder(View view) {
        //Writing to file
        EditText reminderText = (EditText) findViewById(R.id.reminderText);
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE | Context.MODE_APPEND);
            outputStream.write(reminderText.getText().toString().concat("\n").getBytes());
            outputStream.close();
            Toast.makeText(this, "Reminder added!", Toast.LENGTH_LONG).show();
            reminderText.getText().clear();
        } catch (Exception e) {
            Log.w("WARNING", "Cannot save reminder..");
        }
    }

    public void addThing(View view) {
        final AppDatabase mDb = AppDatabase.getInstance(MainActivity.this);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText attributeText = (EditText) findViewById(R.id.attributeText);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void ... params ) {
                mDb.thingModel().insert(new Thing(nameText.getText().toString(), attributeText.getText().toString()));
                return null;
            }

            @Override
            protected void onPostExecute( final Void result ) {
                nameText.getText().clear();
                attributeText.getText().clear();
            }
        }.execute();

    }

}
