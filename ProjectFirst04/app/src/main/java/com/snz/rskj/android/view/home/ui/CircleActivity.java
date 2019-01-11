package com.snz.rskj.android.view.home.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.activitylive.StartLiveActivity;
import com.snz.rskj.android.design.CommonTitle;
import com.snz.rskj.android.view.activity.HomeActivity;
import com.snz.rskj.android.view.home.model.ChatModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircleActivity extends BaseNetActivity<CommonPresenter, ChatModel> implements IConmmonView {


    @BindView(R.id.cirle_common_title)
    CommonTitle cirleCommonTitle;
    @BindView(R.id.chat_ed_search)
    EditText chatEdSearch;
    @BindView(R.id.chat_table)
    TabLayout chatTable;
    @BindView(R.id.chat_review_recommend)
    RecyclerView chatReviewRecommend;
    @BindView(R.id.chat_review_my)
    RecyclerView chatReviewMy;
    @BindView(R.id.chat_lin)
    LinearLayout chatLin;
    @BindView(R.id.chat_circle_add)
    TextView chatCircleAdd;
    @BindView(R.id.chat_root_rela)
    RelativeLayout chatRootRela;
    private PopupWindow mWindow;
    private ImageView mImg;
    private TextInputEditText mMassgag;
    private TextInputEditText mName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle;
    }

    @Override
    public void initView() { }

    @Override
    public void initData() { }

    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public ChatModel getModel() {
        return new ChatModel();
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {

    }

    @Override
    public void onError(Throwable e) {
        presenter.onError(e);
    }

    @OnClick(R.id.chat_circle_add)
    public void onViewClicked() {
        initPop();

    }

    private void initPop() {
        View popupView = this.getLayoutInflater().inflate(R.layout.chat_circle_add_pop, null);

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        mWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // TODO: 2016/5/17 设置背景颜色
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        mWindow.setBackgroundDrawable(dw);
        // TODO: 2016/5/17 设置可以获取焦点
        mWindow.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        mWindow.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        mWindow.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        mWindow.showAsDropDown(chatRootRela, MODE_APPEND, MODE_APPEND);
        TextView viewById = popupView.findViewById(R.id.chat_pop_num);
        TextView viewById1 = popupView.findViewById(R.id.chat_pop_num1);
        mName = popupView.findViewById(R.id.chat_et_text);
        mMassgag = popupView.findViewById(R.id.chat_et_text1);
        mImg = popupView.findViewById(R.id.chat_pop_off);
        popupView.findViewById(R.id.chat_pop_creat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mName.getText().toString();
                String s1 = mName.getText().toString();
                initCreat(s,s1);
            }


        });

    }

    private void initCreat(String s, String s1) {


    }

}
