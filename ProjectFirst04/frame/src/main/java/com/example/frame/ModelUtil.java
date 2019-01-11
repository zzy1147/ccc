package com.example.frame;

import com.example.frame.base.BaseObserver;
import com.example.frame.interfaces.IConmmonView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ModelUtil {
    public static <T> void netRequest(Observable<T> observable, final IConmmonView view, final int loadType, final int apiType, Object... t){
        observable .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onBaseNext(Object value) {
                        view.onRespose(loadType, apiType, value);
                    }
                    @Override
                    public void onBaseError(Throwable e) {
                        view.onError(e);
                    }
                });
    }
}
