package com.jitenderpal.xpensemanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.mikephil.charting.data.PieEntry;
import com.jitenderpal.xpensemanager.Adapters.ExpenseDetailsCardViewAdapter;
import com.jitenderpal.xpensemanager.Adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Hashtable;

import Helpers.StaticData;

public class ExpenseDetails extends AppCompatActivity {
int year=0,month=0;
float total=0;
SQLiteDatabase mydatabase;
ArrayList<String> titles;
ArrayList<Float> sumAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);
        mydatabase = openOrCreateDatabase(StaticData.DATABASE_NAME, MODE_PRIVATE, null);

        try{
            Bundle extras = getIntent().getExtras();
            total = extras.getFloat("total");
            year = extras.getInt("year");
            month = extras.getInt("month");
        } catch (Exception e)
        {
            Log.d("J:ExpenseDetails",e+"");
        }
        Cursor itemSet = mydatabase.rawQuery( "SELECT title, sum(amount) FROM "+ StaticData.MAIN_TABLE_NAME+" where year = '"+year+"' AND month='"+month+"' GROUP BY title", null);
        int allItem = itemSet.getCount();
        titles = new ArrayList<>(allItem);
        sumAmount = new ArrayList<>(allItem);
        for(int k=0;k<allItem;k++)
        {
            itemSet.moveToNext();
            titles.add(itemSet.getString(0));
            sumAmount.add(itemSet.getFloat(1));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ExpenseDetailsCardViewAdapter mAdapter = new ExpenseDetailsCardViewAdapter(titles,sumAmount,total,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(mAdapter);
    }
}
