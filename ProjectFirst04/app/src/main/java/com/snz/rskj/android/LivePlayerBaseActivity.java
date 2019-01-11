package com.snz.rskj.android;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.frame.base.BaseActivity;
import com.netease.nim.chatroom.demo.DemoCache;
import com.netease.nim.chatroom.demo.base.util.log.LogUtil;
import com.netease.nim.chatroom.demo.entertainment.activity.MainActivity;
import com.netease.nim.chatroom.demo.entertainment.constant.GiftType;
import com.netease.nim.chatroom.demo.entertainment.constant.LiveType;
import com.netease.nim.chatroom.demo.entertainment.constant.PushLinkConstant;
import com.netease.nim.chatroom.demo.entertainment.constant.PushMicNotificationType;
import com.netease.nim.chatroom.demo.entertainment.helper.ChatRoomMemberCache;
import com.netease.nim.chatroom.demo.entertainment.helper.GiftAnimation;
import com.netease.nim.chatroom.demo.entertainment.helper.SimpleCallback;
import com.netease.nim.chatroom.demo.entertainment.module.ConnectedAttachment;
import com.netease.nim.chatroom.demo.entertainment.module.DisconnectAttachment;
import com.netease.nim.chatroom.demo.entertainment.module.GiftAttachment;
import com.netease.nim.chatroom.demo.entertainment.module.LikeAttachment;
import com.netease.nim.chatroom.demo.im.session.emoji.MoonUtil;
import com.netease.nim.chatroom.demo.im.ui.dialog.DialogMaker;
import com.netease.nim.chatroom.demo.permission.MPermission;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatTextureViewRenderer;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.constant.MemberQueryType;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomKickOutEvent;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomStatusChangeData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.NotificationType;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.snz.rskj.android.activitylive.StartLiveActivity;
import com.snz.rskj.android.activitylive.inputmodule.BaseAction;
import com.snz.rskj.android.activitylive.inputmodule.ChatRoomMsgListPanel;
import com.snz.rskj.android.activitylive.inputmodule.Container;
import com.snz.rskj.android.activitylive.inputmodule.InputActivity;
import com.snz.rskj.android.activitylive.inputmodule.InputConfig;
import com.snz.rskj.android.activitylive.inputmodule.InputPanel;
import com.snz.rskj.android.activitylive.inputmodule.ModuleProxy;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 直播端和观众端的基类
 * Created by hzxuwen on 2016/4/5.
 */
public abstract class LivePlayerBaseActivity extends BaseActivity implements ModuleProxy, AVChatStateObserver {
    private static final String TAG = LivePlayerBaseActivity.class.getSimpleName();
    private static Handler handler;
    private boolean isCreator = false;
    private boolean isDestroyed = false;

    protected String roomId ;
    private AbortableFuture<EnterChatRoomResultData> enterRequest;
    protected LiveType liveType;
    protected ChatRoomMsgListPanel messageListPanel;
    private InputPanel inputPanel;
    private InputConfig inputConfig = new InputConfig(false, false, false);
    protected GiftAnimation giftAnimation;
    private TextView mTvinput;
    private Timer timer;
    private final static int FETCH_ONLINE_PEOPLE_COUNTS_DELTA = 10 * 1000;
    private boolean isOnMic;
    private LinearLayout mMessageBottom;
    protected boolean isMessageGroup = false;
    protected boolean isStartLive = false;
    protected boolean isGift = false;
    protected boolean isimgGroup = false;
    protected boolean isRend = false;
    protected boolean isBeauty = false;
    protected Container mContainer;
    protected View mAudioModeBgLayout;
    protected View mVideoModeBgLayout;
    protected ViewGroup mInteraction_group_layout;
    protected String masterNick;

    protected abstract int getActivityLayout(); // activity布局文件

    protected abstract int getLayoutId(); // 根布局资源id

    protected abstract int getControlLayout(); // 控制按钮布局

    protected int maxInteractionMembers = 2;
    protected final int LIVE_PERMISSION_REQUEST_CODE = 100;
    protected final static String EXTRA_ROOM_ID = "ROOM_ID";

