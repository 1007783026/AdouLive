package com.example.administrator.zhibodemo.ui.weight;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.zhibodemo.R;

/**
 * Created by Administrator on 2018/1/17.
 */

public class EditProfileDialog_erweima{

    Activity activity;
    Dialog dialog;
    LayoutInflater inflater;
    private View v;
    private WindowManager windowManager;
    private int screenWidth;

    public EditProfileDialog_erweima(Activity activity) {
        this.activity = activity;
        //把dialog实例化
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    public EditProfileDialog_erweima(Activity activity, int styleid) {
        this.activity = activity;
        //把dialog实例化
        dialog = new Dialog(activity, styleid);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    public void init() {
        inflater = LayoutInflater.from(activity);
        windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        //把dialog布局填充进来
        v = inflater.inflate(R.layout.dialog_erweima, null, false);
        dialog.setContentView(v);
        //在这给dialog设置宽高
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void hide() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setCancelable(boolean value) {
        dialog.setCancelable(value);
    }


}