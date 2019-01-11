package com.snz.rskj.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.netease.nim.avchatkit.AVChatKit;
import com.netease.nim.avchatkit.activity.AVChatActivity;
import com.netease.nim.avchatkit.config.AVChatOptions;
import com.netease.nim.avchatkit.model.IUserInfoProvider;
import com.netease.nim.chatroom.demo.DemoCache;
import com.netease.nim.chatroom.demo.base.util.string.MD5;
import com.netease.nim.chatroom.demo.im.activity.WelcomeActivity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.snz.rskj.android.R;
import com.snz.rskj.android.activitylive.StartLiveActivity;
import com.snz.rskj.android.activitywatch.WatchLiveActivity;
import com.snz.rskj.android.bean.LoginBean;
import com.snz.rskj.android.bean.UserMsg;
import com.snz.rskj.android.configs.Config_Sp;
import com.snz.rskj.android.design.CommonTitle;
import com.snz.rskj.android.utils.SharedPrefrenceUtils;
import com.snz.rskj.android.view.fragment.ChatFragment;
import com.snz.rskj.android.view.fragment.HomeFragment;
import com.snz.rskj.android.view.fragment.MatchingFragment;
import com.snz.rskj.android.view.fragment.MianeFragment;
import com.snz.rskj.android.view.home.model.HomeFragModel;
import com.snz.rskj.android.widget.PopupMenuUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.HEAD;

public class HomeActivity extends BaseNetActivity<CommonPresenter, HomeFragModel> implements IConmmonView, RadioGroup.OnCheckedChangeListener {
    //HomeActivity 中或者在欢迎页面中需要完成权限处理的问题(要处理的话我希望是所有的权限)
    public CommonTitle mTitle;
//    @BindView(R.id.bottom_cutLine)
//    View bottomCutLine;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.home_rb)
    RadioButton homeRb;
    @BindView(R.id.talk_rb)
    RadioButton talkRb;
//    @BindView(R.id.circle_rb)
//    Button circleRb;
    @BindView(R.id.massage_rb)
    RadioButton massageRb;
    @BindView(R.id.mine_rb)
    RadioButton mineRb;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private PopupWindow window;
    private FragmentManager mManager;
    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private MianeFragment mianeFragment;
    private MatchingFragment matchingFragment;
    private AbortableFuture<LoginInfo> loginRequest;
    private static final String TAG = "HomeActivity";
    private PopupWindow mChoosPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        ButterKnife.bind(this);
        final ImageView imageView=findViewById(R.id.image_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenuUtil.getInstance()._show(HomeActivity.this, imageView);
            }
        });
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setStatusBarColor(ContextCompat.getColor(this, R.color.red_theme));
        mManager = getSupportFragmentManager();
        radioGroup.setOnCheckedChangeListener(this);

