package com.studinotes.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.Activities.HomePage;
import com.studinotes.Activities.Setting;
import com.studinotes.AdapterClass.AdapterListFolder;
import com.studinotes.AdapterClass.AdapterPDF;
import com.studinotes.AdapterClass.AdapterWord;
import com.studinotes.AdapterClass.AddSubFolder;
import com.studinotes.AdapterClass.StudyNotes_adapter;
import com.studinotes.AdapterClass.TimeManagement_adapter;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.FeedItem;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;
import com.studinotes.Utils.Utils;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;
import com.studinotes.AdapterClass.StudyNotesFoldersOnClick_Audio;
import com.studinotes.DialogClass.StudyNotesFoldersOnClick_Dialog;
import com.studinotes.AdapterClass.StudyNotesFoldersOnClick_Document;
import com.studinotes.AdapterClass.StudyNotesFoldersOnClick_Folder;
import com.studinotes.AdapterClass.StudyNotesFoldersOnClick_Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;
import static com.studinotes.Fragments.TipsnTricks.TAG;

public class StudyNotesFoldersOnClick extends Fragment {

    private static final int IMPORTPATIENT_ACTIVITY_REQUEST_CODE =1 ;
    private static final int PICKFILE_REQUEST_CODE = 1;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    ImageView next;
    String colorCode;
    String id,foldername;
    AddSubFolder studyNotes_adapter;
    public ArrayList<FeedItem> feedlist;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ImageButton add,back4,search,cross,settings;
    ArrayList<HashMap<String,String>> folderList;
    ArrayList<HashMap<String,String>> foldersearch;
    ArrayList<HashMap<String,String>> foldersearchList;

    ArrayList<HashMap<String,String>> fileDataList;
    ArrayList<HashMap<String,String>> fileData;
    ArrayList<HashMap<String,String>> newSeacrh;
    RecyclerView rv_main;
    LinearLayout searchfield;
    TextView textFile,textView;
    File imageFile;
    Context context;
    String extension_withdot;
    EditText search_folder_file;
    AdapterListFolder adapterListFolder;;
   String TAG="StudyNotesFoldersOnClick";
    TextView tv_name1;

    StudyNotesFoldersOnClick_Folder adapter;
    private static final int PICKFILE_RESULT_CODE = 1;
    private static  final int PICK_IMAGE=1;

