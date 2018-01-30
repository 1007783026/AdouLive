package com.example.administrator.zhibodemo.ui.ui.watchlive;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.zhibodemo.R;
import com.example.administrator.zhibodemo.ui.app.MyApplication;
import com.example.administrator.zhibodemo.ui.bean.DanmuMsgInfo;
import com.example.administrator.zhibodemo.ui.bean.TextMsgInfo;
import com.example.administrator.zhibodemo.ui.engine.MessageObservable;
import com.example.administrator.zhibodemo.ui.engine.live.Constants;
import com.example.administrator.zhibodemo.ui.engine.live.DemoFunc;
import com.example.administrator.zhibodemo.ui.timcustom.CustomTimConstant;
import com.example.administrator.zhibodemo.ui.utils.ToastUtils;
import com.example.administrator.zhibodemo.ui.weight.BottomChatSwitchLayout;
import com.example.administrator.zhibodemo.ui.weight.BottomSwitchLayout;
import com.example.administrator.zhibodemo.ui.weight.HeightSensenableRelativeLayout;
import com.example.administrator.zhibodemo.ui.weight.LiveMsgListView;
import com.example.administrator.zhibodemo.ui.weight.danmu.DanmuView;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.livesdk.ILVText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tyrantgit.widget.HeartLayout;

public class WatchLiveActivity extends AppCompatActivity implements ILVLiveConfig.ILVLiveMsgListener {

    //倒计时时间范围
    private int repeatTimeLimit = 10;
    long firstSendTimeMillion;
    //心形定时器
    Timer heartTimer = new Timer();
    //产生心形颜色的随机器
    Random heartColorRandom = new Random();
    //心布局
    private HeartLayout heartLayout;
    private AVRootView avRootView;
    private int roomId;
    private String hostId;
    private BottomSwitchLayout bottomswitchlayout;
    private DanmuView danmuView;
    private LiveMsgListView lmlv;
    //创建集合专门存储消息
    private ArrayList<TextMsgInfo> mList = new ArrayList<TextMsgInfo>();
    private BottomChatSwitchLayout chatswitchlayout;
    private HeightSensenableRelativeLayout hsrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlive);
        //初始化消息的接受者
        MessageObservable.getInstance().addObserver(this);
        initView();
        //设置默认状态
        setDefultStatus();

        lmlv.setData(mList);
        setListener();
        initRootView();
        //获取房间号，和主播号
        getinfoAndJoinRoom();
    }


    private void setDefultStatus() {
        chatswitchlayout.setVisibility(View.INVISIBLE);
        bottomswitchlayout.setVisibility(View.VISIBLE);
    }

    private void initRootView() {
        ILVLiveManager.getInstance().setAvVideoView(avRootView);
    }
    //加入房间
    private void getinfoAndJoinRoom() {
        Intent intent = getIntent();
        if (intent != null) {
            roomId = intent.getIntExtra("roomId", -1);
            hostId = intent.getStringExtra("hostId");
            joinRoom(roomId + "");
        }
    }

    private void initView() {
        heartLayout = (HeartLayout) findViewById(R.id.heartlayout);

        avRootView = (AVRootView) findViewById(R.id.av_rootview);
        bottomswitchlayout = (BottomSwitchLayout) findViewById(R.id.bottomswitchlayout);
        chatswitchlayout =(BottomChatSwitchLayout) findViewById(R.id.chatswitchlayout);
        bottomswitchlayout.iv_switch_gift.setVisibility(View.VISIBLE);
        danmuView = (DanmuView) findViewById(R.id.danmuview);
        hsrl = (HeightSensenableRelativeLayout)findViewById(R.id.hsrl);
        lmlv = (LiveMsgListView)findViewById(R.id.lmlv);
    }

