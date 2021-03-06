package com.example.administrator.zhibodemo.ui.weight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.zhibodemo.R;

/**
 * Created by Administrator on 2018/1/17.
 */

public class EditProfile_Gender_Dialog implements View.OnClickListener {

    private static final String TAG = "EditProfile_Gender_Dial";
    private RadioGroup rg_gender;
    private RadioButton rb_male;
    private RadioButton rb_female;

    public interface OnProfileChangedListener {
        //改变内容成功
        void onChangeSuccess(String value);

        void onChangeError();
    }

    Activity activity;
    Dialog dialog;
    LayoutInflater inflater;
    private TextView tv_title;
    private Button bt_confim;
    private TextView bt_cancel;
    private View v;
    private WindowManager windowManager;
    private int screenWidth;

    private OnProfileChangedListener mListener;

    public EditProfile_Gender_Dialog(Activity activity, OnProfileChangedListener listener) {
        this.activity = activity;
        //把dialog实例化
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mListener = listener;
        init();
    }

    public EditProfile_Gender_Dialog(Activity activity, int styleid, OnProfileChangedListener listener) {
        this.activity = activity;
        //把dialog实例化
        dialog = new Dialog(activity, styleid);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mListener = listener;
        init();
    }

    public void init() {
        inflater = LayoutInflater.from(activity);
        windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        //把dialog布局填充进来
        v = inflater.inflate(R.layout.dialog_edit_profile_gender, null, false);
        Log.e(TAG, "init: hehehehe");
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        rg_gender = (RadioGroup) v.findViewById(R.id.rg_gender);
        rb_male = (RadioButton) v.findViewById(R.id.rb_male);
        rb_female = (RadioButton) v.findViewById(R.id.rb_female);

        bt_confim = (Button) v.findViewById(R.id.bt_confim);
        bt_cancel = (Button) v.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);
        bt_confim.setOnClickListener(this);
        dialog.setContentView(v);
        //在这给dialog设置宽高
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
//        layoutParams.width=screenWidth*80/100;
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confim:
                if (rb_female.isChecked() || rb_male.isChecked()) {
                    if (mListener != null) {
                        mListener.onChangeSuccess(rb_female.isChecked() ? "女生" : "男生");
                    }
                } else {
                    //内容为空时
                    if (mListener != null) {
                        mListener.onChangeSuccess("未知性别");
                    }
                }
                break;
            case R.id.bt_cancel:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }

}