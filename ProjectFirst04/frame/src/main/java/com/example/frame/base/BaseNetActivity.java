package com.example.frame.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;
import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.FriendServiceObserve;
import com.netease.nimlib.sdk.friend.model.AddFriendNotify;
import com.netease.nimlib.sdk.friend.model.Friend;
import com.netease.nimlib.sdk.friend.model.FriendChangedNotify;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.UserServiceObserve;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public abstract class BaseNetActivity<P extends BasePresenter,M> extends BaseActivity implements IConmmonView{
    public P presenter;
    private M model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        presenter = getPresenter();
        model = getModel();
        if (presenter != null && model != null){
            presenter.attach(this, (ICommonModel) model);
        }
        initData();
    }

    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void initData();
    public abstract P getPresenter();
    public abstract M getModel();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null && model != null)presenter.dettach();
    }


    protected void registerObserver(Boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeReceiveSystemMsg(systemMessageObserver, register);
        NIMClient.getService(FriendServiceObserve.class).observeFriendChangedNotify(friendChangedNotifyObserver, register);
        NIMClient.getService(UserServiceObserve.class).observeUserInfoUpdate(userInfoUpdateObserver, register);
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, register);
    }

    //消息接收
    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    if (messages.size() == 0) {
                        return;
                    }
                    IMMessage imMessage = messages.get(0);
                    addMeesage(imMessage);
                    // if(imMessage.getMsgType() == MsgTypeEnum.text)addMeesage(imMessage);
                    //  else if(imMessage.getMsgType() == MsgTypeEnum.image)addImage(imMessage);
                }
            };

    Observer<SystemMessage> systemMessageObserver = new Observer<SystemMessage>() {
        @Override
        public void onEvent(SystemMessage message) {
            Log.d("BaseNetActivity", "friendmessage");
            if (message.getType() == SystemMessageType.AddFriend) {
                AddFriendNotify attachData = (AddFriendNotify) message.getAttachObject();
                if (attachData != null) {
                    // 针对不同的事件做处理
                    if (attachData.getEvent() == AddFriendNotify.Event.RECV_ADD_FRIEND_DIRECT) {
                        // 对方直接添加你为好友
                    } else if (attachData.getEvent() == AddFriendNotify.Event.RECV_AGREE_ADD_FRIEND) {
                        // 对方通过了你的好友验证请求
                    } else if (attachData.getEvent() == AddFriendNotify.Event.RECV_REJECT_ADD_FRIEND) {
                        // 对方拒绝了你的好友验证请求
                    } else if (attachData.getEvent() == AddFriendNotify.Event.RECV_ADD_FRIEND_VERIFY_REQUEST) {
                        Log.e("BaseNetActivity", "有人请求添加");
                        // 对方请求添加好友，一般场景会让用户选择同意或拒绝对方的好友请求。
                        // 通过message.getContent()获取好友验证请求的附言
                    }
                }
            }
        }
    };

    protected void addFriendChange(NimUserInfo nimUserInfo) {
    }

    protected void addMeesage(IMMessage message) {
    }
    //  protected void addImage(IMMessage message){}

    private List<String> accountList = new ArrayList<>();
    private Observer<FriendChangedNotify> friendChangedNotifyObserver = new Observer<FriendChangedNotify>() {
        @Override
        public void onEvent(FriendChangedNotify friendChangedNotify) {
            if (friendChangedNotify.getAddedOrUpdatedFriends().size() == 0 || friendChangedNotify.getDeletedFriends().size() == 0) {
                return;
            }
            //添加好友更新
            List<Friend> addedOrUpdatedFriends = friendChangedNotify.getAddedOrUpdatedFriends(); // 新增的好友
            String account = addedOrUpdatedFriends.get(0).getAccount();
            accountList.add(account);
            List<NimUserInfo> nimList = getNimList(accountList);
            addFriendChange(nimList.get(0));


            List<String> deletedFriendAccounts = friendChangedNotify.getDeletedFriends(); // 删除好友或者被解除好友
            Log.e("BaseNetActivity", "deletedFriendAccounts:" + deletedFriendAccounts);
        }
    };
    // 用户资料变更观察者
    protected Observer<List<NimUserInfo>> userInfoUpdateObserver = new Observer<List<NimUserInfo>>() {
        @Override
        public void onEvent(List<NimUserInfo> users) {
            Log.e("BaseNetActivity", "users:" + users);
        }
    };

    protected void ackAddFriendRequest(String account, Boolean isConsent) {
        // 以通过对方好友请求为例
        NIMClient.getService(FriendService.class).ackAddFriendRequest(account, isConsent).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("BaseNetActivity", "同意添加好友");
            }

            @Override
            public void onFailed(int i) {
                Log.e("BaseNetActivity", "i:" + i);
            }

            @Override
            public void onException(Throwable throwable) {
                Log.e("BaseNetActivity", throwable.getMessage());
            }
        });
    }


    protected void deleteFriend(String account) {
        NIMClient.getService(FriendService.class).deleteFriend(account)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("BaseNetActivity", "删除");
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.e("BaseNetActivity", "i:" + i);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.e("BaseNetActivity", throwable.getMessage());
                    }
                });
    }

    //获取好友集合
    protected List<String> getFriendList() {
        return NIMClient.getService(FriendService.class).getFriendAccounts();
    }


    protected List<NimUserInfo> getNimList(List<String> accountList) {
        return NIMClient.getService(UserService.class).getUserInfoList(accountList);
    }

    //判断是不是我的好友
    protected Boolean isMyFriend(String account) {
        return NIMClient.getService(FriendService.class).isMyFriend(account);
    }

    // 添加用户到黑名单
    protected void addToBlackList(String account) {
        NIMClient.getService(FriendService.class).addToBlackList(account)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("BaseNetActivity", "");
                    }

                    @Override
                    public void onFailed(int i) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                });
    }

    //把用户从黑名单中移除

    protected void removeFromBlackList(String account) {
        NIMClient.getService(FriendService.class).removeFromBlackList(account)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }

                    @Override
                    public void onFailed(int i) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                });
    }

    // 判断用户是否已被拉黑
    boolean isInBlackList(String account) {
        return NIMClient.getService(FriendService.class).isInBlackList(account);
    }

    //设置消息提醒/静音
    protected void setMessageNotify(String account, final boolean notify) {
        NIMClient.getService(FriendService.class).setMessageNotify(account, notify)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (notify) {
                            Toast.makeText(BaseNetActivity.this, "开启消息提醒", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BaseNetActivity.this, "关闭消息提醒", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(int i) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                });
    }

    // 判断用户是否需要消息提醒/静音

    boolean isNeedMessageNotify(String account) {
        return NIMClient.getService(FriendService.class).isNeedMessageNotify(account);
    }

    // 更新本人用户资料
    protected void updateUserInfo(Map<UserInfoFieldEnum, Object> fields) {
        fields.put(UserInfoFieldEnum.Name, "new name");
        NIMClient.getService(UserService.class).updateUserInfo(fields)
                .setCallback(new RequestCallbackWrapper<Void>() {
                    @Override
                    public void onResult(int i, Void aVoid, Throwable throwable) {

                    }
                });
    }

    protected void sendTextMessage(String accout, String text) {
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        final IMMessage textMessage = MessageBuilder.createTextMessage(accout, sessionType, text);
        InvocationFuture<Void> voidInvocationFuture =
                NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
        voidInvocationFuture.setCallback(new MessageCallBack<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                super.onSuccess(aVoid);
                addMeesage(textMessage);
            }
        });
    }

    protected void sendEmojiMessage(String account, File file) {
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        final IMMessage message = MessageBuilder.createImageMessage(account, sessionType, file, file.getName());
        NIMClient.getService(MsgService.class).sendMessage(message, false).setCallback(new MessageCallBack<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                super.onSuccess(aVoid);
                addMeesage(message);
            }
        });
    }

    protected void queryMeesageList(IMMessage imMessage) {
        // 查询比 anchor时间更早的消息，查询20条，结果按照时间降序排列
        NIMClient.getService(MsgService.class).queryMessageListEx(imMessage, QueryDirectionEnum.QUERY_OLD,
                10, false).setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
            @Override
            public void onResult(int code, List<IMMessage> result, Throwable exception) {

            }
        });

    }

    //查询最近联系人列表
    protected void queryRecentContacts() {
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                        Log.e("ChatRoomMessage", "recents:" + recents);
                    }
                });
    }
}
