package com.example.administrator.zhibodemo.ui.ui.home;

import com.example.administrator.zhibodemo.ui.adapter.HomeRoomInfoAdapter;

/**
 * Created by Administrator on 2018/1/22.
 */

public interface HomeContract {
    interface View{
        void setHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter);
        void updataHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter);
    }
    interface  Presenter{
        void getHomeLiveList(int page);
    }
}
