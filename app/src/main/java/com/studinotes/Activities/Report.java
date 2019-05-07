package com.studinotes.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;
import com.studinotes.Utils.Utils;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;

import java.util.HashMap;
import java.util.Map;

public class Report extends AppCompatActivity {
    String TAG="report";
    ImageButton back3;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;
    EditText review;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    ImageButton send_suggestion;

    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        globalClass = (GlobalClass)getApplicationContext();
        prefrence = new Shared_Preference(Report.this);
        prefrence.loadPrefrence();
        pd=new ProgressDialog(Report.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        sharedpreference = new Sharedpreference(Report.this);
        initialfunction();




    }
    private void initialfunction() {

        main_layout = findViewById(R.id.main_layout);
        review=findViewById(R.id.review);
        send_suggestion=findViewById(R.id.send_report);

        back3 = findViewById(R.id.back3);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review_str=review.getText().toString().trim();
                if(!review.getText().toString().isEmpty()) {
                    SendSuggestion(review_str);
                }
                else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.insert_report), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                }
            }
        });
    }


    @Override
    protected void onPostResume() {

        Utils.changeBackgroundColor(Report.this, main_layout, sharedpreference.getStyle());
        super.onPostResume();
    }
    private void SendSuggestion(final String review) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV +"adduserReport", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject suggestion = gson.fromJson(response, JsonObject.class);
                    String result = suggestion.get("status").toString().replaceAll("\"", "");
                    String message = suggestion.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {

                        FancyToast.makeText(getApplicationContext(),message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        finish();


                    }
                    else {
                        FancyToast.makeText(getApplicationContext(),message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                    }







                } catch (Exception e) {
                    Toast.makeText(Report.this, "issue", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(Report.this, "Exception", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
// Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("userid", globalClass.getId());
                params.put("reason", review);

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
