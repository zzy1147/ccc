package com.snz.rskj.android.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetFragment;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.UserMsg;
import com.snz.rskj.android.bean.VerifyCode;
import com.snz.rskj.android.configs.Config_Sp;
import com.snz.rskj.android.configs.Log_Config;
import com.snz.rskj.android.model.LoginModel;
import com.snz.rskj.android.utils.SharedPrefrenceUtils;
import com.snz.rskj.android.utils.ToastUtils;
import com.snz.rskj.android.utils.Verify;

import butterknife.BindView;


public class PhoneNumber_Fragment extends BaseNetFragment<CommonPresenter, LoginModel> implements IConmmonView {

    @BindView(R.id.input_phoneNumber)
    EditText inputRegisterEdText1;

    @BindView(R.id.fragment_getCode_btu)
    Button registerButton;
    private String mNumber;


    @Override
    public int getLayoutId() {
        return R.layout.phonenumber_fragment;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mNumber = inputRegisterEdText1.getText().toString();
                if (Verify.verifyPhoneNumber(mNumber))
                    mPresenter.getData(0, Log_Config.REGISITER_PHONE, mNumber);
                else showToast("输入有误请重新输入");

            }
        });
    }

    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public LoginModel getModel() {
        return new LoginModel();
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {

        VerifyCode loginBean = (VerifyCode) o;
        int code = loginBean.code;
        if (code == 200) {
            //进行网络请求
            SharedPrefrenceUtils.putObject(getActivity(), Config_Sp.USRMSG, new UserMsg(mNumber));

        } else if (code == 0) {
            ToastUtils.show(getActivity(), "发送失败");
        }
        Registerinterface1.PhoneButter();

    }

    @Override
    public void onError(Throwable e) {
        getPresenter().onError(e);
    }


    public PhoneNumber1 Registerinterface1;

    public void setRegisterInterface1(PhoneNumber1 interface1) {
        this.Registerinterface1 = interface1;
    }

    public interface PhoneNumber1 {
        void PhoneButter();
    }

}
