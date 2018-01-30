package com.example.administrator.zhibodemo.ui.utils;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import com.example.administrator.zhibodemo.R;

import org.zackratos.ultimatebar.UltimateBar;

/**
 * Created by Administrator on 2018/1/8.
 */

public class UltimateBarUtils {

    public static void showUltimateBar(Activity activity){
        UltimateBar ultimateBar =  new UltimateBar(activity);
        ultimateBar.setColorBar(ContextCompat.getColor(activity, R.color.pink),50);
    }
}
