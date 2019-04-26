package com.studinotes.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ResetPassword extends AppCompatActivity {
    String TAG="Reset";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    EditText input_otp,input_pass,input_c_pass;
    RelativeLayout rl_login;
    TextView tv_register;
    String mail,otp;
    ImageView next3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword);
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(ResetPassword.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(ResetPassword.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
       mail=getIntent().getExtras().getString("email");
        otp=getIntent().getExtras().getString("otp_code");
        input_otp=findViewById(R.id.edt_otp);
        input_pass=findViewById(R.id.create);
        input_c_pass=findViewById(R.id.reset);
        next3=findViewById(R.id.next3);

        rl_login=findViewById(R.id.rl_login);

        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp_txt=input_otp.getText().toString().trim();
                String pass=input_pass.getText().toString().trim();
                //  String confirm_pass=input_c_pass.getText().toString().trim();
                if(!input_otp.getText().toString().isEmpty()){
                    if ((!input_pass.getText().toString().isEmpty())){
                        if(!input_c_pass.getText().toString().isEmpty()){
                            if(input_pass.getText().toString().equals(input_c_pass.getText().toString())){
                                ResetPass(otp_txt,pass);
                            }
                            else {
                                FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.pass_not_match), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                            }
                        }
                        else {
                            FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.rePass), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                        }
                    }
                    else {
                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                    }
                }
                else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.otp_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                }

            }
        });
    }
    private void ResetPass(final String otp,final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        preference.clearPrefrence();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"resetPassword", new Response.Listener<String>() {


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


                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(ResetPassword.this, Login.class);
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

                params.put("email", mail);
                params.put("otp_code", otp);
                params.put("new_password", password);



                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}