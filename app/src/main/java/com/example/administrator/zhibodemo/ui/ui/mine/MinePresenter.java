package com.example.administrator.zhibodemo.ui.ui.mine;

import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;
import com.example.administrator.zhibodemo.ui.engine.TimProfileHelper;
import com.example.administrator.zhibodemo.ui.ui.MainActivity;

/**
 * Created by Administrator on 2018/1/4.
 */

public class MinePresenter implements MineContract.Presenter{

    private MineContract.View mView;
    private MineFragment mineFragment;

    public MinePresenter(MineContract.View mView){
        this.mView=mView;
        mineFragment= (MineFragment) mView;
    }

    @Override
    public void getUserProfile() {
        new TimProfileHelper().getSelfProfile(mineFragment.getActivity(),new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(TimUserProfile mProfile) {
                mView.updateProfile(mProfile);
            }

            @Override
            public void noGet() {
                mView.updateProfileError();
            }
        });
    }
}
