package com.example.studinotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.Theme.Utils;

public class Report extends AppCompatActivity {

    ImageButton back3;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;

    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        sharedpreference = new Sharedpreference(Report.this);
        main_layout = findViewById(R.id.main_layout);

        back3 = findViewById(R.id.back3);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this,HomePage.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPostResume() {

        Utils.changeBackgroundColor(Report.this, main_layout, sharedpreference.getStyle());
        super.onPostResume();
    }
}
