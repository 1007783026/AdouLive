package com.example.administrator.zhibodemo.ui.ui.hostlive;

import com.example.administrator.zhibodemo.ui.app.MyApplication;
import com.example.administrator.zhibodemo.ui.engine.live.LiveHelper;
import com.example.administrator.zhibodemo.ui.httputil.BaseOnRequestComplete;
import com.example.administrator.zhibodemo.ui.httputil.Constants;
import com.example.administrator.zhibodemo.ui.httputil.OkHttpHelper;
import com.tencent.TIMUserProfile;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/23.
 */

public class HostLivePresenter implements HostLiveContract.Presenter {

    HostLiveContract.View mView;
    HostLiveActivity hostLiveActivity;
    @Override
    public void createHost(int roomId) {
        LiveHelper.getInstance(hostLiveActivity).createRoom(roomId+"");
    }

    @Override
    public void quitHost(int roomId) {
        //获取userId 和房间id，然后退出
//        TIMUserProfile profile = MyApplication.getApp().getTimUserProfile().getProfile();
//        String userId = profile.getIdentifier();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("action","quit");
//        map.put("userId",userId);
//        map.put("roomId",roomId+""); TIMUserProfile profile = MyApplication.getApp().getTimUserProfile().getProfile();
//        String userId = profile.getIdentifier();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("action","quit");
//        map.put("userId",userId);
//        map.put("roomId",roomId+"");

//        OkHttpHelper.getInstance().postString(Constants.HOST, map, new BaseOnRequestComplete<String>() {
//            @Override
//            public void onSuccess(String s) {
//                hostLiveActivity.finish();
//            }
//        });
    }

    public HostLivePresenter(HostLiveContract.View mView) {
        this.mView = mView;
        hostLiveActivity=(HostLiveActivity)mView;
    }
}
