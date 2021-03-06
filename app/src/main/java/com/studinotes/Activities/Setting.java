package com.studinotes.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;
import com.studinotes.AdapterClass.AdapterPlan;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;
import com.studinotes.Utils.Utils;
import com.studinotes.AdapterClass.R;
import com.studinotes.DialogClass.Setting_Dialog;
import com.studinotes.AdapterClass.Sharedpreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

//import static com.example.studinotes.Sharedpreference.THEME_WHITE;

public class Setting  extends AppCompatActivity {
    String TAG = "login_screen";
    String deviceid;
    File p_image;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    TextView edt_firsr,edt_mail,edt_school,last,tv_used_space,tv_free_space;
    EditText edt_name,edt_school1,edt_email,edt_last;
    ProgressDialog pd;
    RelativeLayout rl_biscuit,rl_white,rl_sky_blue,rl_pesto,rl_fave,rl_calm,rl_grey,rl_pink,rl_light_blue;
    TextView logout,upgrade_button;
    RecyclerView lockRecycle;
    ArrayList<HashMap<String,String>> examination_arraylist;
    HorizontalScrollView sc_main;
    LinearLayout colors;
     AdapterPlan adapterLock;
    CircularImageView image1;
    RelativeLayout main2,nameedit,first,name;
    ImageButton edit,back1;
    BottomSheetDialog mBottomDialogNotificationAction;
    Button savebutton,bck1, bck2, bck3, bck4, bck5, bck6, bck7,bck8,editpicture,white;
    int[] image_array = new int[] { R.drawable.circle_black };
    Sharedpreference sharedpreference;
    PieChartView pieChartView;
    String ButtonCode="";
    String free_space,used_space;
    double result_free_space,result_used_space;
    private static int RESULT_LOAD_IMAGE = 1;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedpreference = new Sharedpreference(this);

        Utils.onActivityCreateSetTheme(this, sharedpreference.getStyle());
        setContentView(R.layout.settings);

