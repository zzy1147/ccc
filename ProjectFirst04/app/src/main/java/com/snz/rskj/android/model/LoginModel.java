package com.snz.rskj.android.model;

import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.configs.Log_Config;

import com.bumptech.glide.request.RequestFutureTarget;
import com.example.frame.ModelUtil;
import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.base.RetrofitManager;
import com.snz.rskj.android.configs.Log_Config;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;


public class LoginModel implements ICommonModel {
    @Override
    public void getData(IConmmonView view, int loadType, int apiType, Object[] t) {
        switch (apiType) {
            case Log_Config.LOGING_:
                LoginShow(view, loadType, apiType, t);
                break;
            case Log_Config.REGISITER_YANCOD:
                registerVerification(view, loadType, apiType, t);
                break;
            case Log_Config.REGISITER_PSW:
                registerPsw(view, loadType, apiType, t);
                break;
            case Log_Config.REGISITER_PHONE:
                registerCode(view, loadType, apiType, t);
                break;
        }
    }

    private void registerVerification(IConmmonView view, int loadType, int apiType, Object[] t) {
        String s = (String) t[0];
        String s1 = (String) t[1];
        ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().getVerification(s,s1), view, loadType, apiType);
    }
    private void registerPsw(IConmmonView view, int loadType, int apiType, Object[] t) {

        String strings = (String) t[0];
        String strings1 = (String) t[1];
        ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().getGegisterPsw(strings,strings1), view, loadType, apiType);
    }

    private void registerCode(IConmmonView view, int loadType, int apiType, Object[] t) {
        String mobile = (String) t[0];
        ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().getGegisterCode(mobile), view, loadType, apiType);
    }

    private void LoginShow(IConmmonView view, int loadType, int apiType, Object[] t) {
        Map<String, String> map = new HashMap();
        map.put("mobile", t[0] + "");
        map.put("password", t[1] + "");
//        map.put("Nickname", t[2] + "");
        ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().getLogin(RetrofitManager.fromRequestBody(map)), view, loadType, apiType);

    }

}
