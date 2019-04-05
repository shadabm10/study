package com.example.studinotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Theme.Utils;

//import static com.example.studinotes.Sharedpreference.THEME_WHITE;

public class Setting  extends AppCompatActivity {

    TextView logout,editpicture;
    RelativeLayout main2,nameedit;
    ImageButton edit,back1;
    Button savebutton,bck1, bck2, bck3, bck4, bck5, bck6, bck7,bck8;

    Sharedpreference sharedpreference;


    public void onCreate(Bundle savedInstanceState) {
       // setTheme(R.style.MyFourthTheme);
        super.onCreate(savedInstanceState);
        sharedpreference = new Sharedpreference(this);

        Utils.onActivityCreateSetTheme(this, sharedpreference.getStyle());
        setContentView(R.layout.settings);

        editpicture = findViewById(R.id.editpicture);
        nameedit = findViewById(R.id.nameedit);
        savebutton = findViewById(R.id.savebutton);
        edit = findViewById(R.id.edit);
        back1= findViewById(R.id.back1);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editpicture.setVisibility(View.VISIBLE);
                nameedit.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                savebutton.setVisibility(View.VISIBLE);
            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editpicture.setVisibility(View.INVISIBLE);
                nameedit.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);
                savebutton.setVisibility(View.INVISIBLE);
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Setting.this, HomePage.class);
                startActivity(intent);
            }
        });


        main2 = findViewById(R.id.main2);
        Utils.changeBackgroundColor(this, main2, sharedpreference.getStyle());
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Login.class);
                startActivity(intent);
            }
        });
        bck1 =  findViewById(R.id.biscuit);
        bck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // main2.setBackgroundResource(R.color.biscuit);
                Utils.changeToTheme(Setting.this, Utils.THEME_DEFAULT);
               // Utils.changeToTheme(this, new Sharedpreference(this).retrieveInt("theme", THEME_WHITE));

                sharedpreference.saveStyle(Utils.THEME_DEFAULT);
            }
        });

        bck2 = findViewById(R.id.skyblue);
        bck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // main2.setBackgroundResource(R.color.skyblue);
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
    }

