package com.studinotes.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.AdapterClass.R;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    String  TAG="Forget";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ProgressDialog pd;
    EditText input_mail_forget;
    RelativeLayout rl_login;
    TextView tv_login;
    String email;
    ImageView next2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        globalClass = (GlobalClass) getApplicationContext();
        shared_preference = new Shared_Preference(ForgotPassword.this);
        shared_preference.loadPrefrence();
        pd = new ProgressDialog(ForgotPassword.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        input_mail_forget = findViewById(R.id.email);
        next2 = findViewById(R.id.next2);
        rl_login = findViewById(R.id.rl_login);

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = input_mail_forget.getText().toString().trim();
                if (!input_mail_forget.getText().toString().isEmpty()) {
                    sendOTP(email);
                } else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_email_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                }

            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(getApplicationContext(),Login.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);

            }


        });

    }
    private void sendOTP(final String username) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"forgotPassword", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if(status.equals("1") ) {
                        JsonObject data=jobj.getAsJsonObject("data");
                        String otp_code = data.get("otp_code").getAsString().replaceAll("\"", "");

                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(ForgotPassword.this, ResetPassword.class);
                        intent.putExtra("email",email);
                        intent.putExtra("otp_code",otp_code);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        pd.dismiss();



                    }
                    else {

                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("email", username);



                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}