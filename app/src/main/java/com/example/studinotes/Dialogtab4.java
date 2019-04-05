package com.example.studinotes;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Dialogtab4 extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button create, upload,insert,open;

    public Dialogtab4(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogtab4);
        create = (Button) findViewById(R.id.create1);
        upload = (Button) findViewById(R.id.upload);
        insert = (Button) findViewById(R.id.insert);
        open = (Button) findViewById(R.id.open);
        create.setOnClickListener(this);
        upload.setOnClickListener(this);
        insert.setOnClickListener(this);
        open.setOnClickListener(this);

        /*final Button mainbutton = (Button) findViewById(R.id.mainbutton);
        final LinearLayout colors = (LinearLayout) findViewById(R.id.colors);
        mainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                colors.setVisibility(View.VISIBLE);
            }
        });*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                c.finish();
                break;
            case R.id.upload:
                dismiss();
                break;
            case R.id.insert:
                dismiss();
                break;
            case R.id.open:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}