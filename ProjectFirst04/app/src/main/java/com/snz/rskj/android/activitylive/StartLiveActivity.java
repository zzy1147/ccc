package com.snz.rskj.android.activitylive;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;


import com.netease.nim.chatroom.demo.DemoCache;
import com.netease.nim.chatroom.demo.base.util.StringUtil;
import com.netease.nim.chatroom.demo.base.util.log.LogUtil;
import com.netease.nim.chatroom.demo.entertainment.activity.LiveActivity;
import com.netease.nim.chatroom.demo.entertainment.constant.GiftType;
import com.netease.nim.chatroom.demo.entertainment.constant.LiveType;
import com.netease.nim.chatroom.demo.entertainment.constant.PKStateEnum;
import com.netease.nim.chatroom.demo.entertainment.constant.PushLinkConstant;
import com.netease.nim.chatroom.demo.entertainment.constant.PushMicNotificationType;
import com.netease.nim.chatroom.demo.entertainment.helper.ChatRoomMemberCache;
import com.netease.nim.chatroom.demo.entertainment.helper.GiftCache;
import com.netease.nim.chatroom.demo.entertainment.helper.SimpleCallback;
import com.netease.nim.chatroom.demo.entertainment.http.ChatRoomHttpClient;
import com.netease.nim.chatroom.demo.entertainment.model.Gift;
import com.netease.nim.chatroom.demo.im.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.chatroom.demo.permission.MPermission;
import com.netease.nim.chatroom.demo.permission.annotation.OnMPermissionDenied;
import com.netease.nim.chatroom.demo.permission.annotation.OnMPermissionGranted;
import com.netease.nim.chatroom.demo.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nim.chatroom.demo.permission.util.MPermissionUtil;
import com.netease.nim.chatroom.demo.thirdparty.video.VideoPlayer;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatAudioMixingEvent;
import com.netease.nimlib.sdk.avchat.constant.AVChatMediaCodecMode;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatUserRole;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoCaptureOrientation;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoCropRatio;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoFrameRate;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoQuality;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.avchat.model.AVChatLiveCompositingLayout;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatTextureViewRenderer;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.constant.MemberQueryType;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomUpdateInfo;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nrtc.sdk.common.ImageFormat;
import com.netease.vcloud.video.effect.VideoEffect;
import com.netease.vcloud.video.effect.VideoEffectFactory;
import com.snz.rskj.android.LivePlayerBaseActivity;
import com.snz.rskj.android.R;
import com.snz.rskj.android.activitywatch.WatchLiveActivity;
import com.snz.rskj.android.adapter.AudienceAdapter;
import com.snz.rskj.android.adapter.GiftAdapter;
import com.snz.rskj.android.adapter.baseAdapter.BaseRecyclerAdapter;
import com.snz.rskj.android.adapter.baseAdapter.BaseViewHolder;
import com.snz.rskj.android.bean.andiencebean.Audience;
import com.snz.rskj.android.bean.beautybean.Beauty;
import com.snz.rskj.android.bean.giftbean.GiftConstant;
import com.snz.rskj.android.model.module.MicHelper;
import com.snz.rskj.android.view.activity.UserActivity;
import com.snz.rskj.android.view.animator.LoveLayout;
import com.umeng.commonsdk.debug.W;
import com.umeng.socialize.bean.SHARE_MEDIA;


