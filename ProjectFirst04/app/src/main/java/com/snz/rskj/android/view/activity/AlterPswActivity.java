package com.snz.rskj.android.view.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
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
import com.snz.rskj.android.bean.VerifyCode;
import com.snz.rskj.android.configs.Log_Config;
import com.snz.rskj.android.model.LoginModel;
import com.snz.rskj.android.utils.LogUtil;
import com.snz.rskj.android.utils.ToastUtils;
import com.snz.rskj.android.utils.Verify;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlterPswActivity extends BaseNetActivity<CommonPresenter, LoginModel> implements IConmmonView {


    @BindView(R.id.log_i)
    ImageView logI;
    @BindView(R.id.log_phone)
    EditText logPhone;
    @BindView(R.id.log_lock)
    RelativeLayout logLock;
    @BindView(R.id.img_2)
    ImageView img2;
    @BindView(R.id.log_yan)
    EditText logYan;
    @BindView(R.id.log_text_yan)
    TextView logTextYan;
    @BindView(R.id.log_0)
    RelativeLayout log0;
    @BindView(R.id.log_2)
    ImageView log2;
    @BindView(R.id.log_psw)
    EditText logPsw;
    @BindView(R.id.log_3)
    RelativeLayout log3;
    @BindView(R.id.log_5)
    ImageView log5;
    @BindView(R.id.log_psw1)
    EditText logPsw1;
    @BindView(R.id.log_4)
    RelativeLayout log4;
    @BindView(R.id.alter_btn)
    Button alterBtn;

    private boolean btnShow = false;
    private String mString;
    private String mString1;
    private String mPsw;
    private String mMPsw1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_alter_psw;
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

    @Override
    public void onRespose(int loadType, int apiType, Object o) {
        VerifyCode loginBean = (VerifyCode) o;
        LogUtil.e("ffffffff", loginBean.toString());
        int code = loginBean.code;
        if (code == 200) {
            //进行网络请求
            finish();
        } else if (code == 0) {
            ToastUtils.show(this, loginBean.msg);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    public int number = 5;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.log_text_yan, R.id.alter_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.log_text_yan:

                if (!btnShow) {
                    logYan.setClipToOutline(false);
                    String yan = logPhone.getText().toString();
                    if (yan != null && TextUtils.isEmpty(yan)) {
                        presenter.getData(0, Log_Config.REGISITER_PHONE, yan);

                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                while (number <= 0) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }.start();
                    }
                } else {
                }
                break;
            case R.id.alter_btn:
                mString = logPhone.getText().toString();
                mString1 = logYan.getText().toString();
                mPsw = logPsw.getText().toString();
                mMPsw1 = logPsw1.getText().toString();
                if (mPsw.equals(mMPsw1)&& Verify.verifyIsStandard(mPsw)&&!TextUtils.isEmpty(mMPsw1)){
                    presenter.getData(0, Log_Config.REGISITER_PSW, mString, mPsw);

                }

                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int str = msg.what;
            logTextYan.setText("倒计时： " + str);
            if (str==0){
                logTextYan.setText("获取验证码");
                logTextYan.setClipToOutline(true);
            }
        }
    };
}
