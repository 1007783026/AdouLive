package com.example.administrator.zhibodemo.ui.app;

import android.app.Application;

import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;
import com.example.administrator.zhibodemo.ui.engine.MessageObservable;
import com.example.administrator.zhibodemo.ui.qiniu.QiniuConfig;
import com.example.administrator.zhibodemo.ui.qiniu.QiniuUploadHelper;
import com.example.administrator.zhibodemo.ui.timcustom.CustomTimConstant;
import com.tencent.TIMManager;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLog;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */

public class MyApplication extends Application{

    static  MyApplication app;
    TimUserProfile timUserProfile;//个人信息bean
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        initLiveSdk();
        //初始化七牛
        initQiniuSdk();
    }
    private void initQiniuSdk() {
        QiniuUploadHelper.init(QiniuConfig.SPACENAME,QiniuConfig.SecretKey,QiniuConfig.AccessKey);
    }

    //初始化直播的sdk
    private void initLiveSdk() {
        if(MsfSdkUtils.isMainProcess(this)){    // 仅在主线程初始化
            // 初始化LiveSDK
            ILiveLog.setLogLevel(ILiveLog.TILVBLogLevel.DEBUG);
            ILiveSDK.getInstance().initSdk(this, 1400059671, 21089);
            //初始化聊天message被观察者
            ILVLiveManager.getInstance().init(new ILVLiveConfig()
                    .setLiveMsgListener(MessageObservable.getInstance()));

        }
    }

    public  static MyApplication getApp(){
        return app;
    }

    //信息页获得个人信息
    public void setTimUserProfile(TimUserProfile profile){
        if (profile!=null){
            timUserProfile=profile;
        }
    }
    public TimUserProfile getTimUserProfile(){
        return  timUserProfile;
    }

}