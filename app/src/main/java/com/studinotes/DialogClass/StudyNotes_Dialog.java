package com.studinotes.DialogClass;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.StudyNotes_adapter;

public class StudyNotes_Dialog extends Dialog implements
        android.view.View.OnClickListener {
    String TAG="study";
    public Activity c;
    public Dialog d;
    public Button yes,no,mustard,navy,black,darkgrey,terracotta;
    EditText name;
    Button mainbutton;
    String colorCode;


    public StudyNotes_Dialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.studynotes_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        name =  findViewById(R.id.folder_name);
        no = (Button) findViewById(R.id.btn_no);
        mustard = (Button) findViewById(R.id.mustardbutton);
        navy = (Button) findViewById(R.id.navybutton);
        black = (Button) findViewById(R.id.blackbutton);
        darkgrey = (Button) findViewById(R.id.darkgreybutton);
        terracotta = (Button) findViewById(R.id.terracottabutton);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        mustard.setOnClickListener(this);
        navy.setOnClickListener(this);
        black.setOnClickListener(this);
        darkgrey.setOnClickListener(this);
        terracotta.setOnClickListener(this);

        mainbutton =  findViewById(R.id.mainbutton);
        final LinearLayout colors =  findViewById(R.id.colors);
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
                colorCode="#FAE876";
                Log.d(TAG, "colorCode: "+colorCode);
            }
        });
        navy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainbutton.setBackgroundResource(R.color.navy);
                colorCode="#182458";
                Log.d(TAG, "colorCode: "+colorCode);

            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainbutton.setBackgroundResource(R.color.black);
                colorCode="#000000";
                Log.d(TAG, "colorCode: "+colorCode);

            }
        });
        darkgrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainbutton.setBackgroundResource(R.color.darkgrey);
                colorCode="#3F3F3F";
                Log.d(TAG, "colorCode: "+colorCode);
            }
        });
        terracotta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainbutton.setBackgroundResource(R.color.terracotta);
                colorCode="#C92700";
                Log.d(TAG, "colorCode: "+colorCode);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                CreateFolder();
               String folder_name=name.getText().toString();
                String  color = String.valueOf(((ColorDrawable)mainbutton.getBackground()).getColor());
                Log.d(TAG, "Button: "+color);

                break;
            case R.id.btn_no:
                dismiss();
           /* case R.id.mustardbutton:
                mainbutton.setBackgroundResource(R.color.mustard);
            case R.id.navybutton:
                mainbutton.setBackgroundResource(R.color.navy);
            case R.id.blackbutton:
                mainbutton.setBackgroundResource(R.color.black);
            case R.id.darkgreybutton:
                mainbutton.setBackgroundResource(R.color.darkgrey);
            case R.id.terracottabutton:
                mainbutton.setBackgroundResource(R.color.terracotta);
                break;*/
            default:
                break;
        }
        dismiss();
    }

    public void CreateFolder() {
        String folder_name=name.getText().toString();
      //  StudyNotes_adapter mLog = new StudyNotes_adapter();


    }

}