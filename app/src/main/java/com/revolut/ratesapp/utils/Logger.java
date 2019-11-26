package com.revolut.ratesapp.utils;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;

public class Logger {

   static boolean enabled = false;

    public static void logFun(@Nullable String tag, @NonNull String msg){

        if (enabled){

            if (TextUtils.isEmpty(tag)){
                tag = "~~";
            }

            Log.d(tag, msg);

        }


    }

}
