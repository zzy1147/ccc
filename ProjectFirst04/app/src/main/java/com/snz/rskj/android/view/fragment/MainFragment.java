package com.snz.rskj.android.view.fragment;/**
 * Created by dell on 2017/4/5/0005.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.snz.rskj.android.R;
import com.snz.rskj.android.utils.StartBrotherEvent;
import com.snz.rskj.android.widget.BottomBar;
import com.snz.rskj.android.widget.PopupMenuUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * created by LiChengalin at 2017/4/5/0005
 */
public class MainFragment extends BaseHomeFragment {

//    private static final int FIRST = 0;
//    private static final int SECOND = 1;
//    private static final int THIRD = 2;
//    private static final int FOURTH = 3;
//    private BottomBar mBottomBar;
//    private ImageView mCenterImage;
//    private SupportFragment[] mFragments = new SupportFragment[5];
//
//    private int mSelectPosition,mCurrentPosition=0;
//
//    public static MainFragment newInstance() {
//
//        Bundle bundle = new Bundle();
//
//        MainFragment fragment = new MainFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_main, container, false);
//
////        mCenterImage = (ImageView) view.findViewById (R.id.);
//        if (savedInstanceState == null) {
//            mFragments[FIRST] = new HomeFragment();
//            mFragments[SECOND] = new ChatFragment();
//            mFragments[THIRD] = new MianeFragment();
//            mFragments[FOURTH] = new MatchingFragment();
//            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
//                    mFragments[FIRST],
//                    mFragments[SECOND],
//                    mFragments[THIRD], mFragments[FOURTH]);
//        } else {
//            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
//            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
//            mFragments[FIRST] = findChildFragment(HomeFragment.class);
//            mFragments[SECOND] = findChildFragment(ChatFragment.class);
//            mFragments[THIRD] = findChildFragment(MianeFragment.class);
//            mFragments[FOURTH] = findChildFragment(MatchingFragment.class);
//        }
//        //注册
//        EventBus.getDefault().register(this);
//        initView(view);
//        return view;
//
//    }
//
//    private void initView(final View view) {
//
//        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
//
//        mBottomBar.setOnBottombarOnclick(new BottomBar.OnBottonbarClick() {
//            @Override
//            public void onFirstClick() {
//                mSelectPosition = 0;
//                showHideFragment(mFragments[mSelectPosition], mFragments[mCurrentPosition]);
//                mCurrentPosition = 0;
//            }
//
//            @Override
//            public void onSecondClick() {
//                mSelectPosition = 1;
//                showHideFragment(mFragments[mSelectPosition], mFragments[mCurrentPosition]);
//                mCurrentPosition = 1;
//            }
//
//            @Override
//            public void onThirdClick() {
//                mSelectPosition = 2;
//                showHideFragment(mFragments[mSelectPosition], mFragments[mCurrentPosition]);
//                mCurrentPosition = 2;
//            }
//
//            @Override
//            public void onFouthClick() {
//                mSelectPosition = 3;
//                showHideFragment(mFragments[mSelectPosition], mFragments[mCurrentPosition]);
//                mCurrentPosition = 3;
//            }
//
//            @Override
//            public void onCenterClick() {
//                PopupMenuUtil.getInstance()._show(getContext(), mCenterImage);
//            }
//
//
//        });
//
//    }
//    /**
//     * start other BrotherFragment
//     */
//    @Subscribe
//    public void startBrother(StartBrotherEvent event) {
//        start(event.targetFragment);
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
}
