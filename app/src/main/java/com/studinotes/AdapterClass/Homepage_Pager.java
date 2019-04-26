package com.studinotes.AdapterClass;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.studinotes.Fragments.Assessment;
import com.studinotes.Fragments.StudyNotes;
import com.studinotes.Fragments.TimeManagement;
import com.studinotes.Fragments.TipsnTricks;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Homepage_Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Homepage_Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                StudyNotes tab1 = new StudyNotes();
                return tab1;
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
