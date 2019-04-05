package com.example.studinotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.Theme.Utils;

public class Suggestion extends AppCompatActivity {

    ImageButton back1;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;

    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest);

        sharedpreference = new Sharedpreference(Suggestion.this);
        main_layout = findViewById(R.id.main_layout);

        back1 = findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suggestion.this,HomePage.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPostResume() {

        Utils.changeBackgroundColor(Suggestion.this, main_layout, sharedpreference.getStyle());
        super.onPostResume();
    }
}
