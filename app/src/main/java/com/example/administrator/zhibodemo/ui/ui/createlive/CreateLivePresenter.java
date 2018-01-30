package com.example.administrator.zhibodemo.ui.ui.createlive;

import android.content.Intent;
import android.text.TextUtils;

import com.example.administrator.zhibodemo.ui.app.MyApplication;
import com.example.administrator.zhibodemo.ui.bean.CreateliveInfo;
import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;
import com.example.administrator.zhibodemo.ui.httputil.BaseOnRequestComplete;
import com.example.administrator.zhibodemo.ui.httputil.Constants;
import com.example.administrator.zhibodemo.ui.httputil.OkHttpHelper;
import com.example.administrator.zhibodemo.ui.ui.hostlive.HostLiveActivity;
import com.tencent.TIMUserProfile;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/21.
 */

public class CreateLivePresenter implements CreateLiveContract.Presenter {
    CreateLiveContract.View view;
    CreateLiveActivity mActivity;

    @Override
    public void createLive(String url, String liveName) {
        //创建直播
        if (TextUtils.isEmpty(url)|| TextUtils.isEmpty(liveName)){
            view.onCreateFailed();
        }else {
            //尝试去创建
          /*
             先把封面和房间名称 包括创建者id，创建者昵称，创建者头像 (application有缓存)  传给服务器，返回信息中包含roomid
           */
            requestRoomId(url,liveName);
        }



    }
    //获取房间id
    private void requestRoomId(String cover,String liveName) {

        HashMap<String, String> map = new HashMap<>();
        TimUserProfile profile = MyApplication.getApp().getTimUserProfile();
        TIMUserProfile profile1 = profile.getProfile();
        map.put("action","create");     //动作
        map.put("userId",profile1.getIdentifier());  //主播id
        map.put("userAvatar",profile1.getFaceUrl()); //主播头像
        map.put("userName",profile1.getNickName()); //主播昵称
        map.put("liveTitle",liveName); //直播标题
        map.put("liveCover",cover); //直播封面


        OkHttpHelper.getInstance().postObject(Constants.HOST, map, new BaseOnRequestComplete<CreateliveInfo>() {
            @Override
            public void onSuccess(CreateliveInfo info) {
                //当创建直播成功后
                if (info!=null){
                    int roomId = info.getData().getRoomId();
                    if (roomId!=0){
                        Intent intent = new Intent(mActivity, HostLiveActivity.class);
                        intent.putExtra("roomId",roomId);
                        mActivity.startActivity(intent);
                    }
                }
                else{
                    //数据为空
                    onEmpty();
                }
            }
        }, CreateliveInfo.class);
    }

    public CreateLivePresenter(CreateLiveContract.View view) {
        this.view = view;
        mActivity=(CreateLiveActivity)view;
    }
}