package com.snz.rskj.android.view.fragment;

import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.frame.CommonPresenter;
import com.example.frame.ModelUtil;
import com.example.frame.base.BaseNetFragment;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.LoginBean;
import com.snz.rskj.android.configs.Config_Sp;
import com.snz.rskj.android.configs.Log_Config;
import com.snz.rskj.android.model.LoginModel;
import com.snz.rskj.android.utils.LogUtil;
import com.snz.rskj.android.utils.SharedPrefrenceUtils;
import com.snz.rskj.android.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VerificationCodeFragment extends BaseNetFragment<CommonPresenter, LoginModel> implements IConmmonView {

    @BindView(R.id.input_register_code)
    EditText inputRegisterCode;
    @BindView(R.id.register_code_button)
    Button registerCodeButton;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_verficationcode;
    }

    @Override
    public void initView() {
        registerCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registerinterface2.PhoneCodeButter();
                //测试跳转
                String s = inputRegisterCode.getText().toString();
                mPresenter.getData(0, Log_Config.REGISITER_YANCOD, SharedPrefrenceUtils.getObject(getActivity(), Config_Sp.USER_PHONE), s);
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

    @Override
    public void onRespose(int loadType, int apiType, Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (apiType == Log_Config.REGISITER_YANCOD) {
            if (loginBean.getCode() == 200) {
                LogUtil.e("REGISITER_YANCOD",loginBean.getMsg());
            } else if (loginBean.getCode() == 100) {
                ToastUtils.show(getContext(), "发送失败");
            } else ToastUtils.show(getContext(), "验证码已过期，请重新发送");
        } else ToastUtils.show(getContext(), "请求有误，请重新发送");
        Registerinterface2.PhoneCodeButter();
    }
    @Override
    public void onError(Throwable e) {
    }
    public PhoneCodeNumber Registerinterface2;
    public void setRegisterinterface2(PhoneCodeNumber registerinterface2) {
        Registerinterface2 = registerinterface2;
    }
    public interface PhoneCodeNumber {
        void PhoneCodeButter();
    }


}
