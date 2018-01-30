package com.example.administrator.zhibodemo.ui.weight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.engine.PicChooseHelper;

/**
 * Created by Administrator on 2018/1/8.
 * 换头像弹出自定义dialog布局
 */

public class SelectPicDialog implements View.OnClickListener {

    private TextView tv_title,tv_quxiao;
    private LinearLayout ll_photo,ll_camera;

    Activity activity;
    LayoutInflater inflater;
    private View v;
    Dialog dialog;
    private WindowManager wm;
    private Display display;
    private ViewGroup.LayoutParams layoutParams;

    public SelectPicDialog(@NonNull Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    public SelectPicDialog(@NonNull Activity activity, int themeResId) {
        this.activity = activity;
        //把dialog实例化
        dialog = new Dialog(activity, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void show() {
        dialog.show();
    }

    private void init() {
        wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        inflater = LayoutInflater.from(activity);

        v = inflater.inflate(R.layout.dialog_photo, null, false);
        ll_photo = (LinearLayout)v.findViewById(R.id.ll_photo);
        ll_camera = (LinearLayout)v.findViewById(R.id.ll_camera);
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        tv_quxiao = (TextView) v.findViewById(R.id.tv_quxiao);
        ll_photo.setOnClickListener(this);
        ll_camera.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);

        dialog.setContentView(v);
        //通过window设置dialog的宽高和位置
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = display.getWidth();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_photo:
                PicChooseHelper.getInstance(activity).startPhotoSelectIntent();
                break;
            case R.id.ll_camera:
                PicChooseHelper.getInstance(activity).startCameraIntent();
                break;
            case R.id.tv_quxiao:
                dialog.dismiss();
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
