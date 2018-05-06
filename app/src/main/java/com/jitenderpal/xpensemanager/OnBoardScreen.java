package com.jitenderpal.xpensemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OnBoardScreen extends AppCompatActivity {
Button submit ;
EditText name ;
Animation fadeInAnimation;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("xpensemanager", MODE_PRIVATE);
        /*if (prefs.getInt("flag", 0) == 1) {
            Intent i = new Intent(OnBoardScreen.this, HomeScreen.class);
            startActivity(i);
            finish();
        } else {*/
            setContentView(R.layout.activity_on_board_screen);
            fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);

            //getSupportActionBar().hide();
            editor = getSharedPreferences("xpensemanager", MODE_PRIVATE).edit();
            submit = (Button) findViewById(R.id.button);
            name = (EditText) findViewById(R.id.editText);
            submit.startAnimation(fadeInAnimation);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!name.getText().toString().equals(null) && !name.getText().toString().equals("")) {
                        editor.putString("name", name.getText().toString());
                        editor.putInt("flag", 1);
                        editor.apply();
                        Intent i = new Intent(OnBoardScreen.this, HomeScreen.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
       // }  //end of else
    }
}