//        final String account = "zzy";
//        final String token = MD5.getStringMD5("123456");
//
//        AVChatOptions avChatOptions = new AVChatOptions(){
//            @Override
//            public void logout(Context context) {
//                // 主程序登出操作
//            }
//        };
//// 点击通知栏，入口Activity
//        avChatOptions.entranceActivity = WelcomeActivity.class;
//// 通知栏图标icon
//        avChatOptions.notificationIconRes = com.example.frame.R.drawable.ic_stat_notify_msg;
//// 初始化 AVChatKit
//        AVChatKit.init(avChatOptions);
//
//
//        AVChatKit.setUserInfoProvider(new IUserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String account) {
//                return NimUIKit.getUserInfoProvider().getUserInfo(account);
//            }
//
//            @Override
//            public String getUserDisplayName(String account) {
//                return "zzy";
//            }
//        });
//        // 登录
//        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
//        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
//            @Override
//            public void onSuccess(LoginInfo param) {
//                Log.e(TAG, "login success");
//
//                DemoCache.setAccount(account);
//                AVChatKit.setAccount(account);
//
//            }
//
//            @Override
//            public void onFailed(int code) {
//                if (code == 302 || code == 404) {
//                    Toast.makeText(HomeActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(HomeActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onException(Throwable exception) {
//
//                Log.e(TAG, exception.getMessage());
//            }
//        });
        final String account = "zzzg";
        final String token = "e10adc3949ba59abbe56e057f20f883e";
        // 登录
        Log.e(TAG, "login");
        NIMClient.getService(AuthService.class).login(new LoginInfo(account, token)).setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                Log.e(TAG, "login success");

                DemoCache.setAccount(account);

            }

            @Override
            public void onFailed(int code) {
                Log.e(TAG, code + "");
                if (code == 302 || code == 404) {
                    Toast.makeText(HomeActivity.this, com.netease.nim.chatroom.demo.R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {

                Log.e(TAG, exception.getMessage());
            }
        });
        ;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({ R.id.container, R.id.home_rb, R.id.talk_rb, R.id.massage_rb, R.id.mine_rb, R.id.radio_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.container:
                break;
            case R.id.home_rb:
                break;
            case R.id.talk_rb:
                break;
            case R.id.massage_rb:
                break;
            case R.id.mine_rb:
                break;
            case R.id.radio_group:
                break;
            case R.id.homeActivity_id:
                break;
        }
    }

    private String showUser;
    private boolean showLog = false;

    //    private
    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        showUser = SharedPrefrenceUtils.getString(this, Config_Sp.USRMSG);
        setStatusBarColor(ContextCompat.getColor(this, R.color.red_theme));
        mManager = getSupportFragmentManager();
        radioGroup.setOnCheckedChangeListener(this);
        mManager = getSupportFragmentManager();
        UserMsg serializableList = SharedPrefrenceUtils.getObject(this, Config_Sp.USRMSG);
        if (serializableList != null) {
            if (serializableList != null) {
                showLog = true;
            }
//        FragmentTransaction transaction = mManager.beginTransaction();
//        homeFragment = new HomeFragment();
//        transaction.replace(R.id.container, homeFragment).commit();
//        homeRb.setChecked(true);
//        if (!TextUtils.isEmpty(showUser)) showLog = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentTransaction transaction = mManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.container, homeFragment).commit();
        homeRb.setChecked(true);
        if (!TextUtils.isEmpty(showUser)) showLog = true;

    }

    @Override
    public void initData() {
    }

    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public HomeFragModel getModel() {
        return new HomeFragModel();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = mManager.beginTransaction();
        switch (checkedId) {
            case R.id.home_rb:
                if (homeFragment == null) homeFragment = new HomeFragment();
                transaction.replace(R.id.container, homeFragment).commit();
                break;
            case R.id.talk_rb:
                if (chatFragment == null) {
                    chatFragment = new ChatFragment();
                }
                transaction.replace(R.id.container, chatFragment).commit();
                break;
            case R.id.circle_rb:
                break;

            case R.id.massage_rb:
                initStartLog();
//                if (mianeFragment == null) mianeFragment = new MianeFragment();
//                transaction.replace(R.id.container, mianeFragment).commit();
                break;
            case R.id.mine_rb:
                Log.i("zzy","========");
//                AVChatKit.setContext(getApplicationContext());
//                AVChatKit.outgoingCall(HomeActivity.this,"zzy1147","zzy1147", AVChatType.VIDEO.getValue(), AVChatActivity.FROM_INTERNAL);
                if (matchingFragment == null) matchingFragment = new MatchingFragment();
                transaction.replace(R.id.container, matchingFragment).commit();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mChoosPopupWindow != null) {
            mChoosPopupWindow.dismiss();
            mChoosPopupWindow = null;
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void initStartLog() {

        startActivity(new Intent(this, LoginActivity.class));

    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {
    }

    @Override
    public void onError(Throwable e) {
        getPresenter().onError(e);
    }
}
