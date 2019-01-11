package com.example.frame.interfaces;

public interface ICommonPresenter<T> {
    void getData(int loadType,int apiType,T... t);
}
