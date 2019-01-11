package com.snz.rskj.android.model;

import com.example.frame.ModelUtil;
import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.base.RetrofitManager;

public class HomeModel implements ICommonModel {
    @Override
    public void getData(IConmmonView view, int loadType, int apiType, Object[] t) {

    }

    private void registerVerification(IConmmonView view, int loadType, int apiType, Object[] t) {
        String s = (String) t[0];
        String s1 = (String) t[1];
        ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().getVerification(s,s1), view, loadType, apiType);
    }
}
