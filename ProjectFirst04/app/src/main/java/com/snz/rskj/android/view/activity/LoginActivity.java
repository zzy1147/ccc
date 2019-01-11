package com.snz.rskj.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.LoginBean;
import com.snz.rskj.android.bean.UserMsg;
import com.snz.rskj.android.configs.Config_Sp;
import com.snz.rskj.android.configs.Log_Config;
import com.snz.rskj.android.design.CircleImageView;
import com.snz.rskj.android.design.CommonTitle;
import com.snz.rskj.android.model.LoginModel;
import com.snz.rskj.android.utils.SharedPrefrenceUtils;
import com.snz.rskj.android.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseNetActivity<CommonPresenter, LoginModel> implements IConmmonView {

    @BindView(R.id.log_i)
    ImageView logI;
    @BindView(R.id.log_phone)
    EditText loginPhone;
    @BindView(R.id.log_lock)
    RelativeLayout logLock;
    @BindView(R.id.log_2)
    ImageView log2;
    @BindView(R.id.log_psw)
    EditText loginPsw;
    @BindView(R.id.log_3)
    RelativeLayout log3;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.log_regster)
    TextView logRegster;
    @BindView(R.id.log_forget_psw)
    TextView logForgetPsw;
    @BindView(R.id.log_protocol)
    TextView logProtocol;
    @BindView(R.id.log_head_img)
    CircleImageView logHeadImg;
    @BindView(R.id.common_title)
    CommonTitle commonTitle;
    @BindView(R.id.no_)
    TextView no;
    @BindView(R.id.log_link)
    ImageView logLink;
    private String mPhone;
    private String mPsw;

    @Override
    public int getLayoutId() {
        return R.layout.activity_log;
    }

    @Override
    public void initView() {

        commonTitle.setUpView(this);
        commonTitle.mReghtBtn.setVisibility(View.GONE);
//        commonTitle.mBackBtn.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        commonTitle.mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
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

    }*/

    @Override
    public void onRespose(int loadType, int apiType, Object o) {
        switch (apiType) {
            case Log_Config.LOGING_:
                LoginBean loginBean = (LoginBean) o;
                int code = loginBean.getCode();
                Log.e("Logss", loginBean.toString());
                if (code == 200) {
                    SharedPrefrenceUtils.putObject(this, Config_Sp.USRMSG, new UserMsg(mPhone, mPsw, loginBean.getData().getYxtoken()));
                    finish();
                } else {
                    Toast.makeText(this, "登陆失败，请重新登陆", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        getPresenter().onError(e);
    }

    @OnClick({R.id.login_btn, R.id.log_regster, R.id.log_forget_psw, R.id.log_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                mPhone = loginPhone.getText().toString();
                mPsw = loginPsw.getText().toString();
                if (!TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mPsw))
                    presenter.getData(0, Log_Config.LOGING_, "18033646108", "123456");
                else ToastUtils.show(this, "请输入正确的账号或密码");
//                presenter.getData(REFRESH, Log_Config.LOGING_);
                break;
            case R.id.log_regster:
                startActivity(new Intent(this, RegisiterActivity.class));
                finish();
                break;
            case R.id.log_forget_psw:
                startActivity(new Intent(this, AlterPswActivity.class));
                break;
            case R.id.log_protocol:
                startActivity(new Intent(this, AlterPswActivity.class));
                break;
        }
    }
    @Override
    public void onNetComplete(int api) {
        super.onNetComplete(api);
    }
}
