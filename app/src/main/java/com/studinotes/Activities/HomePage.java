package com.studinotes.Activities;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;



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
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

      //  loadFragment(new StudyNotes());

        global_class = (GlobalClass)getApplicationContext();



         bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);



        fragment = new StudyNotes();

        bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotesselected);
        loadFragment(fragment);





     //    bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotesselected);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.cart:


                                fragment = new StudyNotes();
                                // item.setCheckable(true);
                                item.setIcon(R.drawable.studynotesselected);
                                bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.assessments);
                                bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timemanagement);
                                bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.tipsntricks);

                                loadFragment(fragment);
                                return true;

                            case R.id.search:


                                fragment = new Assessment();
                               // item.getItemId(0)
                                item.setIcon(R.drawable.assessmentsselected);
                                bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotes);
                                bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timemanagement);
                                bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.tipsntricks);

                                loadFragment(fragment);
                                return true;

                            case R.id.profile:
                                fragment = new TimeManagement();
                                item.setIcon(R.drawable.timemanagementselected);
                                bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotes);
                                bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.assessments);
                                bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.tipsntricks);

                                loadFragment(fragment);
                                return true;

                            case R.id.profile1:


                                fragment = new TipsnTricks();
                                item.setIcon(R.drawable.tipsntricksselected);
                                bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotes);
                                bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.assessments);
                                bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timemanagement);

                                loadFragment(fragment);
                                return true;



                            default:
                                fragment = new StudyNotes();
                                item.setIcon(R.drawable.studynotesselected);
                                bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.assessments);
                                bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timemanagement);
                                bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.tipsntricks);

                                loadFragment(fragment);
                                return true;


                        }

                    }
                });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
               // Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.cart:


                        fragment = new StudyNotes();
                        // item.setCheckable(true);
                        item.setIcon(R.drawable.studynotesselected);
                        bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.assessments);
                        bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timemanagement);
                        bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.tipsntricks);

                        loadFragment(fragment);

                    case R.id.search:


                        fragment = new Assessment();
                        // item.getItemId(0)
                        item.setIcon(R.drawable.assessmentsselected);
                        bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotes);
                        bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timemanagement);
                        bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.tipsntricks);

                        loadFragment(fragment);

                    case R.id.profile:
                        fragment = new TimeManagement();
                        item.setIcon(R.drawable.timemanagementselected);
                        bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotes);
                        bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.assessments);
                        bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.tipsntricks);

                        loadFragment(fragment);

                    case R.id.profile1:


                        fragment = new TipsnTricks();
                        item.setIcon(R.drawable.tipsntricksselected);
                        bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.studynotes);
                        bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.assessments);
                        bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timemanagement);

                        loadFragment(fragment);



                    default:
                        fragment = new StudyNotes();
                        item.setIcon(R.drawable.studynotesselected);
                        bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.assessments);
                        bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timemanagement);
                        bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.tipsntricks);

                        loadFragment(fragment);

                }


            }
        });


     //   mToolbar = (Toolbar) findViewById(R.id.toolbar);







    }

    private void loadFragment(Fragment fragment) {
        // load fragment
      /*  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
*/
        FragmentManager fragmentManager2 = getSupportFragmentManager();
        fragmentManager2.beginTransaction().replace(R.id.contentContainer, fragment).commit();
       // fragmentManager2.addT

    }





    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }}
