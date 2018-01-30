package com.example.administrator.zhibodemo.ui.ui.login;

import android.content.Intent;
import android.text.TextUtils;

import com.example.administrator.zhibodemo.ui.engine.TimProfileHelper;
import com.example.administrator.zhibodemo.ui.ui.MainActivity;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by Administrator on 2018/1/2.
 */

public class LoginPresenter implements LoginContract.Presenter{

    LoginContract.View mView;
    LoginActivity loginActivity;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        loginActivity = (LoginActivity) mView;
    }


    @Override
    public void login(String acount, String pass) {
        //为空判断
        if (TextUtils.isEmpty(acount) || TextUtils.isEmpty(pass)) {
            mView.loginInfoEmpty();
            return;
        }
        if (acount.length() < 6 || pass.length() < 8) {
            mView.loginInfoError();
            return;
        }
        realLogin(acount, pass);
    }

    private void realLogin(String acount, String pass) {
        ILiveLoginManager.getInstance().tlsLoginAll(acount, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mView.loginSuccess();
                getUserInfo();
                Intent intent = new Intent(loginActivity, MainActivity.class);
                loginActivity.startActivity(intent);
                loginActivity.finish();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mView.loginError(errCode,errMsg);
            }
        });
    }

    //获取用户的个人信息
    private void getUserInfo() {
        new TimProfileHelper().getSelfProfile(loginActivity,null);
    }
}
