package com.studinotes.DialogClass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.studinotes.AdapterClass.R;

public class Setting_Dialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button create, camera,gallery,open,save,cancel,mustard,navy,black,darkgrey,terracotta;
    LinearLayout folder;
    RelativeLayout dialog_main;

    public Setting_Dialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_dialog);

        camera = (Button) findViewById(R.id.camera);
        gallery = (Button) findViewById(R.id.gallery);
        cancel = (Button) findViewById(R.id.btn_no);


        cancel.setOnClickListener(this);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);


        /*final Button mainbutton = (Button) findViewById(R.id.mainbutton);
        final LinearLayout colors = (LinearLayout) findViewById(R.id.colors);
        mainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                colors.setVisibility(View.VISIBLE);
            }
        });*/


    }

    public void openGallery(int req_code){

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        c.startActivityForResult(Intent.createChooser(intent,"Select file to upload "), req_code);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no:
                dismiss();
                break;
            case R.id.camera:
                //Toast.makeText(Setting.this,"Entered in On ClickListener", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // intent.setAction(Intent.ACTION_GET_CONTENT);
                c.startActivity(intent);
                break;
            case R.id.gallery:
                openGallery(10);
                break;

            default:
                break;
        }
        dismiss();
    }
}
