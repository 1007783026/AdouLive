package com.example.administrator.zhibodemo.ui.ui.profile;

import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;

/**
 * Created by Administrator on 2018/1/14.
 */

public interface EditProfileContract {
    interface View{
        void updateView(TimUserProfile profile);
        void onGetInfoFailed();
        //修改失败
        void updateInfoError();
        //修改成功
        void updateInfoSuccess();
    }
    interface  Presenter{
        void getUserInfo();
        void onUpdateInfoSuccess();//重置修改的信息
    }
}
