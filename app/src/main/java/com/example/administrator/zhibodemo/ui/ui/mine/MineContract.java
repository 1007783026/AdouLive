package com.example.administrator.zhibodemo.ui.ui.mine;

import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;

/**
 * Created by Administrator on 2018/1/4.
 */

public interface MineContract {

    interface View{
        //更新
        void updateProfile(TimUserProfile profile);
        //失败
        void updateProfileError();
    }
    interface  Presenter{
        //获得用户信息
        void getUserProfile();
    }
}
