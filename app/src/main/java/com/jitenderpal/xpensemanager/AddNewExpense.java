package com.jitenderpal.xpensemanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Helpers.StaticData;

public class AddNewExpense extends AppCompatActivity {
AutoCompleteTextView title,tags;
EditText amount,description;
Button save;
Calendar myCalendar;
TextView dateView,error;
DatePickerDialog.OnDateSetListener date;
Animation shake;
SharedPreferences.Editor editor;
SharedPreferences prefs;
SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mydatabase = openOrCreateDatabase(StaticData.DATABASE_NAME, MODE_PRIVATE, null);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        title = (AutoCompleteTextView) findViewById(R.id.title);
        amount = (EditText) findViewById(R.id.amount);
        description = (EditText) findViewById(R.id.description);
        tags = (AutoCompleteTextView) findViewById(R.id.tags);
        save = (Button) findViewById(R.id.save);
        dateView = (TextView) findViewById(R.id.date);
        error = (TextView) findViewById(R.id.error);
        myCalendar = Calendar.getInstance();

        tags.setAdapter(getTags(this,mydatabase));
        title.setAdapter(getTitles(this,mydatabase));
        dateView.setText(getDate());

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                dateView.setText(sdf.format(myCalendar.getTime()));
            }

        };
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewExpense.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(title.getText().toString().equals("")||title.getText().toString().equals(null))
                {   error.setText("Title is mandatory");
                    error.startAnimation(shake);
                }
               else if(amount.getText().toString().equals("")||amount.getText().toString().equals(null)){
                    error.setText("Amount is mandatory");
                    error.startAnimation(shake);
                }
               else {
                    String splitTags[] = tags.getText().toString().split(",");
                    String finaldate[] = dateView.getText().toString().split("/");
                    prefs = getSharedPreferences(StaticData.SHARED_PREF, MODE_PRIVATE);
                    int id = prefs.getInt("id", 0);
                    String currentDate[] = getDate().split("/");
                    mydatabase = openOrCreateDatabase(StaticData.DATABASE_NAME, MODE_PRIVATE, null);
                    mydatabase.execSQL("INSERT INTO "+StaticData.MAIN_TABLE_NAME+" (id,title,amount,description,tags,datetoday,monthtoday,yeartoday,daytoday,time,date,month,year,day)" +
                            " VALUES('"+id+"','" + title.getText().toString().toUpperCase()  + "','" + Float.parseFloat(amount.getText().toString()) + "','" +
                              description.getText().toString() + "','" + tags.getText().toString() + "','" + Integer.parseInt(currentDate[0]) + "','" + Integer.parseInt(currentDate[1]) +
                            "','"+Integer.parseInt(currentDate[2])+ "','" +getDayOfWeek(getDate())+ "','" +getTime()+ "','" +Integer.parseInt(finaldate[0])+ "','" +
                            Integer.parseInt(finaldate[1])+ "','" + Integer.parseInt(finaldate[2])+ "','" +getDayOfWeek( dateView.getText().toString())+"');");

                    for(int i =0;i<splitTags.length;i++)
                    { mydatabase.execSQL("INSERT INTO "+StaticData.TAGS_TABLE+" (expenseid,tags) VALUES('"+id+"','" + splitTags[i].toUpperCase() +"');"); }

                    mydatabase.execSQL("INSERT INTO "+StaticData.TITLE_TABLE+" (expenseid,title) VALUES('"+id+"','" + title.getText().toString().toUpperCase() +"');");

                    editor = getSharedPreferences(StaticData.SHARED_PREF, MODE_PRIVATE).edit();
                    editor.putInt("id", id + 1);
                    editor.apply();

                    Toast.makeText(getApplicationContext(),title.getText().toString()+ " Added.",Toast.LENGTH_SHORT).show();

                    title.setText("");
                    amount.setText("");
                    description.setText("");
                    tags.setText("");
                    tags.setAdapter(getTags(getApplicationContext(),mydatabase));
                    title.setAdapter(getTitles(getApplicationContext(),mydatabase));

                }
            }
        });

    } // end of class

    private ArrayAdapter<String> getTitles(Context context,SQLiteDatabase mydatabase) {
        String titles[] ;
        Cursor resultSet = mydatabase.rawQuery("Select DISTINCT(title) from  "+StaticData.TITLE_TABLE, null);
        int rows = resultSet.getCount();
        titles= new String[rows];
        int i=0;
        while(resultSet.moveToNext())
        {
            titles[i]=resultSet.getString(0);
            Log.d("title",titles[i]);
            i++;
        }
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, titles);
    }

    private ArrayAdapter<String> getTags(Context context,SQLiteDatabase mydatabase) {
        String titles[] ;
        Cursor resultSet = mydatabase.rawQuery("Select DISTINCT(tags) from  "+StaticData.TAGS_TABLE, null);
        int rows = resultSet.getCount();
        titles= new String[rows];
        int i=0;
        while(resultSet.moveToNext())
        {
            titles[i]=resultSet.getString(0);
            Log.d("tags",titles[i]);
            i++;
        }
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, titles);
    }

    static String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
    static String getDayOfWeek(String date)
    { String finalDay="";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = format1.parse(date);
            DateFormat format2 = new SimpleDateFormat("EEEE");
            finalDay = format2.format(dt1);

        }catch (Exception e)
        {
            Log.d("J:Error",""+e);
        }
        return finalDay;
    }
    static String getTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(AddNewExpense.this,HomeScreen.class);
            startActivity(i);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onSupportNavigateUp(){
        mydatabase.close();
        Intent i = new Intent(AddNewExpense.this,HomeScreen.class);
        startActivity(i);
        finish();
        return true;
    }

}
