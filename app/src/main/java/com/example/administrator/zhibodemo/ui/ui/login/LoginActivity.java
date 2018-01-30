package com.example.administrator.zhibodemo.ui.ui.login;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.ui.MainActivity;
import com.example.administrator.zhibodemo.ui.ui.regist.RegistActivity;
import com.example.administrator.zhibodemo.ui.utils.ToastUtils;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,LoginContract.View{

    private EditText et_pass,et_user;
    private Button btn_login;
    private TextView tv_regist;
    private LoginContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getPhoto();
        initView();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter=new LoginPresenter(this);
    }


    private void initView() {
        et_pass=(EditText)findViewById(R.id.et_pass);
        et_user=(EditText)findViewById(R.id.et_user);
        btn_login=(Button)findViewById(R.id.btn_login);
        tv_regist=(TextView)findViewById(R.id.tv_regist);
        btn_login.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                //登录
                String user = et_user.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                presenter.login(user,pass);
                break;
            case R.id.tv_regist:
                //注册
                Intent intent=new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void loginSuccess() {
        //登录成功
        ToastUtils.show("登陆成功!");
    }

    @Override
    public void loginError(int errCode, String errMsg) {
        //登录失败
        ToastUtils.show("登陆失败!"+errMsg+"错误码："+errCode);
    }

    @Override
    public void loginInfoEmpty() {
        //账号密码信息为空
        ToastUtils.show("账号或者密码不能空~");
    }

    @Override
    public void loginInfoError() {
        //账号密码信息错误
        ToastUtils.show("账号或密码长度不足8位，请检查~");
    }

    //获取照片的动态权限
    public void getPhoto() {
        PermissionGen.needPermission(this, 101,
                new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }

    @PermissionSuccess(requestCode = 101)
    public void success(){
        ToastUtils.show("权限申请成功");
    }
    @PermissionFail(requestCode = 102)
    public void fail(){
        ToastUtils.show("权限申请失败");
    }
}
