package com.example.Theme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.studinotes.R;
import com.example.studinotes.Sharedpreference;

public class Utils {

   // public static boolean isPurchsed = true;
    private static int cTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_ONE = 1;
    public final static int THEME_TWO = 2;
    public final static int THEME_THREE = 3;
    public final static int THEME_FOUR = 4;
    public final static int THEME_FIVE = 5;
    public final static int THEME_SIX = 6;
    public final static int THEME_SEVEN = 7;






    public static void changeToTheme(Activity activity, int theme) {

        cTheme = theme;
        //onActivityCreateSetTheme(activity);
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));


    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity, int theme) {
        switch (theme) {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_ONE:
                activity.setTheme(R.style.MySecondTheme);
                break;
            case THEME_TWO:
                activity.setTheme(R.style.MyThirdTheme);
                break;
            case THEME_THREE:
                activity.setTheme(R.style.MyFourthTheme);
                break;
            case THEME_FOUR:
                activity.setTheme(R.style.MyFifthTheme);
                break;
            case THEME_FIVE:
                activity.setTheme(R.style.MySixthTheme);
                break;
            case THEME_SIX:
                activity.setTheme(R.style.MySeventhTheme);
                break;
            case THEME_SEVEN:
                activity.setTheme(R.style.MyEigthTheme);
                break;
        }
    }

    public static void changeBackgroundColor(Context context, View view, int theme) {
        switch (theme) {
            default:
            case THEME_DEFAULT:

                view.setBackgroundColor(context.getResources().getColor(R.color.biscuit));

                break;
            case THEME_ONE:
                view.setBackgroundColor(context.getResources().getColor(R.color.skyblue));
                break;
            case THEME_TWO:
                view.setBackgroundColor(context.getResources().getColor(R.color.pesto));
                break;
            case THEME_THREE:
                view.setBackgroundColor(context.getResources().getColor(R.color.fave));
                break;
            case THEME_FOUR:
                view.setBackgroundColor(context.getResources().getColor(R.color.calmgreen));
                break;
            case THEME_FIVE:
                view.setBackgroundColor(context.getResources().getColor(R.color.lightgrey));
                break;
            case THEME_SIX:
                view.setBackgroundColor(context.getResources().getColor(R.color.pink));
                break;
            case THEME_SEVEN:
                view.setBackgroundColor(context.getResources().getColor(R.color.lightblue));
                break;
        }
    }

}