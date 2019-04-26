package com.studinotes.AdapterClass;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.studinotes.Fragments.Assessment;
import com.studinotes.Fragments.StudyNotesFoldersOnClick;
import com.studinotes.Fragments.TimeManagement;
import com.studinotes.Fragments.TipsnTricks;

public class StudyNotesFoldersOnClick_Tabs_Homepage_Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    String id,foldername;
    String TAG="abc";

    //Constructor to the class
    public StudyNotesFoldersOnClick_Tabs_Homepage_Pager(FragmentManager fm, int tabCount,String id,String foldername) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.id=id;
        this.foldername=foldername;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("folder_name", foldername);



                StudyNotesFoldersOnClick folder = new StudyNotesFoldersOnClick();
                folder.setArguments(bundle);



                Log.d(TAG, "StudyNotesFoldersOnClick: "+id);
                return folder;
            case 1:
                Assessment tab2 = new Assessment();
                return tab2;
            case 2:
                TimeManagement tab3 = new TimeManagement();
                return tab3;
            case 3:
                TipsnTricks tab4 = new TipsnTricks();
                return tab4;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}

