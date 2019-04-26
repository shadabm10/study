package com.studinotes.DialogClass;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.StudyNotes_adapter;
import com.studinotes.Utils.FeedItem;

import java.util.ArrayList;

public class StudyNotesFoldersOnClick_Dialog extends Dialog implements
        android.view.View.OnClickListener {
    StudyNotes_adapter adapter;
    public Activity c;
    public Dialog d;
    String TAG="folder";
    String colorCode;
    ProgressDialog pd;
    RecyclerView rv_main;
    public ArrayList<FeedItem> feedlist;
    public Button create, upload,insert,open,save,cancel,mustard,navy,black,darkgrey,terracotta;
    LinearLayout folder;
    RelativeLayout dialog_main;

    public StudyNotesFoldersOnClick_Dialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.studynotesfoldersonclick_dialog);

        pd = new ProgressDialog(getContext());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        pd.setMessage("Loading...");
        create = (Button) findViewById(R.id.create1);
        upload = (Button) findViewById(R.id.upload);
        insert = (Button) findViewById(R.id.add_image);
        open = (Button) findViewById(R.id.add_video);
        folder = (LinearLayout) findViewById(R.id.folder);
        dialog_main =  findViewById(R.id.dialog_main);

           feedlist=new ArrayList<>();
        final Button mainbutton = (Button) findViewById(R.id.mainbutton);
        final LinearLayout colors = (LinearLayout) findViewById(R.id.colors);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AddFolder();


            }
        });

        upload.setOnClickListener(this);
        insert.setOnClickListener(this);
        open.setOnClickListener(this);




    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_no:
                dismiss();
            case R.id.upload:
                dismiss();
                break;
            case R.id.add_image:
                dismiss();
                break;
            case R.id.add_video:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}