package com.studinotes.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class Register extends AppCompatActivity {
    String TAG="register";
    TextView login;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    String device_id;
    RelativeLayout rl_login;
    ImageView img_register;
    EditText edt_first_name,edt_last_name,edt_school_name,edt_email1,edt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Register.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(Register.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        //////////////////Define of Views////////////////////////////
        rl_login=findViewById(R.id.rl_login);
        img_register=findViewById(R.id.next1);

        edt_first_name=findViewById(R.id.first);
        edt_last_name=findViewById(R.id.last);
        edt_school_name=findViewById(R.id.school);
        edt_email1=findViewById(R.id.email1);
        edt_password=findViewById(R.id.password1);


        /////////////////////////intent to register page///////////////////////////
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity=new Intent(getApplicationContext(),Login.class);
                startActivity(loginActivity);

            }
        });
        img_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edt_first_name.getText().toString().trim();
                String lastName = edt_last_name.getText().toString().trim();
                String schoolName = edt_school_name.getText().toString().trim();
                String emailName = edt_email1.getText().toString().trim();
                String passwordName = edt_password.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {
                    if (!edt_first_name.getText().toString().isEmpty()) {
                        if (!edt_last_name.getText().toString().isEmpty()) {
                            if (isValidEmail(edt_email1.getText().toString())) {
                                if (!edt_email1.getText().toString().isEmpty()) {
                                    if (!edt_password.getText().toString().isEmpty()) {


                                        checkRegister(firstName,lastName,schoolName,emailName,passwordName);

                                    } else {
                                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                                    }
                                } else {
                                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_email_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                                }
                            } else {
                                FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.validate_email), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                            }
                        } else {
                            FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_second_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                        }
                    } else {
                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_first_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }
                }
                else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                }
            }

        });
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void checkRegister(final String firstName, final String lastName, final String schoolName, final String emailID,final String Password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"registration", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " +response);

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        String res_user_id =data.get("id").toString().replaceAll("\"", "");
                        String res_email=data.get("email").toString().replaceAll("\"", "");
                        String res_firstname=data.get("first_name").toString().replaceAll("\"", "");
                        String res_lastname=data.get("last_name").toString().replaceAll("\"", "");
                        String res_school_name=data.get("school_name").toString().replaceAll("\"", "");
                        String device_type=data.get("device_type").toString().replaceAll("\"", "");
                        String device_id=data.get("device_id").toString().replaceAll("\"", "");
                        String fcm_token=data.get("fcm_token").toString().replaceAll("\"", "");

                        globalClass.setId(res_user_id);
                        globalClass.setEmail(res_email);
                        globalClass.setFname(res_firstname);
                        globalClass.setLname(res_lastname);
                        globalClass.setDeviceid(device_type);
                        globalClass.setDeviceid(device_id);
                        globalClass.setFcm_reg_token(fcm_token);

                        globalClass.setSchool_name(res_school_name);
                        globalClass.setLogin_status(true);

                        prefrence.savePrefrence();

                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                        pd.dismiss();

                    } else
                    {
                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    FancyToast.makeText(getApplicationContext(), "Data Connection", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("Firstname", firstName);
                params.put("Lastname", lastName);
                params.put("school", schoolName);
                params.put("email", emailID);
                params.put("password", Password);
                params.put("device_type", "android");
                params.put("device_id", device_id);
                params.put("fcm_token", "1234456");





                Log.d(TAG, "Register: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
