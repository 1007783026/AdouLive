package com.example.administrator.zhibodemo.ui.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;
import com.example.administrator.zhibodemo.ui.ui.MainActivity;
import com.example.administrator.zhibodemo.ui.ui.profile.EditProfileActivity;
import com.example.administrator.zhibodemo.ui.utils.ImageUtils;
import com.example.administrator.zhibodemo.ui.utils.ToastUtils;
import com.example.administrator.zhibodemo.ui.utils.UltimateBarUtils;
import com.tencent.TIMFriendGenderType;

import org.zackratos.ultimatebar.UltimateBar;

/**
 * 资料展示
 */
public class MineFragment extends Fragment implements View.OnClickListener, MineContract.View {

    private RelativeLayout rl_profile;
    private ImageView iv_avatar;//头像
    private TextView tv_nickname;//昵称
    private TextView tv_acount_id;//阿斗号
    private ImageView iv_gender;//性别
    private TextView tv_my_fork;//关注
    private TextView tv_my_fans;//粉丝

    private MinePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(v);
        initPresenter();
        UltimateBarUtils.showUltimateBar(getActivity());
        return v;
    }



    private void initPresenter() {
        presenter = new MinePresenter(this);
    }


    private void initView(View v) {
        rl_profile = (RelativeLayout) v.findViewById(R.id.rl_profile);
        iv_avatar = (ImageView) v.findViewById(R.id.iv_avatar);
        iv_gender = (ImageView) v.findViewById(R.id.iv_gender);
        tv_nickname = (TextView) v.findViewById(R.id.tv_nickname);
        tv_acount_id = (TextView) v.findViewById(R.id.tv_acount_id);
        tv_my_fork = (TextView) v.findViewById(R.id.tv_my_fork);
        tv_my_fans = (TextView) v.findViewById(R.id.tv_my_fans);
        rl_profile.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_profile:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUserProfile();
    }

    @Override
    public void updateProfile(TimUserProfile profile) {
        //更新view,设置图片跟字段
        if (!TextUtils.isEmpty(profile.getProfile().getFaceUrl())) {
            ImageUtils.getInstance().loadCircle(profile.getProfile().getFaceUrl(), iv_avatar);
        } else {
            ImageUtils.getInstance().loadCircle(R.mipmap.ic_launcher, iv_avatar);
        }


        if (!TextUtils.isEmpty(profile.getProfile().getNickName())) {
            tv_nickname.setText(profile.getProfile().getNickName());
        } else {
            tv_nickname.setText("暂无昵称");
        }
        if (!TextUtils.isEmpty(profile.getProfile().getIdentifier())) {
            tv_acount_id.setText("阿斗号:" + profile.getProfile().getIdentifier());
        }
        if (profile.getProfile().getGender() == TIMFriendGenderType.Male) {
            iv_gender.setBackgroundResource(R.mipmap.boy);
        } else if (profile.getProfile().getGender() == TIMFriendGenderType.Female) {
            iv_gender.setBackgroundResource(R.mipmap.girl);
        } else {
            //默认是男
            iv_gender.setBackgroundResource(R.mipmap.girl);
        }
        tv_my_fork.setText(profile.getFork() + "");
        tv_my_fans.setText(profile.getFans() + "");
    }

    @Override
    public void updateProfileError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show("获取信息失败");
            }
        });
    }
}
