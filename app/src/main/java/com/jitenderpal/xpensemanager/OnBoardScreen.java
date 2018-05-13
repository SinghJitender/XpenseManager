package com.jitenderpal.xpensemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import Helpers.StaticData;

public class OnBoardScreen extends AppCompatActivity {
Button submit ;
EditText name ;
Animation fadeInAnimation;
SharedPreferences.Editor editor;
LinearLayout lowerLayout;
SharedPreferences prefs;
SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      try {
          prefs = getSharedPreferences(StaticData.SHARED_PREF, MODE_PRIVATE);
          if (prefs.getInt("flag", 0) == 1) {
              Intent i = new Intent(OnBoardScreen.this, HomeScreen.class);
              startActivity(i);
              finish(); }
          else {
              setContentView(R.layout.activity_on_board_screen);
              fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_move_up);
              lowerLayout = (LinearLayout) findViewById(R.id.lowerlayout);
              getSupportActionBar().hide();

              //Setting up database for the first time
              try {
                  mydatabase = openOrCreateDatabase(StaticData.DATABASE_NAME, MODE_PRIVATE, null);
                  mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS " + StaticData.MAIN_TABLE_NAME +
                          " ( id INTEGER PRIMARY KEY NOT NULL, title TEXT, amount REAL, description TEXT, tags TEXT," +
                          " datetoday INTEGER, monthtoday INTEGER, yeartoday INTEGER, daytoday TEXT, time TEXT, date INTEGER," +
                          " month INTEGER, year INTEGER,day TEXT );");

                  mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS " + StaticData.TITLE_TABLE +
                          " ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,expenseid INTEGER, title TEXT );");

                  mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS " + StaticData.TAGS_TABLE +
                          " ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,expenseid INTEGER, tags TEXT );");

                  mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS " + StaticData.BILLS_TABLE +
                          " ( id INTEGER PRIMARY KEY NOT NULL,expenseid INTEGER, billlocation TEXT );");

              }catch (Exception e)
              {
                  Log.d("J:Database",e+"");
                  Toast.makeText(getApplicationContext(),"Error while setting up database.",Toast.LENGTH_LONG).show();
              }

              submit = (Button) findViewById(R.id.button);
              name = (EditText) findViewById(R.id.editText);
              //submit.startAnimation(fadeInAnimation);
              lowerLayout.startAnimation(fadeInAnimation);
              submit.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (!name.getText().toString().equals(null) && !name.getText().toString().equals("")) {
                          editor = getSharedPreferences(StaticData.SHARED_PREF, MODE_PRIVATE).edit();
                          editor.putString("name", name.getText().toString());
                          editor.putInt("flag", 1);
                          editor.putInt("id", 1);
                          editor.apply();
                          Intent i = new Intent(OnBoardScreen.this, HomeScreen.class);
                          startActivity(i);
                          finish();
                      } else {
                          Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_SHORT).show();
                      }
                  }
              });
          }  //end of else
      }
      catch (Exception e)
      {Toast.makeText(getApplicationContext(),"Unable to open app.",Toast.LENGTH_LONG).show(); }
    }
}
