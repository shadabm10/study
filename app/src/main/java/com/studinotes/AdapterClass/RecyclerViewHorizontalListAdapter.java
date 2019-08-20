package com.studinotes.AdapterClass;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shadab on 12/24/2019.
 */

public class RecyclerViewHorizontalListAdapter extends RecyclerView.Adapter<RecyclerViewHorizontalListAdapter.GroceryViewHolder>{
    ArrayList<HashMap<String,String>> videoList;   // Context context;
    Context context;
    ArrayList<HashMap<String,String>> planSubList;


    public RecyclerViewHorizontalListAdapter( Context context,    ArrayList<HashMap<String,String>> planSubList){
        this.videoList= videoList;
        this.context = context;
        this.planSubList = planSubList;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontallist, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        String notify_me=planSubList.get(position).get("notify_me");
        if (notify_me.equals("Y")){
            holder.imageView.setVisibility(View.VISIBLE);
        }
        else {
            holder.imageView.setVisibility(View.GONE);

        }
      //  holder.imageView.setImageResource(horizontalGrocderyList.get(position).getSubject_name());
        holder.txtview.setText(planSubList.get(position).get("subject_name"));
        holder.time.setText(planSubList.get(position).get("start_time")+" to "+planSubList.get(position).get("end_time"));

    }

    @Override
    public int getItemCount() {
        return planSubList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview,time;
        public GroceryViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.idProductImage);
            txtview=view.findViewById(R.id.idProductName);
            time=view.findViewById(R.id.time);
        }
    }
}