package com.studinotes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.AdapterClass.Homepage_Pager;
import com.studinotes.AdapterClass.R;
import com.studinotes.Fragments.Assessment;
import com.studinotes.Fragments.StudyNotes;
import com.studinotes.Fragments.TimeManagement;
import com.studinotes.Fragments.TipsnTricks;
import com.studinotes.Utils.GlobalClass;

public class HomePage extends AppCompatActivity {

    private Toolbar mToolbar;
    GlobalClass global_class;
    Fragment fragment;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        loadFragment(new StudyNotes());

        global_class = (GlobalClass)getApplicationContext();
       // global_class.setCurrency_Symbol("₹");


       // Mint.initAndStartSession(this.getApplication(), "0ae6c687");


      //  DatabaseHelper databaseHelper = new DatabaseHelper(this);
        //databaseHelper.deleteAll();

        fragment = new StudyNotes();

        loadFragment(fragment);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotesselected);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.cart:


                                fragment = new StudyNotes();
                                // item.setCheckable(true);
                                item.setIcon(R.drawable.selectimage);
                                loadFragment(fragment);
                                return true;

                            case R.id.search:


                                fragment = new Assessment();
                               // item.getItemId(0)
                                item.setIcon(R.drawable.selectasset);
                                loadFragment(fragment);
                                return true;

                            case R.id.profile:
                                fragment = new TimeManagement();
                                item.setIcon(R.drawable.select_time_management);
                                loadFragment(fragment);
                                return true;

                            case R.id.profile1:


                                fragment = new TipsnTricks();
                                item.setIcon(R.drawable.select_tips_trick);
                                loadFragment(fragment);
                                return true;



                            default:
                                fragment = new StudyNotes();
                                item.setIcon(R.drawable.select_tips_trick);
                                loadFragment(fragment);
                                return true;


                        }

                    }
                });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.cart:


                        fragment = new StudyNotes();
                      //  item.setIcon(R.mipmap.studynotes);
                        loadFragment(fragment);


                    case R.id.search:


                        fragment = new Assessment();
                        //item.setIcon(R.mipmap.studynotes);
                        loadFragment(fragment);


                    case R.id.profile:
                        fragment = new TimeManagement();
                       // item.setIcon(R.mipmap.studynotes);
                        loadFragment(fragment);


                    case R.id.profile1:


                        fragment = new TipsnTricks();
                       // item.setIcon(R.mipmap.studynotes);
                        loadFragment(fragment);




                    default:
                        fragment = new StudyNotes();
                        loadFragment(fragment);



                }


            }
        });



     //   mToolbar = (Toolbar) findViewById(R.id.toolbar);







    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }





    @Override
    public void onBackPressed() {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                finish();// finish activity if you are at home screen
                return;
            } else {
                getSupportFragmentManager().popBackStack();//will pop previous fragment
            }
        } catch (Exception e) {
            super.onBackPressed();
        }
    }
}
