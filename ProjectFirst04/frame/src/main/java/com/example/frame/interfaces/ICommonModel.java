package com.example.frame.interfaces;

public interface ICommonModel<T> {
    void getData(IConmmonView view,int loadType,int apiType,T... t);
}
