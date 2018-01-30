package com.example.administrator.zhibodemo.ui.ui.regist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.utils.ToastUtils;
import com.example.administrator.zhibodemo.ui.utils.UltimateBarUtils;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener,RegistContract.View{

    private Button btn_regist;
    private EditText et_pass,et_user,et_pass2;
    private RegistPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        initPresenter();
        UltimateBarUtils.showUltimateBar(this);
    }


    private void initPresenter() {
        this.presenter=new RegistPresenter(this);
    }


    private void initView() {
        et_pass=(EditText)findViewById(R.id.et_pass);
        et_pass2=(EditText)findViewById(R.id.et_pass2);
        et_user=(EditText)findViewById(R.id.et_user);
        btn_regist=(Button)findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regist:
                String user=et_user.getText().toString().trim();
                String pass=et_pass.getText().toString().trim();
                String pass2=et_pass2.getText().toString().trim();

                presenter.regist(user,pass,pass2);
                break;
        }
    }

    @Override
    public void registSuccess() {
        ToastUtils.show("注册成功！");
        finish();
    }

    @Override
    public void registError(int errCode, String errMsg) {
        ToastUtils.show("注册失败！"+errMsg+"错误码："+errCode);
    }

    @Override
    public void registInfoEmpty() {
        ToastUtils.show("账号或者密码不能为空~");
    }

    @Override
    public void registInfoError() {
        ToastUtils.show("账号或者密码不能少于8位~");
    }

    @Override
    public void registConfirmPassError() {
        ToastUtils.show("两次密码输入不一致，请检查~");
    }
}
