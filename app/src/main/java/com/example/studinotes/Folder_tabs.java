package com.example.studinotes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Folder_tabs extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

   /* Tab1_adapter recycler_foldergrid;
    ArrayList<String> types1;*/

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    // RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_tabs);

        final View touchView = findViewById(R.id.view);
        touchView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
/*

        types1 = new ArrayList<>();
        recyclerView = findViewById(R.id.pager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycler_foldergrid = new Tab1_adapter(this, types1);
       // recycler_foldergrid.setClickListener(this);
        recyclerView.setAdapter(recycler_foldergrid);*/

      /*  mLayoutManager = new GridLayoutManager(mContext,3);
        recycler_foldergrid.setLayoutManager(mLayoutManager);*/

    /*    //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        //Initializing the tablayout
        tabLayout =  findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(R.string.study).setIcon(R.mipmap.studynotesselected));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.assess).setIcon(R.mipmap.assessments));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.time).setIcon(R.mipmap.timemanagement));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tips).setIcon(R.mipmap.tipsntricks));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        //Creating our pager adapter
        Pager1 adapter1 = new Pager1(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter1);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

        if(tabLayout.getSelectedTabPosition() == 0){
            tabLayout.getTabAt(0).setIcon(R.mipmap.studynotesselected);
            tabLayout.getTabAt(1).setIcon(R.mipmap.assessments);
            tabLayout.getTabAt(2).setIcon(R.mipmap.timemanagement);
            tabLayout.getTabAt(3).setIcon(R.mipmap.tipsntricks);
            //Toast.makeText(MainActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
        }else if(tabLayout.getSelectedTabPosition() == 1){
            tabLayout.getTabAt(1).setIcon(R.mipmap.assessmentsselected);
            tabLayout.getTabAt(0).setIcon(R.mipmap.studynotes);
            tabLayout.getTabAt(2).setIcon(R.mipmap.timemanagement);
            tabLayout.getTabAt(3).setIcon(R.mipmap.tipsntricks);
            //Toast.makeText(MainActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
        }else if(tabLayout.getSelectedTabPosition() == 2){
            tabLayout.getTabAt(2).setIcon(R.mipmap.timemanagementselected);
            tabLayout.getTabAt(0).setIcon(R.mipmap.studynotes);
            tabLayout.getTabAt(1).setIcon(R.mipmap.assessments);
            tabLayout.getTabAt(3).setIcon(R.mipmap.tipsntricks);
            //Toast.makeText(MainActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
        }else if(tabLayout.getSelectedTabPosition() == 3){
            tabLayout.getTabAt(3).setIcon(R.mipmap.tipsntricksselected);
            tabLayout.getTabAt(0).setIcon(R.mipmap.studynotes);
            tabLayout.getTabAt(1).setIcon(R.mipmap.assessments);
            tabLayout.getTabAt(2).setIcon(R.mipmap.timemanagement);
            //Toast.makeText(MainActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
        }
    }

    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new TwoFragment(), "TWO");
        adapter.addFrag(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }
*/
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
