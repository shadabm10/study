package com.example.studinotes;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreference  {

    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    private String fcm;

    private static final String PREFS_NAME = "preferences0001";
    public static final String key_style = "key_style";


   /* public final static int THEME_DEFAULT = 0;
    public final static int THEME_WHITE = 1;
    public final static int THEME_BLUE = 2;

    public static final String PREF_logInStatus = "logInStatus";
    public static final String PREF_f_name = "f_name";
    public static final String PREF_l_name = "l_name";
    public static final String PREF_email = "email";
    public static final String PREF_phone_number = "phone_number";
    public static final String PREF_user_type = "user_type";
    public static final String PREF_id = "id";
    public static final String PREF_profile_img = "profile_img";

    public static final String is_login = "is_login";
    public static final String f_cm = "fcm";
    public static final String shipping_address = "shipping_address";
    public static final String shipping_address_id = "shipping_address_id";
    public static final String cart_count = "cart_count";
    public static final String apply_gst_tax = "apply_gst_tax";
    public static final String all_taxes_include = "all_taxes_include";*/


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
