package com.example.administrator.zhibodemo.ui.ui.home;

import android.content.Intent;

import com.example.administrator.zhibodemo.ui.adapter.HomeRoomInfoAdapter;
import com.example.administrator.zhibodemo.ui.bean.AllHomeInfo;
import com.example.administrator.zhibodemo.ui.httputil.BaseOnRequestComplete;
import com.example.administrator.zhibodemo.ui.httputil.Constants;
import com.example.administrator.zhibodemo.ui.httputil.OkHttpHelper;
import com.example.administrator.zhibodemo.ui.ui.watchlive.WatchLiveActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/22.
 */

public class HomePresenter implements HomeContract.Presenter{

    private HomeContract.View view;
    HomeFragment homeFragment;
    private ArrayList<AllHomeInfo.DataBean> data;

    public HomePresenter(HomeContract.View view){
        this.view=view;
        homeFragment= (HomeFragment) view;
    }
    @Override
    public void getHomeLiveList(final int page) {
        String url= Constants.HOST;
        HashMap<String, String> map = new HashMap<>();
        map.put("action","getList");
        map.put("pageIndex",page+"");

        //获取首页直播列表
        OkHttpHelper.getInstance().postObject(url, map, new BaseOnRequestComplete<AllHomeInfo>() {


            private HomeRoomInfoAdapter homeRoomInfoAdapter;

            @Override
            public void onSuccess(AllHomeInfo baseBean) {
//                Logger.e(baseBean.getCode()) ;
                if (baseBean.getData()!=null){
                    //第一次获取数据时直接赋值给list
                    data=baseBean.getData();
//                    if (data==null||page==0){
//                        data = baseBean.getData();
                    homeRoomInfoAdapter = new HomeRoomInfoAdapter(homeFragment.getActivity(), data);
                    homeRoomInfoAdapter.setOnMyClickListener(new HomeRoomInfoAdapter.OnMyClickListener() {
                        @Override
                        public void OnMyClick(AllHomeInfo.DataBean bean) {
                            //做相应的操作：跳转到直播页面 观看直播
                            Intent intent = new Intent(homeFragment.getActivity(), WatchLiveActivity.class);
                            intent.putExtra("roomId",bean.getRoomId());
                            intent.putExtra("hostId",bean.getUserId());
                            homeFragment.getActivity().startActivity(intent);
                        }
                    });
//
                    view.setHomeRoomInfoAdapter(homeRoomInfoAdapter);
                }else{
                    //报数据为空
                    onEmpty();
                }

                //停止刷新
                homeFragment.srl.setRefreshing(false);

            }

            @Override
            public void onEmpty() {
                super.onEmpty();
                //停止刷新
                homeFragment.srl.setRefreshing(false);
            }

            @Override
            public void onError() {
                super.onError();
                //停止刷新
                homeFragment.srl.setRefreshing(false);
            }

            @Override
            public void onFailed() {
                super.onFailed();
                //停止刷新
                homeFragment.srl.setRefreshing(false);
            }
        },AllHomeInfo.class);
    }
}