        editpicture = findViewById(R.id.editpicture);
        nameedit = findViewById(R.id.nameedit);
        tv_used_space = findViewById(R.id.usedspace);
        tv_free_space = findViewById(R.id.free_space);
        name = findViewById(R.id.name);
        upgrade_button = findViewById(R.id.upgrade_button);
        sc_main = findViewById(R.id.sc_main);
        bck6 =  findViewById(R.id.lightgrey);
        colors = findViewById(R.id.colors);
        rl_biscuit = findViewById(R.id.rl_biscuit);
        rl_calm = findViewById(R.id.rl_calm);
        rl_fave = findViewById(R.id.rl_fave);
        rl_grey = findViewById(R.id.rl_grey);
        rl_light_blue = findViewById(R.id.rl_light_blue);
        rl_pesto = findViewById(R.id.rl_pesto);
        rl_pink = findViewById(R.id.rl_pink);
        rl_sky_blue = findViewById(R.id.rl_sky_blue);
        bck7 =  findViewById(R.id.pink);
        bck8 =  findViewById(R.id.lightblue);
        rl_white = findViewById(R.id.rl_white);
        savebutton = findViewById(R.id.savebutton);
        edit = findViewById(R.id.edit);
        back1= findViewById(R.id.back1);
        edt_name= findViewById(R.id.edt_firsr);
        edt_school1= findViewById(R.id.two1);
        edt_email= findViewById(R.id.three1);
        bck4 =  findViewById(R.id.fave);
        edt_firsr= findViewById(R.id.one);
        edt_mail= findViewById(R.id.three);
        edt_school= findViewById(R.id.two);
        image1= findViewById(R.id.image);
        bck1 =  findViewById(R.id.biscuit);
        bck2 = findViewById(R.id.skyblue);
        bck3 =  findViewById(R.id.pesto);
        white= findViewById(R.id.white);
        bck5 =  findViewById(R.id.calmgreen);
        white.setEnabled(false);
        white.setFocusable(false);
        last= findViewById(R.id.last);
        edt_last= findViewById(R.id.edt_last);
        pieChartView = findViewById(R.id.chart);
        deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        globalClass = (GlobalClass)getApplicationContext();
        prefrence = new Shared_Preference(Setting.this);
        prefrence.loadPrefrence();
        pd=new ProgressDialog(Setting.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        examination_arraylist = new ArrayList<>();
        getProfile();
        Log.d(TAG, "result_free_space: "+result_free_space);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Setting.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Setting.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){

                }

            }
        }



        if (globalClass.iseditable){
            onEdit();
        }else {
            onSave();
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit();


            }
        });
        upgrade_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   getData();
                dialogExamCreateDate();
            }
        });
        editpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();


            }
        });


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
                String str_fname=edt_name.getText().toString();
                String str_lname=edt_last.getText().toString();
                String str_schoolName=edt_school1.getText().toString();
                String str_mail_id=edt_email.getText().toString();
                updateProfile(str_fname,str_lname,str_schoolName);
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        main2 = findViewById(R.id.main2);
        Utils.changeBackgroundColor(this, main2, sharedpreference.getStyle());
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefrence.clearPrefrence();
                globalClass.setLogin_status(false);
                    Logout();


            }
        });


        bck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonCode="#FDFFDB";
                Utils.changeToTheme(Setting.this, Utils.THEME_DEFAULT);
                sharedpreference.saveStyle(Utils.THEME_DEFAULT);
            }
        });
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonCode="#FFFFFF";

                Utils.changeToTheme(Setting.this, Utils.THEME_EIGHT);
                sharedpreference.saveStyle(Utils.THEME_EIGHT);

            }
        });



        bck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ButtonCode="#00BBDE";
                Utils.changeToTheme(Setting.this, Utils.THEME_ONE);
                sharedpreference.saveStyle(Utils.THEME_ONE);

                rl_sky_blue.setBackgroundColor(Color.parseColor("#307417"));
            }
        });




        bck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     ButtonCode="#B2B669";
                Utils.changeToTheme(Setting.this, Utils.THEME_TWO);
                sharedpreference.saveStyle(Utils.THEME_TWO);
            }
        });



        bck4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonCode="#9D0069";
                Utils.changeToTheme(Setting.this, Utils.THEME_THREE);
                sharedpreference.saveStyle(Utils.THEME_THREE);
            }
        });



        bck5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonCode="#9DD8B7";
                Utils.changeToTheme(Setting.this, Utils.THEME_FOUR);
                sharedpreference.saveStyle(Utils.THEME_FOUR);
            }
        });


        bck6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonCode="#AEA7A6";
                Utils.changeToTheme(Setting.this, Utils.THEME_FIVE);
                sharedpreference.saveStyle(Utils.THEME_FIVE);
            }
        });

        bck7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ButtonCode="#EDDFE0";
                Utils.changeToTheme(Setting.this, Utils.THEME_SIX);
                sharedpreference.saveStyle(Utils.THEME_SIX);
            }
        });


        bck8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonCode="#C2F7FE";
                Utils.changeToTheme(Setting.this, Utils.THEME_SEVEN);
                sharedpreference.saveStyle(Utils.THEME_SEVEN);
            }
        });


    }

    public void onEdit(){
        globalClass.setIseditable(true);
        nameedit.setVisibility(View.VISIBLE);
        name.setVisibility(View.GONE);
        editpicture.setVisibility(View.VISIBLE);
        savebutton.setVisibility(View.VISIBLE);
        edit.setVisibility(View.GONE);
        edt_email.setFocusable(false);
        edt_last.setFocusable(true);
        edt_email.setEnabled(false);
        bck1.setEnabled(true);
        white.setEnabled(true);
        bck2.setEnabled(true);
        bck3.setEnabled(true);
        bck4.setEnabled(true);
        bck5.setEnabled(true);
        bck6.setEnabled(true);
        bck7.setEnabled(true);
        bck8.setEnabled(true);

    }
    public void onSave(){
        globalClass.setIseditable(false);
        nameedit.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        editpicture.setVisibility(View.GONE);
        savebutton.setVisibility(View.GONE);
        edit.setVisibility(View.VISIBLE);
        sc_main.setEnabled(false);
        bck1.setEnabled(false);
        bck2.setFocusable(false);
        bck2.setEnabled(false);
        bck3.setFocusable(false);
        bck3.setEnabled(false);
        bck4.setFocusable(false);
        bck4.setEnabled(false);
        bck5.setFocusable(false);
        bck5.setEnabled(false);
        bck6.setFocusable(false);
        bck6.setEnabled(false);
        bck7.setFocusable(false);
        bck7.setEnabled(false);
        bck8.setFocusable(false);
        bck8.setEnabled(false);


    }
    private void Logout() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"logout", new Response.Listener<String>() {


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
                        Intent intent3 = new Intent(Setting.this, Login.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent3);
                       // FancyToast.makeText(getApplicationContext(), "Successfully Logout", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

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

                params.put("userid", globalClass.getId());
                params.put("device_type", "android");
                params.put("device_id", deviceid);
                params.put("fcm_token", "123456");
                Log.d(TAG, "getParams: "+params);


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(Setting.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

                    Log.d("permisssion","not granted");


                    if (shouldShowRequestPermissionRationale(permissions[i])) {

                        Log.d("if","if");
                        permissionsNeeded.add(perm);

                    } else {
                        // add the request.
                        Log.d("else","else");
                        permissionsNeeded.add(perm);
                    }

                }
            }
        }

        if (permissionsNeeded.size() > 0) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // go ahead and request permissions
                requestPermissions(permissionsNeeded.toArray(new String[permissionsNeeded.size()]), permRequestCode);
            }
            return false;
        } else {
            // no permission need to be asked so all good...we have them all.
            return true;
        }

    }

    private void getProfile() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"getProfileInfo", new Response.Listener<String>() {


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
                        String profile_pic=data.get("profile_pic").toString().replaceAll("\"", "");
                         free_space=data.get("free_space").toString().replaceAll("\"", "");
                         used_space=data.get("used_space").toString().replaceAll("\"", "");

                        result_free_space = Double.parseDouble(free_space);
                        result_used_space=Double.parseDouble(used_space);
                        tv_free_space.setText("Free Space :"+result_free_space);
                        tv_used_space.setText("Used Space :"+result_used_space);
                        List pieData = new ArrayList<>();

                        pieData.add(new SliceValue((float) result_free_space, Color.BLUE));

                        pieData.add(new SliceValue((float) result_used_space, Color.RED));

                        PieChartData pieChartData = new PieChartData(pieData);
                        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
                        pieChartData.setHasCenterCircle(true).setCenterText1("Storage Plan").setCenterText1FontSize(16).setCenterText1Color(Color.parseColor("#0097A7"));
                        pieChartView.setPieChartData(pieChartData);
                        Log.d(TAG, "result_free_space response: "+result_used_space);
                        globalClass.setId(res_user_id);
                        globalClass.setEmail(res_email);
                        globalClass.setFname(res_firstname);
                        globalClass.setLname(res_lastname);
                        globalClass.setProfil_pic(profile_pic);
                        globalClass.setSchool_name(res_school_name);
                        prefrence.savePrefrence();
                        if(globalClass.getProfil_pic().equals("")){
                            Picasso.with(getApplicationContext()).load(image_array[0]).placeholder(R.drawable.circle_black).into(image1);
                        }
                        else {
                            Picasso.with(getApplicationContext()).load(globalClass.getProfil_pic()).into(image1);
                        }
                        edt_name.setText(globalClass.getFname());
                        last.setText(globalClass.getLname());
                        edt_firsr.setText(globalClass.getFname());
                        edt_last.setText(globalClass.getLname());

                        edt_mail.setText(globalClass.getEmail());
                        edt_email.setText(globalClass.getEmail());
                        edt_school.setText(globalClass.getSchool_name());
                        edt_school1.setText(globalClass.getSchool_name());
                        Log.d(TAG, "getFname last name: "+globalClass.getFname()+" "+globalClass.getLname());
                        Log.d(TAG, "email "+globalClass.getEmail());
                        Log.d(TAG, "school name: "+globalClass.getSchool_name());
                       // FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

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

                params.put("userid", globalClass.getId());
                Log.d(TAG, "getParams: "+globalClass.getId());


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void updateProfile1(final String fname,final String lname,final String school,final String filepath) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"updateUserProfile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " +response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    int status = jobj.get("status").getAsInt();
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+status);

                    if(status==1) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        String res_user_id =data.get("id").toString().replaceAll("\"", "");
                        String res_email=data.get("email").toString().replaceAll("\"", "");
                        String res_firstname=data.get("first_name").toString().replaceAll("\"", "");
                        String res_lastname=data.get("last_name").toString().replaceAll("\"", "");
                        String res_school_name=data.get("school_name").toString().replaceAll("\"", "");
                        String profile_pic=data.get("profile_pic").toString().replaceAll("\"", "");


                        globalClass.setId(res_user_id);
                        globalClass.setEmail(res_email);
                        globalClass.setFname(res_firstname);
                        globalClass.setLname(res_lastname);
                        globalClass.setProfil_pic(profile_pic);
                        globalClass.setSchool_name(res_school_name);
                        prefrence.savePrefrence();
                        if(globalClass.getProfil_pic().equals("")){
                            Picasso.with(getApplicationContext()).load("http://i.imgur.com/DvpvklR.png").into(image1);
                        }
                        else {
                            Picasso.with(getApplicationContext()).load(globalClass.getProfil_pic()).into(image1);
                        }
                        edt_name.setText(globalClass.getFname());
                        last.setText(globalClass.getLname());
                        edt_firsr.setText(globalClass.getFname());
                        edt_last.setText(globalClass.getLname());

                        edt_mail.setText(globalClass.getEmail());
                        edt_email.setText(globalClass.getEmail());
                        edt_school.setText(globalClass.getSchool_name());
                        edt_school1.setText(globalClass.getSchool_name());
                        Log.d(TAG, "getFname last name: "+globalClass.getFname()+" "+globalClass.getLname());
                        Log.d(TAG, "email "+globalClass.getEmail());
                        Log.d(TAG, "school name: "+globalClass.getSchool_name());
                        // FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

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

                params.put("userid", globalClass.getId());
                params.put("Firstname",fname);
                params.put("Lastname", lname);
                params.put("school_name", school);
                params.put("profileImage",filepath);
                Log.d(TAG, "getParams: "+params);


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Setting.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(Setting.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(Setting.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            p_image = new File(getRealPathFromURI(uri));


            Log.d(TAG, "image = "+p_image);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                image1.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {


            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }


            try {
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

/*
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);*/

                Log.d(TAG, "bitmap: "+bitmap);

                image1.setImageBitmap(bitmap);

                String path = Environment.getExternalStorageDirectory()+File.separator;
                // + File.separator
                //   + "Phoenix" + File.separator + "default";
                f.delete();
                OutputStream outFile = null;
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                try {

                    p_image = file;

                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                    outFile.flush();
                    outFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            // iv_product_image.setImageBitmap(photo);
        }
        if(globalClass.isNetworkAvailable()){
           // user_profile_pic_update_url();
        }else{
            FancyToast.makeText(getApplicationContext(), "Please check your internet connection", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void dialogExamCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Setting.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_bottom_navigation, null);
        dialogBuilder.setView(dialogView);


        dialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        LinearLayout ll_close=dialogView.findViewById(R.id.ll_close);

        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

    }


    public void updateProfile(final String fname,final String lname,final String school){

         pd.show();

        String url = AppConfig.URL_DEV+"updateUserProfile";
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        params.put("userid", globalClass.getId());
        params.put("Firstname",fname);
        params.put("Lastname", lname);
        params.put("school_name", school);
        params.put("app_color", ButtonCode);
        try{

            params.put("profileImage", p_image);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }



        Log.d(TAG , "URL "+url);
        Log.d(TAG , "params "+params.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (response != null) {
                    Log.d(TAG, "user_profile_pic_update- " + response.toString());
                    try {

                        //JSONObject result = response.getJSONObject("result");

                        int status = response.getInt("status");
                        String message = response.getString("message");

                        if (status == 1) {

                            // Log.d(TAG, "name: "+name)

                            JSONObject data = response.getJSONObject("data");

                            String res_user_id =data.get("id").toString().replaceAll("\"", "");
                            String res_email=data.get("email").toString().replaceAll("\"", "");
                            String res_firstname=data.get("first_name").toString().replaceAll("\"", "");
                            String res_lastname=data.get("last_name").toString().replaceAll("\"", "");
                            String res_school_name=data.get("school_name").toString().replaceAll("\"", "");
                            String profile_pic=data.get("profile_pic").toString().replaceAll("\"", "");


                            globalClass.setId(res_user_id);
                            globalClass.setEmail(res_email);
                            globalClass.setFname(res_firstname);
                            globalClass.setLname(res_lastname);
                            globalClass.setProfil_pic(profile_pic);
                            globalClass.setSchool_name(res_school_name);
                            prefrence.savePrefrence();
                            if(globalClass.getProfil_pic().equals("")){
                                Picasso.with(getApplicationContext()).load("http://i.imgur.com/DvpvklR.png").into(image1);
                            }
                            else {
                                Picasso.with(getApplicationContext()).load(globalClass.getProfil_pic()).into(image1);
                            }
                            edt_name.setText(globalClass.getFname());
                            last.setText(globalClass.getLname());
                            edt_firsr.setText(globalClass.getFname());
                            edt_last.setText(globalClass.getLname());

                            edt_mail.setText(globalClass.getEmail());
                            edt_email.setText(globalClass.getEmail());
                            edt_school.setText(globalClass.getSchool_name());
                            edt_school1.setText(globalClass.getSchool_name());
                            Log.d(TAG, "getFname last name: "+globalClass.getFname()+" "+globalClass.getLname());
                            Log.d(TAG, "email "+globalClass.getEmail());
                            Log.d(TAG, "school name: "+globalClass.getSchool_name());


                            FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();




                        }else{

                            FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                        }

                         pd.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                // pd.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });


    }



}

