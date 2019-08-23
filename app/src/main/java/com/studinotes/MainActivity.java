package com.studinotes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.studinotes.AdapterClass.R;
import com.studinotes.guide.DoodleGuideActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.hzw.doodle.DoodleActivity;
import cn.hzw.doodle.DoodleParams;
import cn.hzw.doodle.DoodleView;



import static cn.hzw.doodle.DoodleActivity.KEY_PARAMS;

public class MainActivity extends Activity {

    public static final int REQ_CODE_SELECT_IMAGE = 100;
    public static final int REQ_CODE_DOODLE = 101;
    private TextView mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){

                }

            }
        }

        findViewById(R.id.btn_select_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // ImageSelectorActivity.startActivityForResult(REQ_CODE_SELECT_IMAGE, MainActivity.this, null, false);

/*

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_CODE_SELECT_IMAGE);
*/

              /*  Intent doodle_ac=new Intent(MainActivity.this,DoodleActivity.class);
               startActivity(doodle_ac);*/




                DoodleParams params = new DoodleParams();
                params.mIsFullScreen = true;
                // 图片路径
                params.mImagePath = null;
                // 初始画笔大小
                params.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
                // 画笔颜色
                params.mPaintColor = Color.RED;
                // 是否支持缩放item
                params.mSupportScaleItem = true;

                Intent intent1 = new Intent(MainActivity.this, DoodleActivity.class);
                intent1.putExtra(KEY_PARAMS, params);
                startActivity(intent1);


            }
        });

/*
        findViewById(R.id.btn_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoodleGuideActivity.class));
            }
        });
*/

/*
        findViewById(R.id.btn_mosaic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MosaicDemo.class));
            }
        });
*/
/*
        findViewById(R.id.btn_scale_gesture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScaleGestureItemDemo.class));
            }
        });
*/
        mPath = (TextView) findViewById(R.id.img_path);
    }
    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (data == null) {
                return;
            }

            Uri imageUri = data.getData();

            Log.d("TAGGG", "imageUri = "+imageUri);

            File file = new File(imageUri.getPath());

            Log.d("TAGGG", "file1 = "+imageUri.getPath());
            Log.d("TAGGG", "file2 = "+file.getAbsolutePath());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            }catch (Exception e){
                e.printStackTrace();
            }

            DoodleParams params = new DoodleParams();
            params.mIsFullScreen = true;
            // 图片路径
            params.mImagePath = saveBitmapAndGetPath(bitmap);
            // 初始画笔大小
            params.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
            // 画笔颜色
            params.mPaintColor = Color.RED;
            // 是否支持缩放item
            params.mSupportScaleItem = true;
            // 启动涂鸦页面
            DoodleActivity.startActivityForResult(MainActivity.this, params, REQ_CODE_DOODLE);


        } else if (requestCode == REQ_CODE_DOODLE) {
            if (data == null) {
                return;
            }
            if (resultCode == DoodleActivity.RESULT_OK) {
                String path = data.getStringExtra(DoodleActivity.KEY_IMAGE_PATH);
                if (TextUtils.isEmpty(path)) {
                    return;
                }
              //  ImageLoader.getInstance(this).display(findViewById(R.id.img), path);
                mPath.setText(path);
            } else if (resultCode == DoodleActivity.RESULT_ERROR) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result = "";
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getApplicationContext().getContentResolver().query(contentURI, proj, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx); // Exception raised HERE
                cursor.close(); }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private String saveBitmapAndGetPath(Bitmap bmp) {

        String path = null;

        try {

            // imgMain.setImageBitmap(bmp);
            File f = new File(get_HBHG_Directory() + "myimg" + ".jpg");
            FileOutputStream fos = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            Log.d("TAGGG", "real path = "+f.getAbsolutePath());

            path = f.getAbsolutePath();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return path;
    }


    public String getPathOfAppInternalStorage() {

        String FILE = Environment.getExternalStorageDirectory().getAbsolutePath().toString();

        //  return mContext.getApplicationContext().getFilesDir().getAbsolutePath();
        return FILE;
    }

    public File Make_Directory(){

        File dbDirectory = new File(getPathOfAppInternalStorage() + "/" + "DoodleSha");
        if(!dbDirectory.exists()) {
            dbDirectory.mkdir();
        }

        return dbDirectory;
    }


    public String get_HBHG_Directory() {
        String FILE = Make_Directory().toString();

        FILE = FILE + "/";


        Log.d("TAG", "dir = "+FILE);

        //  return mContext.getApplicationContext().getFilesDir().getAbsolutePath();
        return FILE;
    }
}
