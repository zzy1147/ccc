package com.example.frame.base;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver implements Observer {

    private Disposable d;

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(Object value) {
        onBaseNext(value);
        dispose();
    }

    @Override
    public void onError(Throwable e) {
        onBaseError(e);
        dispose();
    }

    @Override
    public void onComplete() {
        Log.e("onNetComplete","--------------------------------");
    }

    private void dispose() {
        if (!d.isDisposed()) {
            d.dispose();
        }
    }

    public abstract void onBaseNext(Object value);
    public abstract void onBaseError(Throwable e);
}
