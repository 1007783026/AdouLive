package com.example.administrator.zhibodemo.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.ui.createlive.CreateLiveActivity;
import com.example.administrator.zhibodemo.ui.ui.home.HomeFragment;
import com.example.administrator.zhibodemo.ui.ui.mine.MineFragment;

import org.zackratos.ultimatebar.UltimateBar;

public class MainActivity extends FragmentActivity {

    private FragmentTabHost tabHost;
    private FragmentManager sfm;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sfm = getSupportFragmentManager();
        initView();

    }



    private void initView() {
        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, sfm, R.id.tabcontent);
        //创建选项卡
        TabHost.TabSpec tabSpec_home = tabHost.newTabSpec("home").setIndicator(getIndicatorView("home"));//已经创建一个首页的选项卡
        TabHost.TabSpec tabSpec_create = tabHost.newTabSpec("create").setIndicator(getIndicatorView("create"));//已经创建一个首页的选项卡
        TabHost.TabSpec tabSpec_mine = tabHost.newTabSpec("mine").setIndicator(getIndicatorView("mine"));//已经创建一个首页的选项卡
        //添加选项卡
        tabHost.addTab(tabSpec_home, HomeFragment.class, null);
        tabHost.addTab(tabSpec_create, null, null);
        tabHost.addTab(tabSpec_mine, MineFragment.class, null);
        tabHost.getTabWidget().setDividerDrawable(R.color.transprant);

        //定义事件将tabspec事件拦截，跳转Activity
        tabHost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,CreateLiveActivity.class);
                startActivity(intent);
            }
        });
    }

    private View getIndicatorView(String tag) {
        View v = LayoutInflater.from(this).inflate(R.layout.tab_item, null, false);
        ImageView img = (ImageView) v.findViewById(R.id.img);
        if ("home".equals(tag)) {
            img.setBackgroundResource(R.drawable.home_selsctor);
        } else if ("mine".equals(tag)) {
            img.setBackgroundResource(R.drawable.mine_selsctor);
        } else if ("create".equals(tag)) {
            img.setBackgroundResource(R.mipmap.zhibo);
        }
        return v;
    }
}