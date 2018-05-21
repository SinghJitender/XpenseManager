package com.jitenderpal.xpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieEntry;
import com.jitenderpal.xpensemanager.Adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import Helpers.StaticData;

public class HomeScreen extends AppCompatActivity {
TextView appName;
Button createBudget;
ArrayList<String> title,date;
ArrayList<Integer> amount,spent;
Hashtable<Integer,List<PieEntry>> piedata;
Hashtable<Integer,ArrayList<Float>> pieinfo;
Hashtable<Integer,ArrayList<Float>> maxValue;
SQLiteDatabase mydatabase;
Animation fadeIn,fadeOut;
CollapsingToolbarLayout collapsingToolbarLayout;
AppBarLayout appBarLayout;
int click=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();
        mydatabase = openOrCreateDatabase(StaticData.DATABASE_NAME, MODE_PRIVATE, null);
        appName= (TextView) findViewById(R.id.appName);
        createBudget = (Button)findViewById(R.id.createbudget);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("XpenseManager");
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                    appName.setVisibility(View.INVISIBLE);
                    createBudget.setVisibility(View.INVISIBLE);
                    appName.startAnimation(fadeOut);
                    createBudget.startAnimation(fadeOut);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    appName.setVisibility(View.VISIBLE);
                    createBudget.setVisibility(View.VISIBLE);
                    appName.startAnimation(fadeIn);
                    createBudget.startAnimation(fadeIn);
                    isShow = false;
                }
            }
        });

        createBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddNewExpense.class) ;
                startActivity(i);
            }
        });
        int key=0;
        piedata = new Hashtable<>();
        pieinfo = new Hashtable<>();
        maxValue = new Hashtable<>();

        Cursor yearSet = mydatabase.rawQuery("Select DISTINCT(year) from  "+ StaticData.MAIN_TABLE_NAME + " ORDER BY year DESC", null);
        int rows = yearSet.getCount();
        for(int i=0;i<rows;i++)
        {
              yearSet.moveToNext();
              int year = yearSet.getInt(0);
              int[] month;
              Cursor monthSet = mydatabase.rawQuery("Select DISTINCT(month) from  "+StaticData.MAIN_TABLE_NAME + " where year="+year+" ORDER BY month DESC", null);
              int allMonths = monthSet.getCount();
              month= new int[allMonths];
              for(int j=0;j<allMonths;j++)
              {
                  monthSet.moveToNext();
                  month[j]=monthSet.getInt(0);
                  Cursor total = mydatabase.rawQuery( "SELECT sum(amount) FROM "+ StaticData.MAIN_TABLE_NAME+" where year = '"+year+"' AND month='"+month[j]+"' GROUP BY month", null);
                  total.moveToNext();
                 // Log.d("Month",month[j]+" - "+year+" - "+total.getFloat(0));
                  Cursor itemSet = mydatabase.rawQuery( "SELECT title, sum(amount) FROM "+ StaticData.MAIN_TABLE_NAME+" where year = '"+year+"' AND month='"+month[j]+"' GROUP BY title", null);
                  int allItem = itemSet.getCount();
                  ArrayList<Float> temp = new ArrayList<>();
                  temp.add((float) year);
                  temp.add((float) month[j]);
                  temp.add(total.getFloat(0));

                  ArrayList<PieEntry> data = new ArrayList<PieEntry>();
                  ArrayList<Float> max = new ArrayList<Float>();
                  for(int k=0;k<allItem;k++)
                  {
                      itemSet.moveToNext();
                     // Log.d("Month",itemSet.getString(0)+" - "+itemSet.getFloat(1));
                      data.add(new PieEntry(itemSet.getFloat(1),itemSet.getString(0)));
                      max.add(itemSet.getFloat(1));
                  }
                    piedata.put(key,data);
                    pieinfo.put(key,temp);
                    maxValue.put(key,max);
                    key++;
              }
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(pieinfo,piedata,maxValue,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(mAdapter);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(click==1) {
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Press again to exit.",Toast.LENGTH_SHORT).show();
                click=1;
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
