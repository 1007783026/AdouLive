package com.example.administrator.zhibodemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.bean.AllHomeInfo;
import com.example.administrator.zhibodemo.ui.utils.ImageUtils;
import com.example.administrator.zhibodemo.ui.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/22.
 */

public class HomeRoomInfoAdapter extends RecyclerView.Adapter<HomeRoomInfoAdapter.MyHolder>{

    ArrayList<AllHomeInfo.DataBean> mList;
    Context context;
    private View v;
    OnMyClickListener myClickListener;

    //添加自定义点击事件
    public  interface OnMyClickListener {
        void OnMyClick(AllHomeInfo.DataBean bean);
    }
    public void setOnMyClickListener(OnMyClickListener listener){
        myClickListener= listener;
    }


    public HomeRoomInfoAdapter(Context context,ArrayList<AllHomeInfo.DataBean> mList){
        this.context=context;
        this.mList=mList;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(context).inflate(R.layout.item_home_live_info, null, false);
        int item_live_height_px = context.getResources().getDimensionPixelOffset(R.dimen.item_live_height);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item_live_height_px);
        v.setLayoutParams(layoutParams);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final AllHomeInfo.DataBean homeInfo= mList.get(position);
        if(homeInfo!=null){
            holder.setContent(homeInfo);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myClickListener!=null){
                    myClickListener.OnMyClick(homeInfo);
                    ToastUtils.show(homeInfo.getUserName());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView tv_live_title,tv_watch_nums,tv_host_id;
        ImageView iv_cover;

        public MyHolder(View itemView) {
            super(itemView);
            tv_live_title=(TextView)itemView.findViewById(R.id.tv_live_title);
            tv_watch_nums=(TextView)itemView.findViewById(R.id.tv_watch_nums);
            tv_host_id=(TextView)itemView.findViewById(R.id.tv_host_id);
            iv_cover=(ImageView)itemView.findViewById(R.id.iv_bg);
        }
        //赋值
        public void setContent(AllHomeInfo.DataBean  info){
            tv_live_title.setText(info.getLiveTitle());
            tv_watch_nums.setText(info.getWatcherNums()+"");
            tv_host_id.setText(info.getUserId());
            ImageUtils.getInstance().load(info.getLiveCover(),iv_cover);
        }
    }
}
