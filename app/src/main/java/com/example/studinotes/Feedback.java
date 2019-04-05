package com.example.studinotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.Theme.Utils;

public class Feedback extends AppCompatActivity {

    ImageButton back2;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;

    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        sharedpreference = new Sharedpreference(Feedback.this);
        main_layout = findViewById(R.id.main_layout);

        back2 = findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this,HomePage.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPostResume() {

        Utils.changeBackgroundColor(Feedback.this, main_layout, sharedpreference.getStyle());

        super.onPostResume();
    }
}
