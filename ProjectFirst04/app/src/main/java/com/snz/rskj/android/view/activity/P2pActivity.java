package com.snz.rskj.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.snz.rskj.android.R;
import com.snz.rskj.android.im.ChatRoomMessage;
import com.snz.rskj.android.im.MessageAdapter;
import com.snz.rskj.android.model.TestModel;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class P2pActivity extends BaseNetActivity<CommonPresenter, TestModel> implements View.OnClickListener, TextView.OnEditorActionListener {

    @BindView(R.id.p2p_title)
    TextView p2pTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.message_list)
    RecyclerView messageList;
    @BindView(R.id.voice_btn)
    ImageView voiceBtn;
    @BindView(R.id.p2p_emoji_btn)
    ImageView p2pEmojiBtn;
    @BindView(R.id.p2p_picture_btn)
    ImageView p2pPictureBtn;
    @BindView(R.id.chat_input)
    EditText chatInput;
    private NimUserInfo mUserinfo;
    private List<IMMessage> mIMMessageList = new ArrayList<>();
    private MessageAdapter mMessageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_p2p;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(this);
        Intent intent = getIntent();
        mUserinfo = (NimUserInfo) intent.getSerializableExtra("userinfo");
        initAdapter();
        String name = mUserinfo.getName();
        p2pTitle.setText(name);
        setClick();
    }

    private void setClick() {
        chatInput.setOnEditorActionListener(this);
    }

    private void initAdapter() {
        mMessageAdapter = new MessageAdapter(mIMMessageList);
        messageList.setAdapter(mMessageAdapter);
        messageList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {

    }

    @Override
    protected void addMeesage(IMMessage message) {
        super.addMeesage(message);
        mIMMessageList.add(message);
        messageList.smoothScrollToPosition(mMessageAdapter.getItemCount());
        mMessageAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.voice_btn, R.id.p2p_emoji_btn, R.id.p2p_picture_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.voice_btn:
                break;
            case R.id.p2p_emoji_btn:
                break;
            case R.id.p2p_picture_btn:
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                sendEmojiMessage(mUserinfo.getAccount(), new File(externalStorageDirectory, "/output.png"));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEND:
                if (v.getText().length() == 0) {
                    Toast.makeText(mApplication, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    sendTextMessage(mUserinfo.getAccount(), v.getText().toString());
                    chatInput.setText("");
                }
                break;
        }
        return true;
    }
}
