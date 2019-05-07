package com.studinotes.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.studinotes.Activities.Feedback;
import com.studinotes.Activities.Report;
import com.studinotes.Activities.Setting;
import com.studinotes.Activities.Suggestion;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;
import com.studinotes.AdapterClass.TipsnTricks_adapter;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;
import com.studinotes.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class TipsnTricks extends Fragment {


    public static final String TAG = "MyFragment";

    private TipsnTricks_adapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> tipsntricks;
    RecyclerView recyclerView;
    RelativeLayout main_layout4;
    Sharedpreference sharedpreference;
    Shared_Preference preference;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.tipsntricks, container, false);
        preference = new Shared_Preference(getActivity().getBaseContext());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading");
        recyclerView=rootView.findViewById(R.id.recycle);
        browseJob();
        function();

        sharedpreference = new Sharedpreference(getActivity());
        main_layout4 = rootView.findViewById(R.id.main_layout4);

        final Button suggest = rootView.findViewById(R.id.suggest);
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Suggestion.class);
                startActivity(intent);
            }
        });
        final Button feedback = (Button) rootView.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Feedback.class);
                startActivity(intent);
            }
        });
        final Button report = (Button) rootView.findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Report.class);
                startActivity(intent);
            }
        });

        final ImageButton settings = rootView.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });

        //Returning the layout file after inflating
        //Change R.layout.studynotes in you classes
        return rootView;

    }

    private void function() {
        tipsntricks = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

    }

    private void browseJob() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV +"listTipsTricks", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                //  productlist.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("data");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String content = images1.get("content").toString().replaceAll("\"", "").trim();
                            String product_img = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");
                            String content_text=(Html.fromHtml(content)).toString().replaceAll("\n", " ").trim();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",id);
                            hashMap.put("title", title);
                            hashMap.put("product_img", product_img);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);
                            hashMap.put("content", content_text);

                            tipsntricks.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        adapter = new TipsnTricks_adapter(getContext(), tipsntricks);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    } else
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
// Posting parameters to login url
                Map<String, String> params = new HashMap<>();

             //   params.put("parent_id", "0");

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout4, sharedpreference.getStyle());
        super.onResume();
    }
}