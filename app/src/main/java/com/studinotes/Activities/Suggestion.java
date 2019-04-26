package com.studinotes.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;
import com.studinotes.Fragments.TipsnTricks;
import com.studinotes.Utils.Utils;

public class Suggestion extends AppCompatActivity {

    ImageButton back1;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;

    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest);

        TipsnTricks fragment = (TipsnTricks) getSupportFragmentManager().findFragmentByTag(TipsnTricks.TAG);
        if(fragment != null){
            // Do something with fragment

            // Add your parameters
            //  TipsnTricks fragment = TipsnTricks.newInstance(10);
            // R.id.container - the id of a view that will hold your fragment; usually a FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_layout4, fragment, TipsnTricks.TAG)
                    .commit();
        }





        sharedpreference = new Sharedpreference(Suggestion.this);
        main_layout = findViewById(R.id.main_layout);

        back1 = findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onPostResume() {

        Utils.changeBackgroundColor(Suggestion.this, main_layout, sharedpreference.getStyle());
        super.onPostResume();
    }
}
