package com.snz.rskj.android.view.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.snz.rskj.android.R;
import com.snz.rskj.android.im.ChatRoomMessage;
import com.snz.rskj.android.im.FreindsAdapter;
import com.snz.rskj.android.model.TestModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseNetActivity<CommonPresenter, TestModel> implements FreindsAdapter.FriendsOnClick, XRecyclerView.LoadingListener {

    @BindView(R.id.chat_title)
    TextView chatTitle;
    @BindView(R.id.chat_add_friend)
    TextView chatAddFriend;
    @BindView(R.id.chat_recycler)
    XRecyclerView chatRecycler;

    private static final String TAG = "ChatActivity";
    private List<NimUserInfo> mNimUserInfoList = new ArrayList<>();
    private FreindsAdapter mFreindsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        registerObserver(true);
        initAdapter();
    }

    private void initAdapter() {
        mFreindsAdapter = new FreindsAdapter(mNimUserInfoList);
        chatRecycler.setAdapter(mFreindsAdapter);
        chatRecycler.setLayoutManager(new LinearLayoutManager(this));
        mFreindsAdapter.setFriendsOnClick(this);
        chatRecycler.setLoadingListener(this);
    }

    @Override
    public void initData() {
        mNimUserInfoList.clear();
        List<NimUserInfo> nimList = getNimList(getFriendList());
        if (nimList != null) {
            mNimUserInfoList.addAll(nimList);
            mFreindsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public TestModel getModel() {
        return new TestModel();
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }


    @OnClick(R.id.chat_add_friend)
    public void onViewClicked() {
        boolean isMyFriend = NIMClient.getService(FriendService.class).isMyFriend("zzg22");
        if (isMyFriend) {
            addFriend("zzg22", "你好");
        } else {
            Toast.makeText(mApplication, "请勿重复添加好友", Toast.LENGTH_SHORT).show();
        }
        sendTextMessage("zzg22", "123");
    }

    private void addFriend(String account, String postscript) {
        VerifyType verifyType = VerifyType.DIRECT_ADD; // 发起好友验证请求
        String msg = postscript;//请求信息
        NIMClient.getService(FriendService.class).addFriend(new AddFriendData(account, verifyType, msg))
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "cg");
                        Toast.makeText(mApplication, "添加成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.e("ChatActivity", "error ： --  i:" + i);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.e("ChatActivity", throwable.getMessage());
                    }
                });
    }

    @Override
    protected void addFriendChange(NimUserInfo nimUserInfo) {
        super.addFriendChange(nimUserInfo);
        mNimUserInfoList.add(nimUserInfo);
        chatRecycler.smoothScrollToPosition(0);
        mFreindsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObserver(false);
    }

    @Override
    public void friends(NimUserInfo nimUserInfo) {
        Intent intent = new Intent(this, P2pActivity.class).putExtra("userinfo", nimUserInfo);
        startActivityForResult(intent, 666);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        chatRecycler.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        chatRecycler.loadMoreComplete();
    }

}