    protected TextView mRoomName;
    protected String meetingName;
    protected ImageView mAnchorHead;
    protected TextView mRoomNum;
    protected TextView mRoomUserNum;
    protected ImageView mFirstUser;
    protected ImageView mSecnodUser;
    protected ImageView mThirdlyUser;
    protected ImageView mCloseLive;
    protected ImageView mReceiveGift;
    protected ImageView mShare;
    protected EditText mInput;
    protected String pullUrl;
    protected ChatRoomInfo roomInfo;
    protected int screenOrientation;
    protected final static String AVATAR_DEFAULT = "avatar_default";


    public class InteractionView {
        public RelativeLayout rootViewLayout; // 连麦画面布局
        public ViewGroup audienceLivingLayout; // 连麦观众正在播放画面
        public ViewGroup audienceLoadingLayout; // 连麦观众等待画面
        public ViewGroup audioModeBypassLayout; // 音频模式旁路直播画面
        ViewGroup connectionCloseConfirmLayout; // 连麦关闭确认画面
        TextView loadingNameText; // 连麦的观众姓名，等待中的画面
        TextView loadingClosingText; // 正在连接/已关闭文案
        public View connectionViewCloseBtn; // 关闭连麦画面按钮
        TextView connectionCloseConfirmTipsTv;
        TextView connectionCloseConfirm; // 连麦关闭确认
        TextView connectionCloseCancel; // 连麦关闭取消
        public TextView livingBg; // 防止用户关闭权限，没有图像时显示
        public AVChatTextureViewRenderer bypassVideoRender; // 旁路直播画面
        TextView onMicNameText; // 连麦的观众姓名
        public String account;
    }

    protected InteractionView[] interactionGroupView = new InteractionView[maxInteractionMembers];

    // 互动连麦布局
    protected <T extends View> T findViewById(ViewGroup parent, int resId) {
        return (T) (parent.findViewById(resId));
    }

    protected int style;