    AdapterWord adapterWord;
    FragmentManager fragmentManager;
    LinearLayout ll_main;
    RecyclerView.LayoutManager mLayoutManager,mLayoutManager1,mLayoutManager2,mLayoutManager3,mLayoutManager4,layoutManager5;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;
    String   filename1;
    String filename;
    ProgressDialog pd;
    String file_type;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.studynotesfoldersonclick, container, false);

        sharedpreference = new Sharedpreference(getActivity());
        main_layout = rootView.findViewById(R.id.main_layout);
        globalClass = (GlobalClass)getActivity().getApplicationContext();
        prefrence = new Shared_Preference(getActivity());
        prefrence.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        pd.setMessage("Loading...");
        folderList=new ArrayList<>();
        fragmentManager = getActivity().getSupportFragmentManager();

        fileDataList=new ArrayList<>();
        feedlist=new ArrayList<>();
        fileData=new ArrayList<>();
        foldersearch=new ArrayList<>();
        foldersearchList=new ArrayList<>();
         ll_main=rootView.findViewById(R.id.ll_main);
        textView=rootView.findViewById(R.id.textView);
         rv_main=rootView.findViewById(R.id.rv_main);
         tv_name1=rootView.findViewById(R.id.tv_name1);
         search_folder_file=rootView.findViewById(R.id.area);
        Bundle arguments = getArguments();


        if (arguments!=null) {
            id = arguments.get("id").toString();
            foldername = arguments.get("folder_name").toString();
        }
        // id = getArguments().getString("id");
      //  foldername = getArguments().getString("folder_name");
        globalClass.setFolderid(id);
        globalClass.setFolderanme(foldername);
        Log.d(TAG, "id: "+id);
        textView.setText(foldername);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onCreate: ");
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){
                    Log.d(TAG, "onCreate: ");
                }

            }

        }
        FolderList();
        ArrayList<String> planetList= new ArrayList<>();
        planetList.add("Mustard");
        planetList.add("Navy");
        planetList.add("Black");
        planetList.add("Dark Grey");
        planetList.add("Terracotta");




        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rv_main.setLayoutManager(mLayoutManager1);
            /* Search files and folder*/
        search_folder_file.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(globalClass.isNetworkAvailable()){


                        FolderListSearch(search_folder_file.getText().toString());


                }else{
                    FancyToast.makeText(getActivity(), "Check Internet Connecton", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                FolderList();

            }
        });


        add = rootView.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog1 = new Dialog(getActivity());
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.studynotesfoldersonclick_dialog);
                Button add_image=dialog1.findViewById(R.id.add_image);
                Button dialogButton = dialog1.findViewById(R.id.create1);
                Button upload_doc = dialog1.findViewById(R.id.upload);
                Button add_video = dialog1.findViewById(R.id.add_video);
                upload_doc.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.setType("*/*");
                        startActivityForResult(intent, PICKFILE_RESULT_CODE);
                        dialog1.dismiss();

                    }});
                add_video.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("video/*");

                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_IMAGE );
                        dialog1.dismiss();

                    }});
                add_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                        dialog1.dismiss();
                    }
                });

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                           dialog1.dismiss();
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.studynotes_dialog);
                        final Button yes,no,mustard,navy,black,darkgrey,terracotta;

                        final Button mainbutton;
                        final EditText name;
                        final LinearLayout colors =  dialog.findViewById(R.id.colors);
                        yes = dialog.findViewById(R.id.btn_yes);
                        //  name =  dialog.findViewById(R.id.folder_name);
                        name =  dialog.findViewById(R.id.folder_name);
                        no = dialog.findViewById(R.id.btn_no);
                        mustard = dialog.findViewById(R.id.mustardbutton);
                        navy = dialog.findViewById(R.id.navybutton);
                        black = dialog. findViewById(R.id.blackbutton);
                        darkgrey = dialog. findViewById(R.id.darkgreybutton);
                        terracotta = dialog. findViewById(R.id.terracottabutton);
                        mainbutton =  dialog.findViewById(R.id.mainbutton);
                        yes.setOnClickListener(this);
                        no.setOnClickListener(this);
                        mustard.setOnClickListener(this);
                        navy.setOnClickListener(this);
                        black.setOnClickListener(this);
                        darkgrey.setOnClickListener(this);
                        terracotta.setOnClickListener(this);
                        mainbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                colors.setVisibility(View.VISIBLE);
                            }
                        });
                        mustard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mainbutton.setBackgroundResource(R.color.mustard);
                                colorCode ="#FAE876";
                                // Log.d(TAG, "colorCode: "+ colorCode);
                            }
                        });
                        navy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mainbutton.setBackgroundResource(R.color.navy);
                                colorCode ="#182458";
                                // Log.d(TAG, "colorCode: "+ colorCode);

                            }
                        });
                        black.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mainbutton.setBackgroundResource(R.color.black);
                                colorCode ="#000000";
                                // Log.d(TAG, "colorCode: "+ colorCode);

                            }
                        });
                        darkgrey.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mainbutton.setBackgroundResource(R.color.darkgrey);
                                colorCode ="#3F3F3F";
                                // Log.d(TAG, "colorCode: "+ colorCode);
                            }
                        });
                        terracotta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mainbutton.setBackgroundResource(R.color.terracotta);
                                colorCode ="#C92700";
                                // Log.d(TAG, "colorCode: "+ colorCode);
                            }
                        });

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String NAME=name.getText().toString();

                                if(NAME.equals(""))
                                {

                                    Toast.makeText(getActivity(),"please insert Name",Toast.LENGTH_LONG).show();
                                }
                                else {


                                    //   ArrayList feed = new ArrayList<FeedItem>();
                                    FeedItem Feed = new FeedItem(NAME,colorCode);
                                    studyNotes_adapter = new AddSubFolder(feedlist, getActivity(),pd,globalClass.getFolderid());
                                    rv_main.setAdapter(studyNotes_adapter);
                                    feedlist.add(Feed);
                                    studyNotes_adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                    if(feedlist.size()>0) {
                                        FeedItem feedlast = feedlist.get(feedlist.size() - 1);
                                        Log.d(TAG, "onClick: "+feedlast.getColorCode());
                                    }
                                }

                            }
                        });

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });

                dialog1.show();
            }


        });

        search = rootView.findViewById(R.id.search);
        searchfield = rootView.findViewById(R.id.searchfield);
        cross = rootView.findViewById(R.id.cross);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.VISIBLE);

            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.INVISIBLE);


            }
        });

        back4 = rootView.findViewById(R.id.back4);


        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomePage.class);
                startActivity(intent);
            }
        });

        settings = rootView.findViewById(R.id.settings);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });

        return rootView;


        }

    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getActivity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {

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



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = new Uri.Builder().build();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICKFILE_REQUEST_CODE: {
                    String path = new File(data.getData().getPath()).getAbsolutePath();
                    String path1=data.getData().getPath();

                    if (path != null) {
                        uri = data.getData();
                        Log.d(TAG, "uri: "+uri);

                        try {
                            imageFile = new File(getFilePath(getActivity(),uri));
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onActivityResult: "+imageFile);
                       Log.d(TAG, "onActivityResult: "+path1);

                        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                        if (cursor == null) { // Source is Dropbox or other similar local file path
                            filename = uri.getPath();
                        } else {
                            cursor.moveToFirst();
                            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                            filename = cursor.getString(idx);
                            cursor.close();
                        }

                    filename1  = filename.substring(0, filename.lastIndexOf("."));

                        String extension = filename.substring(filename.lastIndexOf(".") + 1);
                         extension_withdot = filename.substring(filename.lastIndexOf(".") );
                        Log.d(TAG, "onActivityResult: "+extension);
                        Log.d(TAG, "filename: "+filename1);
                        Log.d(TAG, "filename: "+extension_withdot);
                        if(extension.equals("jpeg")||(extension.equals("jpg"))||(extension.equals("png"))){
                            file_type="image";
                        }
                        else if(extension.equals("doc")){
                            file_type="MSWord";
                        }
                        else if(extension.equals("pdf")){
                            file_type="pdf";
                        }
                        else if(extension.equals("ppt")){
                            file_type="powerpoint";
                        }
                        else if(extension.equals("flv"))
                        {
                            file_type="video";
                        }
                        else if(extension.equals("mov")){
                            file_type="video";
                        }
                        else if(extension.equals("mp4")){
                            file_type="video";
                        }
                        else if(extension.equals("wmv")){
                            file_type="video";
                        }
                        else if(extension.equals("avi")){
                            file_type="video";
                        }
                        CustomDialog();
                    }
                    break;
                }
            }
        }
    }


