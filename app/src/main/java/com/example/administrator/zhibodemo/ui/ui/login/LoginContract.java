package com.example.administrator.zhibodemo.ui.ui.login;

/**
 * Created by Administrator on 2018/1/2.
 */

public interface LoginContract {

    interface  View{
        void loginSuccess();
        void loginError(int errCode, String errMsg);
        //输入为空
        void loginInfoEmpty();
        //输入错误
        void loginInfoError();
    }

    interface  Presenter{
        void login(String acount,String pass);
    }
}
