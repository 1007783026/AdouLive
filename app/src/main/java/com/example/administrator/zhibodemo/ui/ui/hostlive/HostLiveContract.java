package com.example.administrator.zhibodemo.ui.ui.hostlive;

/**
 * Created by Administrator on 2018/1/23.
 */

public interface HostLiveContract {

    interface View{

    }
    interface  Presenter{
        void createHost(int roomId);
        void quitHost(int roomId);
    }
}
