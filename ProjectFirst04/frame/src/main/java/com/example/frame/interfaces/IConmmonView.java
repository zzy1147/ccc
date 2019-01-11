package com.example.frame.interfaces;

public interface IConmmonView<T> {
    void onRespose(int loadType,int apiType,T t);
    void onError(Throwable e);
}
