package com.example.administrator.zhibodemo.ui.utils;

import android.widget.Toast;

import com.example.administrator.zhibodemo.ui.app.MyApplication;

/**
 * Created by Administrator on 2018/1/1.
 */

public class ToastUtils {
    static Toast toast;

    public static void show(String str) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getApp().getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }

}
