package com.snz.rskj.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseActivity;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.design.CircleImageView;
import com.snz.rskj.android.model.LoginModel;
import com.snz.rskj.android.model.UserModel;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity  extends BaseNetActivity<CommonPresenter, UserModel> implements IConmmonView {


    @BindView(R.id.own_head_image)
    CircleImageView ownHeadImage;
    @BindView(R.id.own_info_layout)
    LinearLayout ownInfoLayout;
    @BindView(R.id.own_safe_layout)
    LinearLayout ownSafeLayout;
    @BindView(R.id.own_pipei_layout)
    LinearLayout ownPipeiLayout;
    @BindView(R.id.own_clear_layout)
    LinearLayout ownClearLayout;
    @BindView(R.id.linear_about_container)
    LinearLayout linearAboutContainer;
    @BindView(R.id.own_exit_button)
    TextView ownExitButton;


    public static void lauchActivity(Context context, String id){
        Intent intent =new Intent(context, SettingActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public CommonPresenter getPresenter() {
        return null;
    }

    @Override
    public UserModel getModel() {
        return new UserModel();
    }


    @OnClick({R.id.own_head_image, R.id.own_info_layout, R.id.own_safe_layout, R.id.own_pipei_layout,R.id.own_clear_layout,R.id.linear_about_container,R.id.own_exit_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.own_head_image:
                break;
            case R.id.own_info_layout:
                break;
            case R.id.own_safe_layout:
                break;
            case R.id.own_pipei_layout:
                break;
            case R.id.own_clear_layout:
                break;
            case R.id.linear_about_container:
                break;
            case R.id.own_exit_button:
                SettingUserInfoActivity.lauchActivity(SettingActivity.this,"");
                break;
        }
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
