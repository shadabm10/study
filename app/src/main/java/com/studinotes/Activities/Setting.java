package com.studinotes.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

//import static com.example.studinotes.Sharedpreference.THEME_WHITE;

public class Setting  extends AppCompatActivity {
    String TAG = "login_screen";
    String deviceid;
    File p_image;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    TextView edt_firsr,edt_mail,edt_school,last;
    EditText edt_name,edt_school1,edt_email,edt_last;
    ProgressDialog pd;
    TextView logout;
    CircularImageView image1;
    RelativeLayout main2,nameedit,first,name;
    ImageButton edit,back1;
    Button savebutton,bck1, bck2, bck3, bck4, bck5, bck6, bck7,bck8,editpicture,white;

    Sharedpreference sharedpreference;
    private static int RESULT_LOAD_IMAGE = 1;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;




    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedpreference = new Sharedpreference(this);

        Utils.onActivityCreateSetTheme(this, sharedpreference.getStyle());
        setContentView(R.layout.settings);

        editpicture = findViewById(R.id.editpicture);
        nameedit = findViewById(R.id.nameedit);
        name = findViewById(R.id.name);
        savebutton = findViewById(R.id.savebutton);
        edit = findViewById(R.id.edit);
        back1= findViewById(R.id.back1);
        edt_name= findViewById(R.id.edt_firsr);
        edt_school1= findViewById(R.id.two1);
        edt_email= findViewById(R.id.three1);
        edt_firsr= findViewById(R.id.one);
        edt_mail= findViewById(R.id.three);
        edt_school= findViewById(R.id.two);
        image1= findViewById(R.id.image);
        white= findViewById(R.id.white);
        last= findViewById(R.id.last);
        edt_last= findViewById(R.id.edt_last);
        deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        globalClass = (GlobalClass)getApplicationContext();
        prefrence = new Shared_Preference(Setting.this);
        prefrence.loadPrefrence();
        pd=new ProgressDialog(Setting.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
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
         getProfile();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameedit.setVisibility(View.VISIBLE);
                name.setVisibility(View.GONE);
                editpicture.setVisibility(View.VISIBLE);
                savebutton.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
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

                nameedit.setVisibility(View.GONE);
                name.setVisibility(View.VISIBLE);
                editpicture.setVisibility(View.GONE);
                savebutton.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
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
        bck1 =  findViewById(R.id.biscuit);
        bck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // main2.setBackgroundResource(R.color.biscuit);
                //first.setBackgroundResource(R.color.green);
                Utils.changeToTheme(Setting.this, Utils.THEME_DEFAULT);
               // Utils.changeToTheme(this, new Sharedpreference(this).retrieveInt("theme", THEME_WHITE));

                sharedpreference.saveStyle(Utils.THEME_DEFAULT);
            }
        });
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // main2.setBackgroundResource(R.color.biscuit);
                //first.setBackgroundResource(R.color.green);
                Utils.changeToTheme(Setting.this, Utils.THEME_EIGHT);
                // Utils.changeToTheme(this, new Sharedpreference(this).retrieveInt("theme", THEME_WHITE));

                sharedpreference.saveStyle(Utils.THEME_EIGHT);
            }
        });

        bck2 = findViewById(R.id.skyblue);
        bck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // main2.setBackgroundResource(R.color.skyblue);
               // second.setBackgroundResource(R.color.green);
                Utils.changeToTheme(Setting.this, Utils.THEME_ONE);
                sharedpreference.saveStyle(Utils.THEME_ONE);
            }
        });


        bck3 =  findViewById(R.id.pesto);
        bck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // main2.setBackgroundResource(R.color.pesto);
                Utils.changeToTheme(Setting.this, Utils.THEME_TWO);
                sharedpreference.saveStyle(Utils.THEME_TWO);
            }
        });

        bck4 =  findViewById(R.id.fave);
        bck4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // main2.setBackgroundResource(R.color.fave);
                Utils.changeToTheme(Setting.this, Utils.THEME_THREE);
                sharedpreference.saveStyle(Utils.THEME_THREE);
            }
        });

        bck5 =  findViewById(R.id.calmgreen);
        bck5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // main2.setBackgroundResource(R.color.fave);
                Utils.changeToTheme(Setting.this, Utils.THEME_FOUR);
                sharedpreference.saveStyle(Utils.THEME_FOUR);
            }
        });
        bck6 =  findViewById(R.id.lightgrey);
        bck6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.changeToTheme(Setting.this, Utils.THEME_FIVE);
                sharedpreference.saveStyle(Utils.THEME_FIVE);
            }
        });
        bck7 =  findViewById(R.id.pink);
        bck7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.changeToTheme(Setting.this, Utils.THEME_SIX);
                sharedpreference.saveStyle(Utils.THEME_SIX);
            }
        });
        bck8 =  findViewById(R.id.lightblue);
        bck8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.changeToTheme(Setting.this, Utils.THEME_SEVEN);
                sharedpreference.saveStyle(Utils.THEME_SEVEN);
            }
        });


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


    public void updateProfile(final String fname,final String lname,final String school){

         pd.show();

        String url = AppConfig.URL_DEV+"updateUserProfile";
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        params.put("userid", globalClass.getId());
        params.put("Firstname",fname);
        params.put("Lastname", lname);
        params.put("school_name", school);
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

