package com.jitenderpal.xpensemanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.jitenderpal.xpensemanager.ColorTemplate;
import com.jitenderpal.xpensemanager.EachExpense;
import com.jitenderpal.xpensemanager.R;
import java.util.ArrayList;

public class ExpenseDetailsCardViewAdapter extends RecyclerView.Adapter<ExpenseDetailsCardViewAdapter.MyViewHolder> {

Context context;
ArrayList<String> titles;
ArrayList<Float> sumAmount;
float total; int year, month;

    public ExpenseDetailsCardViewAdapter(ArrayList<String> titles, ArrayList<Float> sumAmount,float total,int year,int month, Context context) {
        this.context = context;
        this.titles = titles;
        this.sumAmount=sumAmount;
        this.total=total;
        this.year=year;
        this.month=month;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.cardview_monthlyview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.amount.setText(Math.round(sumAmount.get(position))+"");
        holder.title.setText(titles.get(position));
       // ArrayList<PieEntry> piedata = new ArrayList<PieEntry>();
        ArrayList<BarEntry> bardata = new ArrayList<>();
        Log.d("J:ExpenseCardView",total+"");

        bardata.add(new BarEntry(2f,sumAmount.get(position)));
        bardata.add(new BarEntry(1f,total-sumAmount.get(position)));
        bardata.add(new BarEntry(0f,total));

        BarDataSet dataset = new BarDataSet(bardata,"");
        dataset.setValueTextSize(10f);
        dataset.setColors(ColorTemplate.MONTHLY_EXPENSE_COLOUR);
        //dataset.setBarSpacePercent(50f);
        String labels[] = new String[3];
        labels[0]=titles.get(position).trim(); labels[1]=", OTHER" ; labels[2]=", TOTAL";
        //dataset.setStackLabels(labels);
        BarData data = new BarData(dataset);
        data.setBarWidth(.6f);
        //holder.mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        //holder.mChart.getXAxis().setCenterAxisLabels(false);
        Legend l = holder.mChart.getLegend();
        l.setExtra(new int[]{0,0,0},labels);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        l.setYOffset(5f);
        holder.mChart.setData(data);
        holder.mChart.invalidate();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick
                //Toast.makeText(context,"Clicked at : "+position,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,EachExpense.class);
                i.putExtra("year",year);
                i.putExtra("month",month);
                i.putExtra("total",total);
                i.putExtra("title",titles.get(position));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView amount,title,description,tags,attachments;
        PieChart pieChart;
        CardView cardView;
        HorizontalBarChart mChart;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardView =  (CardView) itemView.findViewById(R.id.cardview);
            amount = (TextView) itemView.findViewById(R.id.amount);
            title = (TextView) itemView.findViewById(R.id.title);
            /*pieChart = (PieChart) itemView.findViewById(R.id.piechart);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(35f);
            pieChart.setHoleRadius(30f);*/

            mChart = (HorizontalBarChart)itemView.findViewById(R.id.chart1);
           // mChart.setOnChartValueSelectedListener(context);
            mChart.getXAxis().setDrawGridLines(false);
            mChart.getXAxis().setLabelRotationAngle(90f);
            /*mChart.getAxisRight().setInverted(false);
            mChart.getAxisLeft().setInverted(true);*/
            mChart.getXAxis().setDrawLabels(false);

            mChart.getAxisLeft().setDrawGridLines(false);
            mChart.getAxisRight().setDrawGridLines(false);
            mChart.getXAxis().setDrawAxisLine(false);
            mChart.getAxisLeft().setDrawAxisLine(false);
            mChart.getAxisRight().setDrawAxisLine(false);
            mChart.getDescription().setEnabled(false);
            mChart.animateY(1000);
            mChart.getAxisLeft().setAxisMinimum(0);
            mChart.getAxisRight().setAxisMinimum(0);
            mChart.getAxisLeft().setEnabled(false);





        }
    }
}
