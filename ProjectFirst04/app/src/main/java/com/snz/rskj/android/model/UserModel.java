package com.snz.rskj.android.model;

import com.example.frame.ModelUtil;
import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.base.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

public class UserModel implements ICommonModel {
    @Override
    public void getData(IConmmonView view, int loadType, int apiType, Object[] t) {

    }
    private void updatePersonInfo(IConmmonView view, int loadType, int apiType, Object[] t) {
        Map<String, String> map = new HashMap();
        map.put("nickname", t[0] + "");
        map.put("gender", t[1] + "");
//        map.put("birth", t[2] + "");
//        map.put("region", t[3] + "");
//        map.put("describe", t[4] + "");
//        map.put("head_img", t[5] + "");
//        map.put("user_id", t[6] + "");
//        map.put("Platform_id",t[7]+"");
        ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().updatePersonInfo(RetrofitManager.fromRequestBody(map)), view, loadType, apiType);

    }

}
