package com.jitenderpal.xpensemanager.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.jitenderpal.xpensemanager.ColorTemplate;
import com.jitenderpal.xpensemanager.R;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ExpenseDetailsCardViewAdapter extends RecyclerView.Adapter<ExpenseDetailsCardViewAdapter.MyViewHolder> {

Context context;
ArrayList<String> titles;
ArrayList<Float> sumAmount;
float total;

    public ExpenseDetailsCardViewAdapter(ArrayList<String> titles, ArrayList<Float> sumAmount,float total, Context context) {
        this.context = context;
        this.titles = titles;
        this.sumAmount=sumAmount;
        this.total=total;
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
        String labels[] = new String[]{"ABC","DEF"};
        //dataset.setStackLabels(labels);
        BarData data = new BarData(dataset);
        data.setBarWidth(.7f);
        holder.mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        holder.mChart.getXAxis().setCenterAxisLabels(true);
        holder.mChart.setData(data);
        holder.mChart.invalidate();

        /*setData(12, 50,holder.mChart);
        holder.mChart.setFitBars(true);
        holder.mChart.animateY(2500);*/
        /*piedata.add(new PieEntry(sumAmount.get(position),titles.get(position)));
        piedata.add(new PieEntry((total-sumAmount.get(position)),"Remaining"));

        PieDataSet dataSet = new PieDataSet(piedata, "");
        dataSet.setColors(ColorTemplate.MONTHLY_EXPENSE_COLOUR);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new DefaultValueFormatter(00));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        holder.pieChart.setDrawEntryLabels(false);
        dataSet.setSliceSpace(1f);
        holder.pieChart.setData(data);
        holder.pieChart.animateXY(800, 400);
        Description d = new Description();
        d.setText(titles.get(position));
        holder.pieChart.setDescription(d);
        holder.pieChart.highlightValue(sumAmount.get(position)>(total-sumAmount.get(position))?0f:1f,0,false);
*/
      /*
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick
                Toast.makeText(context,"Clicked at : "+position,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,ExpenseDetails.class);
                context.startActivity(i);
            }
        });*/

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
            mChart.getXAxis().setDrawLabels(true);

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
