package com.example.frame.base;

import android.util.Log;

import com.netease.nimlib.sdk.RequestCallback;

public class MessageCallBack<T> implements RequestCallback<T> {
    @Override
    public void onSuccess(T t) {

    }

    @Override
    public void onFailed(int i) {
        Log.e("MessageCallBack", "i:" + i);
    }

    @Override
    public void onException(Throwable throwable) {
        Log.e("MessageCallBack", throwable.getMessage());
    }
}
