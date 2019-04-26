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
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        holder.name.setText(tipsntricks.get(position).get("content"));
        holder.title.setText(tipsntricks.get(position).get("title"));
        Log.d(TAG, "NAME NEW: "+text);

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
           /* text1= (TextView) itemView.findViewById(R.id.white1);
            text2= (TextView) itemView.findViewById(R.id.white2);
            text3= (TextView) itemView.findViewById(R.id.white3);
            final TextView add1 = (TextView) itemView.findViewById(R.id.textView1);
            final TextView add11 = (TextView) itemView.findViewById(R.id.textView11);
            final TextView add2 = (TextView) itemView.findViewById(R.id.textView2);
            final TextView add21 = (TextView) itemView.findViewById(R.id.textView21);
            final TextView add3 = (TextView) itemView.findViewById(R.id.textView3);
            final TextView add31 = (TextView) itemView.findViewById(R.id.textView31);
            final TextView add4 = (TextView) itemView.findViewById(R.id.textView4);
            final TextView add41 = (TextView) itemView.findViewById(R.id.textView41);*/



            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name.setVisibility(View.VISIBLE);
                    plus.setVisibility(View.INVISIBLE);
                   /* add1.setVisibility(View.INVISIBLE);
                    add21.setVisibility(View.INVISIBLE);
                    add31.setVisibility(View.INVISIBLE);
                    add41.setVisibility(View.INVISIBLE);
                    add2.setVisibility(View.VISIBLE);
                    add3.setVisibility(View.VISIBLE);
                    add4.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.GONE);
                    text2.setVisibility(View.GONE);
                    text3.setVisibility(View.GONE);*/


                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name.setVisibility(View.GONE);
                    plus.setVisibility(View.VISIBLE);
                    //add1.setVisibility(View.VISIBLE);

                }
            });

           /* add2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text1.setVisibility(View.VISIBLE);
                    add21.setVisibility(View.VISIBLE);
                    add2.setVisibility(View.INVISIBLE);
                    add11.setVisibility(View.INVISIBLE);
                    add31.setVisibility(View.INVISIBLE);
                    add41.setVisibility(View.INVISIBLE);
                    add1.setVisibility(View.VISIBLE);
                    add3.setVisibility(View.VISIBLE);
                    add4.setVisibility(View.VISIBLE);
                    text.setVisibility(View.GONE);
                    text2.setVisibility(View.GONE);
                    text3.setVisibility(View.GONE);


                }
            });

            add21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text1.setVisibility(View.GONE);
                    add21.setVisibility(View.INVISIBLE);
                    add2.setVisibility(View.VISIBLE);

                }
            });
            add3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text2.setVisibility(View.VISIBLE);
                    add31.setVisibility(View.VISIBLE);
                    add3.setVisibility(View.INVISIBLE);
                    add21.setVisibility(View.INVISIBLE);
                    add11.setVisibility(View.INVISIBLE);
                    add41.setVisibility(View.INVISIBLE);
                    add2.setVisibility(View.VISIBLE);
                    add1.setVisibility(View.VISIBLE);
                    add4.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    text3.setVisibility(View.GONE);

                }
            });

            add31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text2.setVisibility(View.GONE);
                    add31.setVisibility(View.INVISIBLE);
                    add3.setVisibility(View.VISIBLE);

                }
            });
            add4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text3.setVisibility(View.VISIBLE);
                    add41.setVisibility(View.VISIBLE);
                    add4.setVisibility(View.INVISIBLE);
                    add21.setVisibility(View.INVISIBLE);
                    add31.setVisibility(View.INVISIBLE);
                    add11.setVisibility(View.INVISIBLE);
                    add2.setVisibility(View.VISIBLE);
                    add3.setVisibility(View.VISIBLE);
                    add1.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.GONE);
                    text2.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);

                }
            });

            add41.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text3.setVisibility(View.GONE);
                    add41.setVisibility(View.INVISIBLE);
                    add4.setVisibility(View.VISIBLE);

                }
            });*/
        }
    }
}