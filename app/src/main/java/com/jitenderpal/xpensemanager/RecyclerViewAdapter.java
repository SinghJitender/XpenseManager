package com.jitenderpal.xpensemanager;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by JitenderPal on 07-05-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
ArrayList<String> budgetTitle;
ArrayList<String> budgetDate;
ArrayList<Integer> budgetamt;
ArrayList<Integer> spentamt;
Hashtable<Integer,List<PieEntry>> piedata;
Context context;


    public RecyclerViewAdapter(ArrayList<String> budgetTitle,Hashtable<Integer,List<PieEntry>> piedata, ArrayList<String> budgetDate, ArrayList<Integer> budgetamt, ArrayList<Integer> spentamt, Context context) {
        this.budgetTitle = budgetTitle;
        this.budgetDate = budgetDate;
        this.budgetamt = budgetamt;
        this.spentamt = spentamt;
        this.context = context;
        this.piedata = piedata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.cardview_homescreen,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.budgetamount.setText(budgetamt.get(position)+"");
        holder.budgetdate.setText(budgetDate.get(position));
        holder.budgettitle.setText(budgetTitle.get(position));
        holder.spentamount.setText(spentamt.get(position)+"");
        holder.remainingamount.setText((budgetamt.get(position)-spentamt.get(position))+"");
        PieDataSet dataSet = new PieDataSet(piedata.get(position), budgetTitle.get(position).toString());
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new DefaultValueFormatter(00));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        //data.setValueTypeface(mTfLight);
        holder.pieChart.setDrawEntryLabels(false);
        holder.pieChart.setData(data);
        holder.pieChart.animateXY(800, 400);
        Description d = new Description();
        d.setText(budgetTitle.get(position));
        holder.pieChart.setDescription(d);


    }

    @Override
    public int getItemCount() {
        return budgetTitle.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView budgettitle,budgetdate, budgetamount, spentamount, remainingamount;
        PieChart pieChart;
        public MyViewHolder(View itemView) {
            super(itemView);

            budgetamount = (TextView) itemView.findViewById(R.id.totalbudgetamt);
            budgettitle = (TextView) itemView.findViewById(R.id.title);
            budgetdate = (TextView) itemView.findViewById(R.id.date);
            spentamount = (TextView) itemView.findViewById(R.id.totalspentamt);
            remainingamount = (TextView) itemView.findViewById(R.id.remainingamt);
            pieChart = (PieChart) itemView.findViewById(R.id.piechart);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(35f);
            pieChart.setHoleRadius(30f);


        }
    }

}
