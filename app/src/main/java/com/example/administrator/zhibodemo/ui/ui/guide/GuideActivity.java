package com.example.administrator.zhibodemo.ui.ui.guide;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.ui.login.LoginActivity;

public class GuideActivity extends AppCompatActivity {

    private int index=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initThread();
    }

    private void initThread() {
        handler.sendEmptyMessageDelayed(0, 3000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (index > 1) {
                    try {
                        Thread.sleep(1000);
                        index--;

                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = index;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Intent in = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        }
    };
}
