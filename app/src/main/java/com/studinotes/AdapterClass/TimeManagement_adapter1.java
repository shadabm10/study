package com.studinotes.AdapterClass;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.GlobalClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeManagement_adapter1 extends RecyclerView.Adapter<TimeManagement_adapter1.PlanetViewHolder> {
    String TAG="Lsir";

    Context context;
    ArrayList<HashMap<String,String>> tipsntricks;
    ArrayList<HashMap<String,String>> planList;
    ArrayList<HashMap<String,String>> planSubList;
    JsonObject data_array;
    JsonArray data;
    GlobalClass globalClass;
    private RecyclerViewHorizontalListAdapter groceryAdapter;

    LinearLayoutManager layoutManager;

    // data is passed into the constructor
    public TimeManagement_adapter1( Context context,ArrayList<HashMap<String,String>> planList,JsonArray data) {
        this.context=context;
        this.planList=planList;
        this.planSubList=planSubList;
        this.data=data;
        globalClass = ((GlobalClass) context.getApplicationContext());

    }

    // inflates the row layout from xml when needed
    @Override
    public PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timemanagement_adapter1, parent, false);
        PlanetViewHolder PlanetViewHolder = new PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {
        holder.text.setText(planList.get(position).get("day"));
        holder.date.setText(planList.get(position).get("date"));

        planSubList=new ArrayList<>();
//        String subarray=planSubList.get(position).get("subject_name");
       // sublist(holder);


        ShowPlan(holder,position);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return planList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text,text1,text2,date;
        protected RecyclerView groceryRecyclerView;


        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.day);
            date= (TextView) itemView.findViewById(R.id.date);
            groceryRecyclerView=itemView.findViewById(R.id.rcv);



        }
    }

   private void ShowPlan(PlanetViewHolder holder, int pos) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

      //  pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV +"userSchedulelistbyday", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

            //    pd.dismiss();
                //  productlist.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    //  String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product = jobj.getAsJsonArray("main");
                        planSubList.clear();

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String day = images1.get("day").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String subject = day.substring(0, 3);

                            if(planList.get(pos).get("date").matches(date)) {
                                data = images1.getAsJsonArray("data");

                                for (int j = 0; j < data.size(); j++) {
                                    JsonObject data_array = data.get(j).getAsJsonObject();
                                    String id = data_array.get("id").toString().replaceAll("\"", "");
                                    String user_id = data_array.get("user_id").toString().replaceAll("\"", "");
                                    String subject_name = data_array.get("subject_name").toString().replaceAll("\"", "");
                                    String day_new = data_array.get("day").toString().replaceAll("\"", "");
                                    String start_time = data_array.get("start_time").toString().replaceAll("\"", "");
                                    String end_time = data_array.get("end_time").toString().replaceAll("\"", "");
                                    String notify_me = data_array.get("notify_me").toString().replaceAll("\"", "");
                                    String notify_before = data_array.get("notify_before").toString().replaceAll("\"", "");
                                    String random = data_array.get("random").toString().replaceAll("\"", "");
                                    String social = data_array.get("social").toString().replaceAll("\"", "");
                                    String entry_date = data_array.get("entry_date").toString().replaceAll("\"", "");
                                    String modified_date = data_array.get("modified_date").toString().replaceAll("\"", "");
                                    String delete_flag = data_array.get("delete_flag").toString().replaceAll("\"", "");
                                    String is_active = data_array.get("is_active").toString().replaceAll("\"", "").trim();


                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("id", id);
                                    hashMap.put("user_id", user_id);
                                    hashMap.put("subject_name", subject_name);
                                    hashMap.put("day_new", day_new);
                                    hashMap.put("start_time", start_time);
                                    hashMap.put("end_time", end_time);
                                    hashMap.put("notify_me", notify_me);
                                    hashMap.put("notify_before", notify_before);
                                    hashMap.put("random", random);
                                    hashMap.put("social", social);
                                    hashMap.put("entry_date", entry_date);
                                    hashMap.put("modified_date", modified_date);
                                    hashMap.put("delete_flag", delete_flag);
                                    hashMap.put("is_active", is_active);

                                    planSubList.add(hashMap);
                                    Log.d(TAG, "subject_name_qwerty " + subject_name);

                                }
                            }

                          //  setMainView();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("day", subject);
                            hashMap.put("date", date);

                          //  planList.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }
                        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        holder.groceryRecyclerView.setLayoutManager(layoutManager);

                        groceryAdapter = new RecyclerViewHorizontalListAdapter(context,planSubList);
                        holder.groceryRecyclerView.setAdapter(groceryAdapter);
                        groceryAdapter.notifyDataSetChanged();


                    } else {
                      //  message = jobj.get("message").toString().replaceAll("\"", "");
                        //  Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }


                } catch(
                        Exception e)

                {
                    e.printStackTrace();
                    Toast.makeText(context, "issue", Toast.LENGTH_LONG).show();

                }

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(context, "Exception", Toast.LENGTH_LONG).show();
              //  pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
// Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                //   params.put("userid",globalClass.getId() );
                params.put("userid",globalClass.getId());

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}
