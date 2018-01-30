package com.example.administrator.zhibodemo.ui.ui.profile;

import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;
import com.example.administrator.zhibodemo.ui.engine.TimProfileHelper;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/1/14.
 */

public class EditProfilePresenter implements EditProfileContract.Presenter{

    EditProfileContract.View view;
    EditProfileActivity editProfileActivity;

    public EditProfilePresenter(EditProfileContract.View view){
        this.view=view;
        editProfileActivity= (EditProfileActivity) view;

    }
    @Override
    public void getUserInfo() {
        //从app中拿
        TimProfileHelper.getInstance().getSelfProfile(editProfileActivity, new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(TimUserProfile mProfile) {
                view.updateView(mProfile);
            }

            @Override
            public void noGet() {
                view.onGetInfoFailed();
            }
        });
    }

    @Override
    public void onUpdateInfoSuccess() {
        //更新application信息
        TimProfileHelper.getInstance().resetApplicationProfile(new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(TimUserProfile mProfile) {
                view.updateView(mProfile);

            }

            @Override
            public void noGet() {

            }
        });
    }
}
