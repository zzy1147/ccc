package com.snz.rskj.android.view.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetFragment;
import com.example.frame.interfaces.IConmmonView;
import com.jaren.lib.view.LikeView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.configs.Config_Home;
import com.snz.rskj.android.design.CommonTitle;
import com.snz.rskj.android.utils.LogUtil;
import com.snz.rskj.android.view.home.adapter.HomeViewPagerAdater;
import com.snz.rskj.android.view.home.bean.HomeBean;
import com.snz.rskj.android.view.home.bean.LiveBean;
import com.snz.rskj.android.view.home.model.HomeFragModel;
import com.snz.rskj.android.view.home.ui.ParticularActivity;
import com.snz.rskj.android.view.home.viewpager.OnViewPagerListener;
import com.snz.rskj.android.view.home.viewpager.ViewPagerLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayer;


public class HomeFragment extends BaseNetFragment<CommonPresenter, HomeFragModel> implements IConmmonView, HomeViewPagerAdater.OnItemCilckListener {



    @BindView(R.id.home_rv_horizontal)
    ListView homeRvHorizontal;
    @BindView(R.id.home_rv_visb)
    RecyclerView homeRvVisb;
    @BindView(R.id.home_commontitles)
    CommonTitle homeCommontitles;
    Unbinder unbinder1;
    @BindView(R.id.home_Fragment)
    FrameLayout homesFragment;
    Unbinder unbinder;
    @BindView(R.id.home_swipe)
    SwipeRefreshLayout homeSwipe;
    Unbinder unbinder2;
    private int index = 0;

    //    private int[] imgs = {R.mipmap.img_video_1, R.mipmap.img_video_2};
//    private int[] videos = {R.raw.video_1, R.raw.video_2};
    private ViewPagerLayoutManager mLayoutManager;


    private boolean liveFlag = true;


    private static final String TAG = "ViewPagerActivity";
    private HomeViewPagerAdater viewPagerAdater;
    private PopupWindow window;
    private List<HomeBean.DataBean> mList = new ArrayList<>();
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager layoutManager;

    public HomeFragment() {
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
    @Override
    public void initView() {
        initsetBar();
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(homeRvVisb);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homeRvVisb.setLayoutManager(layoutManager);
        viewPagerAdater = new HomeViewPagerAdater( mList,getContext());
        homeRvVisb.setAdapter(viewPagerAdater);
        viewPagerAdater.setOnItemCilckListener(this);
        initSwitListener();
    }
    @Override
    public void initData() {
        mPresenter.getData(0, Config_Home.HOME_showvideo, 0+"");
    }
    @Override
    public void onRespose(int loadType, int apiType, Object o) {
        Log.e("sssssssss",o.toString());
        if (apiType == Config_Home.HOME_showvideo) {
            HomeBean homeBean = (HomeBean) o;
            List<HomeBean.DataBean> data = homeBean.getData();
            if (loadType == 1) {
                mList.clear();
            }
            LogUtil.e("data-----",data.toString());
            mList.addAll(data);
            viewPagerAdater.notifyDataSetChanged();
            homeSwipe.setRefreshing(false);
            addListener();
        }
    }
    private void addListener() {

        homeRvVisb.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        View view = snapHelper.findSnapView(layoutManager);
                        JZVideoPlayer.releaseAllVideos();
                        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                        if (viewHolder != null && viewHolder instanceof  HomeViewPagerAdater.VideoViewHolder) {
                            ((HomeViewPagerAdater.VideoViewHolder) viewHolder).mp_video.startVideo();
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }

            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    private void initsetBar() {
        homeCommontitles.mReghtBtn.setImageResource(R.drawable.home_btn_live);
        homeCommontitles.mMoreImage.setImageResource(R.drawable.home_btn_search);
        homeCommontitles.mReghtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liveFlag) {
                    homeRvHorizontal.setVisibility(View.VISIBLE);
                    liveFlag = false;
                } else {
                    homeRvHorizontal.setVisibility(View.GONE);
                    liveFlag = true;
                }
            }
        });
    }

    private void initSwitListener() {
        initPullRefresh();

    }

    private void initPullRefresh() {
        homeSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //调用刷新接口
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.getData(1, Config_Home.HOME_showvideo, 0+"");
                        //刷新完成
                        homeSwipe.setRefreshing(false);

                    }
                }, 3000);
            }
        });
    }



    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public HomeFragModel getModel() {
        return new HomeFragModel();
    }


    @Override
    public void onError(Throwable e) {
        getPresenter().onError(e);
    }



    @Override
    public void Call_OnItemCilck(int position) {
        Toast.makeText(getContext(), "Call_OnItemCilck", Toast.LENGTH_SHORT);
        Log.e("Share_OnItemCilck", "3");
        initPop(position);
    }

    private void initPop(int position) {
        final View popupView = this.getLayoutInflater().inflate(R.layout.pop_fg_call, null);
        window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        // TODO: 2016/5/17 设置背景颜色
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAtLocation(homesFragment, Gravity.CENTER, 0, 0);


        popupView.findViewById(R.id.pop_fg_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "连接中。。。。", Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });
    }


    @Override
    public void Share_OnItemCilck(int position) {

    }

    @Override
    public void Msg_OnItemCilck(int position) {
        Toast.makeText(getContext(), "Msg_OnItemCilck", Toast.LENGTH_SHORT);

    }

    @Override
    public void Praise_OnItemCilck(int position) {
        Toast.makeText(getContext(), "Praise_OnItemCilck", Toast.LENGTH_SHORT);


    }

    @Override
    public void Music_OnItemCilck(int position) {
        Toast.makeText(getContext(), "Music_OnItemCilck", Toast.LENGTH_SHORT);
        Log.e("Share_OnItemCilck", "3");


    }

    @Override
    public void Head_OnItemCilck(int position) {
        Toast.makeText(getContext(), "Head_OnItemCilck", Toast.LENGTH_SHORT);
        Log.e("Share_OnItemCilck", "3");
//        startActivity(new Intent(getContext(),ParticularActivity.class));

    }

    @Override
    public void Zan_OnItemCilck(int position) {
        final LikeView likeView = new LikeView(getContext());
    }

    @Override
    public void Rotate_OnItemCilck(int position) {

    }
}
