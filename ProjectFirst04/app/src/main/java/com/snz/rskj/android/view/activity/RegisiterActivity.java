package com.snz.rskj.android.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.model.LoginModel;
import com.snz.rskj.android.view.fragment.PhoneNumber_Fragment;
import com.snz.rskj.android.view.fragment.RegisterPassWordFragment;
import com.snz.rskj.android.view.fragment.VerificationCodeFragment;

import butterknife.BindView;


public class RegisiterActivity extends BaseNetActivity<CommonPresenter, LoginModel> implements IConmmonView, VerificationCodeFragment.PhoneCodeNumber, RegisterPassWordFragment.PhoneNumberPassWord, PhoneNumber_Fragment.PhoneNumber1 {

    @BindView(R.id.register_numberOne)
    TextView registerNumberOne;
    @BindView(R.id.register_numberTwo)
    TextView registerNumberTwo;
    @BindView(R.id.register_Three)
    TextView registerThree;
    @BindView(R.id.register_frame)
    FrameLayout registerFrame;

    private VerificationCodeFragment verificationCodeFragment;
    private RegisterPassWordFragment registerPassWordFragment;
    private PhoneNumber_Fragment phoneNumber_fragment;
    private FragmentManager fragmentManager;

    @SuppressLint("ResourceAsColor")

    @Override
    public int getLayoutId() {
        return R.layout.activity_regisiter;
    }

    @Override
    public void initView() {
        verificationCodeFragment = new VerificationCodeFragment();
        registerPassWordFragment = new RegisterPassWordFragment();
        phoneNumber_fragment = new PhoneNumber_Fragment();
        registerPassWordFragment.setRegisterInterface3(this);
        verificationCodeFragment.setRegisterinterface2(this);
        phoneNumber_fragment.setRegisterInterface1(this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.register_frame, phoneNumber_fragment);
        fragmentTransaction.commit();
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

    //此为注册页面的验证码接口方法
    @SuppressLint("ResourceAsColor")
    @Override
    public void PhoneCodeButter() {
        if (registerPassWordFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(verificationCodeFragment);
            fragmentTransaction.add(R.id.register_frame, registerPassWordFragment);
            fragmentTransaction.commit();
            registerNumberOne.setTextColor(getResources().getColor(R.color.app_text_color_gray));
            registerNumberTwo.setTextColor(R.color.app_text_color_gray);
            registerThree.setText(getResources().getColor(R.color.white));
        }
    }
    //此为注册页面的输入密码的接口方法
    @Override
    public void PhonePswButter() {
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }
    @Override
    public void onRespose(int loadType, int apiType, Object o) {
    }

    @Override
    public void onError(Throwable e) {
        presenter.onError(e);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void PhoneButter() {
        //此为注册页面的输入手机号的接口方法
        if (verificationCodeFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            registerNumberOne.setTextColor(getResources().getColor(R.color.app_text_color_gray));
            registerNumberTwo.setTextColor(R.color.white);
            registerThree.setText(getResources().getColor(R.color.app_text_color_gray));

            fragmentTransaction.remove(phoneNumber_fragment);
            fragmentTransaction.add(R.id.register_frame, verificationCodeFragment);
            fragmentTransaction.commit();

        }
    }
}
