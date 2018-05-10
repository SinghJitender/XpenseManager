package com.jitenderpal.xpensemanager;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
//EditText budgetTitle;
Button createBudget;
ArrayList<String> title,date;
ArrayList<Integer> amount,spent;
Hashtable<Integer,List<PieEntry>> piedata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //budgetTitle= (EditText)findViewById(R.id.budgettitle);
        createBudget = (Button)findViewById(R.id.createbudget);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);
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
                   // budgetTitle.setVisibility(View.INVISIBLE);
                    createBudget.setVisibility(View.INVISIBLE);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                   // budgetTitle.setVisibility(View.VISIBLE);
                    createBudget.setVisibility(View.VISIBLE);
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
                title = new ArrayList<>();
        title.add("March");title.add("April");title.add("May");title.add("June");
        date = new ArrayList<>();
        date.add("01/03/2018");date.add("01/04/2018");date.add("01/05/2018");date.add("01/06/2018");
        amount = new ArrayList<>();
        amount.add(5000);amount.add(10000);amount.add(8500);amount.add(6000);
        spent = new ArrayList<>();
        spent.add(4000);spent.add(6700);spent.add(3500);spent.add(7000);
        piedata = new Hashtable<>();
        ArrayList<PieEntry> march = new ArrayList<PieEntry>();
        march.add(new PieEntry((float) (1500),"DMart"));march.add(new PieEntry((float) (500),"Twilight"));march.add(new PieEntry((float) (1700),"BigBazaar"));march.add(new PieEntry((float) (300),"Chicken"));
        ArrayList<PieEntry> april = new ArrayList<PieEntry>();
        april.add(new PieEntry((float) (3000),"DMart"));april.add(new PieEntry((float) (500),"Twilight"));april.add(new PieEntry((float) (1500),"BigBazaar"));april.add(new PieEntry((float) (500),"Chicken"));april.add(new PieEntry((float) (1000),"Petrol"));april.add(new PieEntry((float) (200),"Eggs"));
        ArrayList<PieEntry> may = new ArrayList<PieEntry>();
        may.add(new PieEntry((float) (800),"Petrol"));may.add(new PieEntry((float) (1500),"DMart"));may.add(new PieEntry((float) (1200),"Fruits"));
        ArrayList<PieEntry> june = new ArrayList<PieEntry>();
        june.add(new PieEntry((float) (1500),"Petrol"));june.add(new PieEntry((float) (1500),"BigBazaar"));june.add(new PieEntry((float) (2000),"Twilight"));june.add(new PieEntry((float) (500),"DMart"));june.add(new PieEntry((float) (600),"Milk"));june.add(new PieEntry((float) (400),"Butter"));june.add(new PieEntry((float) (500),"Bike Service"));
        piedata.put(0,march);piedata.put(1,april);piedata.put(2,may);piedata.put(3,june);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(title,piedata,date,amount,spent,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(mAdapter);
    }
}
