package com.snz.rskj.android.base;

import com.snz.rskj.android.bean.BeanUser;
import com.snz.rskj.android.bean.ChannelInfo;
import com.snz.rskj.android.bean.DataInfo;
import com.snz.rskj.android.bean.NewsInfo;
import com.snz.rskj.android.bean.DataInfo;
import com.snz.rskj.android.bean.LoginBean;
import com.snz.rskj.android.bean.RegisterBean;
import com.snz.rskj.android.bean.SCodeBean;
import com.snz.rskj.android.bean.VerifyCode;
import com.snz.rskj.android.view.home.bean.HomeBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/*
* 049de01db14a4c8184faa0aca7facf8a

c383f4c9026d471da0184ad5b24c0365

e8bbadbd51c44a139c789fb1ef062b94
* */
public interface INetService {
    //登陆
    @Multipart
    @POST("/index/index/login")
    Observable<LoginBean> getLogin(@PartMap Map<String, RequestBody> requestBodyMap);

    //获取验证码
    //http://zgztest.com/index/sendmail/sendmailcode
    @FormUrlEncoded
    @POST("index/sendmail/sendmailcode")
    Observable<VerifyCode> getGegisterCode(@Field("mobile") String mobile);

    //验证验证码
    //http://zgztest.com/index/sendmail/verification
    @FormUrlEncoded
    @POST("index/sendmail/verification")
    Observable<VerifyCode> getVerification(@Field("mobile") String mobile, @Field("yzcode") String yzcode);

    @FormUrlEncoded
    @POST("index/index/register")
    Observable<BeanUser> getGegisterPsw(@Field("mobile") String mobile, @Field("password") String password);

    //显示短视频 请求首页数据
//验证验证码
//http://zgztest.com/index/sendmail/verification
    @FormUrlEncoded
    @POST("index/index/showvideo")
    Observable<HomeBean> gethomeShow(@Field("vid") String vid);
//    @POST("news/listNewsChannel")
//    Observable<ChannelInfo> getChannelKey();
//
//    @POST("login")
//    @Headers("Content-Type:application/json")
//    Observable<NewsInfo> getNews(@Body RequestBody body);

    @FormUrlEncoded
    @POST("index/index/update")
    Observable<HomeBean> updatePersonInfo(@PartMap Map<String, RequestBody> requestBodyMap);
}
