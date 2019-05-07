package com.studinotes.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.studinotes.Utils.GlobalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TipsnTricks_adapter extends RecyclerView.Adapter<TipsnTricks_adapter.PlanetViewHolder> {
    String TAG="";
    private Context context;
    ArrayList<HashMap<String,String>> tipsntricks;
    String text;
    LayoutInflater inflater;
    GlobalClass globalClass;
    int row_index=-1;
     int row_index1;


    public TipsnTricks_adapter(Context context, ArrayList<HashMap<String, String>> tipsntricks) {

        this.context = context;
        this.tipsntricks = tipsntricks;

       globalClass = ((GlobalClass) context.getApplicationContext());

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tipsntricks_adapter, parent, false);
        PlanetViewHolder PlanetViewHolder = new PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    @Override
    public void onBindViewHolder(final PlanetViewHolder holder, int position) {

        holder.name.setText(tipsntricks.get(position).get("content"));
        holder.title.setText(tipsntricks.get(position).get("title"));
        Log.d(TAG, "NAME NEW: "+text);
        /*holder.plus.setOnClickListener(v -> {



            holder.name.setVisibility(View.VISIBLE);
           holder. plus.setVisibility(View.INVISIBLE);
        });
        holder.minus.setOnClickListener(v -> {
            holder.name.setVisibility(View.GONE);
            holder.plus.setVisibility(View.VISIBLE);
        });*/
        holder.plus.setOnClickListener(view -> {
             row_index = position;
            notifyDataSetChanged();
        });

        if(row_index==position){
            holder.name.setVisibility(View.VISIBLE);
            holder.plus.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.name.setVisibility(View.GONE);
            holder.plus.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return tipsntricks.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        TextView name,title;
        Button minus, plus;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.white);
            title = itemView.findViewById(R.id.textView);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);







            minus.setOnClickListener(v -> {
                name.setVisibility(View.GONE);
                plus.setVisibility(View.VISIBLE);
              

            });



        }
    }
}