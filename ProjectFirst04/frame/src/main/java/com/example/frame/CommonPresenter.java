package com.example.frame;

import com.example.frame.base.BasePresenter;
import com.example.frame.interfaces.ICommonPresenter;
import com.example.frame.interfaces.IConmmonView;

public class CommonPresenter extends BasePresenter implements ICommonPresenter, IConmmonView {

    @Override
    public void onRespose(int loadType, int apiType, Object o) {
        if (getView() != null) getView().onRespose(loadType, apiType, o);
    }

    @Override
    public void onError(Throwable e) {
        if (getView() != null) getView().onError(e);
    }


    @Override
    public void getData(int loadType, int apiType, Object... t) {
        if (getModel() != null) getModel().getData(this, loadType, apiType, t);
    }
}
