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
import com.snz.rskj.android.bean.LoginBean;
import com.snz.rskj.android.bean.UserMsg;
import com.snz.rskj.android.configs.Config_Sp;
import com.snz.rskj.android.configs.Log_Config;
import com.snz.rskj.android.model.LoginModel;
import com.snz.rskj.android.utils.SharedPrefrenceUtils;
import com.snz.rskj.android.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RegisterPassWordFragment extends BaseNetFragment<CommonPresenter, LoginModel> implements IConmmonView {
    @BindView(R.id.input_register_psw1)
    EditText inputRegisterPsw1;
    @BindView(R.id.input_register_psw2)
    EditText inputRegisterPsw2;
    @BindView(R.id.register_psw_button)
    Button registerPswButton;
    Unbinder unbinder;
    private UserMsg mObject;
    private String mS1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_psw;
    }

    @Override
    public void initView() {
        registerPswButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = inputRegisterPsw1.getText().toString();
                mS1 = inputRegisterPsw2.getText().toString();
                if (s.equals(mS1)&& s!=null ) {
                    mObject = (UserMsg) SharedPrefrenceUtils.getObject(getActivity(), Config_Sp.USRMSG);
                    mPresenter.getData(0, Log_Config.REGISITER_PSW,mObject.getName(), s);
                } else {
                    showToast("您输入的密码不一致，请重新输入");
                }
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
        if (loginBean.getCode() == 200) {
            Registerinterface3.PhonePswButter();
            SharedPrefrenceUtils.putObject(getActivity(), Config_Sp.USRMSG, new UserMsg(mObject.getName(), mObject.getPsw(), mObject.getToKen()));
        } else ToastUtils.show(getActivity(), "你注册账号失败，请重新注册");
    }

    @Override
    public void onError(Throwable e) {
        mPresenter.onError(e);
    }

    public PhoneNumberPassWord Registerinterface3;

    public void setRegisterInterface3(PhoneNumberPassWord interface3) {
        this.Registerinterface3 = interface3;
    }
    public interface PhoneNumberPassWord {
        void PhonePswButter();
    }

}
