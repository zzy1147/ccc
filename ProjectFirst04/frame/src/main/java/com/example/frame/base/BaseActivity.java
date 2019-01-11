package com.example.frame.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.frame.FramApplication;
import com.example.frame.R;
import com.example.frame.tools.StatusBarManager;
import com.jcodecraeer.xrecyclerview.CustomDialog;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class BaseActivity extends AppCompatActivity {
    public FramApplication mApplication;
    private Dialog mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mApplication = (FramApplication) getApplication();
        mLoading = CustomDialog.LineDialog(this, ProgressStyle.BallPulse, null);
        Log.e("我是华丽丽的类名---：",this.getClass().getSimpleName());
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
        LinearLayoutManager manager = new LinearLayoutManager(this);
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

    // 设置状态栏的背景色。对于Android4.4和Android5.0以上版本要区分处理
    public void setStatusBarColor(int color) {
        StatusBarManager.setStatusBarColor(this,color);
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
