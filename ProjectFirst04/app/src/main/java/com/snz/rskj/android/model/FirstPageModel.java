package com.snz.rskj.android.model;

import com.example.frame.ModelUtil;
import com.example.frame.configs.ApiConfig;
import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.base.RetrofitManager;

import okhttp3.RequestBody;

/**
 * Created by pwoer on 2018/12/24.
 */
public class FirstPageModel implements ICommonModel {
    @Override
    public void getData(IConmmonView view, int loadType, int apiType, Object[] t) {
        switch (apiType) {
            case ApiConfig.GET_CHANNEL_KEY:
//                ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().getChannelKey(),
//                        view, loadType, apiType);
                break;
            case ApiConfig.GET_NEWS_LIST:
//                String page = (String) t[1];
//                String json = "{\n" +
//                        "\"channelId\": \"" + t[0] + "\", \n" +
//                        "\"cursor\": \"" + page + "\",\n" +
//                        "}";
//                RequestBody body = RequestBody.create(null,json);
//                ModelUtil.netRequest(RetrofitManager.getInstance().getNetService().getNews(body),view,loadType,apiType);
                break;
        }
    }
}
