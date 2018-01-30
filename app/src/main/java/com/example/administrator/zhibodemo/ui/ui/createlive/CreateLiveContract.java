package com.example.administrator.zhibodemo.ui.ui.createlive;

/**
 * Created by Administrator on 2018/1/21.
 */

public interface CreateLiveContract {
    interface  View{
        void onCreateSuccess();//成功
        void onCreateFailed();//失败
    }
    interface Presenter{
        void createLive(String url,String liveName);//创建直播
    }
}
