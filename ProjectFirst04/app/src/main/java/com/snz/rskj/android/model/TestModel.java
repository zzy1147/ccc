package com.snz.rskj.android.model;

import com.example.frame.ModelUtil;
import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.base.RetrofitManager;

public class TestModel implements ICommonModel {

    @Override
    public void getData(final IConmmonView view, final int loadType, final int apiType, Object... t) {
//        ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().getData(),view,loadType,apiType);
    }
}
