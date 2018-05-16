package com.jitenderpal.xpensemanager.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.jitenderpal.xpensemanager.R;

import java.util.ArrayList;

public class EachExpenseCardViewAdapter extends RecyclerView.Adapter<EachExpenseCardViewAdapter.MyViewHolder> {
    ArrayList<Float> amount;
    ArrayList<String> description;
    ArrayList<String> tags;
    ArrayList<String> date;
    ArrayList<String> dayofweek;
    int year,month;
    Context context;

    public EachExpenseCardViewAdapter(ArrayList<Float> amount, ArrayList<String> description, ArrayList<String> tags, ArrayList<String> date,
                                      ArrayList<String> dayofweek, int year, int month, Context context)
    {
        this.amount=amount;
        this.description=description;
        this.tags=tags;
        this.date=date;
        this.dayofweek = dayofweek;
        this.year=year;
        this.month=month;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.cardview_each_expense,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.xdate.setText(date.get(position)+"-"+month+"-"+year);
        holder.xdayofweek.setText(dayofweek.get(position).toUpperCase());
        holder.xdescription.setText(description.get(position));
        holder.xtags.setText(tags.get(position));
        holder.xamount.setText(amount.get(position)+"");

    }

    @Override
    public int getItemCount() {
        return amount.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xamount,xdescription,xtags,xdate,xdayofweek;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView =  (CardView) itemView.findViewById(R.id.cardview);
            xamount = (TextView) itemView.findViewById(R.id.amount);
            xdescription = (TextView) itemView.findViewById(R.id.description);
            xtags = (TextView) itemView.findViewById(R.id.tags);
            xdate = (TextView) itemView.findViewById(R.id.date);
            xdayofweek = (TextView) itemView.findViewById(R.id.dayofweek);

        }
    }
}
