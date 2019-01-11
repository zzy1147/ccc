package com.snz.rskj.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseActivity;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.netease.nim.chatroom.demo.entertainment.activity.MainActivity;
import com.netease.nim.uikit.common.util.C;
import com.snz.rskj.android.R;
import com.snz.rskj.android.configs.Config_UserInfo;
import com.snz.rskj.android.configs.Log_Config;
import com.snz.rskj.android.design.CircleImageView;
import com.snz.rskj.android.model.UserModel;
import com.snz.rskj.android.utils.AddressPickTask;
import com.wheelview.library.dialog.DialogStyle;
import com.wheelview.library.dialog.LoadStyle;
import com.wheelview.library.dialog.MyWheelDialog;
import com.wheelview.library.dialog.callback.OnWheelClickListener;
import com.wheelview.library.dialog.entity.Address;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class SettingUserInfoActivity  extends BaseNetActivity<CommonPresenter, UserModel> implements IConmmonView {


    @BindView(R.id.linear_user_icon_image)
    CircleImageView linearUserIconImage;
    @BindView(R.id.text_user_nick)
    EditText textUserNick;
    @BindView(R.id.linear_user_nick_container)
    LinearLayout linearUserNickContainer;
    @BindView(R.id.text_user_sex_text)
    TextView textUserSexText;
    @BindView(R.id.linear_user_sex_container)
    LinearLayout linearUserSexContainer;
    @BindView(R.id.text_user_birth)
    TextView textUserBirth;
    @BindView(R.id.linear_user_birth_container)
    LinearLayout linearUserBirthContainer;
    @BindView(R.id.text_user_area)
    TextView textUserArea;
    @BindView(R.id.linear_user_area_container)
    LinearLayout linearUserAreaContainer;
    @BindView(R.id.text_user_qianming_edit)
    EditText textUserQianmingEdit;
    @BindView(R.id.own_exit_button)
    TextView ownExitButton;


    public static final int REQUEST_CODE_CHOOSE=1;
    public static void lauchActivity(Context context,String id){
        Intent intent =new Intent(context, SettingUserInfoActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_user_info;
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public UserModel getModel() {
        return new UserModel();
    }


    @OnClick({R.id.linear_user_icon_image, R.id.linear_user_nick_container, R.id.linear_user_sex_container, R.id.linear_user_birth_container,R.id.linear_user_area_container,R.id.own_exit_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_user_icon_image:
                Matisse.from(SettingUserInfoActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .gridExpectedSize(40)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.linear_user_nick_container:
                break;
            case R.id.linear_user_sex_container:
                break;
            case R.id.linear_user_birth_container:
                final DatePicker picker = new DatePicker(this);
                picker.setCanceledOnTouchOutside(true);
                picker.setUseWeight(true);
                picker.setTopPadding(ConvertUtils.toPx(this, 10));
                picker.setRangeEnd(2111, 1, 11);
                picker.setRangeStart(2016, 8, 29);
                picker.setSelectedItem(2050, 10, 14);
                picker.setResetWhileWheel(false);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        showToast(year + "-" + month + "-" + day);
                    }
                });
                picker.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
                    }
                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
                    }
                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
                    }
                });

                picker.show();
                break;
            case R.id.linear_user_area_container:
                AddressPickTask task = new AddressPickTask(this);
                task.setHideProvince(false);
                task.setHideCounty(false);
                task.setCallback(new AddressPickTask.Callback() {
                    @Override
                    public void onAddressInitFailed() {
                        showToast("数据初始化失败");
                    }
                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        if (county == null) {
                            showToast(province.getAreaName() + city.getAreaName());
                        } else {
                            showToast(province.getAreaName() + city.getAreaName() + county.getAreaName());
                        }
                    }
                });

                task.execute("贵州", "毕节", "纳雍");
                break;
            case R.id.own_exit_button:
              String nickName =  textUserNick.getText().toString().trim();
              String userSex =textUserSexText.getText().toString().trim().equals("男")?"1":"0";
                presenter.getData(0, Config_UserInfo.USERINFO_UPDATE, nickName, userSex);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
        }
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {
        Log.i("zzy",o.toString());
        if( Config_UserInfo.USERINFO_UPDATE==apiType){
            Toast.makeText(SettingUserInfoActivity.this,"成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Throwable e) {

    }
}
