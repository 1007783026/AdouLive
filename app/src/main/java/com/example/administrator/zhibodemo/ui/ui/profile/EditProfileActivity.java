package com.example.administrator.zhibodemo.ui.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.bean.TimUserProfile;
import com.example.administrator.zhibodemo.ui.engine.PicChooseHelper;
import com.example.administrator.zhibodemo.ui.qiniu.QiniuConfig;
import com.example.administrator.zhibodemo.ui.qiniu.QiniuUploadHelper;
import com.example.administrator.zhibodemo.ui.ui.MainActivity;
import com.example.administrator.zhibodemo.ui.utils.ToastUtils;
import com.example.administrator.zhibodemo.ui.utils.UltimateBarUtils;
import com.example.administrator.zhibodemo.ui.weight.EditProfileDialog;
import com.example.administrator.zhibodemo.ui.weight.EditProfileDialog_erweima;
import com.example.administrator.zhibodemo.ui.weight.EditProfileItem;
import com.example.administrator.zhibodemo.ui.weight.EditProfile_Gender_Dialog;
import com.example.administrator.zhibodemo.ui.weight.SelectPicDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendGenderType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;

import org.json.JSONObject;

import java.io.File;

/**
 * 编辑资料
 */
public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.View, View.OnClickListener {

    private Toolbar toolbar;
    private EditProfileItem ep_avatar;//头像
    private EditProfileItem ep_nickname;//昵称
    private EditProfileItem ep_adouid;//id
    private EditProfileItem ep_gender;//性别
    private EditProfileItem ep_location;//地区
    private EditProfileItem ep_geqian;//个签
    private RelativeLayout ep_erweima;//二维码
    private EditProfileContract.Presenter presenter;
    private TextView tv_save;

    SelectPicDialog avatarDialog;
    EditProfileDialog nickNameDialog;
    EditProfile_Gender_Dialog genderDialog;
    EditProfileDialog geQianDialog;
    EditProfileDialog locationDialog;
    EditProfileDialog_erweima erweimaDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
        initListener();
        initPresenter();
        UltimateBarUtils.showUltimateBar(this);
    }

    private void initPresenter() {
        this.presenter = new EditProfilePresenter(this);
        presenter.getUserInfo();
    }

    private void initListener() {
        //设置导航按钮点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ep_avatar.setOnClickListener(this);
        ep_nickname.setOnClickListener(this);
        ep_adouid.setOnClickListener(this);
        ep_gender.setOnClickListener(this);
        ep_location.setOnClickListener(this);
        ep_erweima.setOnClickListener(this);
        ep_geqian.setOnClickListener(this);
        tv_save.setOnClickListener(this);

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ep_avatar = (EditProfileItem) findViewById(R.id.ep_avatar);
        ep_nickname = (EditProfileItem) findViewById(R.id.ep_nickname);
        ep_adouid = (EditProfileItem) findViewById(R.id.ep_adouid);
        ep_gender = (EditProfileItem) findViewById(R.id.ep_gender);
        ep_location = (EditProfileItem) findViewById(R.id.ep_location);
        ep_geqian = (EditProfileItem) findViewById(R.id.ep_geqian);
        ep_erweima =  (RelativeLayout)findViewById(R.id.ep_erweima);
        tv_save = (TextView) findViewById(R.id.tv_save);
    }


    @Override
    public void updateView(TimUserProfile profile) {
        if (profile != null) {
            TIMUserProfile userProfile = profile.getProfile();
            String nickName = userProfile.getNickName();//昵称
            String faceUrl = userProfile.getFaceUrl();//头像
            String identifier = userProfile.getIdentifier();//id
            TIMFriendGenderType gender = userProfile.getGender();//性别
            String location = userProfile.getLocation();
            String selfSignature = userProfile.getSelfSignature();
            if (!TextUtils.isEmpty(faceUrl)) {
                ep_avatar.setAvatar(faceUrl);
            }
            if (!TextUtils.isEmpty(nickName)) {
                ep_nickname.setValue(nickName);
            }
            if (!TextUtils.isEmpty(identifier)) {
                ep_adouid.setValue(identifier);
            }
            if (!TextUtils.isEmpty(location)) {
                ep_location.setValue(location);
            }
            if (!TextUtils.isEmpty(selfSignature)) {
                ep_geqian.setValue(selfSignature);
            }
            if (gender == TIMFriendGenderType.Male) {
                ep_gender.setValue("男");
            } else if (gender == TIMFriendGenderType.Female) {
                ep_gender.setValue("女");
            } else {
                ep_gender.setValue("未知");
            }
        }
    }

    @Override
    public void onGetInfoFailed() {

    }

    @Override
    public void updateInfoError() {
        ToastUtils.show("更新信息失败，请重试");
    }

    @Override
    public void updateInfoSuccess() {
        ToastUtils.show("更新信息成功");
        presenter.getUserInfo();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ep_avatar:
                showSelectAvatarDialog();
                break;
            case R.id.ep_nickname:
                showEditNickNameDialog();
                break;
            case R.id.ep_adouid:
                ToastUtils.show("阿斗id已复制到剪切板");
                break;
            case R.id.ep_erweima:
                showEditErWeiMaDialog();
                break;
            case R.id.ep_gender:
                showEditGenderDialog();
                break;
            case R.id.ep_location:
                showEditLocationDialog();
                break;
            case R.id.ep_geqian:
                showEditGeQianDialog();
                break;
            case R.id.tv_save:
//                ToastUtils.show("退出登录");
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                //添加标记 说明是从这跳过取得
                intent.putExtra("from", "EditProfileActivity");
                startActivity(intent);
            default:
                break;

        }
    }

    //二维码
    private void showEditErWeiMaDialog() {
        erweimaDialog =new EditProfileDialog_erweima(this);
        erweimaDialog.show();
    }

    //修改地区
    private void showEditLocationDialog() {
        locationDialog=new EditProfileDialog(this, new EditProfileDialog.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(final String value) {
                //先通过api 设置location
                String value2 = value;
                TIMFriendshipManager.getInstance().setLocation(value2, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        updateInfoError();
                    }
                    @Override
                    public void onSuccess() {
                        //再展示新的信息
//                         ep_active_area.setValue(value2);
                        locationDialog.hide();
                        locationDialog = null;
                        presenter.onUpdateInfoSuccess();

                    }
                });
                //更新应用缓存
            }

            @Override
            public void onChangeError() {
                locationDialog.hide();
                locationDialog = null;
                updateInfoError();

            }
        });

        locationDialog.setTitle("修改地区");
        locationDialog.show();
    }

    //修改个签
    private void showEditGeQianDialog() {
        geQianDialog = new EditProfileDialog(this, new EditProfileDialog.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(String value) {
                TIMFriendshipManager.getInstance().setSelfSignature(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        //更新信息失败
                        updateInfoError();
                    }

                    @Override
                    public void onSuccess() {
                        geQianDialog.hide();
                        geQianDialog = null;
                        presenter.onUpdateInfoSuccess();
                    }
                });
            }

            @Override
            public void onChangeError() {
            }
        });
        geQianDialog.setTitle("修改个性签名");
        geQianDialog.show();
    }

    //修改性别
    private void showEditGenderDialog() {
        genderDialog = new EditProfile_Gender_Dialog(this, new EditProfile_Gender_Dialog.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(String value) {
                TIMFriendGenderType type;
                if ("男生".equals(value)) {
                    type = TIMFriendGenderType.Male;
                } else if ("女生".equals(value)) {
                    type = TIMFriendGenderType.Female;
                } else {
                    type = TIMFriendGenderType.Unknow;
                }

                TIMFriendshipManager.getInstance().setGender(type, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        //更新信息失败
                        updateInfoError();
                    }

                    @Override
                    public void onSuccess() {
                        genderDialog.hide();
                        genderDialog = null;
                        presenter.onUpdateInfoSuccess();
                    }
                });
            }

            @Override
            public void onChangeError() {
            }
        });
        genderDialog.setTitle("性别选择");
        genderDialog.show();
    }


    //修改昵称
    private void showEditNickNameDialog() {
        nickNameDialog = new EditProfileDialog(this, new EditProfileDialog.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(String value) {
                //更改服务器上内容
                TIMFriendshipManager.getInstance().setNickName(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        //更新信息失败
                        updateInfoError();
                    }

                    @Override
                    public void onSuccess() {
                        nickNameDialog.hide();
                        nickNameDialog = null;
                        presenter.onUpdateInfoSuccess();
                    }
                });
            }

            @Override
            public void onChangeError() {
            }
        });

        nickNameDialog.setTitle("修改昵称");
        nickNameDialog.show();
    }


    //修改头像
    private void showSelectAvatarDialog() {
        avatarDialog = new SelectPicDialog(this);
        avatarDialog.setTitle("头像选择");
        avatarDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PicChooseHelper.getInstance(this).onActivityResult(requestCode, resultCode, data, PicChooseHelper.CropType.Avatar,new PicChooseHelper.OnPicReadyListener() {
            @Override
            public void onReady(Uri outUri) {
                ep_avatar.setAvatar(outUri);
                avatarDialog.dismiss();
                //需要把路径传到服务器（七牛）
                String path = outUri.getPath();
                File file = new File(path);

                String name = file.getName();
                while (file.length()==0){

                }



                try {
                    QiniuUploadHelper.uploadPic(file.getAbsolutePath(), name, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()) {

                                Log.i("qiniu", "Upload Success");
                                updateNetAvatarInfo(QiniuConfig.QINIU_HOST + key);

                            } else {
                                Log.i("qiniu", "Upload Fail");
                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            }
                            Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void updateNetAvatarInfo(String url) {
        //需要把图片同步到服务器
        TIMFriendshipManager.getInstance().setFaceUrl(url, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                updateInfoError();
            }
            @Override
            public void onSuccess() {
                //重置缓存信息
                presenter.onUpdateInfoSuccess();
            }
        });
    }

}