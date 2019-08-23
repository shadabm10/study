package com.studinotes.Activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.studinotes.AdapterClass.R;
import com.studinotes.Fragments.Assessment;
import com.studinotes.Fragments.StudyNotes;
import com.studinotes.Fragments.TimeManagement;
import com.studinotes.Fragments.TipsnTricks;
import com.studinotes.MainActivity;
import com.studinotes.Utils.GlobalClass;

import java.io.File;

import cn.hzw.doodle.DoodleActivity;
import cn.hzw.doodle.DoodleParams;
import cn.hzw.doodle.DoodleView;

public class HomePage extends AppCompatActivity {
     String TAG="Homepage";
    private Toolbar mToolbar;
    GlobalClass global_class;
    Fragment fragment;
    boolean doubleBackToExitPressedOnce = false;
    BottomNavigationView bottomNavigationView;
    public static final int REQ_CODE_SELECT_IMAGE = 100;
    public static final int REQ_CODE_DOODLE = 101;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+requestCode );
        Log.d(TAG, "onActivityResult: "+resultCode );
        Log.d(TAG, "onActivityResult: "+data );
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (data == null) {
                return;
            }



        } else if (requestCode == REQ_CODE_DOODLE) {
            if (data == null) {
                return;
            }
            if (resultCode == DoodleActivity.RESULT_OK) {
                String path = data.getStringExtra(DoodleActivity.KEY_IMAGE_PATH);
                Log.d(TAG, "onActivityResult: "+path);
                if (TextUtils.isEmpty(path)) {
                    return;
                }
                //  ImageLoader.getInstance(this).display(findViewById(R.id.img), path);
               // mPath.setText(path);
            } else if (resultCode == DoodleActivity.RESULT_ERROR) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        }
    }





    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }}
