package com.studinotes.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.studinotes.AdapterClass.R;
import com.studinotes.Utils.GlobalClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivityNotepad extends Activity {
     String TAG="ActivityNotepad";
    private WebView webView;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessages;
    private Uri mCapturedImageURI = null;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int FILECHOOSER_RESULTCODE = 1;
    ImageButton back1;
    GlobalClass globalClass;
    String device_id,user_id,parent_id,parent_id_from_edit,from,file_id;
    public static String URL_DEV = "http://lab-3.sketchdemos.com/P1184_studiNotes/api/add_edit_note/";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        back1=findViewById(R.id.back1);
        globalClass=(GlobalClass)getApplicationContext();
        parent_id=getIntent().getStringExtra("parent_folder_id");
        parent_id_from_edit=getIntent().getStringExtra("parent_id");
        from=getIntent().getStringExtra("from");
        file_id=getIntent().getStringExtra("file_id");
        device_id=globalClass.getDeviceid();
        user_id=globalClass.getId();
        webView = findViewById(R.id.webView);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ActivityNotepad.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ActivityNotepad.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){

                }

            }

        }
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        Log.d(TAG, "onCreate: "+from);
      //  Log.d(TAG, "onCreate: "+URL_DEV+parent_id+"/"+user_id+"/"+"add");
        //webView.loadUrl(URL_DEV+parent_id+"/"+user_id+"/"+"add"+"/");
       // webView.loadUrl("http://lab-3.sketchdemos.com/P1184_studiNotes/api/add_edit_note/7/3/add");
        // webView.getSettings().setDatabasePath("/data/data/" + webView.getContext().getPackageName() + "/databases/");
        if(from.equals("add")) {

            webView.loadUrl(URL_DEV+parent_id+"/"+user_id+"/"+"add");
          //  webView.loadUrl("http://lab-3.sketchdemos.com/P1184_studiNotes/api/add_edit_note/7/3/add/123213");
        }
        else if(from.equals("edit")){
            webView.loadUrl(URL_DEV+parent_id_from_edit+"/"+user_id+"/"+file_id);

        }
        // pd.dismiss();
        webView.setWebChromeClient(new WebChromeClient() {
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (mProgress == null) {
                    mProgress = new ProgressDialog(ActivityNotepad.this);
                    mProgress.show();
                }
                mProgress.setMessage("Loading the notepad"+ " " + String.valueOf(progress) + "%");
                if (progress == 100) {
                    mProgress.dismiss();
                    mProgress = null;
                }
            }
            // For api level bellow 24
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http")) {
                    // Return false means, web view will handle the link
                    return false;
                }

                return false;
            }


            // From api level 24
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                /*Toast.makeText(mcontext, "New Method",Toast.LENGTH_SHORT).show();*/

                // Get the tel: url
                String url = request.getUrl().toString();

                if (url.startsWith("http")) {
                    // Return false means, web view will handle the link
                    return false;
                }

                return false;
            }

            // openFileChooser for Android 3.0+

           /* public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                openImageChooser();
            }*/

            // For Lollipop 5.0+ Devices

            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                mUploadMessages = filePathCallback;
                openImageChooser();
                return true;
            }

            // openFileChooser for Android < 3.0

           /* public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            //openFileChooser for other Android versions

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }*/
        });


    }

    private void openImageChooser() {
        try {

            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "FolderName");

            if (!imageStorageDir.exists()) {
                imageStorageDir.mkdirs();
            }
            File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
            mCapturedImageURI = Uri.fromFile(file);
            Log.d(TAG, "openImageChooser: "+mCapturedImageURI);

            final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");

            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == FILECHOOSER_RESULTCODE) {

            if (null == this.mUploadMessage && null == this.mUploadMessages) {
                return;
            }

           /* Uri result;
            if (requestCode != RESULT_OK){
                result = null;
            }else {
                result = intent == null ? this.mCapturedImageURI : intent.getData();
            }
            this.mUploadMessage.onReceiveValue(result);
            this.mUploadMessage = null;*/
            if (null != mUploadMessage) {
                handleUploadMessage(requestCode, resultCode, intent);

            } else if (mUploadMessages != null) {
                handleUploadMessages(requestCode, resultCode, intent);
            }
        }
    }

    private void handleUploadMessage(int requestCode, int resultCode, Intent intent) {
        Uri result = null;
        try {
            if (resultCode != RESULT_OK) {
                result = null;
            } else {
                // retrieve from the private variable if the intent is null

                result = intent == null ? mCapturedImageURI : intent.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleUploadMessages(int requestCode, int resultCode, Intent intent) {
        Uri[] results = null;
        try {
            if (resultCode != RESULT_OK) {
                results = null;
            } else {
                if (intent != null) {
                    String dataString = intent.getDataString();
                    ClipData clipData = intent.getClipData();
                    if (clipData != null) {
                        results = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            results[i] = item.getUri();
                        }
                    }
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                } else {
                    results = new Uri[]{mCapturedImageURI};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUploadMessages.onReceiveValue(results);
        mUploadMessages = null;
    }
    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(ActivityNotepad.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

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

}