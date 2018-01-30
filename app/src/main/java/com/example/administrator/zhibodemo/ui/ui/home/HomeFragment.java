package com.example.administrator.zhibodemo.ui.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.adapter.HomeRoomInfoAdapter;
import com.example.administrator.zhibodemo.ui.utils.UltimateBarUtils;


public class HomeFragment extends Fragment implements HomeContract.View{

    public SwipeRefreshLayout srl;
    private RecyclerView rv;
    private HomeContract.Presenter presenter;
    int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        initView(v);
        initPresenter();
        UltimateBarUtils.showUltimateBar(getActivity());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //加载第0页的数据
        initPullToRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(0);
    }

    private void initPullToRefresh() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0);
            }
        });
    }

    private void initData(int page) {
        presenter.getHomeLiveList(page);
    }

    @Override
    public void setHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter) {
        if (adapter!=null){
            rv.setAdapter(adapter);
        }
    }

    @Override
    public void updataHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter) {
        if (adapter!=null){
            //更新数据
            rv.setAdapter(adapter);
        }
    }

    private void initView(View v) {
        srl=(SwipeRefreshLayout)v.findViewById(R.id.srl);
        rv=(RecyclerView)v.findViewById(R.id.rv);
        //设置布局样式
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initPresenter() {
        presenter=new HomePresenter(this);
    }

}
