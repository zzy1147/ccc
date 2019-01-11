package com.example.frame.base;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.frame.FramApplication;
import com.example.frame.R;
import com.jcodecraeer.xrecyclerview.CustomDialog;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
public class BaseFragment extends Fragment {
    private Dialog mLoading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoading = CustomDialog.LineDialog(getContext(), ProgressStyle.BallPulse, null);
    }
    public void onNetComplete(int api) {
        dismissLoading();
        Log.e(getString(R.string.net_complete), getString(R.string.net_code) + api);
    }

    public void onErrorLog(Throwable e, int api) {
        Log.e(getString(R.string.net_error), getString(R.string.net_code) + api + e.toString());
    }


    public void showLoading(){
        if (mLoading != null && !mLoading.isShowing())mLoading.show();
    }

    public void dismissLoading(){
        if (mLoading != null && mLoading.isShowing())mLoading.dismiss();
    }

    public void initRecycleView(XRecyclerView recyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.getDefaultFootView().setLoadingHint(getString(R.string.loading));
        recyclerView.getDefaultFootView().setNoMoreHint(getString(R.string.no_more_data));
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    public void refresh() {

    }

    public void loadMore() {
    }

    public void showToast(String content) {
        Toast.makeText(FramApplication.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String content) {
        Toast.makeText(FramApplication.getContext(), content, Toast.LENGTH_LONG).show();
    }
}
