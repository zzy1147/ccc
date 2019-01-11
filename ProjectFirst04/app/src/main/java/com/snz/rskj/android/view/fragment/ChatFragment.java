package com.snz.rskj.android.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jcodecraeer.xrecyclerview.CustomDialog;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.snz.rskj.android.R;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetFragment;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.design.CommonTitle;
import com.snz.rskj.android.view.activity.CommunityActivity;
import com.snz.rskj.android.view.home.model.ChatModel;
import com.snz.rskj.android.view.home.ui.CircleActivity;
import com.snz.rskj.android.view.widget.tagcloud.TagCloudView;
import com.snz.rskj.android.view.widget.tagcloud.TextTagsAdapter;
import com.snz.rskj.android.view.widget.tagcloud.ViewTagsAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */


public class ChatFragment extends BaseNetFragment<CommonPresenter, ChatModel> implements IConmmonView {


    @BindView(R.id.common_title)
    CommonTitle commonTitle;
    @BindView(R.id.chat_tt_top)
    TextView chatTtTop;
    @BindView(R.id.chat_tt_achievement)
    TextView chatTtAchievement;
    @BindView(R.id.chat_circle_add)
    ImageView chatCircleAdd;
    @BindView(R.id.chat_rela_circle)
    RelativeLayout chatRelaCircle;
    @BindView(R.id.chat_tt_wish)
    TextView chatTtWish;
//    @BindView(R.id.chat_text)
//    TextView tagCloudView;
    @BindView(R.id.tag_cloud)
    TagCloudView fragmentTagcloud;
    Unbinder unbinder;
    private String[] mString = {"R.drawable.add_button_live", "R.drawable.add_button_live", "R.drawable.add_button_live", "R.drawable.add_button_live", "R.drawable.add_button_live"};

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextTagsAdapter textTagsAdapter = new TextTagsAdapter(new String[20]);
        fragmentTagcloud.setAdapter(textTagsAdapter);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_chat, container, false);
//    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void initView() {
        commonTitle.setUpView(getContext());
        commonTitle.setTitle("空间");
        commonTitle.setmReghtBtn(R.drawable.img_msg);
//        tagCloudView.setBackgroundColor(Color.LTGRAY);
//        tagCloudView.setDarkColor(Color.alpha(R.color.color_ff9800));
//        TextTagsAdapter tagsAdapter = new TextTagsAdapter(mString);
//        tagCloudView.setAdapter(tagsAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public ChatModel getModel() {
        return new ChatModel();
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }


    @OnClick({R.id.chat_tt_top, R.id.chat_tt_achievement, R.id.chat_circle_add, R.id.chat_rela_circle, R.id.chat_tt_wish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chat_tt_top:

                break;
            case R.id.chat_tt_achievement:







                break;
            case R.id.chat_circle_add:

                CommunityActivity.lauchActivity(getActivity(),"");
                break;
            case R.id.chat_rela_circle:
                startActivity(new Intent(getContext(), CircleActivity.class));
                break;
            case R.id.chat_tt_wish:
                break;
        }
    }


}