    protected void findInteractionViews() {
        mAudioModeBgLayout = findViewById(R.id.audio_mode_background);
        mVideoModeBgLayout = findViewById(R.id.video_layout);
        mInteraction_group_layout = findViewById(R.id.interaction_group_layout);

        ViewGroup micNameLayout = findViewById(R.id.on_mic_name_layout);
        for (int i = 0; i < interactionGroupView.length; i++) {
            final InteractionView interactionView = new InteractionView();
            int rootResTd = 0;
            int micNameId = 0;
            switch (i) {
                case 0:
                    rootResTd = R.id.interaction_view_layout_1;
                    micNameId = R.id.on_mic_name_1;
                    break;
                case 1:
                    rootResTd = R.id.interaction_view_layout_2;
                    micNameId = R.id.on_mic_name_2;
                    break;
                case 2:
                    rootResTd = R.id.interaction_view_layout_3;
                    micNameId = R.id.on_mic_name_3;
                    break;
                default:
                    break;
            }
            interactionView.rootViewLayout = findViewById(mInteraction_group_layout, rootResTd);
            interactionView.bypassVideoRender = findViewById(interactionView.rootViewLayout, R.id.bypass_video_render);
            interactionView.loadingNameText = findViewById(interactionView.rootViewLayout, R.id.loading_name);
            interactionView.onMicNameText = findViewById(micNameLayout, micNameId);
            interactionView.audienceLoadingLayout = findViewById(interactionView.rootViewLayout, R.id.audience_loading_layout);
            interactionView.audienceLivingLayout = findViewById(interactionView.rootViewLayout, R.id.audience_living_layout);
            interactionView.livingBg = findViewById(interactionView.rootViewLayout, R.id.no_video_bg);

            interactionView.connectionViewCloseBtn = findViewById(interactionView.rootViewLayout, R.id.interaction_close_btn);
            interactionView.connectionCloseConfirmLayout = findViewById(interactionView.rootViewLayout, R.id.interaction_close_confirm_layout);
            interactionView.connectionCloseConfirmTipsTv = findViewById(interactionView.rootViewLayout, R.id.interaction_close_confirm_tips_tv);
            interactionView.connectionCloseConfirm = findViewById(interactionView.rootViewLayout, R.id.close_confirm);
            interactionView.connectionCloseCancel = findViewById(interactionView.rootViewLayout, R.id.close_cancel);
            interactionView.loadingClosingText = findViewById(interactionView.rootViewLayout, R.id.loading_closing_text);
            interactionView.audioModeBypassLayout = findViewById(interactionView.rootViewLayout, R.id.audio_mode_audience_layout);
            interactionView.connectionViewCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interactionView.connectionViewCloseBtn.setVisibility(View.GONE);
                    interactionView.connectionCloseConfirmLayout.setVisibility(View.VISIBLE);
                    if (style == AVChatType.AUDIO.getValue()) {
                        interactionView.connectionCloseConfirmTipsTv.setText(R.string.interaction_audio_close_title);
                    } else {
                        interactionView.connectionCloseConfirmTipsTv.setText(R.string.interaction_video_close_title);
                    }
                }
            });
            final int index = i;
            interactionView.connectionCloseConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCloseInteraction(index);
                    doCloseInteractionView(index);
                }
            });
            interactionView.connectionCloseCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interactionView.connectionCloseConfirmLayout.setVisibility(View.GONE);
                    interactionView.connectionViewCloseBtn.setVisibility(View.VISIBLE);
                }
            });

            interactionGroupView[i] = interactionView;
        }


        showModeLayout();
    }

    protected abstract void doCloseInteraction(int index);

    protected void doCloseInteractionView(final int index) {
        if (index == -1) {
            return;
        }
        InteractionView interactionView = interactionGroupView[index];
        interactionView.loadingClosingText.setText(style == AVChatType.AUDIO.getValue() ? R.string.audio_closed : R.string.video_closed);
        interactionView.audienceLoadingLayout.setVisibility(View.VISIBLE);
        interactionView.loadingNameText.setText(!TextUtils.isEmpty(interactionView.account) ? interactionView.account : "");
        interactionView.livingBg.setVisibility(View.GONE);
        interactionView.connectionViewCloseBtn.setVisibility(View.GONE);
        interactionView.connectionCloseConfirmLayout.setVisibility(View.GONE);
        interactionView.bypassVideoRender.setVisibility(View.GONE);
        interactionView.account = null;
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetConnectionView(index);
            }
        }, 2000);
    }

    // 连麦布局显示
    protected void showModeLayout() {
        if (liveType == LiveType.VIDEO_TYPE) {
            Log.e(TAG, "mVideoModeBgLayout:" + mVideoModeBgLayout);
            mVideoModeBgLayout.setVisibility(View.VISIBLE);
            mAudioModeBgLayout.setVisibility(View.GONE);
        } else if (liveType == LiveType.AUDIO_TYPE) {
            mVideoModeBgLayout.setVisibility(View.GONE);
            mAudioModeBgLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        mRoomName = findViewById(R.id.room_name);
        mAnchorHead = findViewById(R.id.bt_anchor_head);
        mRoomNum = findViewById(R.id.tv_room_num);
        mRoomUserNum = findViewById(R.id.tv_room_user_num);
        mFirstUser = findViewById(R.id.img_first_user);
        mSecnodUser = findViewById(R.id.img_secnod_user);
        mThirdlyUser = findViewById(R.id.img_thirdly_user);
        mCloseLive = findViewById(R.id.img_close_live);
        mReceiveGift = findViewById(R.id.img_receive_gift);
        mShare = findViewById(R.id.img_share);
        mInput = findViewById(R.id.et_input);

        mMessageBottom = findViewById(R.id.messageActivityBottomLayout);
        findGiftLayout();
        findInputViews();
        registerObservers(true);

    }

    private void registerObservers(boolean register) {
        NIMClient.getService(ChatRoomServiceObserver.class).observeReceiveMessage(incomingChatRoomMsg, register);
        NIMClient.getService(ChatRoomServiceObserver.class).observeOnlineStatus(onlineStatus, register);
        NIMClient.getService(ChatRoomServiceObserver.class).observeKickOutEvent(kickOutObserver, register);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotification, register);
    }

    // 权限控制
    protected static final String[] LIVE_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE};


    protected void requestLivePermission() {
        MPermission.with(this)
                .addRequestCode(LIVE_PERMISSION_REQUEST_CODE)
                .permissions(LIVE_PERMISSIONS)
                .request();
    }

    Observer<CustomNotification> customNotification = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification customNotification) {
            if (customNotification == null) {
                return;
            }

            String content = customNotification.getContent();
            try {
                JSONObject json = JSON.parseObject(content);
                String fromRoomId = json.getString(PushLinkConstant.roomid);
                if (!roomId.equals(fromRoomId)) {
                    return;
                }
                int id = json.getIntValue(PushLinkConstant.command);
                LogUtil.i(TAG, "receive command type:" + id);
                Log.e(TAG, "id:" + id);
                if (id == PushMicNotificationType.JOIN_QUEUE.getValue()) {
                    // 加入连麦队列
                    joinQueue(customNotification, json);
                } else if (id == PushMicNotificationType.EXIT_QUEUE.getValue()) {
                    // 退出连麦队列
                    exitQueue(customNotification);
                } else if (id == PushMicNotificationType.CONNECTING_MIC.getValue()) {
                    Log.e(TAG, "连麦了");
                    // 主播选中某人连麦
                    onMicLinking(json);
                } else if (id == PushMicNotificationType.DISCONNECT_MIC.getValue()) {
                    // 被主播断开连麦
                    onMicCanceling();
                } else if (id == PushMicNotificationType.REJECT_CONNECTING.getValue()) {
                    // 观众由于重新进入了房间而拒绝连麦
                    rejectConnecting(customNotification.getFromAccount());
                }

            } catch (Exception e) {
                LogUtil.e(TAG, e.toString());
            }
        }
    };

    /**************************
     * 互动连麦入队/出队操作
     **************************/

    // 加入连麦队列，由主播端实现
    protected void joinQueue(CustomNotification customNotification, JSONObject json) {

    }

    // 子类继承
    protected void showConnectionView(int index, String account, String nick, int style) {
        isOnMic = true;
        updateOnMicName(index, nick);
    }

    // 设置连麦者昵称
    protected void updateOnMicName(int index, String nick) {
        Log.e(TAG, index + " updateOnMicName: " + nick);
        if (nick == null) {
            return;
        }
    }

    // 主播断开连麦者，由观众实现
    protected void onMicCanceling() {

    }


    // 退出连麦队列，由主播端实现
    protected void exitQueue(CustomNotification customNotification) {

    }

    // 主播选中某人连麦，由观众实现
    protected void onMicLinking(JSONObject jsonObject) {

    }

    // 观众由于重新进入房间，而拒绝连麦，由主播实现
    protected void rejectConnecting(String account) {

    }

    Observer<ChatRoomKickOutEvent> kickOutObserver = new Observer<ChatRoomKickOutEvent>() {
        @Override
        public void onEvent(ChatRoomKickOutEvent chatRoomKickOutEvent) {
            Toast.makeText(LivePlayerBaseActivity.this, "被踢出聊天室，原因:" + chatRoomKickOutEvent.getReason(), Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(getApplicationContext(), IdentifyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
            clearChatRoom();
        }
    };

    /*******************
     * 离开聊天室
     ***********************/

    private void clearChatRoom() {
        ChatRoomMemberCache.getInstance().clearRoomCache(roomId);
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 50);
    }


    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    // 进入聊天室
    public void enterRoom() {
        if (isDestroyed) {
            return;
        }
        if (enterRequest != null) {
            enterRequest.abort();
        }
        EnterChatRoomData data = new EnterChatRoomData(roomId);
        setEnterRoomExtension(data);
        enterRequest = NIMClient.getService(ChatRoomService.class).enterChatRoom(data);
        Log.e(TAG, "enterRequest:" + enterRequest);
        enterRequest.setCallback(new RequestCallback<EnterChatRoomResultData>() {
            @Override
            public void onSuccess(EnterChatRoomResultData result) {
                roomInfo = result.getRoomInfo();
                Log.e(TAG, roomInfo.getRoomId());
                Log.e(TAG, roomInfo.getCreator());
                Log.e(TAG, roomInfo.getName());
                ChatRoomMember member = result.getMember();
                member.setRoomId(roomInfo.getRoomId());
                ChatRoomMemberCache.getInstance().saveMyMember(member);
                Map<String, Object> ext = roomInfo.getExtension();
                getLiveMode(ext);
                updateUI();
                //updateRoomUI(false);
                Log.d(TAG, "进入房间");
            }

            @Override
            public void onFailed(int code) {
                if (code == ResponseCode.RES_CHATROOM_BLACKLIST) {
                    Toast.makeText(LivePlayerBaseActivity.this, "你已被拉入黑名单，不能再进入", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LivePlayerBaseActivity.this, "enter chat room failed, code=" + code, Toast.LENGTH_SHORT).show();
                }
                if (isCreator) {
                    finish();
                }
            }

            @Override
            public void onException(Throwable exception) {
                Log.e(TAG, "exception:" + exception);
                Toast.makeText(LivePlayerBaseActivity.this, "enter chat room exception, e=" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                if (isCreator) {
                    finish();
                }

            }
        });
    }

    protected void updataLayout() {

    }

    // 更新在线人数
    protected void updateUI() {
    }
    protected List<ChatRoomMember> mChatRoomMemberList = new ArrayList<>();

    protected void updataRoomCount(){}
    // 一分钟轮询一次在线人数
    protected void fetchOnlineCount() {
        if (timer == null) {
            timer = new Timer();
        }

        //开始一个定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ChatRoomMemberCache.getInstance().fetchRoomMembers(roomId, MemberQueryType.ONLINE_NORMAL, 0, 10, new SimpleCallback<List<ChatRoomMember>>() {
                    @Override
                    public void onResult(boolean success, List<ChatRoomMember> result) {
                        mChatRoomMemberList.clear();
                        mChatRoomMemberList.addAll(result);
                        updataRoomCount();
                    }
                });
                NIMClient.getService(ChatRoomService.class).fetchRoomInfo(roomId).setCallback(new RequestCallback<ChatRoomInfo>() {
                    @Override
                    public void onSuccess(final ChatRoomInfo param) {
                        mRoomUserNum.setText(String.format("%s人", String.valueOf(param.getOnlineUserCount())));
                    }

                    @Override
                    public void onFailed(int code) {
                        LogUtil.d(TAG, "fetch room info failed:" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        LogUtil.d(TAG, "fetch room info exception:" + exception);
                    }
                });
            }
        }, FETCH_ONLINE_PEOPLE_COUNTS_DELTA, FETCH_ONLINE_PEOPLE_COUNTS_DELTA);
    }

    // 获取当前直播的模式
    private void getLiveMode(Map<String, Object> ext) {
        if (ext != null) {
            if (ext.containsKey(PushLinkConstant.type)) {
                int type = (int) ext.get(PushLinkConstant.type);
                liveType = LiveType.typeOfValue(type);
            }

            if (ext.containsKey(PushLinkConstant.meetingName)) {
                meetingName = (String) ext.get(PushLinkConstant.meetingName);
            }

            if (ext.containsKey(PushLinkConstant.orientation)) {
                screenOrientation = (int) ext.get(PushLinkConstant.orientation);
                if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                        ? 1 : 2) != screenOrientation) {
                    setRequestedOrientation(screenOrientation == 1 ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        }
    }

    // 主播将自己的模式放到进入聊天室的通知扩展中，告诉观众，由主播实现。
    protected void setEnterRoomExtension(EnterChatRoomData enterChatRoomData) {

    }

    View.OnClickListener inputClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    isMessageGroup = true;
                    isGift = false;
                    isimgGroup = false;
                    isRend = false;
                    isBeauty = false;
                    updataLayout();
                    //startInputActivity();
                }
            });
        }
    };

    protected void findInputViews() {
        mContainer = new Container(this, roomId, SessionTypeEnum.ChatRoom, this);
        View view = findViewById(getLayoutId());
        if (messageListPanel == null) {
            messageListPanel = new ChatRoomMsgListPanel(mContainer, view);
        }
        InputConfig inputConfig = new InputConfig();
        inputConfig.isTextAudioSwitchShow = false;
        inputConfig.isMoreFunctionShow = false;
        inputConfig.isEmojiButtonShow = false;
        if (inputPanel == null) {
            inputPanel = new InputPanel(mContainer, view, getActionList(), inputConfig);
        } else {
            inputPanel.reload(mContainer, inputConfig);
        }
        mTvinput = findViewById(R.id.tv_input);
        mInput.setOnClickListener(inputClick);
        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            /****
             *
             * @param v 可以理解为是向上转型的EditText，可以用来操作当前的EditText
             * @param actionId 动作标识，是跟EditorInfo.IME_**这里的值对比可以判断执行了什么动作
             * @param event  跟KeyEvent.ACTION_**比较值判断它的事件
             * @return 如果不往下执行到此结束，返回true，否则为false。
             */
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {//判断动作标识是否匹配
                    send();
                    return true;
                }
                return false;
            }
        });

        mTvinput.setAlpha(0.3f);
        inputPanel.hideInputPanel();
        inputPanel.collapse(true);

        findInteractionViews();

    }

    protected void send() {
        IMMessage textMessage;
        if (TextUtils.isEmpty(mInput.getText().toString())) {
            Toast.makeText(LivePlayerBaseActivity.this, "不要输入空消息！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mContainer.sessionType == SessionTypeEnum.ChatRoom) {
            textMessage = ChatRoomMessageBuilder.createChatRoomTextMessage(mContainer.account, mInput.getText().toString());
        } else {
            textMessage = MessageBuilder.createTextMessage(mContainer.account, mContainer.sessionType, mInput.getText().toString());
        }
        if (mContainer.proxy.sendMessage(textMessage)) {
            mInput.setText("");
        }
    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();
        return actions;
    }


    private void startInputActivity() {
       /* InputActivity.startActivityForResult(this, mInput.getText().toString(),
                inputConfig, new InputActivity.InputActivityProxy() {
                    @Override
                    public void onSendMessage(String text) {
                        inputPanel.onTextMessageSendButtonPressed(text);
                    }
                });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (messageListPanel != null) {
            messageListPanel.onResume();
        }
    }


    /**************************
     * Module proxy
     ***************************/


    Observer<ChatRoomStatusChangeData> onlineStatus = new Observer<ChatRoomStatusChangeData>() {
        @Override
        public void onEvent(ChatRoomStatusChangeData chatRoomStatusChangeData) {
            if (chatRoomStatusChangeData.status == StatusCode.CONNECTING) {
                DialogMaker.updateLoadingMessage("连接中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.UNLOGIN) {
                onOnlineStatusChanged(false);
                Toast.makeText(LivePlayerBaseActivity.this, com.netease.nim.chatroom.demo.R.string.nim_status_unlogin, Toast.LENGTH_SHORT).show();
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINING) {
                DialogMaker.updateLoadingMessage("登录中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINED) {
                onOnlineStatusChanged(true);
            } else if (chatRoomStatusChangeData.status.wontAutoLogin()) {
            } else if (chatRoomStatusChangeData.status == StatusCode.NET_BROKEN) {
                onOnlineStatusChanged(false);
                Toast.makeText(LivePlayerBaseActivity.this, com.netease.nim.chatroom.demo.R.string.net_broken, Toast.LENGTH_SHORT).show();
            }
            LogUtil.i(TAG, "Chat Room Online Status:" + chatRoomStatusChangeData.status.name());
        }
    };

    /**************************
     * 断网重连
     ****************************/

    protected void onOnlineStatusChanged(boolean isOnline) {
        if (isOnline) {
            onConnected();
        } else {
            onDisconnected();
        }
    }

    private RelativeLayout giftAnimationViewDown; // 礼物动画布局1
    private RelativeLayout giftAnimationViewUp; // 礼物动画布局2

    protected void findGiftLayout() {

        giftAnimationViewDown = findViewById(R.id.gift_animation_view);
        giftAnimationViewUp = findViewById(R.id.gift_animation_view_up);

        giftAnimation = new GiftAnimation(giftAnimationViewDown, giftAnimationViewUp);
    }

    protected abstract void onConnected(); // 网络连上

    protected abstract void onDisconnected(); // 网络断开

    Observer<List<ChatRoomMessage>> incomingChatRoomMsg = new Observer<List<ChatRoomMessage>>() {
        @Override
        public void onEvent(List<ChatRoomMessage> messages) {
            if (messages == null || messages.isEmpty()) {
                return;
            }

            for (ChatRoomMessage message : messages) {
                if (message != null && message.getAttachment() instanceof GiftAttachment) {
                    // 收到礼物消息
                    Log.e(TAG, "收到礼物");
                    GiftType type = ((GiftAttachment) message.getAttachment()).getGiftType();
                    updateGiftList(type);
                    giftAnimation.showGiftAnimation(message);
                } else if (message != null && message.getAttachment() instanceof LikeAttachment) {
                    Toast.makeText(mApplication, "shouda", Toast.LENGTH_SHORT).show();
                    // 收到点赞爱心
                    lovelyout();
                } else if (message != null && message.getAttachment() instanceof ChatRoomNotificationAttachment) {
                    // 通知类消息
                    Log.e(TAG, "message.getAttachment():" + message.getAttachment());
                    Log.e(TAG, "message.getMsgType():" + message.getMsgType());
                    ChatRoomNotificationAttachment attachment = (ChatRoomNotificationAttachment) message.getAttachment();
                    Log.e(TAG, "attachment.getType():" + attachment.getType());
                    Log.e(TAG, "attachment.getExtension():" + attachment.getExtension());
                    if (attachment.getType() == NotificationType.ChatRoomMemberIn) {
                        getLiveMode(attachment.getExtension());
                    } else if (attachment.getType() == NotificationType.ChatRoomInfoUpdated) {
                        onReceiveChatRoomInfoUpdate(attachment.getExtension());
                    } else if (attachment.getType() == NotificationType.ChatRoomQueueChange) {

                    }
                } else if (message != null && message.getAttachment() instanceof ConnectedAttachment) {
                    // 观众收到旁路直播连接消息
                    onMicConnectedMsg(message);
                } else if (message != null && message.getAttachment() instanceof DisconnectAttachment) {
                    // 观众收到旁路直播断开消息
                    DisconnectAttachment attachment = (DisconnectAttachment) message.getAttachment();
                    int index = getInteractionViewIndexByAccount(attachment.getAccount());
                    if (!TextUtils.isEmpty(attachment.getAccount()) && attachment.getAccount().equals(roomInfo.getCreator())) {
                        if (index != -1) {
                            resetConnectionView(index);
                        }
                    } else {
                        onMicDisConnectedMsg(attachment.getAccount());
                    }
                } else if (message != null && message instanceof IMMessage) {
                    messageListPanel.onIncomingMessage(message);
                } else if (message != null && message instanceof ChatRoomMember) {
                    messageListPanel.saveMessage(message, true);
                } else {

                }
                Log.e(TAG, "message.getMsgType():" + message.getMsgType());
            }
        }
    };

    protected void lovelyout() {

    }

    // 更新礼物列表，由子类定义
    protected void updateGiftList(GiftType type) {

    }

    protected void onReceiveChatRoomInfoUpdate(Map<String, Object> extension) {

    }

    // 收到取消连麦消息,由观众的实现
    protected void onMicDisConnectedMsg(String account) {
    }


    // 收到连麦成功消息，由观众端实现
    protected void onMicConnectedMsg(ChatRoomMessage message) {
    }

    protected int getInteractionViewIndexByAccount(String account) {
        int index = -1;
        if (account == null) {
            LogUtil.d(TAG, " getInteractionViewIndexByAccount account is null");
            return index;
        }
     /*   for(int i = 0 ;i < interactionGroupView.length;i++){
            if(account.equals(interactionGroupView[i].account)){
                index = i;
                break;
            }
        }*/
        return index;
    }

    // 收到主播断开连麦全局消息
    protected void resetConnectionView() {
        LogUtil.i(TAG, "reset Connection view");

    }

    protected void resetConnectionView(int index) {
        isOnMic = false;
        interactionGroupView[index].rootViewLayout.setVisibility(View.GONE);
        interactionGroupView[index].connectionCloseConfirmLayout.setVisibility(View.GONE);
        interactionGroupView[index].audienceLivingLayout.setVisibility(View.GONE);
        interactionGroupView[index].audienceLoadingLayout.setVisibility(View.GONE);
        interactionGroupView[index].connectionViewCloseBtn.setVisibility(View.VISIBLE);
        interactionGroupView[index].onMicNameText.setVisibility(View.GONE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == InputActivity.REQ_CODE) {
            // 设置EditText显示的内容
            String text = data.getStringExtra(InputActivity.EXTRA_TEXT);
            MoonUtil.identifyFaceExpression(DemoCache.getContext(), mInput, text, ImageSpan.ALIGN_BOTTOM);
            mInput.setSelection(text.length());
            inputPanel.hideInputPanel();
            // 根据mode显示表情布局或者键盘布局
            int mode = data.getIntExtra(InputActivity.EXTRA_MODE, InputActivity.MODE_KEYBOARD_COLLAPSE);
            if (mode == InputActivity.MODE_SHOW_EMOJI) {
                inputPanel.toggleEmojiLayout();
            } else if (mode == InputActivity.MODE_SHOW_MORE_FUNC) {
                inputPanel.toggleActionPanelLayout();
            }

            //inputPanel.show();
        }
    }

    protected void UMshare(SHARE_MEDIA share_media){
        new ShareAction(this)
                .setPlatform(share_media)//传入平台
                .withText("hello")//分享内容
                .setCallback(mUMShareListener)//回调监听器
                .share();
    }
    UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.e(TAG,"分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.e(TAG, throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.e(TAG,"分享取消");
        }
    };
}