import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StartLiveActivity extends LivePlayerBaseActivity implements AudienceAdapter.MicrophoneDelete, AudienceAdapter.AudienceMicrophoneInterface, AudienceAdapter.MicrophoneAttentionInterface, AudienceAdapter.MicrophoneApplayInterface, InteractionAdapter.MemberLinkListener {

    @BindView(R.id.live_img_delete)
    ImageView liveImgDelete;
    @BindView(R.id.live_lin)
    LinearLayout liveLin;
    @BindView(R.id.live_viwe)
    View liveViwe;
    @BindView(R.id.live_img_wechat)
    ImageView liveImgWechat;
    @BindView(R.id.live_img_circle)
    ImageView liveImgCircle;
    @BindView(R.id.live_img_micro)
    ImageView liveImgMicro;
    @BindView(R.id.live_btn_agreement)
    TextView liveBtnAgreement;
    @BindView(R.id.live_lin2)
    LinearLayout liveLin2;
    @BindView(R.id.live_btn_start)
    Button liveBtnStart;
    @BindView(R.id.live_btn_gorgeous)
    TextView liveBtnGorgeous;
    private static final String TAG = "StartLiveActivity";
    protected final int LIVE_PERMISSION_REQUEST_CODE = 100;

    //View
    private ImageView mTurnCamera;
    private AVChatTextureViewRenderer mRenderer;
    private LiveType liveType = LiveType.NOT_ONLINE;
    private boolean isPermissionGrant;
    private AVChatCameraCapturer mVideoCapturer;
    private String pushUrl;
    private RelativeLayout mPreparePage;
    private boolean isStartLiving = false;
    private boolean disconnected = false;


    private boolean isMyVideoLink;
    private VideoPlayer videoPlayer;
    private int style;
    private boolean isDestroyed = false;
    private AbortableFuture<EnterChatRoomResultData> enterRequest;

    private View mStartView;
    private boolean isDestroyRtc = false;
    private VideoEffect mVideoEffect;
    private boolean isUninitVideoEffect;
    private Handler mVideoEffectHandler = new Handler();
    private boolean mHasSetFilterType;

    //gift
    List<Gift> mGiftList = new ArrayList<>();
    private RecyclerView mGiftRecvclerView;
    private GiftAdapter mGiftAdapter;
    private View mGiftView;
    //audience
    List<Audience> mAudienceList = new ArrayList<>();
    private View mAudienceView;
    private TextView mOnLineMicrophone;
    private TextView mOnLineAudience;
    //private ImageView mAudienceSwitch;
    private RecyclerView mAudienceRecyclerView;
    private AudienceAdapter mAudienceAdapter;
    private RelativeLayout mImageGroup;
    private RelativeLayout mMessageGrouo;
    private RelativeLayout mRootView;

    List<Beauty> mBeautyList = new ArrayList<>();
    private View mBeautyView;
    private ImageView mBeautyCancel;
    private SeekBar mBeautySeekbar;
    private SeekBar mFaceSeekbar;
    private ImageView mBeautyClose;
    private RecyclerView mBeautyRecycler;
    private RelativeLayout mFaceLayout;
    private RelativeLayout mBeautyLayout;
    private RelativeLayout mLiveTitleGroup;


    private View mShareView;
    private TextView mShareWechat;
    private TextView mShareFriends;
    private TextView mShareMicroblog;
    private TextView mShareCancel;
    private LoveLayout mLoveLayout;
    private ArrayList<InteractionMember> interactionDataSource;
    private LinkedList<InteractionMember> currentInteractionMembers;
    private LinkedList<Audience> currentAudienceMembers;
    private InteractionAdapter interactionAdapter;
    private int interactionCount;
    private GridView mInteractionGridView;
    private TextView mApplyCountText;
    private TextView mNoApplyText;
    private BaseRecyclerAdapter<ChatRoomMember> mLiveAudienceRecyclerAdapter;
    private RecyclerView mMaixuRecyclerView;
    private BaseRecyclerAdapter<Audience> mMaixuAdapter;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_start_live;
    }

    @Override
    protected int getLayoutId() {
        return R.id.root_view;
    }

    @Override
    protected int getControlLayout() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        ButterKnife.bind(this);
        findInputViews();
        loadGift();
         initappay();
        requestLivePermission(); // 请求权限



    }

    private String clickAccount; // 选择的互动人员帐号

    private void initappay() {
        mInteractionGridView = findViewById(R.id.apply_grid_view);
        interactionDataSource = new ArrayList<>();
        currentInteractionMembers = new LinkedList<>();
        interactionAdapter = new InteractionAdapter(interactionDataSource, this, new InteractionAdapter.MemberLinkListener() {
            @Override
            public void onClick(InteractionMember member) {
                if (currentInteractionMembers.size() < maxInteractionMembers) {
                    LogUtil.d(TAG, "link status: waiting. do link");
                    doLink(member);
                    getHandler().postDelayed(userJoinRunnable, USER_JOIN_OVERTIME);
                } else {
                    // 不允许点击
                }
            }
        });
        mInteractionGridView.setAdapter(interactionAdapter);
        mInteractionGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InteractionMember member = (InteractionMember) interactionAdapter.getItem(position);
                member.setSelected(true);

                if (clickAccount != null && !clickAccount.equals(member.getAccount())) {
                    for (InteractionMember m : interactionDataSource) {
                        if (m.getAccount().equals(clickAccount)) {
                            m.setSelected(false);
                            break;
                        }
                    }
                }

                clickAccount = member.getAccount();
                interactionAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initView();
        updateUI();
        loadGift();
        if (liveType == LiveType.VIDEO_TYPE && AVChatManager.getInstance().checkPermission(this).size() == 0) {
            mPreparePage.setVisibility(View.GONE);
            AVChatParameters parameters = new AVChatParameters();
            int videoOrientation = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? AVChatVideoCaptureOrientation.ORIENTATION_PORTRAIT : AVChatVideoCaptureOrientation.ORIENTATION_LANDSCAPE_RIGHT;
            parameters.setInteger(AVChatParameters.KEY_VIDEO_CAPTURE_ORIENTATION, videoOrientation);
            AVChatManager.getInstance().setParameters(parameters);
            AVChatManager.getInstance().setupLocalVideoRender(null, false, 0);
            AVChatManager.getInstance().setupLocalVideoRender(mRenderer, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        }
        if (AVChatManager.getInstance().checkPermission(this).size() != 0 && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //startLiveBgIv.setBackgroundResource(com.netease.nim.chatroom.demo.R.drawable.live_start_landscape_bg);
        }
    }

    protected void updataLayout() {
        if (isBeauty && !isStartLive) {
            mAudienceView.setVisibility(View.GONE);
            mPreparePage.setVisibility(View.GONE);
            mGiftView.setVisibility(View.GONE);
            mBeautyView.setVisibility(View.VISIBLE);
            mMessageGrouo.setVisibility(View.GONE);
        }else{
        }
        if (isStartLive) {//直播开启
            mMessageGrouo.setVisibility(View.VISIBLE);
            mPreparePage.setVisibility(View.GONE);
        }else{
            mPreparePage.setVisibility(View.VISIBLE);
        }
        if (isGift) {
            mGiftView.setVisibility(View.VISIBLE);
            mPreparePage.setVisibility(View.GONE);
            mAudienceView.setVisibility(View.GONE);
            mMessageGrouo.setVisibility(View.GONE);
            mBeautyView.setVisibility(View.GONE);
            mMessageGrouo.setVisibility(View.GONE);
        }
        if (isimgGroup) {
            mAudienceView.setVisibility(View.VISIBLE);
            mPreparePage.setVisibility(View.GONE);
            mGiftView.setVisibility(View.GONE);
            mMessageGrouo.setVisibility(View.GONE);
            mBeautyView.setVisibility(View.GONE);
            mMessageGrouo.setVisibility(View.GONE);
        }
        if (isMessageGroup) {
            mAudienceView.setVisibility(View.GONE);
            mPreparePage.setVisibility(View.GONE);
            mGiftView.setVisibility(View.GONE);
            mMessageGrouo.setVisibility(View.VISIBLE);
            mBeautyView.setVisibility(View.GONE);
        }
    }

    private void initView() {
        mRenderer = findViewById(R.id.render);
        mTurnCamera = findViewById(R.id.img_turn_camera);
        mLiveTitleGroup = findViewById(R.id.live_title_group);
        mStartView = findViewById(R.id.layout_start);
        mGiftView = findViewById(R.id.layout_gift);
        mRootView = findViewById(R.id.root_view);
        mAudienceView = findViewById(R.id.layout_audience);
        mBeautyView = findViewById(R.id.layout_beauty);
        mPreparePage = findViewById(R.id.rl_prepare_page);
        mShareView = findViewById(R.id.layout_share);
        mLoveLayout = mStartView.findViewById(R.id.love_layout);

        mImageGroup = mStartView.findViewById(R.id.rl_image_group);
        mMessageGrouo = mStartView.findViewById(R.id.rl_message_group);

        mGiftRecvclerView = mGiftView.findViewById(R.id.rv_gift_list);

        mOnLineAudience = mAudienceView.findViewById(R.id.tv_online_audience);
        mOnLineMicrophone = mAudienceView.findViewById(R.id.tv_online_microphone);
        mAudienceRecyclerView = mAudienceView.findViewById(R.id.live_audience_list);
        mMaixuRecyclerView = mAudienceView.findViewById(R.id.live_maixu_list);
        // mAudienceSwitch = mAudienceView.findViewById(R.id.audience_switch);

        mBeautyCancel = mBeautyView.findViewById(R.id.beauty_cancel);
        mBeautyClose = mBeautyView.findViewById(R.id.beauty_close);
        mBeautySeekbar = mBeautyView.findViewById(R.id.beauty_beauty_seek);
        mFaceSeekbar = mBeautyView.findViewById(R.id.beauty_face_seek);
        mBeautyRecycler = mBeautyView.findViewById(R.id.beauty_recycler);
        mBeautyLayout = mBeautyView.findViewById(R.id.beauty_rl_beauty_layout);
        mFaceLayout = mBeautyView.findViewById(R.id.beauty_rl_face_layout);

        mShareWechat = mShareView.findViewById(R.id.share_wechat);
        mShareFriends = mShareView.findViewById(R.id.share_firends);
        mShareMicroblog = mShareView.findViewById(R.id.share_microblog);
        mShareCancel = mShareView.findViewById(R.id.share_cancel);

        mNoApplyText = findViewById(R.id.no_apply_tip);
        mApplyCountText = findViewById(R.id.apply_count_text);
        initAdapter();
        setOnClick();
    }

    //收到礼物
    protected void updateGiftList(GiftType type) {
        if (!updateGiftCount(type)) {
            mGiftList.add(new Gift(type, GiftConstant.titles[type.getValue()], 1, GiftConstant.images[type.getValue()]));
            mGiftAdapter.notifyDataSetChanged();
        }
        GiftCache.getInstance().saveGift(roomId, type.getValue());
    }

    // 更新收到礼物的数量
    private boolean updateGiftCount(GiftType type) {
        for (Gift gift : mGiftList) {
            if (type == gift.getGiftType()) {
                gift.setCount(gift.getCount()+1);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void joinQueue(CustomNotification customNotification, JSONObject json) {
        Log.e(TAG, "有人加入");
        super.joinQueue(customNotification, json);
        for (InteractionMember dataSource : interactionDataSource) {
            Log.e(TAG, dataSource.getAccount());
            if (dataSource.getAccount().equals(customNotification.getFromAccount())) {
                if (!json.containsKey(PushLinkConstant.style)) {
                    return;
                }
                dataSource.setAvChatType(AVChatType.typeOfValue(json.getIntValue(PushLinkConstant.style)));
                interactionAdapter.notifyDataSetChanged();
                return;
            }
        }
        interactionCount++;
        saveToLocalInteractionList(customNotification.getFromAccount(), json);
        updateQueueUI();
    }

    // 更新连麦列表和连麦人数
    private void updateQueueUI() {
        updateInteractionNumbers();
        switchInteractionUI();
    }

    // 显示互动人数
    private void updateInteractionNumbers() {
        if (interactionCount <= 0) {
            interactionCount = 0;
            // interactionBtn.setText("");
            // interactionBtn.setBackgroundResource(R.drawable.ic_interaction_normal);
        } else {
            //  interactionBtn.setBackgroundResource(R.drawable.ic_interaction_numbers);
            // interactionBtn.setText(String.valueOf(interactionCount));
        }
    }

    // 有无连麦人的布局切换
    private void switchInteractionUI() {
        if (interactionCount <= 0) {
            mNoApplyText.setVisibility(View.VISIBLE);
            mApplyCountText.setVisibility(View.GONE);
            interactionDataSource.clear();
        } else {
            mNoApplyText.setVisibility(View.GONE);
            mApplyCountText.setVisibility(View.VISIBLE);
            mApplyCountText.setText(String.format("有%d人想要连线", interactionCount));
        }
        interactionAdapter.notifyDataSetChanged();
    }

    private InteractionMember interactionMember = null;

    // 主播保存互动观众
    private void saveToLocalInteractionList(String account, JSONObject jsonObject) {
        JSONObject info = (JSONObject) jsonObject.get(PushLinkConstant.info);
        String nick = info.getString(PushLinkConstant.nick);
        AVChatType style = AVChatType.typeOfValue(jsonObject.getIntValue(PushLinkConstant.style));
        if (!TextUtils.isEmpty(account)) {
            InteractionMember interactionMember = new InteractionMember(account, nick, AVATAR_DEFAULT, style);
            this.interactionMember = interactionMember;
            Log.e(TAG, "interactionMember:" + interactionMember);
            interactionDataSource.add(interactionMember);
            Audience audience = new Audience(interactionMember.getName(), "", false, false, "");
            mInteractionMemberList.add(audience);
        }
        mMaixuAdapter.notifyDataSetChanged();
        interactionAdapter.notifyDataSetChanged();

    }

    // 断开连麦
    protected void doCloseInteraction(int index) {
        if (currentInteractionMembers.get(index) == null) {
            return;
        }

        if (currentInteractionMembers.get(index).getMicStateEnum() == MicStateEnum.CONNECTED) {
            MicHelper.getInstance().masterBrokeMic(roomId, currentInteractionMembers.get(index).getAccount());
        } else if (currentInteractionMembers.get(index).getMicStateEnum() == MicStateEnum.CONNECTING) {
            // 正在连麦中被关闭了,从显示队列中删除，并刷新数字
            for (InteractionMember member : interactionDataSource) {
                if (member.getAccount().equals(currentInteractionMembers.get(index).getAccount())) {
                    interactionDataSource.remove(member);
                    interactionAdapter.notifyDataSetChanged();
                    interactionCount--;
                    updateInteractionNumbers();
                    break;
                }
            }
        }
        currentInteractionMembers.get(index).setMicStateEnum(MicStateEnum.LEAVING);
        currentInteractionMembers.remove(index);
    }

    protected void rejectConnecting(String account) {
        Toast.makeText(StartLiveActivity.this, "被观众拒绝", Toast.LENGTH_SHORT).show();
        InteractionMember interactionMember = getByAccount(account);
        if (interactionMember == null) {
            LogUtil.e(TAG, "rejectConnecting : " + account + " can not find");
            return;
        }
        interactionMember.setMicStateEnum(MicStateEnum.NONE);
        getHandler().removeCallbacks(userJoinRunnable);
        cancelLinkMember(interactionMember.getAccount());
  //   resetConnectionView(index);
        currentInteractionMembers.remove(interactionMember);
    }
    @Override
    protected void resetConnectionView(int index) {
        super.resetConnectionView(index);
        interactionGroupView[index].bypassVideoRender.setVisibility(View.GONE);
    }
    // 主播选择观众连麦的超时
    Runnable userJoinRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(StartLiveActivity.this, "连麦超时", Toast.LENGTH_SHORT).show();
            if (currentInteractionMembers.getLast() != null)
                currentInteractionMembers.getLast().setMicStateEnum(MicStateEnum.NONE);
            interactionAdapter.notifyDataSetChanged();
        }
    };


    private InteractionMember getByAccount(String account) {
        InteractionMember interactionMember = null;
        for (InteractionMember tmp : currentInteractionMembers) {
            if (tmp.getAccount().equals(account)) {
                interactionMember = tmp;
                break;
            }
        }
        return interactionMember;
    }

    // 取消连麦申请 界面变化
    private void cancelLinkMember(String account) {
        removeCancelLinkMember(account);
        updateQueueUI();
    }

    // 移除取消连麦人员
    private void removeCancelLinkMember(String account) {
        if (interactionDataSource == null || interactionDataSource.isEmpty()) {
            return;
        }
        for (InteractionMember m : interactionDataSource) {
            if (m.getAccount().equals(account)) {
                interactionDataSource.remove(m);
                interactionCount--;
                break;
            }
        }
    }


    @Override
    protected void lovelyout() {
        super.lovelyout();
        mLoveLayout.addLoveView();
    }

    private void initAdapter() {
        //gift
        mGiftAdapter = new GiftAdapter(mGiftList);
        mGiftRecvclerView.setAdapter(mGiftAdapter);
        mGiftRecvclerView.setLayoutManager(new LinearLayoutManager(this));
        //audience
        mAudienceAdapter = new AudienceAdapter(mAudienceList);

        mAudienceAdapter.setMicrophoneDeleteInterface(this);
        mAudienceAdapter.setAudienceMicrophoneInterface(this);
        mAudienceAdapter.setMicrophoneAttentionInterface(this);
        mAudienceAdapter.setMicrophoneApplayInterface(this);
        mBeautyList.add(new Beauty("自然", R.drawable.gift_bolster));
        mBeautyList.add(new Beauty("初夏", R.drawable.gift_bolster));
        mBeautyList.add(new Beauty("暖阳", R.drawable.gift_bolster));
        BaseRecyclerAdapter baseRecyclerAdapter = new BaseRecyclerAdapter(this, R.layout.live_beauty_item, mBeautyList) {
            @Override
            public void convert(BaseViewHolder holder, Object o, int position) {
                Beauty beauty = (Beauty) o;
                holder.setText(R.id.beauty_name, ((Beauty) o).getBeautyName());
                holder.setImageResource(R.id.beauty_image, ((Beauty) o).getBeautyImage());
                holder.setOnClickListener(R.id.beauty_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isVideoBeautyOriginCurrent = false;
                        mBeautyLayout.setVisibility(View.VISIBLE);
                        mFaceLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        };
        mLiveAudienceRecyclerAdapter = new BaseRecyclerAdapter<ChatRoomMember>(StartLiveActivity.this, R.layout.live_audience_item, mChatRoomMemberList) {
            @Override
            public void convert(BaseViewHolder holder, ChatRoomMember o, int position) {
                holder.setImageResource(R.id.audience_image,R.drawable.avatar);
                holder.setText(R.id.audience_name,o.getNick());
            }
        };
        mAudienceRecyclerView.setAdapter(mLiveAudienceRecyclerAdapter);
        mAudienceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMaixuAdapter = new BaseRecyclerAdapter<Audience>(this, R.layout.live_microphone_item, mInteractionMemberList) {
            @Override
            public void convert(BaseViewHolder holder, Audience audience, final int position) {
                holder.setImageResource(R.id.microphone_image,R.drawable.avatar);
                holder.setText(R.id.microphone_name,audience.getAndienceName());
                if(audience.getAndienceConnectM()){
                    holder.getView(R.id.microphone_attention).setVisibility(View.VISIBLE);
                }else{
                    holder.getView(R.id.microphone_attention).setVisibility(View.GONE);
                }
                holder.setOnClickListener(R.id.microphone_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInteractionMemberList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        interactionMember.setMicStateEnum(MicStateEnum.CONNECTING);
                        interactionMember.setAvChatType(AVChatType.VIDEO);
                        doLink(interactionMember);
                    }
                });
            }
        };
        mMaixuRecyclerView.setAdapter(mMaixuAdapter);
        mMaixuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBeautyRecycler.setAdapter(baseRecyclerAdapter);
        mBeautyRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }
    private List<Audience> mInteractionMemberList = new ArrayList<>();
    private void setOnClick() {
        mCloseLive.setOnClickListener(liveDeleteClick);
        mTurnCamera.setOnClickListener(turnCameraClick);
        mReceiveGift.setOnClickListener(giftLiveOnClick);
        mPreparePage.setOnClickListener(prepareOnClick);
        mRootView.setOnTouchListener(mOnTouchListener);
        mImageGroup.setOnClickListener(imageGroupClick);
        mOnLineAudience.setOnClickListener(onLineAudienceClick);
        mOnLineMicrophone.setOnClickListener(onLineMicrophoneClick);
        mFirstUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mBeautyClose.setOnClickListener(closeCancelClick);
        mBeautyCancel.setOnClickListener(beautyCancelClick);
        mFaceSeekbar.setOnSeekBarChangeListener(faceSeekbarClick);
        mBeautySeekbar.setOnSeekBarChangeListener(beautySeekbarClick);

        mShare.setOnClickListener(shareClick);
        mShareFriends.setOnClickListener(shareFriendsClick);
        mShareWechat.setOnClickListener(shareWechatClick);
        mShareMicroblog.setOnClickListener(shareMicrobolgClick);
        mShareCancel.setOnClickListener(shareCancelClick);

        mAnchorHead.setOnClickListener(authorHeadClick);

    }

    @OnClick({R.id.live_img_delete, R.id.live_lin, R.id.live_viwe, R.id.live_img_wechat, R.id.live_img_circle, R.id.live_img_micro, R.id.live_btn_agreement, R.id.live_lin2, R.id.live_btn_start, R.id.live_btn_gorgeous})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.live_img_delete:
                finish();
                break;
            case R.id.live_lin:
                break;
            case R.id.live_viwe:
                break;
            case R.id.live_img_wechat:
                break;
            case R.id.live_img_circle:
                break;
            case R.id.live_img_micro:
                break;
            case R.id.live_btn_agreement:
                break;
            case R.id.live_lin2:
                break;
            case R.id.live_btn_start:
                openLive();
                //   LiveActivity.start(this,true,true);
                break;
            case R.id.live_btn_gorgeous://美颜
                isBeauty = true;
                isMessageGroup = false;
                isimgGroup = false;
                isGift = false;
                isRend = true;
                updataLayout();
                break;
        }
    }


    @Override
    public void onTakeSnapshotResult(String s, boolean b, String s1) {

    }

    @Override
    public void onAVRecordingCompletion(String s, String s1) {

    }

    @Override
    public void onAudioRecordingCompletion(String s) {

    }

    @Override
    public void onLowStorageSpaceWarning(long l) {

    }

    @Override
    public void onAudioMixingProgressUpdated(long l, long l1) {

    }

    @Override
    public void onAudioMixingEvent(int i) {

    }

    @Override
    public void onJoinedChannel(int i, String s, String s1, int i1) {

    }

    @Override
    public void onUserJoined(String s) {
        // 1、主播显示旁路直播画面
        // 2、主播发送全局自定义消息告诉观众有人连麦拉

        InteractionMember interactionMember = getByAccount(s);
        if (interactionMember == null) {
            LogUtil.e(TAG, "onUserJoined : " + s + " can not find in currentInteractionMembers");
            return;
        }
        int index = getEmptyInteractionView();
        interactionMember.setMicStateEnum(MicStateEnum.CONNECTED);
        getHandler().removeCallbacks(userJoinRunnable);

        MicHelper.getInstance().sendConnectedMicMsg(roomId, interactionMember);
        MicHelper.getInstance().updateMemberInChatRoom(roomId, interactionMember);
        removeMemberFromList(s);

        if (interactionGroupView[index].audienceLivingLayout.getVisibility() == View.VISIBLE && interactionMember.getAvChatType() == AVChatType.VIDEO) {
            // 如果是已经有连麦的人，下一个连麦人上麦，不隐藏小窗口，直接切换画面
            LogUtil.d(TAG, "another one show on screen");
            AVChatManager.getInstance().setupRemoteVideoRender(s, null, false, 0);
            AVChatManager.getInstance().setupRemoteVideoRender(s, interactionGroupView[index].bypassVideoRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
            updateOnMicName(index, interactionMember.getName());
            interactionGroupView[index].audienceLoadingLayout.setVisibility(View.GONE);
            interactionGroupView[index].livingBg.setVisibility(View.VISIBLE);
        } else {
            Log.e(TAG, "xianshiyemi");
            LogUtil.d(TAG, "show someone on screen");
            showConnectionView(index, s, interactionMember.getName(), interactionMember.getAvChatType().getValue());
        }
        interactionGroupView[index].account = s;
    }
    protected int getEmptyInteractionView(){
        int index = -1;
        for(int i = 0 ;i < interactionGroupView.length;i++){
            if(interactionGroupView[i].account == null){
                index = i;
                break;
            }
        }
        return index;
    }
    // 移除互动布局中的申请连麦成员
    private void removeMemberFromList(String account) {
        currentInteractionMembers.getLast().setMicStateEnum(MicStateEnum.CONNECTED);
        cancelLinkMember(account);
    }
    @Override
    public void onUserLeave(String s, int i) {
        LogUtil.d(TAG, "on user leave");

        // 连麦者离开房间
        MicHelper.getInstance().popQueue(roomId, s);
        MicHelper.getInstance().sendBrokeMicMsg(roomId, s);

        getHandler().removeCallbacks(userLeaveRunnable);

        int index = getInteractionViewIndexByAccount(s);
        Iterator<InteractionMember> it = currentInteractionMembers.iterator();
        while (it.hasNext()) {
            InteractionMember interactionMember = it.next();
            if (s.equals(interactionMember.getAccount())) {
                it.remove();
                if (index != -1) {
                    LogUtil.d(TAG, "on user leave, do close view");
                    doCloseInteractionView(index);
                }
                break;
            }
        }

    }
    // 主播让观众下麦的超时
    Runnable userLeaveRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(StartLiveActivity.this, "超时，请重新连麦", Toast.LENGTH_SHORT).show();
            if (currentInteractionMembers.getLast() != null)
                currentInteractionMembers.getLast().setMicStateEnum(MicStateEnum.LEAVING);
            updateMemberListUI(currentInteractionMembers.getLast(), MicStateEnum.NONE);
        }
    };

    @Override
    public void onLeaveChannel() {

    }

    @Override
    public void onProtocolIncompatible(int i) {

    }

    @Override
    public void onDisconnectServer(int i) {
        LogUtil.i(TAG, "onDisconnectServer");
        Toast.makeText(StartLiveActivity.this, "与音视频服务器已断开连接，自动退出", Toast.LENGTH_SHORT).show();
        releaseRtc(true, true);
        NIMClient.getService(ChatRoomService.class).exitChatRoom(roomId);
        clearChatRoom();
    }
    // 清空聊天室缓存
    private void clearChatRoom() {
        ChatRoomMemberCache.getInstance().clearRoomCache(roomId);
        finish();
    }
    @Override
    public void onNetworkQuality(String s, int i, AVChatNetworkStats avChatNetworkStats) {

    }

    @Override
    public void onCallEstablished() {

    }

    @Override
    public void onDeviceEvent(int i, String s) {

    }

    @Override
    public void onConnectionTypeChanged(int i) {

    }

    @Override
    public void onFirstVideoFrameAvailable(String s) {

    }

    @Override
    public void onFirstVideoFrameRendered(String s) {

        int index = getInteractionViewIndexByAccount(s);
        if (index != -1) {
            interactionGroupView[index].livingBg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onVideoFrameResolutionChanged(String s, int i, int i1, int i2) {

    }

    @Override
    public void onVideoFpsReported(String s, int i) {

    }

    private int mCurWidth, mCurHeight;
    private int mDropFramesWhenConfigChanged = 0; //丢帧数
    private int markMode; //0关闭，1静态，2动态
    private Bitmap mWaterMaskBitmapStatic;
    private Bitmap[] mWaterMaskBitmapDynamic;
    private boolean mIsmWaterMaskAdded = false;
    private int rotation;
    private final int VIDEO_MARK_MODE_CLOSE = 0;
    private final int VIDEO_MARK_MODE_STATIC = 1;
    private final int VIDEO_MARK_MODE_DYNAMIC = 2;
    private boolean isBeautyBtnCancel = false;
    private boolean isVideoBeautyOriginCurrent = false; //美颜默认打开
    private boolean isVideoBeautyOriginLast = false; //美颜默认打开

    protected synchronized void notifyCapturerConfigChange() {
        mDropFramesWhenConfigChanged = 2;
    }

    @Override
    public boolean onVideoFrameFilter(AVChatVideoFrame frame, boolean maybeDualInput) {
        //        如果用户不需要对视频进行美颜，这里直接返回true即可，以下示例是使用sdk提供的美颜和水印功能，用户也可以在此接入第三方的美颜sdk
//        sdk提供的滤镜模块（美颜和水印功能）要求4.3以上版本
//        LogUtil.i(TAG, "on video frame filter, avchatVideoFrame:" + avChatVideoFrame + ", gpuEffect:" + mGPUEffect);
        if (frame == null || (Build.VERSION.SDK_INT < 18)) {
            return true;
        }
        //onVideoFrameFilter回调不在主线程，VideoEffect初始化必须要和onVideoFrameFilter回调不在主线程在同一个线程
        if (mVideoEffect == null && isUninitVideoEffect == false) {
            LogUtil.d(TAG, "create Video Effect");
            mVideoEffectHandler = new Handler();
            mVideoEffect = VideoEffectFactory.getVCloudEffect();
            mVideoEffect.init(this, true, false);
            //需要delay 否则filter设置不成功
            mVideoEffect.setBeautyLevel(5);
            mVideoEffect.setFilterLevel(0.5f);
            //mVideoEffect.setFilterType(VideoEffect.FilterType.nature);  //VideoEffect.FilterType是美颜模式，这里以自然（nature）作为示例
        }
        //分辨率、清晰度变化后设置丢帧数为2
        if (mCurWidth != frame.width || mCurHeight != frame.height) {
            mCurWidth = frame.width;
            mCurHeight = frame.height;
            notifyCapturerConfigChange();
        }

        if (mVideoEffect == null) {
            return true;
        }

        if (markMode != VIDEO_MARK_MODE_CLOSE) {
            if (mWaterMaskBitmapStatic == null) {
                try {
                    InputStream is = getResources().getAssets().open("mark/video_mark_static.png");
                    mWaterMaskBitmapStatic = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mWaterMaskBitmapDynamic == null) {
                mWaterMaskBitmapDynamic = new Bitmap[23];
                for (int i = 0; i < 23; i++) {
                    String resName = "mark/video_mark_dynamic_" + i + ".png";
                    try {
                        InputStream is = getResources().getAssets().open(resName);
                        mWaterMaskBitmapDynamic[i] = BitmapFactory.decodeStream(is);
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (markMode == VIDEO_MARK_MODE_STATIC && (!mIsmWaterMaskAdded || rotation != frame.rotation)) {
                rotation = frame.rotation;
                mIsmWaterMaskAdded = true;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    if (frame.rotation == 0) {
                        mVideoEffect.addWaterMark(null, 0, 0);
                        mVideoEffect.addWaterMark(mWaterMaskBitmapStatic, frame.width / 2, frame.height / 2);
                    } else {
                        mVideoEffect.addWaterMark(null, 0, 0);
                        mVideoEffect.addWaterMark(mWaterMaskBitmapStatic, frame.height / 2, frame.width / 2);
                    }
                } else {
                    if (frame.rotation == 0) {
                        mVideoEffect.addWaterMark(null, 0, 0);
                        mVideoEffect.addWaterMark(mWaterMaskBitmapStatic, frame.width / 2, frame.height / 2);
                    } else {
                        mVideoEffect.addWaterMark(null, 0, 0);
                        mVideoEffect.addWaterMark(mWaterMaskBitmapStatic, frame.height / 2, frame.width / 2);
                    }
                }
                mVideoEffect.closeDynamicWaterMark(true);
            }
            if (markMode == VIDEO_MARK_MODE_DYNAMIC && (!mIsmWaterMaskAdded || rotation != frame.rotation)) {
                rotation = frame.rotation;
                mIsmWaterMaskAdded = true;
                mVideoEffect.addWaterMark(null, 0, 0);
                mVideoEffect.closeDynamicWaterMark(false);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    if (frame.rotation == 0) {
                        mVideoEffect.addDynamicWaterMark(null, frame.width / 2, frame.height / 2, 23, AVChatVideoFrameRate.FRAME_RATE_15, true);
                        mVideoEffect.addDynamicWaterMark(mWaterMaskBitmapDynamic, frame.width / 2, frame.height / 2, 23, AVChatVideoFrameRate.FRAME_RATE_15, true);
                    } else {
                        mVideoEffect.addDynamicWaterMark(null, frame.height / 2, frame.width / 2, 23, AVChatVideoFrameRate.FRAME_RATE_15, true);
                        mVideoEffect.addDynamicWaterMark(mWaterMaskBitmapDynamic, frame.height / 2, frame.width / 2, 23, AVChatVideoFrameRate.FRAME_RATE_15, true);
                    }
                } else {
                    if (frame.rotation == 0) {
                        mVideoEffect.addDynamicWaterMark(null, frame.width / 2, frame.height / 2, 23, AVChatVideoFrameRate.FRAME_RATE_15, true);
                        mVideoEffect.addDynamicWaterMark(mWaterMaskBitmapDynamic, frame.width / 2, frame.height / 2, 23, AVChatVideoFrameRate.FRAME_RATE_15, true);
                    } else {
                        mVideoEffect.addDynamicWaterMark(null, frame.height / 2, frame.width / 2, 23, AVChatVideoFrameRate.FRAME_RATE_15, true);
                        mVideoEffect.addDynamicWaterMark(mWaterMaskBitmapDynamic, frame.height / 2, frame.width / 2, 23, AVChatVideoFrameRate.FRAME_RATE_15, true);
                    }
                }
            }
        } else {
            mVideoEffect.addWaterMark(null, 0, 0);
            mVideoEffect.closeDynamicWaterMark(true);
            mIsmWaterMaskAdded = false;
        }

        VideoEffect.DataFormat format = frame.format == ImageFormat.I420 ? VideoEffect.DataFormat.YUV420 : VideoEffect.DataFormat.NV21;
        boolean needMirrorData = (markMode != VIDEO_MARK_MODE_CLOSE && maybeDualInput);
        VideoEffect.YUVData[] result;
        if ((!isBeautyBtnCancel && !isVideoBeautyOriginCurrent) || (isBeautyBtnCancel && !isVideoBeautyOriginLast)) {
            byte[] intermediate = mVideoEffect.filterBufferToRGBA(format, frame.data, frame.width, frame.height);
            if (!mHasSetFilterType) {
                mHasSetFilterType = true;
                mVideoEffect.setFilterType(VideoEffect.FilterType.nature);
                return true;
            }

            result = mVideoEffect.TOYUV420(intermediate, VideoEffect.DataFormat.RGBA, frame.width, frame.height,
                    frame.rotation, 90, frame.width, frame.height, needMirrorData, true);

        } else {
            result = mVideoEffect.TOYUV420(frame.data, format, frame.width, frame.height,
                    frame.rotation, 90, frame.width, frame.height, needMirrorData, true);
        }
        synchronized (this) {
            if (mDropFramesWhenConfigChanged-- > 0) {
                return false;
            }
        }
        System.arraycopy(result[0].data, 0, frame.data, 0, result[0].data.length);
        frame.width = result[0].width;
        frame.height = result[0].height;
        frame.dataLen = result[0].data.length;
        frame.rotation = 0;

        if (needMirrorData) {
            System.arraycopy(result[1].data, 0, frame.dataMirror, 0, result[1].data.length);
        }
        frame.dualInput = needMirrorData;
        //默认都是转换成I420
        frame.format = ImageFormat.I420;

        return true;
    }

    @Override
    public boolean onAudioFrameFilter(AVChatAudioFrame avChatAudioFrame) {
        return false;
    }

    @Override
    public void onAudioDeviceChanged(int i) {

    }

    @Override
    public void onReportSpeaker(Map<String, Integer> map, int i) {

    }

    @Override
    public void onSessionStats(AVChatSessionStats avChatSessionStats) {

    }

    @Override
    public void onLiveEvent(int i) {

    }

    protected void setEnterRoomExtension(EnterChatRoomData enterChatRoomData) {
        Map<String, Object> notifyExt = new HashMap<>();
        if (liveType == LiveType.VIDEO_TYPE) {
            notifyExt.put(PushLinkConstant.type, AVChatType.VIDEO.getValue());
        } else if (liveType == LiveType.AUDIO_TYPE) {
            notifyExt.put(PushLinkConstant.type, AVChatType.AUDIO.getValue());
        }
        notifyExt.put(PushLinkConstant.meetingName, meetingName);
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        notifyExt.put(PushLinkConstant.orientation, isPortrait ? 1 : 2);
        enterChatRoomData.setNotifyExtension(notifyExt);
    }

    @Override
    public boolean sendMessage(IMMessage msg) {

        ChatRoomMessage message = (ChatRoomMessage) msg;

        Map<String, Object> ext = new HashMap<>();
        ChatRoomMember chatRoomMember = ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount());
        if (chatRoomMember != null && chatRoomMember.getMemberType() != null) {
            ext.put("type", chatRoomMember.getMemberType().getValue());
            message.setRemoteExtension(ext);
        }

        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "用户被禁言", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DemoCache.getContext(), code + "-----", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(DemoCache.getContext(), "消息发送失败！", Toast.LENGTH_SHORT).show();
                    }
                });
        messageListPanel.onMsgSend(msg);
        return true;
    }

    @Override
    protected void onConnected() {

    }

    @Override
    protected void onDisconnected() {

    }

    @Override
    public void onInputPanelExpand() {

    }

    @Override
    public void shouldCollapseInputPanel() {

    }

    @Override
    public boolean isLongClickEnabled() {
        return false;
    }


    //主播直播前预览
    private void startPreview() {
        AVChatManager.getInstance().enableRtc();
        if (mVideoCapturer == null) {
            mVideoCapturer = AVChatVideoCapturerFactory.createCameraCapturer();
            AVChatManager.getInstance().setupVideoCapturer(mVideoCapturer);
        }
        AVChatParameters parameters = new AVChatParameters();
        if (parameters != null) {
            parameters.setBoolean(AVChatParameters.KEY_SESSION_LIVE_MODE, true);
            parameters.setInteger(AVChatParameters.KEY_SESSION_MULTI_MODE_USER_ROLE, AVChatUserRole.NORMAL);
            parameters.setString(AVChatParameters.KEY_VIDEO_ENCODER_MODE, AVChatMediaCodecMode.MEDIA_CODEC_SOFTWARE);
            parameters.setString(AVChatParameters.KEY_VIDEO_DECODER_MODE, AVChatMediaCodecMode.MEDIA_CODEC_AUTO);
            parameters.setInteger(AVChatParameters.KEY_VIDEO_QUALITY, AVChatVideoQuality.QUALITY_720P);
            //如果用到美颜功能，建议这里设为15帧
            parameters.setInteger(AVChatParameters.KEY_VIDEO_FRAME_RATE, AVChatVideoFrameRate.FRAME_RATE_15);
            //如果不用美颜功能，这里可以设为25帧
            //parameters.setInteger(AVChatParameters.KEY_VIDEO_FRAME_RATE, AVChatVideoFrameRate.FRAME_RATE_25);
            parameters.set(AVChatParameters.KEY_SESSION_LIVE_COMPOSITING_LAYOUT, new AVChatLiveCompositingLayout(AVChatLiveCompositingLayout.Mode.LAYOUT_FLOATING_RIGHT_VERTICAL));
            parameters.setInteger(AVChatParameters.KEY_VIDEO_FIXED_CROP_RATIO, AVChatVideoCropRatio.CROP_RATIO_16_9);
            parameters.setBoolean(AVChatParameters.KEY_VIDEO_ROTATE_IN_RENDING, false);
            int videoOrientation = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? AVChatVideoCaptureOrientation.ORIENTATION_PORTRAIT : AVChatVideoCaptureOrientation.ORIENTATION_LANDSCAPE_RIGHT;
            parameters.setInteger(AVChatParameters.KEY_VIDEO_CAPTURE_ORIENTATION, videoOrientation);
            parameters.setBoolean(AVChatParameters.KEY_VIDEO_FRAME_FILTER, true);
            AVChatManager.getInstance().setParameters(parameters);
            //为直播种类  直播前准备
            if (liveType == LiveType.NOT_ONLINE && !isStartLiving) {
                AVChatManager.getInstance().enableVideo();
                //      AVChatManager.getInstance().setupLocalVideoRender(null, false, 0);
                AVChatManager.getInstance().setupLocalVideoRender(mRenderer, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
                AVChatManager.getInstance().startVideoPreview();
            }
        }
    }

    private void openLive() {
        if (disconnected) {
            // 如果网络不通
            Toast.makeText(StartLiveActivity.this, com.netease.nim.chatroom.demo.R.string.net_broken, Toast.LENGTH_SHORT).show();
            return;
        }
        if (AVChatManager.getInstance().checkPermission(StartLiveActivity.this).size() != 0) {
            Toast.makeText(StartLiveActivity.this, com.netease.nim.chatroom.demo.R.string.permission_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isPermissionGrant) {
            startPreview();
        }
        if (isStartLiving) {
            return;
        }
        isStartLiving = true;
        createChannel();
    }

    private void createChannel() {
        this.meetingName = StringUtil.get36UUID();
        // 这里用uuid，作为多人通话房间的名称
        AVChatManager.getInstance().createRoom(meetingName, null, new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                isStartLive = true;
                liveType = LiveType.VIDEO_TYPE;
                masterEnterRoom(liveType == LiveType.VIDEO_TYPE);
            }

            @Override
            public void onFailed(int i) {
                if (i == ResponseCode.RES_EEXIST) {
                    // 417表示该频道已经存在
                    LogUtil.e(TAG, "create room 417, enter room");
                    isStartLive = true;
                } else {
                    isStartLiving = false;
                    mPreparePage.setVisibility(View.VISIBLE);
                    LogUtil.e(TAG, "create room failed, code:" + i);
                    Toast.makeText(StartLiveActivity.this, "create room failed, code:" + i, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable throwable) {
                isStartLiving = false;
                mPreparePage.setVisibility(View.VISIBLE);
                LogUtil.e(TAG, "create room onException, throwable:" + throwable.getMessage());
                Toast.makeText(StartLiveActivity.this, "create room onException, throwable:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void masterEnterRoom(final boolean isVideoMode) {
        Map<String, Object> ext = new HashMap<>();
        ext.put("type", isVideoMode ? AVChatType.VIDEO.getValue() : AVChatType.AUDIO.getValue());
        ext.put(meetingName, meetingName);
        JSONObject jsonObject = null;
        try {
            jsonObject = parseMap(ext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        ChatRoomHttpClient.getInstance().masterEnterRoom(DemoCache.getAccount(), jsonObject.toString(), isVideoMode, isPortrait,
                new ChatRoomHttpClient.ChatRoomHttpCallback<ChatRoomHttpClient.EnterRoomParam>() {
                    @Override
                    public void onSuccess(ChatRoomHttpClient.EnterRoomParam enterRoomParam) {
                        pullUrl = enterRoomParam.getPullUrl();
                        roomId = enterRoomParam.getRoomId();
                        pushUrl = enterRoomParam.getPushUrl();

                        isBeauty = false;
                        isMessageGroup = true;
                        isimgGroup = false;
                        isGift = false;
                        isRend = true;
                        updataLayout();
                        findInputViews();
                        joinChannel(pushUrl);
                        enterRoom();
                    }

                    @Override
                    public void onFailed(int code, String errorMsg) {
                        isStartLiving = false;
                        mPreparePage.setVisibility(View.VISIBLE);
                        Toast.makeText(StartLiveActivity.this, "创建直播间失败，code:" + code + ", errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void updateUI() {
        super.updateUI();
        mPreparePage.setVisibility(View.GONE);
        mStartView.setVisibility(View.VISIBLE);
        mRoomNum.setText(roomId);
        mRoomUserNum.setText(String.format("%s人", String.valueOf(roomInfo.getOnlineUserCount())));
        fetchOnlineCount();

    }

    // 显示连麦画面
    @Override
    protected void showConnectionView(int index, String account, String nick, int style) {
        super.showConnectionView(index, account, nick, style);
        this.style = style;
        interactionGroupView[index].rootViewLayout.setVisibility(View.VISIBLE);
        interactionGroupView[index].audienceLoadingLayout.setVisibility(View.GONE);
        interactionGroupView[index].livingBg.setVisibility(View.VISIBLE);
        Log.e(TAG, ""+liveType  +"----"    + style+""+AVChatType.VIDEO.getValue());
        if (liveType == LiveType.VIDEO_TYPE && style == AVChatType.VIDEO.getValue()) {
            interactionGroupView[index].bypassVideoRender.setVisibility(View.VISIBLE);
            interactionGroupView[index].audienceLivingLayout.setVisibility(View.VISIBLE);
            interactionGroupView[index].audioModeBypassLayout.setVisibility(View.GONE);
            AVChatManager.getInstance().setupRemoteVideoRender(account, null, false, 0);
            AVChatManager.getInstance().setupRemoteVideoRender(account, interactionGroupView[index].bypassVideoRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        } else if (style == AVChatType.AUDIO.getValue()) {
            interactionGroupView[index].audienceLivingLayout.setVisibility(View.GONE);
            interactionGroupView[index].audioModeBypassLayout.setVisibility(View.VISIBLE);
        }
    }

    /*********************** join channel
     * @param pushUrl***********************/


    protected void joinChannel(String pushUrl) {
        if (isDestroyed || isDestroyRtc) {
            return;
        }
        AVChatManager.getInstance().setParameter(AVChatParameters.KEY_SESSION_LIVE_URL, pushUrl);
        MicHelper.getInstance().joinChannel(meetingName, liveType == LiveType.VIDEO_TYPE, new MicHelper.ChannelCallback() {

            @Override
            public void onJoinChannelSuccess() {
                if (liveType == LiveType.AUDIO_TYPE) {
                    AVChatManager.getInstance().setSpeaker(true);
                }
                MicHelper.getInstance().sendBrokeMicMsg(roomId, null);
                dropQueue();
            }

            @Override
            public void onJoinChannelFailed() {
                mPreparePage.setVisibility(View.VISIBLE);
                Toast.makeText(DemoCache.getContext(), "join channel failed", Toast.LENGTH_SHORT).show();
                showLiveFinishLayout();
            }
        });
    }

    // 主播进来清空队列
    private void dropQueue() {
        NIMClient.getService(ChatRoomService.class).dropQueue(roomId).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                LogUtil.d(TAG, "drop queue success");
            }

            @Override
            public void onFailed(int i) {
                LogUtil.d(TAG, "drop queue failed, code:" + i);
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    //展示失败的布局
    private void showLiveFinishLayout() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


            }
        });
    }


    private JSONObject parseMap(Map map) throws JSONException {
        if (map == null) {
            return null;
        }

        JSONObject obj = new JSONObject();
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = String.valueOf(entry.getKey());
            Object value = entry.getValue();
            obj.put(key, value);
        }

        return obj;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && liveType == LiveType.VIDEO_TYPE) {
            showCloseLive();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void showCloseLive() {
        EasyAlertDialogHelper.createOkCancelDiolag(this, null, getString(com.netease.nim.chatroom.demo.R.string.finish_confirm),
                getString(com.netease.nim.chatroom.demo.R.string.confirm), getString(com.netease.nim.chatroom.demo.R.string.cancel), true,
                new EasyAlertDialogHelper.OnDialogActionListener() {
                    @Override
                    public void doCancelAction() {

                    }

                    @Override
                    public void doOkAction() {
                        isStartLive = false;
                        liveType = LiveType.NOT_ONLINE;
                        doUpdateRoomInfo();
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                releaseRtc(true, true);
                                finish();
                            }
                        }, 50);
                    }
                }).show();
    }

    private void doUpdateRoomInfo() {
        ChatRoomUpdateInfo chatRoomUpdateInfo = new ChatRoomUpdateInfo();
        Map<String, Object> map = new HashMap<>(1);
        map.put(PushLinkConstant.type, -1);
        chatRoomUpdateInfo.setExtension(map);
        NIMClient.getService(ChatRoomService.class)
                .updateRoomInfo(roomId, chatRoomUpdateInfo, true, map)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        LogUtil.i(TAG, "leave room, update room info success");
                    }

                    @Override
                    public void onFailed(int i) {
                        LogUtil.e(TAG, "leave room, update room info failed, code:" + i);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LogUtil.e(TAG, "leave room, update room info onException, throwable:" + throwable.getMessage());
                    }
                });
    }


    private void releaseRtc(boolean isReleaseRtc, boolean isLeaveRoom) {
        releaseVideoEffect();

        if (isReleaseRtc) {
            isDestroyRtc = true;
            String meetName = meetingName;
            MicHelper.getInstance().leaveChannel(liveType == LiveType.VIDEO_TYPE, liveType == LiveType.VIDEO_TYPE, isLeaveRoom, meetName);
        }
    }

    private void releaseVideoEffect() {
        Log.e(TAG, "liveType:" + liveType);
        if (liveType == LiveType.VIDEO_TYPE) {
            // 释放资源
            if (mVideoEffect != null) {
                isUninitVideoEffect = true;
                mHasSetFilterType = false;
                mVideoEffectHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mVideoEffect.unInit();
                        mVideoEffect = null;
                    }
                });
            }

        }
    }

    //权限申请
    /***********************
     * 录音摄像头权限申请
     *******************************/

    protected static final String[] LIVE_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(LIVE_PERMISSION_REQUEST_CODE)
    public void onLivePermissionGranted() {
        if (liveType == LiveType.VIDEO_TYPE) {

        }

        isPermissionGrant = true;
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPreview();
            }
        }, 50);
    }

    @OnMPermissionDenied(LIVE_PERMISSION_REQUEST_CODE)
    public void onLivePermissionDenied() {
        List<String> deniedPermissions = MPermission.getDeniedPermissions(this, LIVE_PERMISSIONS);
        String tip = "您拒绝了权限" + MPermissionUtil.toString(deniedPermissions) + "，无法开启直播";
        Toast.makeText(StartLiveActivity.this, tip, Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionNeverAskAgain(LIVE_PERMISSION_REQUEST_CODE)
    public void onLivePermissionDeniedAsNeverAskAgain() {
        List<String> deniedPermissions = MPermission.getDeniedPermissionsWithoutNeverAskAgain(this, LIVE_PERMISSIONS);
        List<String> neverAskAgainPermission = MPermission.getNeverAskAgainPermissions(this, LIVE_PERMISSIONS);
        StringBuilder sb = new StringBuilder();
        sb.append("无法开启直播，请到系统设置页面开启权限");
        sb.append(MPermissionUtil.toString(neverAskAgainPermission));
        if (deniedPermissions != null && !deniedPermissions.isEmpty()) {
            sb.append(",下次询问请授予权限");
            sb.append(MPermissionUtil.toString(deniedPermissions));
        }

        Toast.makeText(StartLiveActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
    }

    private void registerAudienceObservers(boolean register) {
        AVChatManager.getInstance().observeAVChatState(this, register);
    }

    // 初始化礼物布局
    protected void findGiftLayout() {
        super.findGiftLayout();
    }


    // 取出缓存的礼物
    private void loadGift() {
        Map gifts = GiftCache.getInstance().getGift(roomId);
        if (gifts == null) {
            return;
        }
        Iterator<Map.Entry<Integer, Integer>> it = gifts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            int type = entry.getKey();
            int count = entry.getValue();
            mGiftList.add(new Gift(GiftType.typeOfValue(type), GiftConstant.titles[type], count, GiftConstant.images[type]));
        }
    }


    @Override
    protected void onDestroy() {
        // 释放资源
        if (videoPlayer != null) {
            videoPlayer.resetVideo();
        }
        registerAudienceObservers(false);
        super.onDestroy();
    }

    View.OnClickListener shareClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mShareView.setVisibility(View.VISIBLE);
        }
    };
    View.OnClickListener shareFriendsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UMshare(SHARE_MEDIA.WEIXIN_CIRCLE);
        }
    };
    View.OnClickListener shareWechatClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UMshare(SHARE_MEDIA.WEIXIN);
        }
    };
    View.OnClickListener shareMicrobolgClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UMshare(SHARE_MEDIA.SINA);
        }
    };
    View.OnClickListener shareCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mShareView.setVisibility(View.GONE);
        }
    };

    View.OnClickListener liveDeleteClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showCloseLive();
        }
    };
    View.OnClickListener authorHeadClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StartLiveActivity.this, UserActivity.class);
            startActivityForResult(intent, 1);
        }
    };
    View.OnClickListener giftLiveOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showGiftLayout();
        }
    };
    View.OnClickListener prepareOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mGiftView.setVisibility(View.GONE);
        }
    };
    View.OnClickListener onLineAudienceClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          /*  ArrayList<Audience> audiences = new ArrayList<>();
            audiences.add(new Audience("大萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", false, true, "123"));
            audiences.add(new Audience("xiao萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", true, false, "133"));
            audiences.add(new Audience("小萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", false, true, "1213"));
            audiences.add(new Audience("水萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", true, false, "1213"));
            mAudienceList.addAll(audiences);
            mAudienceAdapter.notifyDataSetChanged();*/
          mAudienceRecyclerView.setVisibility(View.VISIBLE);
          mMaixuRecyclerView.setVisibility(View.GONE);
        }
    };
    View.OnClickListener onLineMicrophoneClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAudienceRecyclerView.setVisibility(View.GONE);
            mMaixuRecyclerView.setVisibility(View.VISIBLE);

        }
    };
    View.OnClickListener turnCameraClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mVideoCapturer.switchCamera();
        }
    };
    SeekBar.OnSeekBarChangeListener faceSeekbarClick = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mVideoEffect == null) {
                return;
            }
            mVideoEffect.setBeautyLevel(seekBar.getProgress() / 20);
        }
    };
    SeekBar.OnSeekBarChangeListener beautySeekbarClick = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mVideoEffect == null) {
                return;
            }
            mVideoEffect.setFilterLevel((float) seekBar.getProgress() / 100);
        }
    };
    View.OnClickListener beautyCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isVideoBeautyOriginCurrent = true;
            mBeautyLayout.setVisibility(View.GONE);
            mFaceLayout.setVisibility(View.GONE);
        }
    };
    View.OnClickListener closeCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isBeautyBtnCancel = false;
            isVideoBeautyOriginLast = isVideoBeautyOriginCurrent;
            mBeautyView.setVisibility(View.GONE);
        }
    };
    View.OnClickListener imageGroupClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isBeauty = false;
            isMessageGroup = false;
            isimgGroup = true;
            isGift = false;
            isRend = true;
            updataLayout();
            mMessageGrouo.setVisibility(View.GONE);
            mAudienceView.setVisibility(View.VISIBLE);
            mAudienceView.setAlpha(0.7f);
            mAudienceAdapter.setShowType(AudienceAdapter.MXU);
            mMaixuRecyclerView.setVisibility(View.GONE);
            mAudienceRecyclerView.setVisibility(View.VISIBLE);
        }
    };
    View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int childCount = mRootView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = mRootView.getChildAt(i);
                if (childAt == mRenderer) {
                    isGift = false;
                    isimgGroup = false;
                    isMessageGroup = true;
                    isRend = true;
                    isBeauty = false;
                    updataLayout();
                    return true;
                }
            }

            return false;
        }
    };



    // 显示礼物布局
    private void showGiftLayout() {
        isBeauty = false;
        isMessageGroup = false;
        isimgGroup = false;
        isGift = true;
        isRend = true;
        updataLayout();
        mGiftView.setVisibility(View.VISIBLE);
        mGiftAdapter.notifyDataSetChanged();
    }

    @Override
    public void delete(int position) {
        mAudienceList.remove(position);
        mAudienceAdapter.notifyDataSetChanged();
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void microphone(int position) {
        if (currentInteractionMembers.size() < maxInteractionMembers) {
            LogUtil.d(TAG, "link status: waiting. do link");
            doLink(interactionMember);
            getHandler().postDelayed(userJoinRunnable, USER_JOIN_OVERTIME);
            mAudienceList.get(position).setAndienceConnectM(true);
            mAudienceAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "人数已满，请先下麦一位观众", Toast.LENGTH_SHORT).show();
            // 不允许点击
        }
    }

    @Override
    protected void updataRoomCount() {
        super.updataRoomCount();
        mLiveAudienceRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void attention(int position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }


    private final int USER_JOIN_OVERTIME = 10 * 1000;

    @Override
    public void applay(int position) {

    }


    protected void exitQueue(CustomNotification customNotification) {
        Log.e(TAG, "tuichule");
        cancelLinkMember(customNotification.getFromAccount());
    }

    // 主播选择某人连麦
    private void doLink(InteractionMember member) {
        LogUtil.d(TAG, "do link");
        if (member == null) {
            return;
        }

        // 发送通知告诉被选中连麦的人
        Log.e(TAG, "我发过去了");
        Log.e(TAG, "member:" + member);
        member.setSelected(true);

        MicHelper.getInstance().sendLinkNotify(roomId, member);
    }

    // 连麦列表显示正在连麦中
    private void updateMemberListUI(InteractionMember member, MicStateEnum micStateEnum) {
          member.setMicStateEnum(micStateEnum);
         interactionAdapter.notifyDataSetChanged();
        //  interactionLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(InteractionMember member) {

    }
}
