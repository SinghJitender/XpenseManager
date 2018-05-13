package com.jitenderpal.xpensemanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by JitenderPal on 07-05-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
Hashtable<Integer,List<PieEntry>> piedata;
Hashtable<Integer,ArrayList<Float>> pieinfo;
Hashtable<Integer,ArrayList<Float>> maxValue;
Context context;


    public RecyclerViewAdapter(Hashtable<Integer,ArrayList<Float>> pieinfo, Hashtable<Integer,List<PieEntry>> piedata,Hashtable<Integer,ArrayList<Float>> maxValue, Context context) {
        this.context = context;
        this.piedata = piedata;
        this.pieinfo = pieinfo;
        this.maxValue=maxValue;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.cardview_homescreen,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.total.setText(Math.round(pieinfo.get(position).get(2))+"");
        holder.budgetdate.setText(Math.round(pieinfo.get(position).get(0))+"");
        holder.budgettitle.setText(getMonth(Math.round(pieinfo.get(position).get(1))));
        //holder.spentamount.setText(spentamt.get(position)+"");
        //holder.remainingamount.setText((budgetamt.get(position)-spentamt.get(position))+"");
        PieDataSet dataSet = new PieDataSet(piedata.get(position), getMonth(Math.round(pieinfo.get(position).get(1))));
        dataSet.setColors(com.jitenderpal.xpensemanager.ColorTemplate.OWN_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new DefaultValueFormatter(00));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        holder.pieChart.setDrawEntryLabels(false);
        dataSet.setSliceSpace(1f);
        holder.pieChart.setData(data);
        holder.pieChart.animateXY(800, 400);
        Description d = new Description();
        d.setText(getMonth(Math.round(pieinfo.get(position).get(1))));
        holder.pieChart.setDescription(d);
        holder.pieChart.highlightValue(indexOfMaxValue(maxValue.get(position)),0,false);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick
                Toast.makeText(context,"Clicked at : "+position,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,ExpenseDetails.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pieinfo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView budgettitle,budgetdate, total;// spentamount, remainingamount;
        PieChart pieChart;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardView =  (CardView) itemView.findViewById(R.id.cardview);
            total = (TextView) itemView.findViewById(R.id.total);
            budgettitle = (TextView) itemView.findViewById(R.id.title);
            budgetdate = (TextView) itemView.findViewById(R.id.date);
            //spentamount = (TextView) itemView.findViewById(R.id.totalspentamt);
            //remainingamount = (TextView) itemView.findViewById(R.id.remainingamt);
            pieChart = (PieChart) itemView.findViewById(R.id.piechart);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(35f);
            pieChart.setHoleRadius(30f);
        }
    }
    public static String getMonth(int month)
    {
        String result="";
        switch (month)
        {
            case 1: result="January"; break;
            case 2: result="Feburary"; break;
            case 3: result="March";break;
            case 4: result="April";break;
            case 5: result="May";break;
            case 6: result="June";break;
            case 7: result="July";break;
            case 8: result="August";break;
            case 9: result="September";break;
            case 10: result="October";break;
            case 11: result="November";break;
            case 12: result="December";break;

        }

        return result;
    }

    public  static float indexOfMaxValue(ArrayList<Float> list)
    {
        float index =0f;
        float max = Float.MIN_VALUE;
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i)>max)
            {
                max = list.get(i);
                index=i;
            }
        }

        return index;
    }
}