//    //把容器的宽高设置成跟屏幕宽高一样，即matchparent
//    private void FullGiftViewMatchParent() {
//        ViewGroup.LayoutParams layoutParams = gift_full_screen_view.getLayoutParams();
//        WindowManager wm = getWindowManager();
//        Display defaultDisplay = wm.getDefaultDisplay();
//        int width = defaultDisplay.getWidth();
//        int height = defaultDisplay.getHeight();
//        layoutParams.width = width;
//        gift_full_screen_view.setLayoutParams(layoutParams);
//    }

    private void setListener() {
        //是否隐藏输入键盘的监听
        hsrl.setOnLayoutHeightChangedListenser(new HeightSensenableRelativeLayout.OnLayoutHeightChangedListenser() {
            @Override
            public void showNormal() {
                setDefultStatus();
            }

            @Override
            public void showChat() {
                chatswitchlayout.setVisibility(View.VISIBLE);
                bottomswitchlayout.setVisibility(View.INVISIBLE);
            }
        });

        //聊天和发礼物和关闭观看直播的监听
        bottomswitchlayout.setOnSwitchListener(new BottomSwitchLayout.OnSwitchListener() {
            @Override
            public void onChat() {
                //聊天
                chatswitchlayout.setVisibility(View.VISIBLE);
                bottomswitchlayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onClose() {
                //关闭时
                finish();
            }

            @Override
            public void onGift() {

            }
        });
        //聊天or弹幕的监听
        chatswitchlayout.setOnMsgSendListener(new BottomChatSwitchLayout.OnMsgSendListener() {

            @Override
            public void sendMsg(String text) {
                //发送消息
                sendTextMsg(text, CustomTimConstant.TEXT_MSG);
            }

            @Override
            public void danmu(String text) {
                String newText = CustomTimConstant.TYPE_DAN + text;
                sendTextMsg(newText, CustomTimConstant.DANMU_MSG);
            }
        });


        //拦截了消息和弹幕！！！！！！！！！！！！！！！！！！！！！！！
        //点击心型区域的监听
        heartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送更多的心形图案，再去发送消息给主播
                sendTextMsg(CustomTimConstant.TYPE_Heart, CustomTimConstant.HEART_MSG);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        ILVLiveManager.getInstance().onPause();
        Log.e("WATCH_LIVE","onpause观看者");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILVLiveManager.getInstance().onResume();
        Log.e("WATCH_LIVE","onresume观看者");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("WATCH_LIVE","ondestoy观看者");
        quitRoom();
        ILVLiveManager.getInstance().onDestory();
    }

    // 加入房间
    private void joinRoom(String roomString) {
        int roomId = DemoFunc.getIntValue(roomString, -1);
        if (-1 == roomId) {
            ToastUtils.show("房间号不合法");
            //退出房间
            finish();
            return;
        }
        ILVLiveRoomOption option = new ILVLiveRoomOption("")
                .controlRole(Constants.ROLE_GUEST) //是一个浏览者
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .autoCamera(false)
                .autoMic(false);
        ILVLiveManager.getInstance().joinRoom(roomId,
                option, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        //加入房间后，为了避免单调，自动弹出心形图案
//                        startHeartAnim();
                    }
                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtils.show("加入房间失败，正在退出。。。");
                        //退出房间
                        finish();
                    }
                });

    }

    //开始心形动画,在零秒之后每隔一秒自动冒心
    private void startHeartAnim() {
        heartTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        heartLayout.addHeart(generateColor());

                    }
                });

            }
        }, 0, 1000);

    }


    private int generateColor() {
        int rgb = Color.rgb(heartColorRandom.nextInt(255), heartColorRandom.nextInt(255), heartColorRandom.nextInt(255));
        return rgb;
    }

    //退出房间的操作
    public void quitRoom() {
        ILVLiveManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                //退出成功
                ToastUtils.show("退出房间成功");
                //修改房间的观看数量
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        });
    }

    //腾讯云发送普通消息
    public void sendTextMsg(final String text, final int cmd) {
        //通过对方id获取对方的等级和对方的昵称

        List<String> ids = new ArrayList<>();
        ids.add(hostId);
        TIMFriendshipManager.getInstance().getFriendsProfile(ids, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                realSend(timUserProfiles, text, cmd);
            }
        });
    }

    //真正的发送消息
    private void realSend(List<TIMUserProfile> timUserProfiles, final String text, final int cmd) {
        //因为获取信息的时候 只传入了只有一个元素的集合，所以到这只能拿到一个用户的信息
        final TIMUserProfile profile = timUserProfiles.get(0);

        //发送普通消息
        ILVLiveManager.getInstance().sendText(new ILVText(ILVText.ILVTextType.eGroupMsg, profile.getIdentifier(), text), new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                String grade;
                //发送成功之后，加入到listview中去
                TextMsgInfo textMsgInfo = new TextMsgInfo();
                byte[] bytes = profile.getCustomInfo().get(CustomTimConstant.INFO_GRADE);
                if (bytes != null) {
                    grade = new String(bytes);
                } else {
                    grade = "0";
                }
                String identifier = MyApplication.getApp().getTimUserProfile().getProfile().getIdentifier();
                textMsgInfo.setAdouID(identifier);
                textMsgInfo.setGrade(Integer.parseInt(grade));
                textMsgInfo.setText(text);
                textMsgInfo.setNickname(profile.getNickName());
                //更新列表

                if (cmd == CustomTimConstant.DANMU_MSG) {
                    //发弹幕
                    String newMsg = text.substring(CustomTimConstant.TYPE_DAN.length(), text.length());
                    String avatar = MyApplication.getApp().getTimUserProfile().getProfile().getFaceUrl();
                    String adouId = MyApplication.getApp().getTimUserProfile().getProfile().getIdentifier();
                    DanmuMsgInfo danmuMsgInfo = new DanmuMsgInfo();
                    danmuMsgInfo.setAdouID(adouId);//
                    danmuMsgInfo.setAvatar(avatar);
                    danmuMsgInfo.setGrade(Integer.parseInt(grade));
                    danmuMsgInfo.setText(newMsg);

                    danmuView.addDanmu(danmuMsgInfo);
                    textMsgInfo.setText(newMsg);
                }
                if (cmd == CustomTimConstant.GIFT_MSG_REPEAT) {
                    //准备数据
                    //通过上边的view开启动画
                    String newMsg = text.substring(CustomTimConstant.TYPE_GIFT_REPEAT.length(), text.length());
                    textMsgInfo.setText(newMsg);
                }
                if (cmd == CustomTimConstant.GIFT_MSG_FULL_SCREEN) {
                    String newMsg = text.substring(CustomTimConstant.TYPE_GIFT_FULL.length(), text.length());
                    textMsgInfo.setText(newMsg);
                }
                if (cmd == CustomTimConstant.HEART_MSG) {
                    textMsgInfo.setText("点亮了一颗心");
                    //添加一颗心
                    addHeart();

                }
                lmlv.addMsg(textMsgInfo);

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtils.show("发送失败，错误信息" + errMsg + "错误码" + errCode);
            }
        });
    }

    private void addHeart() {
        //添加一一颗心
        heartLayout.addHeart(generateColor());
    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        //当接受到普通消息的时候，展示到listview上边去
        TextMsgInfo textMsgInfo = new TextMsgInfo();
        String msg = text.getText();
        String nickName = userProfile.getNickName();
        String grade;
        String avatar = userProfile.getFaceUrl();
        byte[] bytes = userProfile.getCustomInfo().get(CustomTimConstant.INFO_GRADE);
        if (bytes != null) {
            grade = new String(bytes);
        } else {
            grade = "0";
        }
        textMsgInfo.setAdouID(SenderId);
        textMsgInfo.setGrade(Integer.parseInt(grade));
        //需要判断发送的是否是弹幕
        if (msg.startsWith(CustomTimConstant.TYPE_DAN)) {
            //是弹幕
            String newMsg = msg.substring(CustomTimConstant.TYPE_DAN.length(), msg.length());
            textMsgInfo.setText(newMsg);
            //发送弹幕
            DanmuMsgInfo danmuMsgInfo = new DanmuMsgInfo();
            danmuMsgInfo.setText(newMsg);
            danmuMsgInfo.setGrade(Integer.parseInt(grade));
            danmuMsgInfo.setAvatar(avatar);
            danmuMsgInfo.setAdouID(SenderId);
            danmuView.addDanmu(danmuMsgInfo);

        }
        else if (msg.startsWith(CustomTimConstant.TYPE_Heart)) {
            msg = "点亮了一颗心";
            addHeart();
        } else {
            textMsgInfo.setText(msg);
        }

        lmlv.addMsg(textMsgInfo);
    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }

}
