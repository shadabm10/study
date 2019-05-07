package com.studinotes.AdapterClass;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreference  {

    private static final String MODUS = "Settings.modus";
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    private String fcm;

    private static final String PREFS_NAME = "preferences0001";
    public static final String key_style = "key_style";





    public Sharedpreference(Context context) {
        this.context = context;

        this.pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.editor = pref.edit();


    }

    public void saveStyle(int value) {
        editor.putInt(key_style, value);
        editor.commit();

    }

    public int getStyle(){
        return pref.getInt(key_style, 0);
    }
}
