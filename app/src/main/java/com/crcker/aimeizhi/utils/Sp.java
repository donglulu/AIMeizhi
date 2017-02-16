package com.crcker.aimeizhi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.crcker.aimeizhi.constant.Constant;

/**
 * Created by hasee on 2017/2/16 0016.
 * d
 */

public class Sp {

    public static void saveString(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SPNAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SPNAME, Context.MODE_APPEND);


        return sharedPreferences.getString(key, "yes");

    }
}
