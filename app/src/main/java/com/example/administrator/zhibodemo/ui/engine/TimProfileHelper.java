package com.example.administrator.zhibodemo.ui.engine;

import android.app.Activity;

import com.example.administrator.zhibodemo.ui.app.MyApplication;
import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;
import com.example.administrator.zhibodemo.ui.timcustom.CustomTimConstant;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/4.
 * 不知道是什么,应该是用户信息
 */

public class TimProfileHelper {
    private static TimProfileHelper helper;

    public static TimProfileHelper getInstance() {
        if (helper == null) {
            helper = new TimProfileHelper();
        }
        return helper;
    }

    public interface OnProfileGet {
        void onGet(TimUserProfile mProfile);
        void noGet();
    }

    //拿到个人信息方法
    public void getSelfProfile(final Activity activity, final OnProfileGet onProfileGet) {
        //先从app中拿
        TimUserProfile adouTimUserProfile = MyApplication.getApp().getTimUserProfile();
        if (adouTimUserProfile != null) {
            onProfileGet.onGet(adouTimUserProfile);
            return;
        }
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                if (onProfileGet != null)
                    onProfileGet.noGet();

            }

            @Override
            public void onSuccess(TIMUserProfile profile) {
                TimUserProfile mProfile = new TimUserProfile();
                //设置整个profile
                if (profile != null) {
                    mProfile.setProfile(profile);
                }
                //附加信息
                Map<String, byte[]> customInfo = profile.getCustomInfo();
                if (customInfo!=null&&!customInfo.isEmpty()) {

                    if (customInfo.containsKey(CustomTimConstant.INFO_GRADE)) {
                        byte[] gradeBytes = customInfo.get(CustomTimConstant.INFO_GRADE);
                        if (gradeBytes != null) {
                            //设置等级
                            mProfile.setGrade(Integer.parseInt(new String(gradeBytes)));
                        }
                    }
                    if (customInfo.containsKey(CustomTimConstant.INFO_RECEIVE)) {
                        byte[] receiveBytes = customInfo.get(CustomTimConstant.INFO_RECEIVE);
                        if (receiveBytes != null) {
                            //设置接收的礼物
                            mProfile.setReceive(Integer.parseInt(new String(receiveBytes)));
                        }
                    }
                    if (customInfo.containsKey(CustomTimConstant.INFO_SEND)) {
                        byte[] sendBytes = customInfo.get(CustomTimConstant.INFO_SEND);
                        if (sendBytes != null) {
                            //设置发送出去的礼物
                            mProfile.setSend(Integer.parseInt(new String(sendBytes)));
                        }
                    }
                    if (customInfo.containsKey(CustomTimConstant.INFO_XINGZUO)) {
                        byte[] xingzuoBytes = customInfo.get(CustomTimConstant.INFO_XINGZUO);
                        if (xingzuoBytes != null) {
                            //设置星族
                            mProfile.setXingzuo(new String(xingzuoBytes));
                        }
                    }
                }
                MyApplication.getApp().setTimUserProfile(mProfile);
                if (onProfileGet != null)
                    onProfileGet.onGet(mProfile);


            }
        });
    }

    //重置应用中保存的信息
    public void resetApplicationProfile(final OnProfileGet onProfileGet) {
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                onProfileGet.noGet();
            }

            @Override
            public void onSuccess(TIMUserProfile profile) {
                TimUserProfile mProfile = new TimUserProfile();
                //设置整个profile
                if (profile != null) {
                    mProfile.setProfile(profile);
                    onProfileGet.onGet(mProfile);
                }
                //附加信息
                Map<String, byte[]> customInfo = profile.getCustomInfo();
                if (customInfo!=null&&customInfo.size() > 0) {

                    if (customInfo.containsKey(CustomTimConstant.INFO_GRADE)) {
                        byte[] gradeBytes = customInfo.get(CustomTimConstant.INFO_GRADE);
                        if (gradeBytes != null) {
                            mProfile.setGrade(Integer.parseInt(new String(gradeBytes)));
                        }
                    }
                    if (customInfo.containsKey(CustomTimConstant.INFO_RECEIVE)) {
                        byte[] receiveBytes = customInfo.get(CustomTimConstant.INFO_RECEIVE);
                        if (receiveBytes != null) {
                            mProfile.setReceive(Integer.parseInt(new String(receiveBytes)));
                        }
                    }
                    if (customInfo.containsKey(CustomTimConstant.INFO_SEND)) {
                        byte[] sendBytes = customInfo.get(CustomTimConstant.INFO_SEND);
                        if (sendBytes != null) {
                            mProfile.setSend(Integer.parseInt(new String(sendBytes)));
                        }
                    }
                    if (customInfo.containsKey(CustomTimConstant.INFO_XINGZUO)) {
                        byte[] xingzuoBytes = customInfo.get(CustomTimConstant.INFO_XINGZUO);
                        if (xingzuoBytes != null) {
                            mProfile.setXingzuo(new String(xingzuoBytes));

                        }
                    }
                }
                MyApplication.getApp().setTimUserProfile(mProfile);
            }


        });
    }
}