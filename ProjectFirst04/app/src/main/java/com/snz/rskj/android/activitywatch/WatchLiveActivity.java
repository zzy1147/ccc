package com.snz.rskj.android.activitywatch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.nim.chatroom.demo.DemoCache;
import com.netease.nim.chatroom.demo.base.util.log.LogUtil;
import com.netease.nim.chatroom.demo.entertainment.adapter.InteractionAdapter;
import com.netease.nim.chatroom.demo.entertainment.constant.GiftType;
import com.netease.nim.chatroom.demo.entertainment.constant.LiveType;
import com.netease.nim.chatroom.demo.entertainment.constant.MicApplyEnum;
import com.netease.nim.chatroom.demo.entertainment.constant.MicStateEnum;
import com.netease.nim.chatroom.demo.entertainment.constant.PushLinkConstant;
import com.netease.nim.chatroom.demo.entertainment.constant.PushMicNotificationType;
import com.netease.nim.chatroom.demo.entertainment.helper.ChatRoomMemberCache;
import com.netease.nim.chatroom.demo.entertainment.helper.MicHelper;
import com.netease.nim.chatroom.demo.entertainment.helper.SimpleCallback;
import com.netease.nim.chatroom.demo.entertainment.http.ChatRoomHttpClient;
import com.netease.nim.chatroom.demo.entertainment.model.InteractionMember;
import com.netease.nim.chatroom.demo.entertainment.module.ConnectedAttachment;
import com.netease.nim.chatroom.demo.entertainment.module.GiftAttachment;
import com.netease.nim.chatroom.demo.entertainment.module.LikeAttachment;
import com.netease.nim.chatroom.demo.im.config.UserPreferences;
import com.netease.nim.chatroom.demo.im.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.chatroom.demo.permission.MPermission;
import com.netease.nim.chatroom.demo.permission.annotation.OnMPermissionDenied;
import com.netease.nim.chatroom.demo.permission.annotation.OnMPermissionGranted;
import com.netease.nim.chatroom.demo.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nim.chatroom.demo.permission.util.MPermissionUtil;
import com.netease.nim.chatroom.demo.thirdparty.video.NEVideoView;
import com.netease.nim.chatroom.demo.thirdparty.video.VideoPlayer;
import com.netease.nim.chatroom.demo.thirdparty.video.constant.VideoConstant;
import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatResCode;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatUserRole;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoCaptureOrientation;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoCropRatio;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatTextureViewRenderer;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.util.Entry;
import com.snz.rskj.android.LivePlayerBaseActivity;
import com.snz.rskj.android.R;
import com.snz.rskj.android.activitylive.StartLiveActivity;
import com.snz.rskj.android.adapter.AudienceAdapter;
import com.snz.rskj.android.adapter.baseAdapter.BaseRecyclerAdapter;
import com.snz.rskj.android.adapter.baseAdapter.BaseViewHolder;
import com.snz.rskj.android.bean.andiencebean.Audience;
import com.snz.rskj.android.bean.giftbean.Gift;
import com.snz.rskj.android.view.activity.UserActivity;
import com.snz.rskj.android.view.animator.LoveLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchLiveActivity extends LivePlayerBaseActivity implements InteractionAdapter.MemberLinkListener, VideoPlayer.VideoPlayerProxy, AudienceAdapter.MicrophoneDelete, AudienceAdapter.AudienceMicrophoneInterface, AudienceAdapter.MicrophoneAttentionInterface, AudienceAdapter.MicrophoneApplayInterface {
    private static final String TAG = "WatchLiveActivity";
    private final int BASIC_PERMISSION_REQUEST_CODE = 110;

    private RelativeLayout mVideoLayout;
    private NEVideoView mVideoView;
    private AVChatTextureViewRenderer mRenderer;
    private ImageView mImgKiss;
    private ImageView mImgReport;
    private VideoPlayer videoPlayer;
    private boolean isAgreeToLink = false;
    private MicApplyEnum micApplyEnum;
    private boolean isMyAlreadyApply;
    private boolean isMyVideoLink;
    private int style;
    private AVChatCameraCapturer mVideoCapturer;

    private List<Gift> mAudienceGiftList = new ArrayList<>();
    private View mGiftLayout;
    private TextView mGiftTitle;
    private TextView mParcelTitle;
    private RecyclerView mGiftShowRecyclerview;
    private TextView mGiftMoneyNum;
    private TextView mGiftRecharge;
    private TextView mGiftSendNum;
    private TextView mGiftSendAdd;
    private TextView mGiftSendSend;
    private RelativeLayout mImageGroup;
    private RelativeLayout mMessageGroup;

    private List<Audience> mAudienceList = new ArrayList<>();
    private View mAudienceView;
    private AudienceAdapter mAudienceAdapter;
    private RecyclerView mAudienceRecyclerView;
    private TextView mOnLineAudience;
    private TextView mOnLineMicrophone;
    private View mShareView;
    private TextView mShareWechat;
    private TextView mShareFriends;
    private TextView mShareMicroblog;
    private TextView mShareCancel;
    private View mReportListView;
    private View mReportView;
    private TextView mTypeFive;
    private TextView mTypeFour;
    private TextView mTypeThree;
    private TextView mTypeTwo;
    private TextView mTypeOne;
    private TextView mTypeOthers;
    private TextView mTypeCancel;
    private EditText mReportInfo;
    private ImageView mReportCommitImg;
    private TextView mReportCommitTv;
    private LoveLayout mLoveLayout;

    private int giftPosition;
    private String mS;
    private EditText mEtInput;
    private TextView mTvInput;
    private boolean isMeOnMic;
    private View mRootView;
    private ImageView mImageView;
    private RecyclerView mMaixuRecyclerView;
    private BaseRecyclerAdapter<ChatRoomMember> mLiveAudienceRecyclerAdapter;

    @Override
    protected void updateUI() {
        super.updateUI();
        mRoomNum.setText(roomId);
        mRoomUserNum.setText(String.format("%s人", String.valueOf(roomInfo.getOnlineUserCount())));


    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_watch_live;
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
    protected void doCloseInteraction(int index) {

    }

    public static void start(Context context, String roomId) {
        Intent intent = new Intent();
        intent.setClass(context, WatchLiveActivity.class);
        intent.putExtra(EXTRA_ROOM_ID, roomId);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
    // 连麦者/非连麦者各自界面显示
    @Override
    protected void showConnectionView(int index, String account, String nick, int style) {
        super.showConnectionView(index, account, nick, style);
        if (!DemoCache.getAccount().equals(account)) {
            InteractionView interactionView = interactionGroupView[index];
            interactionView.rootViewLayout.setVisibility(View.VISIBLE);
            interactionView.connectionViewCloseBtn.setVisibility(View.GONE);
            // 非连麦者的语音模式
            Log.e(TAG, ""+liveType  +"----"    + style+""+AVChatType.VIDEO.getValue());
            if (style == AVChatType.AUDIO.getValue()) {
                interactionView.audienceLoadingLayout.setVisibility(View.GONE);
                interactionView.audioModeBypassLayout.setVisibility(View.VISIBLE);
                interactionView.bypassVideoRender.setVisibility(View.GONE);
            } else {
                Log.e(TAG, "走了" );
                interactionView.audienceLivingLayout.setVisibility(View.VISIBLE);
                interactionView.audioModeBypassLayout.setVisibility(View.GONE);
                interactionView.bypassVideoRender.setVisibility(View.VISIBLE);
                interactionView.livingBg.setVisibility(View.GONE);
                try {
                    AVChatManager.getInstance().setupRemoteVideoRender(account, null, false, 0);
                    AVChatManager.getInstance().setupRemoteVideoRender(account, interactionView.bypassVideoRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            interactionView.account = account;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAudienceObservers(true);
        findInputViews();
        initView();
        enterRoom();
        requestBasicPermission();
        NELivePlayer.init(this, null);

    }

    private void initView() {
        mVideoLayout = findViewById(R.id.video_layout);
        mVideoView = findViewById(R.id.video_view);
        mRenderer = findViewById(R.id.video_render);
        mImgKiss = findViewById(R.id.img_kiss);
        mImgReport = findViewById(R.id.img_report);
        mImageGroup = findViewById(R.id.rl_image_group);
        mMessageGroup = findViewById(R.id.rl_message_group);
        mAudienceView = findViewById(R.id.audience_layout);
        mGiftLayout = findViewById(R.id.layout_gift);
        mShareView = findViewById(R.id.layout_share);
        mReportListView = findViewById(R.id.layout_report_list);
        mReportView = findViewById(R.id.layout_report);
        mLoveLayout = findViewById(R.id.love_layout);
        mEtInput = findViewById(R.id.et_input);
        mTvInput = findViewById(R.id.tv_input);
        mRootView = findViewById(R.id.root_view);


        mGiftTitle = mGiftLayout.findViewById(R.id.gift_gift_title);
        mParcelTitle = mGiftLayout.findViewById(R.id.gift_parcel_title);
        mGiftShowRecyclerview = mGiftLayout.findViewById(R.id.gift_show_recyclerview);
        mGiftMoneyNum = mGiftLayout.findViewById(R.id.gift_money_num);
        mGiftRecharge = mGiftLayout.findViewById(R.id.gift_recharge);
        mGiftSendNum = mGiftLayout.findViewById(R.id.gift_send_num);
        mGiftSendAdd = mGiftLayout.findViewById(R.id.gift_send_add);
        mGiftSendSend = mGiftLayout.findViewById(R.id.gift_send_send);

        mAudienceRecyclerView = mAudienceView.findViewById(R.id.live_audience_list);
        mMaixuRecyclerView = mAudienceView.findViewById(R.id.live_maixu_list);

        mOnLineAudience = mAudienceView.findViewById(R.id.tv_online_audience);
        mOnLineMicrophone = mAudienceView.findViewById(R.id.tv_online_microphone);

        mShareWechat = mShareView.findViewById(R.id.share_wechat);
        mShareFriends = mShareView.findViewById(R.id.share_firends);
        mShareMicroblog = mShareView.findViewById(R.id.share_microblog);
        mShareCancel = mShareView.findViewById(R.id.share_cancel);

        mTypeOne = mReportListView.findViewById(R.id.report_type_one);
        mTypeTwo = mReportListView.findViewById(R.id.report_type_two);
        mTypeThree = mReportListView.findViewById(R.id.report_type_three);
        mTypeFour = mReportListView.findViewById(R.id.report_type_four);
        mTypeFive = mReportListView.findViewById(R.id.report_type_five);
        mTypeOthers = mReportListView.findViewById(R.id.report_type_others);
        mTypeCancel = mReportListView.findViewById(R.id.report_type_cancel);
        mReportInfo = mReportView.findViewById(R.id.report_info);
        mReportCommitImg = mReportView.findViewById(R.id.report_commit_img);
        mReportCommitTv = mReportView.findViewById(R.id.report_commit_tv);
        initAdapter();
        setClick();
    }

    private void initAdapter() {
        mAudienceGiftList.add(new Gift(GiftType.ICECREAM, "冰淇淋", 12, R.drawable.gift_icecream, "1毛"));
        mAudienceGiftList.add(new Gift(GiftType.CHOCOLATE, "可爱兄", 3, R.drawable.gift_bolster, "2毛"));
        mAudienceGiftList.add(new Gift(GiftType.ROSE, "巧克力", 6, R.drawable.gift_chocolate, "免费"));
        mAudienceGiftList.add(new Gift(GiftType.ROSE, "玫瑰花", 1, R.drawable.gift_rose, "免费"));
        mGiftShowRecyclerview.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
        BaseRecyclerAdapter<Gift> baseRecyclerAdapter = new BaseRecyclerAdapter<Gift>(this, R.layout.audience_gift_item, mAudienceGiftList) {
            @Override
            public void convert(final BaseViewHolder holder, Gift audienceGift, final int position) {
                holder.setImageResource(R.id.gift_image, audienceGift.getImageId());
                holder.setText(R.id.gift_name, audienceGift.getTitle());
                holder.setText(R.id.gift_money, audienceGift.getGiftPrice());
                holder.setText(R.id.gift_current_num, audienceGift.getCount() + "");
                holder.setOnClickListener(R.id.gift_image, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView view = holder.getView(R.id.gift_current_num);
                        mS = view.getText().toString();
                        giftPosition = position;
                        mImageView = holder.getView(R.id.gift_image);
                        if(mImageView.isSelected()){
                            mImageView.setBackground(null);
                            mImageView.setSelected(false);
                            mImageView.setTag("0");
                        }else{
                            mImageView.setTag("1");
                            mImageView.setBackgroundResource(R.drawable.shape_gift_border);
                            mImageView.setSelected(true);
                        }
                        if(mImageView.getTag().equals("1")){
                            mImageView.setBackground(null);
                            mImageView.setSelected(false);
                        }
                    }
                });

            }
        };

        mLiveAudienceRecyclerAdapter = new BaseRecyclerAdapter<ChatRoomMember>(WatchLiveActivity.this, R.layout.live_audience_item, mChatRoomMemberList) {
            @Override
            public void convert(BaseViewHolder holder, ChatRoomMember o, int position) {
                holder.setImageResource(R.id.audience_image,R.drawable.avatar);
                holder.setText(R.id.audience_name,o.getNick());
                holder.getView(R.id.audience_microphone).setVisibility(View.GONE);
            }
        };
        mAudienceRecyclerView.setAdapter(mLiveAudienceRecyclerAdapter);
        mAudienceRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        mGiftShowRecyclerview.setAdapter(baseRecyclerAdapter);

        mAudienceAdapter = new AudienceAdapter(mAudienceList);
        mAudienceAdapter.setMicrophoneDeleteInterface(this);
        mAudienceAdapter.setAudienceMicrophoneInterface(this);
        mAudienceAdapter.setMicrophoneAttentionInterface(this);
        mAudienceAdapter.setMicrophoneApplayInterface(this);
    }

    @Override
    protected void updataRoomCount() {
        super.updataRoomCount();
        mLiveAudienceRecyclerAdapter.notifyDataSetChanged();
    }

    private void setClick() {
        mReceiveGift.setOnClickListener(giftClick);
        mGiftSendAdd.setOnClickListener(giftAddClick);
        mGiftSendSend.setOnClickListener(sendClick);
        mGiftRecharge.setOnClickListener(rechargeClick);
        mImageGroup.setOnClickListener(imageGroupClick);

        mOnLineAudience.setOnClickListener(onLineAudienceClick);
        mOnLineMicrophone.setOnClickListener(onLineMicrophoneClick);


        mShare.setOnClickListener(shareClick);
        mShareFriends.setOnClickListener(shareFriendsClick);
        mShareWechat.setOnClickListener(shareWechatClick);
        mShareMicroblog.setOnClickListener(shareMicrobolgClick);
        mShareCancel.setOnClickListener(shareCancelClick);


        mTypeOne.setOnClickListener(typeOneClick);
        mTypeTwo.setOnClickListener(typeTwoClick);
        mTypeThree.setOnClickListener(typeThreeClick);
        mTypeFour.setOnClickListener(typeFourClick);
        mTypeFive.setOnClickListener(typeFiveClick);
        mTypeOthers.setOnClickListener(typeOthersClick);
        mTypeCancel.setOnClickListener(typeCancelClick);
        mAnchorHead.setOnClickListener(authorHeadClick);
        mReportInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mReportInfo.getText().toString().length() == 0) {
                    mReportCommitImg.setSelected(false);
                } else {
                    mReportCommitImg.setSelected(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mReportCommitImg.setOnClickListener(typeCommitClick);
        mImgReport.setOnClickListener(reportShowClick);

        mImgKiss.setOnClickListener(kissClick);

        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {//判断动作标识是否匹配
                    send();
                    return true;
                }
                return false;
            }
        });

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGiftLayout.setVisibility(View.GONE);
                mShareView.setVisibility(View.GONE);
                mReportView.setVisibility(View.GONE);
                mReportListView.setVisibility(View.GONE);
                mAudienceView.setVisibility(View.GONE);
                mMessageGroup.setVisibility(View.VISIBLE);
            }
        });

    }


    View.OnClickListener kissClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLoveLayout.addLoveView();
            sendLike();
        }
    };
    View.OnClickListener reportShowClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mReportListView.setVisibility(View.VISIBLE);
        }
    };
    View.OnClickListener typeCommitClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mReportInfo.getText().toString().length() == 0) {
                Toast.makeText(WatchLiveActivity.this, "输入内容不可为空", Toast.LENGTH_SHORT).show();
            } else {
                mReportInfo.setText("");
                mReportListView.setVisibility(View.GONE);
                mReportView.setVisibility(View.GONE);
            }
        }
    };
    View.OnClickListener typeOneClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(WatchLiveActivity.this, "1", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener typeTwoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(WatchLiveActivity.this, "2", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener typeThreeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(WatchLiveActivity.this, "3", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener typeFourClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(WatchLiveActivity.this, "4", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener typeFiveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(WatchLiveActivity.this, "5", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener authorHeadClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WatchLiveActivity.this, UserActivity.class);
            startActivityForResult(intent, 1);
        }
    };
    View.OnClickListener typeCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mReportView.setVisibility(View.GONE);
            mReportListView.setVisibility(View.GONE);
        }
    };
    View.OnClickListener typeOthersClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mReportListView.setVisibility(View.GONE);
            mReportView.setVisibility(View.VISIBLE);
        }
    };


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
    View.OnClickListener onLineAudienceClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          /*  mAudienceAdapter.setShowType(AudienceAdapter.AUDIENCE);
            mAudienceList.clear();
            ArrayList<Audience> audiences = new ArrayList<>();
            audiences.add(new Audience("大萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", false, false, "123"));
            audiences.add(new Audience("xiao萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", true, false, "133"));
            audiences.add(new Audience("小萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", false, true, "1213"));
            audiences.add(new Audience("水萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", true, false, "1213"));
            mAudienceList.addAll(audiences);
            mAudienceAdapter.notifyDataSetChanged();*/
        }
    };
    View.OnClickListener onLineMicrophoneClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAudienceAdapter.setShowType(AudienceAdapter.MXU);

        }
    };
    View.OnClickListener imageGroupClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMessageGroup.setVisibility(View.GONE);
            mAudienceView.setVisibility(View.VISIBLE);
            /*mAudienceView.setAlpha(0.7f);
            mAudienceAdapter.setShowType(AudienceAdapter.MXU);
            mAudienceList.clear();
            ArrayList<Audience> audiences = new ArrayList<>();
            audiences.add(new Audience("大萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", false, false, "123"));
            audiences.add(new Audience("xiao萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", true, false, "133"));
            audiences.add(new Audience("小萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", false, true, "1213"));
            audiences.add(new Audience("水萝卜", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=935292084,2640874667&fm=27&gp=0.jpg", true, false, "1213"));
            mAudienceList.addAll(audiences);
            mAudienceAdapter.notifyDataSetChanged();*/
        }
    };


    // 发送点赞爱心
    private void sendLike() {
        LikeAttachment attachment = new LikeAttachment();
        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomCustomMessage(roomId, attachment);
        setMemberType(message);
        NIMClient.getService(ChatRoomService.class).sendMessage(message, false);
    }

    View.OnClickListener rechargeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(WatchLiveActivity.this, "请充值", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener sendClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendGift();
        }
    };
    View.OnClickListener giftAddClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mGiftSendNum.setText((Integer.parseInt(mGiftSendNum.getText().toString()) + 1) + "");
        }
    };

    View.OnClickListener giftClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mGiftLayout.setVisibility(View.VISIBLE);
        }
    };

    // 发送礼物
    private void sendGift() {
        if (giftPosition == -1) {
            Toast.makeText(WatchLiveActivity.this, "请选择礼物", Toast.LENGTH_SHORT).show();
            return;
        }
        //giftLayout.setVisibility(View.GONE);
        if (Integer.parseInt(mS) >= Integer.parseInt(mGiftSendNum.getText().toString())) {
            GiftAttachment attachment = new GiftAttachment(GiftType.typeOfValue(giftPosition), Integer.parseInt(mGiftSendNum.getText().toString()));
            ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomCustomMessage(roomId, attachment);
            setMemberType(message);
            InvocationFuture<Void> voidInvocationFuture = NIMClient.getService(ChatRoomService.class).sendMessage(message, false);
            voidInvocationFuture.setCallback(new RequestCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onFailed(int i) {
                    Toast.makeText(WatchLiveActivity.this, "i:" + i, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onException(Throwable throwable) {
                    Toast.makeText(WatchLiveActivity.this, "throwable:" + throwable, Toast.LENGTH_SHORT).show();
                }
            });
            giftAnimation.showGiftAnimation(message);
            giftPosition = -1; // 发送完毕，置空
        } else {
            Toast.makeText(this, "您的礼物数量不足，请充值购买", Toast.LENGTH_SHORT).show();
        }

    }

    private void setMemberType(ChatRoomMessage message) {
        Map<String, Object> ext = new HashMap<>();
        ChatRoomMember chatRoomMember = ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount());
        if (chatRoomMember != null && chatRoomMember.getMemberType() != null) {
            ext.put("type", chatRoomMember.getMemberType().getValue());
            message.setRemoteExtension(ext);
        }
    }


    /********************************
     * 初始化
     *******************************/

    private void fetchLiveUrl() {
        ChatRoomHttpClient.getInstance().studentEnterRoom(DemoCache.getAccount(), roomId, new ChatRoomHttpClient.ChatRoomHttpCallback<ChatRoomHttpClient.EnterRoomParam>() {
            @Override
            public void onSuccess(ChatRoomHttpClient.EnterRoomParam enterRoomParam) {
                if (enterRoomParam.getAvType().equals("AUDIO")) {
                    liveType = LiveType.AUDIO_TYPE;
                } else if (enterRoomParam.getAvType().equals("VIDEO")) {
                    liveType = LiveType.VIDEO_TYPE;
                    setRequestedOrientation(enterRoomParam.getOrientation() == 1 ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                pullUrl = enterRoomParam.getPullUrl();
                LogUtil.d(TAG, "fetchLiveUrl pullUrl：" + pullUrl);
                initAudienceParam();
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                if (code == -1) {
                    Toast.makeText(WatchLiveActivity.this, "无法连接服务器, code=" + code, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WatchLiveActivity.this, "观众进入房间失败, code=" + code + ", errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initAudienceParam() {
        // 初始化拉流
        mRenderer.setVisibility(View.GONE);
        mVideoView.setVisibility(View.VISIBLE);
        Log.e(TAG, pullUrl);
        videoPlayer = new VideoPlayer(WatchLiveActivity.this, mVideoView, null, pullUrl,
                UserPreferences.getPlayerStrategy(), this, VideoConstant.VIDEO_SCALING_MODE_FILL_BLACK);

        videoPlayer.openVideo();
    }

    /**
     * 基本权限管理
     */
    private void requestBasicPermission() {
        MPermission.with(WatchLiveActivity.this)
                .addRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(
                        Manifest.permission.READ_PHONE_STATE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        fetchLiveUrl();
        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        finish();
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionGranted(LIVE_PERMISSION_REQUEST_CODE)
    public void onLivePermissionGranted() {
        Toast.makeText(WatchLiveActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
        if (isAgreeToLink) {
            Log.e(TAG, ""+isAgreeToLink);
            doMicLinking();
        } else {
            if (micApplyEnum == MicApplyEnum.VIDEO_VIDEO) {
                Log.e(TAG, "视频");
                doVideoLink();
            } else if (micApplyEnum == MicApplyEnum.VIDEO_AUDIO) {
                //语音连接
            } else {
                doAudioModeLink();
            }
        }
    }

    private void doAudioModeLink() {
        showPushLinkLayout(false, com.netease.nim.chatroom.demo.R.string.audio_applying);
        String ext = getPushLinkExt(AVChatType.AUDIO.getValue());
        ChatRoomHttpClient.getInstance().pushMicLink(roomId, DemoCache.getAccount(), ext, new ChatRoomHttpClient.ChatRoomHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // 加入连麦队列成功，发送自定义通知给主播
                sendPushMicLinkNotify(AVChatType.AUDIO.getValue(), PushMicNotificationType.JOIN_QUEUE.getValue());
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                Toast.makeText(WatchLiveActivity.this, "http push mic link, errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                revertPushUI();
            }
        });
    }

    // 撤销连麦申请布局
    private void revertPushUI() {
        if (liveType == LiveType.VIDEO_TYPE) {

            //   interationInitLayout.setVisibility(View.VISIBLE);
            // audioInteractInitLayout.setVisibility(View.GONE);
        } else if (liveType == LiveType.AUDIO_TYPE) {
            //   interationInitLayout.setVisibility(View.GONE);
            //   audioInteractInitLayout.setVisibility(View.VISIBLE);
        }
        //  applyingLayout.setVisibility(View.GONE);
    }
    public void onBackPressed() {
        super.onBackPressed();
        finishLive();
    }
    // 取消连麦申请
    private void cancelLinking() {
        revertPushUI();
        ChatRoomHttpClient.getInstance().popMicLink(roomId, DemoCache.getAccount(), new ChatRoomHttpClient.ChatRoomHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // 取消连麦成功，发送自定义通知给主播
                MicHelper.getInstance().sendCustomNotify(roomId, roomInfo.getCreator(), PushMicNotificationType.EXIT_QUEUE.getValue(), null, true);
                isMyAlreadyApply = false;
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                LogUtil.d(TAG, "join queue failed, code:" + code);
                Toast.makeText(WatchLiveActivity.this, "join queue failed, errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finishLive() {
        if (isStartLive) {
            logoutChatRoom();
        } else {
            NIMClient.getService(ChatRoomService.class).exitChatRoom(roomId);
            clearChatRoom();
        }
    }
    // 离开聊天室
    private void logoutChatRoom() {
        EasyAlertDialogHelper.createOkCancelDiolag(this, null, getString(R.string.finish_confirm),
                getString(R.string.confirm), getString(R.string.cancel), true,
                new EasyAlertDialogHelper.OnDialogActionListener() {
                    @Override
                    public void doCancelAction() {

                    }

                    @Override
                    public void doOkAction() {
                        if (isMeOnMic) {
                            MicHelper.getInstance().leaveChannel(isMyVideoLink, liveType == LiveType.VIDEO_TYPE, true, meetingName);
                            mVideoCapturer = null;
                        }
                        NIMClient.getService(ChatRoomService.class).exitChatRoom(roomId);
                        clearChatRoom();
                    }
                }).show();
    }
    private void clearChatRoom() {
        ChatRoomMemberCache.getInstance().clearRoomCache(roomId);
        finish();
    }
    /**
     * 连麦
     **/

    // 收到连麦通知
    protected void onMicLinking(JSONObject jsonObject) {
        LogUtil.d(TAG, "on mic linking");
        Log.e(TAG, "isMyAlreadyApply:" + isMyAlreadyApply);
        if (!isMyAlreadyApply) {
            // 我是第一次进来，上次状态清空，所以不连麦
            LogUtil.d(TAG, "first coming, send reject");
            MicHelper.getInstance().sendCustomNotify(roomId, roomInfo.getCreator(), PushMicNotificationType.REJECT_CONNECTING.getValue(), null, true);
            return;
        }
        Log.e(TAG, "QWQ");
        if (!jsonObject.containsKey(PushLinkConstant.style)) {
            return;
        }
        Log.e(TAG, "QAQ");
        isAgreeToLink = true;
        isMyVideoLink = jsonObject.getIntValue(PushLinkConstant.style) == AVChatType.VIDEO.getValue();
        style = jsonObject.getIntValue(PushLinkConstant.style);

        LogUtil.d(TAG, "audience request permission and join channel");
        requestLivePermission();
    }

    protected final static String AVATAR_DEFAULT = "avatar_default";


    // 连麦申请的扩展字段
    private String getPushLinkExt(int style) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PushLinkConstant.style, style);
        jsonObject.put(PushLinkConstant.state, MicStateEnum.WAITING.getValue());

        JSONObject infoJSON = new JSONObject();
        infoJSON.put(PushLinkConstant.nick, DemoCache.getUserInfo().getName());
        infoJSON.put(PushLinkConstant.avatar, AVATAR_DEFAULT);
        jsonObject.put(PushLinkConstant.info, infoJSON);

        return jsonObject.toString();
    }

    // 发送自定义通知给主播
    private void sendPushMicLinkNotify(int style, int command) {
        CustomNotification notification = new CustomNotification();
        notification.setSessionId(roomInfo.getCreator());
        notification.setSessionType(SessionTypeEnum.P2P);

        JSONObject json = new JSONObject();
        json.put(PushLinkConstant.roomid, roomId);
        json.put(PushLinkConstant.style, style);
        json.put(PushLinkConstant.command, command);
        JSONObject infoJSON = new JSONObject();
        infoJSON.put(PushLinkConstant.nick, DemoCache.getUserInfo().getName());
        infoJSON.put(PushLinkConstant.avatar, AVATAR_DEFAULT);
        json.put(PushLinkConstant.info, infoJSON);
        notification.setContent(json.toString());

        NIMClient.getService(MsgService.class).sendCustomNotification(notification).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                LogUtil.d(TAG, "send push mic success");
                Log.e(TAG, "send push mic success");
                isMyAlreadyApply = true;
            }

            @Override
            public void onFailed(int i) {
                LogUtil.d(TAG, "send push mic failed, code:" + i);
                Toast.makeText(WatchLiveActivity.this, "申请失败, code:" + i, Toast.LENGTH_SHORT).show();
                revertPushUI();
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(WatchLiveActivity.this, "throwable:" + throwable, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 申请视频连接
    private void doVideoLink() {
        showPushLinkLayout(true, com.netease.nim.chatroom.demo.R.string.video_applying);
        String ext = getPushLinkExt(AVChatType.VIDEO.getValue());
        ChatRoomHttpClient.getInstance().pushMicLink(roomId, DemoCache.getAccount(), ext, new ChatRoomHttpClient.ChatRoomHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // 加入连麦队列成功，发送自定义通知给主播
                Log.e(TAG, "success  video");
                sendPushMicLinkNotify(AVChatType.VIDEO.getValue(), PushMicNotificationType.JOIN_QUEUE.getValue());
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                LogUtil.d(TAG, "join queue failed, code:" + code);
                if (code == 419) {
                    Toast.makeText(WatchLiveActivity.this, "队列已满", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WatchLiveActivity.this, "join queue failed, code:" + code + ", errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                }
                revertPushUI();
            }
        });
    }

    // 显示连接申请布局
    private void showPushLinkLayout(boolean isVideoMode, int applyingMode) {
        /*if (isVideoMode) {
            interationInitLayout.setVisibility(View.GONE);
        } else {
            audioInteractInitLayout.setVisibility(View.GONE);
        }
        applyingLayout.setVisibility(View.VISIBLE);
        applyMasterNameText.setText(TextUtils.isEmpty(masterNick) ? roomInfo.getCreator() : masterNick);
        applyingTip.setText(applyingMode);*/
    }

    // 开始加入音视频房间，与主播连麦
    private void doMicLinking() {
        // 加入音视频房间
        joinChannel();
    }


    /*********************** join channel ***********************/

    protected void joinChannel() {
        AVChatManager.getInstance().enableRtc();
        if (mVideoCapturer == null) {
            mVideoCapturer = AVChatVideoCapturerFactory.createCameraCapturer();
            AVChatManager.getInstance().setupVideoCapturer(mVideoCapturer);
        }
        Log.e(TAG, ""+meetingName);
        AVChatParameters parameters = new AVChatParameters();
        parameters.setBoolean(AVChatParameters.KEY_SESSION_LIVE_MODE, true);
        parameters.setInteger(AVChatParameters.KEY_SESSION_MULTI_MODE_USER_ROLE, AVChatUserRole.NORMAL);
        parameters.setInteger(AVChatParameters.KEY_VIDEO_FIXED_CROP_RATIO, AVChatVideoCropRatio.CROP_RATIO_16_9);
        int videoOrientation = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? AVChatVideoCaptureOrientation.ORIENTATION_PORTRAIT : AVChatVideoCaptureOrientation.ORIENTATION_LANDSCAPE_RIGHT;
        parameters.setInteger(AVChatParameters.KEY_VIDEO_CAPTURE_ORIENTATION, videoOrientation);
        parameters.setBoolean(AVChatParameters.KEY_VIDEO_ROTATE_IN_RENDING, false);
        AVChatManager.getInstance().setParameters(parameters);
        if (liveType == LiveType.VIDEO_TYPE) {
            AVChatManager.getInstance().enableVideo();
        }
        if (isMyVideoLink) {
            AVChatManager.getInstance().startVideoPreview();
        }
        MicHelper.getInstance().joinChannel(meetingName, isMyVideoLink, new MicHelper.ChannelCallback() {

            @Override
            public void onJoinChannelSuccess() {
                // 打开话筒
                AVChatManager.getInstance().setSpeaker(true);
                // 释放拉流
                releaseVideoPlayer();
                //查找所有连麦者
                fetchMicList();
                // 连麦者显示连麦画面
                showOnMicView(DemoCache.getAccount());

                mVideoView.setVisibility(View.GONE);
                mRenderer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onJoinChannelFailed() {
                Toast.makeText(DemoCache.getContext(), "join channel failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showOnMicView(String account) {
        if (DemoCache.getAccount().equals(account)) {
            // 我是连麦者，所以永远是第一个窗口
            isMeOnMic = true;
            InteractionView interactionView = interactionGroupView[0]; //自己作为最底部的画面
            interactionView.account = account;
            interactionView.rootViewLayout.setVisibility(View.VISIBLE);
            interactionView.audienceLoadingLayout.setVisibility(View.GONE);
            updateMicUI(style);
            if (liveType == LiveType.VIDEO_TYPE && style == AVChatType.VIDEO.getValue()) {
                interactionView.audienceLivingLayout.setVisibility(View.VISIBLE);
                interactionView.audioModeBypassLayout.setVisibility(View.GONE);
                interactionView.bypassVideoRender.setVisibility(View.VISIBLE);
                AVChatManager.getInstance().setupLocalVideoRender(null, false, 0);
                AVChatManager.getInstance().setupLocalVideoRender(interactionView.bypassVideoRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
            } else if (style == AVChatType.AUDIO.getValue()) {
                interactionView.bypassVideoRender.setVisibility(View.GONE);
                interactionView.audienceLivingLayout.setVisibility(View.GONE);
                interactionView.audioModeBypassLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    // 更新UI布局,包括输入框,按钮的变化
    private void updateMicUI(int style) {
        interactionGroupView[0].rootViewLayout.setVisibility(View.VISIBLE);
     //   switchBtn.setVisibility(style == AVChatType.VIDEO.getValue() ? View.VISIBLE : View.GONE);
      //  interactionBtn.setVisibility(View.GONE);
       // inputPanel.hideInputPanel();
        //inputPanel.collapse(true);
       // controlContainer.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//设置置底
    //    controlContainer.setLayoutParams(lp);//动态改变布局
    }

    /******************** fetch mic list *************************/

    private void fetchMicList() {
        NIMClient.getService(ChatRoomService.class).fetchQueue(roomId).setCallback(new RequestCallback<List<Entry<String, String>>>() {
            @Override
            public void onSuccess(List<Entry<String, String>> entries) {
                showOnMicMember(entries);
            }

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    // 普通观众显示连麦者昵称
    private void showOnMicMember(List<Entry<String, String>> entries) {
        boolean isShowNick = false;
        for (Entry<String, String> entry : entries) {
            String ext = entry.value;
            String account = entry.key;
            String nick = null;
            MicStateEnum micStateEnum = null;

            try {
                JSONObject jsonObject = JSONObject.parseObject(ext);
                if (jsonObject != null) {
                    JSONObject info = (JSONObject) jsonObject.get(PushLinkConstant.info);
                    nick = info.getString(PushLinkConstant.nick);
                    micStateEnum = MicStateEnum.typeOfValue(jsonObject.getIntValue(PushLinkConstant.state));
                    style = jsonObject.getInteger(PushLinkConstant.style);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (micStateEnum == MicStateEnum.CONNECTED && !account.equals(DemoCache.getAccount())) {
                int index = getEmptyIndex();
//                updateOnMicName(index,nick);
                showConnectionView(index, account, nick, style);
                isShowNick = true;
            }
        }

        if (!isShowNick) {
//            onMicNameText.setVisibility(View.GONE);
        }
    }

    private int getEmptyIndex() {
        int index = 1;
      /*  if (interactionGroupView[index].account != null) {
            index = 2;
        }*/
        return index;
    }

    // 释放拉流
    private void releaseVideoPlayer() {
        if (videoPlayer != null) {
            videoPlayer.resetVideo();
        }
        videoPlayer = null;
    }

    @OnMPermissionDenied(LIVE_PERMISSION_REQUEST_CODE)
    public void onLivePermissionDenied() {
        List<String> deniedPermissions = MPermission.getDeniedPermissions(this, LIVE_PERMISSIONS);
        String tip = "您拒绝了权限" + MPermissionUtil.toString(deniedPermissions) + "，无法开启直播";
        Toast.makeText(WatchLiveActivity.this, tip, Toast.LENGTH_SHORT).show();
        if (isAgreeToLink) {
            LogUtil.d(TAG, "permission denied, send reject");
            MicHelper.getInstance().sendCustomNotify(roomId, roomInfo.getCreator(), PushMicNotificationType.REJECT_CONNECTING.getValue(), null, true);
        }
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

        Toast.makeText(WatchLiveActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
        if (isAgreeToLink) {
            LogUtil.d(TAG, "permission denied as never ask again, send reject");
            MicHelper.getInstance().sendCustomNotify(roomId, roomInfo.getCreator(), PushMicNotificationType.REJECT_CONNECTING.getValue(), null, true);
        }
    }



    private void registerAudienceObservers(boolean register) {
        AVChatManager.getInstance().observeAVChatState(this, register);
    }

    @Override
    protected void onConnected() {

    }

    @Override
    protected void onDisconnected() {

    }

    @Override
    public void onClick(InteractionMember member) {

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
        if (i == AVChatResCode.JoinChannelCode.OK && liveType == LiveType.AUDIO_TYPE) {
            AVChatManager.getInstance().setSpeaker(true);
        }
    }

    @Override
    public void onUserJoined(String s) {
        if (liveType == LiveType.VIDEO_TYPE && s.equals(roomInfo.getCreator())) {
            Log.e(TAG, s +"----"+DemoCache.getAccount());
            if (s.equals(DemoCache.getAccount())) {
                interactionGroupView[0].livingBg.setVisibility(View.VISIBLE);
            }
            AVChatManager.getInstance().setupRemoteVideoRender(roomInfo.getCreator(), null, false, 0);
            AVChatManager.getInstance().setupRemoteVideoRender(roomInfo.getCreator(), mRenderer, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        }
        if (liveType != LiveType.VIDEO_TYPE) {
            //  preparedText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserLeave(String s, int i) {
        Log.e(TAG, "onUserLeave");
        if (s.equals(roomInfo.getCreator())) {
            MicHelper.getInstance().leaveChannel(isMyVideoLink, liveType == LiveType.VIDEO_TYPE, true, meetingName);
            mVideoCapturer = null;
        } else if (s.equals(DemoCache.getAccount())) {
            closeMySelfMicView();
            switchVideoPlayer();
        } else {
            int index = getInteractionViewIndexByAccount(s);
            doCloseInteractionView(index);
        }
    }

    @Override
    public void onLeaveChannel() {

    }

    @Override
    public void onProtocolIncompatible(int i) {

    }

    @Override
    public void onDisconnectServer(int i) {

    }

    @Override
    public void onNetworkQuality(String s, int i, AVChatNetworkStats avChatNetworkStats) {

    }

    @Override
    public void onCallEstablished() {
        AVChatManager.getInstance().enableAudienceRole(false);
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
        if (s.equals(roomInfo.getCreator())) {
        }
        if (s.equals(DemoCache.getAccount())) {
            interactionGroupView[0].livingBg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onVideoFrameResolutionChanged(String s, int i, int i1, int i2) {

    }

    @Override
    public void onVideoFpsReported(String s, int i) {

    }

    @Override
    public boolean onVideoFrameFilter(AVChatVideoFrame avChatVideoFrame, boolean b) {
        return true;
    }

    @Override
    public boolean onAudioFrameFilter(AVChatAudioFrame avChatAudioFrame) {
        return true;
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
                        Toast.makeText(WatchLiveActivity.this, "cg", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "用户被禁言", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DemoCache.getContext(), "消息发送失败：code:" + code, Toast.LENGTH_SHORT).show();
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
    public void onInputPanelExpand() {

    }

    @Override
    public void shouldCollapseInputPanel() {

    }

    @Override
    public boolean isLongClickEnabled() {
        return false;
    }

    @Override
    public boolean isDisconnected() {
        return false;
    }

    @Override
    public void onError() {

    }

    @Override
    public void onCompletion() {

    }

    @Override
    public void onPrepared() {
        LogUtil.d(TAG, "on prepared, hide preparedText");
        if (liveType == LiveType.NOT_ONLINE) {
            return;
        }
        isStartLive = true;
        mRenderer.setVisibility(View.GONE);
        mVideoView.setVisibility(View.VISIBLE);
        showModeLayout();
    }

    @Override
    public void onInfo(NELivePlayer mp, int what, int extra) {

    }

    @Override
    protected void onReceiveChatRoomInfoUpdate(Map<String, Object> extension) {
        super.onReceiveChatRoomInfoUpdate(extension);
        if (extension != null) {
            if (extension.get(PushLinkConstant.type) != null) {
                liveType = LiveType.typeOfValue((int) extension.get(PushLinkConstant.type));
                if (liveType == LiveType.NOT_ONLINE) {
                  //  pkInfoLayout.setVisibility(View.GONE);
                   // isPk = false;
                  // showFinishLayout();
                    closeMySelfMicView();
                    // videoPlayer不等于null 则属于拉流状况
                   reOpenVideoPlay();
                }
            }
        //    parseRoomPkInfo(extension);
        }
    }

    @Override
    protected void onMicConnectedMsg(ChatRoomMessage message) {
        super.onMicConnectedMsg(message);
        ConnectedAttachment attachment = (ConnectedAttachment) message.getAttachment();

        if (!DemoCache.getAccount().equals(attachment.getAccount()) && isMeOnMic) {
            showConnectionView(getEmptyIndex(), attachment.getAccount(), attachment.getNick(), attachment.getStyle());
        }
    }

    // 收到主播断开连麦通知
    @Override
    protected void onMicCanceling() {
        closeMySelfMicView();

        // 确保还处在频道中时，才要切换成拉流模式
        if (videoPlayer != null) {
            return;
        }

        //preparedText.setVisibility(View.VISIBLE);

        // 离开频道
        if (liveType != LiveType.NOT_ONLINE) {
            MicHelper.getInstance().leaveChannel(isMyVideoLink, liveType == LiveType.VIDEO_TYPE, true, meetingName);
            mVideoCapturer = null;
            // 切换拉流
            switchVideoPlayer();
        }
    }
    private void switchVideoPlayer() {
        if (videoPlayer == null && liveType != LiveType.NOT_ONLINE) {
            initAudienceParam();
        }
    }

    @Override
    protected void onMicDisConnectedMsg(String account) {
        super.onMicDisConnectedMsg(account);
        if (isMeOnMic && !TextUtils.isEmpty(account) && !account.equals(DemoCache.getAccount())) {
            LogUtil.i(TAG, "onMicDisConnectedMsg account: " + account + "  myself is: " + DemoCache.getAccount());
            return;
        }
        closeMySelfMicView();
        reOpenVideoPlay();
    }

    private void closeMySelfMicView() {
        // 隐藏布局（无论是否处于频道中，都要清理界面上的布局，如连麦者姓名）
        for (int i = 0; i < maxInteractionMembers; i++) {
            doCloseInteractionView(i);
        }
        resetConnectionView();
    }
    // 收到主播断开连麦全局消息
    protected void resetConnectionView() {
        LogUtil.i(TAG, "reset Connection view");
        interactionGroupView[0].livingBg.setVisibility(View.GONE);
        isAgreeToLink = false;
        if (isMeOnMic) {
            isMeOnMic = false;
            isMyAlreadyApply = false;
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ABOVE, R.id.messageActivityBottomLayout);//设置在输入框上方
            interactionGroupView[0].bypassVideoRender.setVisibility(View.GONE);
            resetApplyLayout();
        }
        super.resetConnectionView(0);
    }
    private void resetApplyLayout() {
    }
    private void reOpenVideoPlay() {
        LogUtil.d(TAG, "reOpenVideoPlay");
        if (mVideoView == null) {
            return;
        }
        if (videoPlayer != null) {
            videoPlayer.resetResource();
        } else {
            // videoPlayer为null 则在连麦中。退出channel
            LogUtil.d(TAG, "leaveRoom, meetingName:" + meetingName);
            MicHelper.getInstance().leaveChannel(isMyVideoLink, liveType == LiveType.VIDEO_TYPE, true, meetingName);
            mVideoCapturer = null;
            videoPlayer = new VideoPlayer(WatchLiveActivity.this, mVideoView, null, pullUrl,
                    UserPreferences.getPlayerStrategy(), this, VideoConstant.VIDEO_SCALING_MODE_FILL_BLACK);
        }
        videoPlayer.postReopenVideoTask(VideoPlayer.VIDEO_COMPLETED_REOPEN_TIMEOUT);
    }

    @Override
    public void delete(int position) {
        mAudienceList.remove(position);
        mAudienceAdapter.notifyDataSetChanged();
        cancelLinking();
    }

    @Override
    public void microphone(int position) {
        requestLivePermission();
    }

    @Override
    public void attention(int position) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initView();
        initAudienceParam();
    }

    @Override
    public void applay(int position) {
        doVideoLink();
    }
}
