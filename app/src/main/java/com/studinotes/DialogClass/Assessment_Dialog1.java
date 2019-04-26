package com.studinotes.DialogClass;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.studinotes.AdapterClass.R;

public class Assessment_Dialog1 extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public Assessment_Dialog1(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.assessment_dialog1);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

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
            case R.id.btn_yes:
                c.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
