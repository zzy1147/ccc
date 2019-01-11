package com.example.frame.base;

import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;

import java.lang.ref.SoftReference;

public class BasePresenter<V extends IConmmonView, M extends ICommonModel> {
    private SoftReference<V> mVSoftReference;
    private SoftReference<M> mMSoftReference;

    public void attach(V view, M model) {
        this.mVSoftReference = new SoftReference<>(view);
        this.mMSoftReference = new SoftReference<>(model);
    }

    public void dettach() {
        if (mVSoftReference != null) {
            mVSoftReference.clear();
            mVSoftReference = null;
        }
        if (mMSoftReference != null) {
            mMSoftReference.clear();
            mMSoftReference = null;
        }
}

    public V getView() {
        return mVSoftReference != null ? mVSoftReference.get() : null;
    }

    public M getModel() {
        return mMSoftReference != null ? mMSoftReference.get() : null;
    }
}
