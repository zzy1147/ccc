package com.snz.rskj.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.LoginBean;
import com.snz.rskj.android.configs.Log_Config;
import com.snz.rskj.android.design.CircleImageView;
import com.snz.rskj.android.design.CommonTitle;
import com.snz.rskj.android.model.LoginModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchActivity extends BaseNetActivity<CommonPresenter, LoginModel> implements IConmmonView {



    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public LoginModel getModel() {
        return new LoginModel();
    }

 /*   public void onClick(View view) {

        switch (view.getId()) {

            case R.id.login_button:

                //这边要进行网络请求服务器进行判断当前账号是否存在并且输入的是否正确


                break;

        }

    }
                //这边要进行网络请求服务器进行判断当前账号是否存在并且输入的是否正确

                break;

        }

    }*/

    @Override
    public void onRespose(int loadType, int apiType, Object o) {
        switch (apiType) {
            case Log_Config.LOGING_:
                LoginBean loginBean = (LoginBean) o;
                Log.e("sssssssssss", loginBean.toString());
//                LoginBean loginBean = (LoginBean) o;
//                int code = loginBean.code;
//                Log.e("Logss", loginBean.toString());
//                if (code == 200) {
//                    finish();
//                } else {
//                    Toast.makeText(this, "登陆失败，请重新登陆", Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        getPresenter().onError(e);
    }

    @OnClick({R.id.login_btn, R.id.log_regster, R.id.log_forget_psw, R.id.log_protocol})
    public void onViewClicked(View view) {
    }

    @Override
    public void onNetComplete(int api) {
        super.onNetComplete(api);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
