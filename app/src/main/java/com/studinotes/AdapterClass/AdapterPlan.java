package com.studinotes.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studinotes.Utils.GlobalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterPlan extends RecyclerView.Adapter<AdapterPlan.MyViewHolder> {

    String TAG="abc";
    Context mContext;
    ArrayList<HashMap<String,String>> list_claim;
    ArrayList<HashMap<String,String>> list_claim_new;
    ArrayList<HashMap<String,String>> productspecifiaction;
    ArrayList<HashMap<String,String>> productfeatures;
    BottomSheetDialog mBottomDialogNotificationAction;
    String id;
    GlobalClass globalClass;
    List<String > list;
    //LayoutInflater inflater;
    private LayoutInflater inflater;


    public AdapterPlan(Context c, ArrayList<HashMap<String, String>> list_claim, BottomSheetDialog mBottomDialogNotificationAction

    ) {
        this.inflater = LayoutInflater.from(c);
        this.mContext = c;
        this.list_claim = list_claim;
        this.id = id;
        this.mBottomDialogNotificationAction = mBottomDialogNotificationAction;



        globalClass = (GlobalClass)mContext.getApplicationContext();

        // globalClass = ((GlobalClass) c.getApplicationContext());

        //  inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }
    @Override
    public AdapterPlan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.plan, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new AdapterPlan.MyViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final AdapterPlan.MyViewHolder holder, final int position) {




      // holder.name.setText(list_claim.get(position).get("id"));
        holder.tv_listing_name.setText(list_claim.get(position).get("id"));



/*
        holder.ll_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                Intent placeorder=new Intent(mContext, PlaceOrder.class);
                placeorder.putExtra("product_id", id);
                placeorder.putExtra("lock_type",list_claim.get(position).get("lock_type"));
                placeorder.putExtra("price",list_claim.get(position).get("price"));
                mBottomDialogNotificationAction.dismiss();
                mContext.startActivity(placeorder);

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return list_claim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name,tv_listing_name;
        // RoundedImageView image;
        LinearLayout ll_lock;

        public MyViewHolder(View itemView) {
            super(itemView);

          //  name =  itemView.findViewById(R.id.lock_type);
            ll_lock =  itemView.findViewById(R.id.ll_lock);

            tv_listing_name =  itemView.findViewById(R.id.price);

        }
    }
}