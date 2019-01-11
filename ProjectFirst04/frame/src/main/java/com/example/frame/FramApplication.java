package com.example.frame;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.netease.nim.chatroom.demo.DemoCache;
import com.netease.nim.chatroom.demo.base.util.ScreenUtil;
import com.netease.nim.chatroom.demo.base.util.crash.AppCrashHandler;
import com.netease.nim.chatroom.demo.base.util.log.LogUtil;
import com.netease.nim.chatroom.demo.base.util.string.MD5;
import com.netease.nim.chatroom.demo.base.util.sys.SystemUtil;

import com.netease.nim.chatroom.demo.im.config.UserPreferences;
import com.netease.nim.chatroom.demo.im.util.storage.StorageType;
import com.netease.nim.chatroom.demo.im.util.storage.StorageUtil;
import com.netease.nim.chatroom.demo.inject.FlavorDependent;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


public class FramApplication extends Application {
    public static FramApplication mFramApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mFramApplication = this;

//        DemoCache.setContext(this);
//
//        NIMClient.init(this, getLoginInfo(), getOptions());
//
//        // crash handler
//        AppCrashHandler.getInstance(this);
//        Log.e("FramApplication", "inMainProcess():" + inMainProcess());
//        if (inMainProcess()) {
//            // 注册自定义消息附件解析器
//            NIMClient.getService(MsgService.class).registerCustomAttachmentParser(FlavorDependent.getInstance().getMsgAttachmentParser());
//            // init tools
//            StorageUtil.init(this, null);
//            ScreenUtil.init(this);
//            DemoCache.initImageLoaderKit();
//
//            // init log
//            initLog();
//            FlavorDependent.getInstance().onApplicationCreate();
//        }


       NIMClient.init(this,getLoginInfo(), getOptions());
        // crash handler
        AppCrashHandler.getInstance(this);
        Log.e("FramApplication", "inMainProcess():" + inMainProcess());
        if (inMainProcess()) {
            // 注册自定义消息附件解析器
            NIMClient.getService(MsgService.class).registerCustomAttachmentParser(FlavorDependent.getInstance().getMsgAttachmentParser());
            // init tools
            StorageUtil.init(this, null);
            ScreenUtil.init(this);
            DemoCache.initImageLoaderKit();

            // init log
            initLog();
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());


            FlavorDependent.getInstance().onApplicationCreate();
        }

        UMConfigure.init(this,"5c24c147b465f5d55c00005a","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");

    }




    public static Context getContext(){
        return mFramApplication.getApplicationContext();
    }

    public static FramApplication getApplication(){
        return mFramApplication;
    }




    private LoginInfo getLoginInfo() {
        String account = "";
        String token = "";
//e10adc3949ba59abbe56e057f20f883e
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private SDKOptions getOptions() {
        SDKOptions options = new SDKOptions();

        StatusBarNotificationConfig config = UserPreferences.getStatusConfig();
        if (config == null) {
            config = new StatusBarNotificationConfig();
        }
        // 点击通知需要跳转到的界面
      //  config.notificationEntrance = WelcomeActivity.class;
        //config.notificationSmallIconId = com.netease.nim.chatroom.demo.R.drawable.ic_stat_notify_msg;

        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        UserPreferences.setStatusConfig(config);

        // 配置保存图片，文件，log等数据的目录
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim/";
        options.sdkStorageRootPath = sdkPath;
        Log.i("demo", FlavorDependent.getInstance().getFlavorName() + " demo nim sdk log path=" + sdkPath);

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "NETEASE";

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小，
        options.thumbnailSize = (int) (0.5 * ScreenUtil.screenWidth);

        // 用户信息提供者
        options.userInfoProvider = null;

        // 定制通知栏提醒文案（可选，如果不定制将采用SDK默认文案）
        options.messageNotifierCustomization = null;

        return options;
    }

    private boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    private void initLog() {
        String path = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG);
        LogUtil.init(path, Log.DEBUG);
        LogUtil.i("demo", FlavorDependent.getInstance().getFlavorName() + " demo log path=" + path);
    }
}
