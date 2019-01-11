package com.snz.rskj.android.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.view.fragment.MainFragment;
import com.snz.rskj.android.view.home.model.HomeFragModel;

import me.yokeyword.fragmentation.SupportActivity;

public class MainHomeActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        if (savedInstanceState == null) {
//            loadRootFragment(R.id.fragment_container, MainFragment.newInstance());
//        }
    }

}
