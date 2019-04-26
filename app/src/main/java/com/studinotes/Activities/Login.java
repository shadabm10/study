package com.studinotes.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
*/

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

public class Login extends AppCompatActivity {
    TextView tv_register,tv_login,tv_forget_pass;
    String TAG = "login_screen";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    EditText input_email,input_password;
    ProgressDialog pd;
    String device_id;
    ImageView img_login;
    RelativeLayout rl_register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        rl_register =  findViewById(R.id.rl_register);

        globalClass = (GlobalClass)getApplicationContext();
        prefrence = new Shared_Preference(Login.this);
        prefrence.loadPrefrence();
        pd=new ProgressDialog(Login.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(TAG, "device_id: "+device_id);
        globalClass.setDeviceid(device_id);
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                Intent intent = new Intent(Login.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        } else {
            FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        }
        rl_register = findViewById(R.id.rl_register);
        input_email = findViewById(R.id.email);
        img_login = findViewById(R.id.next);
        input_password = findViewById(R.id.password);

      //  rl_login =  findViewById(R.id.rl_login);
        tv_forget_pass =  findViewById(R.id.forgot);




        tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        img_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_new = input_email.getText().toString().trim();
                String password_new = input_password.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {
                    if (!input_email.getText().toString().isEmpty()) {

                        if (!input_password.getText().toString().isEmpty()) {
                            checkLogin(email_new,password_new);
                        } else {
                            FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                        }

                    } else {
                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_email_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }
                } else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                }
            }
        });



    }

    private void checkLogin(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"login", new Response.Listener<String>() {


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
                        JsonObject data = jobj.getAsJsonObject("data");
                        String res_user_id =data.get("id").toString().replaceAll("\"", "");
                        String res_email=data.get("email").toString().replaceAll("\"", "");
                        String res_firstname=data.get("first_name").toString().replaceAll("\"", "");
                        String res_lastname=data.get("last_name").toString().replaceAll("\"", "");
                        String res_school_name=data.get("school_name").toString().replaceAll("\"", "");
                        String device_type=data.get("device_type").toString().replaceAll("\"", "");
                        String device_id=data.get("device_id").toString().replaceAll("\"", "");
                        String fcm_token=data.get("fcm_token").toString().replaceAll("\"", "");
                        String profile_pic=data.get("profile_pic").toString().replaceAll("\"", "");

                        globalClass.setId(res_user_id);
                        globalClass.setEmail(res_email);
                        globalClass.setFname(res_firstname);
                        globalClass.setLname(res_lastname);
                        globalClass.setDeviceid(device_type);
                        globalClass.setDeviceid(device_id);
                        globalClass.setFcm_reg_token(fcm_token);
                        globalClass.setProfil_pic(profile_pic);

                        globalClass.setSchool_name(res_school_name);


                        globalClass.setLogin_status(true);
                        globalClass.setLogin_from("signup");
                        prefrence.savePrefrence();
                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(Login.this, HomePage.class);
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
                params.put("password", password);
                params.put("device_type", "android");
                params.put("device_id", device_id);
                params.put("fcm_token", "1233456");


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        // super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}