public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
    String selection = null;
    String[] selectionArgs = null;
    // Uri is different in versions after KITKAT (Android 4.4), we need to
    if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
        if (isExternalStorageDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            return Environment.getExternalStorageDirectory() + "/" + split[1];
        } else if (isDownloadsDocument(uri)) {
            final String id = DocumentsContract.getDocumentId(uri);
            uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
        } else if (isMediaDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];
            if ("image".equals(type)) {
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            selection = "_id=?";
            selectionArgs = new String[]{
                    split[1]
            };
        }
    }
    if ("content".equalsIgnoreCase(uri.getScheme())) {


        if (isGooglePhotosUri(uri)) {
            return uri.getLastPathSegment();
        }

        String[] projection = {
                MediaStore.Images.Media.DATA
        };
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(uri, projection, selection, selectionArgs, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
        }
    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
        return uri.getPath();
    }
    return null;
}

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    public void CustomDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);


        // set the custom dialog components - text, image and button
        final EditText text = dialog.findViewById(R.id.folder_name);
        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        Button btn_no = dialog.findViewById(R.id.btn_no);


         text.setText(filename1);

        // if button is clicked, close the custom dialog
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFile(text.getText().toString());
                dialog.dismiss();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public void AddFile(final String reason){

        pd.show();

        String url = AppConfig.URL_DEV+"addFile";
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("userid", globalClass.getId());
        params.put("folder_id", globalClass.getFolderid());
        params.put("file_type", file_type);
        params.put("file_name", reason+extension_withdot);


        try {
            params.put("file_url", imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Log.d(TAG , "URL "+url);
        Log.d(TAG , "Add File "+params);


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

                        if(status==1 ) {

                            pd.dismiss();
// Reload current fragment
                            Fragment currentFragment = getFragmentManager().findFragmentById(R.id.contentContainer);
                            if (currentFragment instanceof StudyNotesFoldersOnClick) {
                                FragmentTransaction fragTransaction =  getFragmentManager().beginTransaction();
                                fragTransaction.detach(currentFragment);
                                fragTransaction.attach(currentFragment);
                                fragTransaction.commit();}

                         //   FancyToast.makeText(getActivity(), message, FancyToast.LENGTH_LONG,
                              //      FancyToast.SUCCESS, false).show();
                            FolderList();



                        }
                        else {

                            FancyToast.makeText(getActivity(), message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                        }


                        // pd.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                 pd.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });


    }

    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout, sharedpreference.getStyle());

        super.onResume();
    }
    public void FolderListSearch(final String search) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"folderAndFileList", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "seacr " + response);

                pd.dismiss();
                foldersearch.clear();
                foldersearchList.clear();
                rv_main.removeAllViews();


                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("folderData");
                        JsonArray fileData=jobj.getAsJsonArray("fileData");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String parent_id = images1.get("parent_id").toString().replaceAll("\"", "");
                            String folder_name = images1.get("folder_name").toString().replaceAll("\"", "");
                            String color_code = images1.get("color_code").toString().replaceAll("\"", "");
                            String delete_flag = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("parent_id", parent_id);
                            hashMap.put("folder_name", folder_name);
                            hashMap.put("color_code", color_code);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);

                            foldersearchList.add(hashMap);
                            Log.d(TAG, "Folder Data " + hashMap);

                            adapter = new StudyNotesFoldersOnClick_Folder(foldersearchList,getActivity(),fragmentManager);
                            rv_main.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }


                        for (int i = 0; i < fileData.size(); i++) {
                            JsonObject object = fileData.get(i).getAsJsonObject();
                            String file_type = object.get("file_type").toString().replaceAll("\"", "");

                            JsonArray file = object.getAsJsonArray("file");

                            HashMap<String, String> hashMap1 = new HashMap<>();
                            hashMap1.put("file_type", file_type);
                            hashMap1.put("file", file.toString());


                            foldersearch.add(hashMap1);
                        }


                        setMainViewNew();

                    } else{
                        FancyToast.makeText(getActivity(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                    }



                } catch (Exception e) {
                    e.printStackTrace();
                    //  FancyToast.makeText(getActivity(), "DATA NOT FOUND", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                FancyToast.makeText(getActivity(), "DATA NOT FOUND", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("userid",globalClass.getId());
                params.put("folder_id",globalClass.getFolderid());
                params.put("search_name",search);

                Log.d(TAG, "Show Folder: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }








    public void FolderList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"folderAndFileList", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                folderList.clear();
                fileDataList.clear();
                rv_main.removeAllViews();


                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("folderData");
                        JsonArray fileData=jobj.getAsJsonArray("fileData");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String parent_id = images1.get("parent_id").toString().replaceAll("\"", "");
                            String folder_name = images1.get("folder_name").toString().replaceAll("\"", "");
                            String color_code = images1.get("color_code").toString().replaceAll("\"", "");
                            String delete_flag = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("parent_id", parent_id);
                            hashMap.put("folder_name", folder_name);
                            hashMap.put("color_code", color_code);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);

                            folderList.add(hashMap);
                           Log.d(TAG, "Folder Data " + hashMap);

                            adapter = new StudyNotesFoldersOnClick_Folder(folderList,getActivity(),fragmentManager);
                            rv_main.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }


                        for (int i = 0; i < fileData.size(); i++) {
                            JsonObject object = fileData.get(i).getAsJsonObject();
                            String file_type = object.get("file_type").toString().replaceAll("\"", "");

                            JsonArray file = object.getAsJsonArray("file");

                            HashMap<String, String> hashMap1 = new HashMap<>();
                            hashMap1.put("file_type", file_type);
                            hashMap1.put("file", file.toString());


                            fileDataList.add(hashMap1);
                        }


                        setMainView();

                    } else{
                        FancyToast.makeText(getActivity(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                    }



                } catch (Exception e) {
                    e.printStackTrace();
                  //  FancyToast.makeText(getActivity(), "DATA NOT FOUND", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                FancyToast.makeText(getActivity(), "DATA NOT FOUND", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("userid",globalClass.getId());
                params.put("folder_id",globalClass.getFolderid());

                Log.d(TAG, "Show Folder: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }




    public ArrayList<HashMap<String, String>> getListFromJson(String json){

        Log.d(TAG, "json 1 = " + json);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(json);

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject subfile = jsonArray.getJSONObject(j);
                String id = subfile.getString("id").replaceAll("\"", "");
                String user_id = subfile.get("user_id").toString().replaceAll("\"", "");
                String folder_id = subfile.get("folder_id").toString().replaceAll("\"", "");
                String file_name = subfile.get("file_name").toString().replaceAll("\"", "");
                String delete_flag = subfile.get("delete_flag").toString().replaceAll("\"", "");
                String is_active = subfile.get("is_active").toString().replaceAll("\"", "");
                String entry_date = subfile.get("entry_date").toString().replaceAll("\"", "");
                String modified_date = subfile.get("modified_date").toString().replaceAll("\"", "");
                String file_type_inner = subfile.get("file_type").toString().replaceAll("\"", "");
                String file_url = subfile.get("file_url").toString().replaceAll("\"", "");


                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", id);
                hashMap.put("user_id", user_id);
                hashMap.put("folder_id", folder_id);
                hashMap.put("file_name", file_name);
                hashMap.put("file_type_inner", file_type_inner);
                hashMap.put("delete_flag", delete_flag);
                hashMap.put("is_active", is_active);
                hashMap.put("entry_date", entry_date);
                hashMap.put("modified_date", modified_date);
                hashMap.put("file_url", file_url);

                list.add(hashMap);


            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


    public void setMainView(){

        ll_main.removeAllViews();
       // fileDataList.clear();


        for (int i = 0; i < fileDataList.size(); i++){

            ll_main.addView(getMainView(fileDataList.get(i)));

        }



    }


    public void setMainViewNew(){

        ll_main.removeAllViews();
        // fileDataList.clear();


        for (int i = 0; i < foldersearch.size(); i++){

            ll_main.addView(getMainView(foldersearch.get(i)));

        }



    }


    public View getMainView(HashMap<String, String> hashMap){


        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.new_list, null);

        // In order to get the view we have to use the new view with text_layout in it
        TextView textView = (TextView)view.findViewById(R.id.tv_name);
        textView.setText(hashMap.get("file_type"));
        RecyclerView dynamic_recycler = view.findViewById(R.id.recycler_main);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        dynamic_recycler.setLayoutManager(mLayoutManager);


        Log.d(TAG, "list = " + getListFromJson(hashMap.get("file")));
        Log.d(TAG, "list size = " + getListFromJson(hashMap.get("file")).size());

        adapterWord = new AdapterWord(getListFromJson(hashMap.get("file")),getActivity());
        dynamic_recycler.setAdapter(adapterWord);
        adapterWord.notifyDataSetChanged();




        return view;
    }



}

