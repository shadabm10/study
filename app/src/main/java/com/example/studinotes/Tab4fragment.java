package com.example.studinotes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Tab4fragment extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new Tab4 ()).commit();}
    }

    public Tab4fragment() {

    }
}
