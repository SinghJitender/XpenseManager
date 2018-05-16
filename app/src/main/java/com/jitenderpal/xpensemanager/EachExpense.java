package com.jitenderpal.xpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;

import com.jitenderpal.xpensemanager.Adapters.EachExpenseCardViewAdapter;
import com.jitenderpal.xpensemanager.Adapters.ExpenseDetailsCardViewAdapter;

import java.util.ArrayList;

import Helpers.StaticData;

public class EachExpense extends AppCompatActivity {
int year=0,month=0;
float total=0;
String title;
SQLiteDatabase mydatabase;
ArrayList<String> description,tags,date,dayofweek;
ArrayList<Float> amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mydatabase = openOrCreateDatabase(StaticData.DATABASE_NAME, MODE_PRIVATE, null);
        try{
            Bundle extras = getIntent().getExtras();
            total = extras.getFloat("total");
            year = extras.getInt("year");
            month = extras.getInt("month");
            title = extras.getString("title");
        } catch (Exception e)
        {
            Log.d("J:EachExpense",e+"");
        }

        Cursor itemSet = mydatabase.rawQuery( "SELECT amount,description,tags,date,day FROM "+ StaticData.MAIN_TABLE_NAME+" where year = '"+year+"' AND month='"+month+"' AND title='"+title+"'", null);
        int allItem = itemSet.getCount();
        description = new ArrayList<>(allItem);
        tags = new ArrayList<>(allItem);
        amount = new ArrayList<>(allItem);
        date = new ArrayList<>(allItem);
        dayofweek = new ArrayList<>(allItem);
        for(int k=0;k<allItem;k++)
        {
            itemSet.moveToNext();
            amount.add(itemSet.getFloat(0));
            description.add(itemSet.getString(1));
            tags.add(itemSet.getString(2));
            date.add(itemSet.getString(3));
            dayofweek.add(itemSet.getString(4));

        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        EachExpenseCardViewAdapter mAdapter = new EachExpenseCardViewAdapter(amount,description,tags,date,dayofweek,year,month,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(mAdapter);




    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mydatabase.close();
            Intent i = new Intent(EachExpense.this,ExpenseDetails.class);
            i.putExtra("year",year);
            i.putExtra("month",month);
            i.putExtra("total",total);
            startActivity(i);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onSupportNavigateUp(){
        mydatabase.close();
        Intent i = new Intent(EachExpense.this,ExpenseDetails.class);
        i.putExtra("year",year);
        i.putExtra("month",month);
        i.putExtra("total",total);
        startActivity(i);
        finish();
        return true;
    }
}
