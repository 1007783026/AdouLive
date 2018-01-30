package com.example.administrator.zhibodemo.ui.ui.regist;

import android.text.TextUtils;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by Administrator on 2018/1/1.
 */

public class RegistPresenter implements RegistContract.Presenter{

    RegistContract.View mView;
    RegistActivity registActivity;

    public RegistPresenter(RegistContract.View mView){
        this.mView=mView;
        registActivity= (RegistActivity) mView;
    }
    @Override
    public void regist(String acount, String pass, String confirmPass) {
        if(TextUtils.isEmpty(acount)
                ||TextUtils.isEmpty(pass)
                ||TextUtils.isEmpty(confirmPass)){
            mView.registInfoEmpty();
            return;
        }

        if(acount.length()<6
                ||pass.length()<8
                ||confirmPass.length()<8){
            mView.registInfoError();
            return;
        }

        if(!pass.equals(confirmPass)){
            mView.registConfirmPassError();
            return;
        }

        realRegist(acount,pass);
    }

    public void realRegist(String acount, String pass){

        ILiveLoginManager.getInstance().tlsRegister(acount, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mView.registSuccess();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mView.registError(errCode, errMsg);
            }
        });
    }


}
