package com.sky.transport.api;

import com.sky.app.library.base.bean.Result;
import com.sky.transport.bean.BaseUser;
import com.sky.transport.bean.BindIn;
import com.sky.transport.bean.CarIn;
import com.sky.transport.bean.CatchOrder;
import com.sky.transport.bean.DistanceMoneyIn;
import com.sky.transport.bean.ForgetIn;
import com.sky.transport.bean.MyOrderFilter;
import com.sky.transport.bean.MyOrderIn;
import com.sky.transport.bean.OrderIn;
import com.sky.transport.bean.QQWeixinIn;
import com.sky.transport.bean.ThidBindIn;
import com.sky.transport.bean.UpdateIn;
import com.sky.transport.bean.UpdatePwdIn;
import com.sky.transport.bean.UserBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 请求接口列表
 */
public interface ApiService {

    /**
     * 获取个人中心数据
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/percenter")
    Observable<Result> requestCenter(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 忘记密码
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/change_pwd")
    Observable<Result> requestForget(@Body UserBean bean);

    /**
     * 注册
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/register")
    Observable<Result> requestRegister(@Body ForgetIn forgetIn);

    /**
     * 登录
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/login")
    Observable<Result> requestLogin(@Body ForgetIn forgetIn);

    /**
     * QQ登录
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/qq_login")
    Observable<Result> requestQQLogin(@Body QQWeixinIn qqWeixinIn);

    /**
     * 微信登录
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/wx_login")
    Observable<Result> requestWeixinLogin(@Body QQWeixinIn qqWeixinIn);

    /**
     * 发送验证码
     * @return 回调监听
     */
    @FormUrlEncoded
    @POST("sms/send.do")
    Observable<String> requestCode(@FieldMap Map<String, String> map);

    /**
     * 我的订单
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/user_list")
    Observable<Result> requestMyOrder(@Query("user_id") String user_id, @Body MyOrderFilter myOrderFilter);

    /**
     * 设置个人信息
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/update_info")
    Observable<Result> requestSettingInfo(@Query("user_id") String user_id, @Body UserBean bean);

    /**
     * 查询余额
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/get_balance")
    Observable<Result> requestQueryAccount(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 订单详情
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/order_detail")
    Observable<Result> requestOrderDetail(@Query("user_id") String user_id, @Body CatchOrder catchOrder);

    /**
     * 获取汽车列表
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/diction/un/get_car_list")
    Observable<Result> requestCarList(@Body CarIn carIn);

    /**
     * 下单
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/add")
    Observable<Result> requestAddOrder(@Query("user_id") String user_id, @Body OrderIn orderIn);

    /**
     * 完成订单
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/user_finsh")
    Observable<Result> requestFinishOrder(@Query("user_id") String user_id, @Body MyOrderIn orderIn);

    /**
     * 取消订单
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/cancle")
    Observable<Result> requestCancelOrder(@Query("user_id") String user_id, @Body MyOrderIn orderIn);

    /**
     * 计算车费
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/un/get_distance_money")
    Observable<Result> requestDistanceMoney(@Body DistanceMoneyIn distanceMoneyIn);

    /**
     * 获取绑定手机号 微信 qq
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/get_bind_info")
    Observable<Result> queryBindInfo(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 修改登录密码
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/modify_password")
    Observable<Result> updateUserPwd(@Query("user_id") String user_id, @Body UpdatePwdIn updatePwdIn);

    /**
     * 绑定手机号 微信 qq
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/bind_info")
    Observable<Result> updateUserPwd(@Query("user_id") String user_id, @Body BindIn bindIn);

    /**
     * 微信QQ  openid 用户信息查询接口
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/get_user_info_by_openid")
    Observable<Result> queryUserIsBindMobile(@Body ThidBindIn thidBindIn);

    /**
     * 支付宝
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/zfb_pay/hj")
    Observable<Result> alipay(@Query("user_id") String user_id, @Body CatchOrder catchOrder);

    /**
     * 微信支付
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/wx_pay/app")
    Observable<Result> weixinPay(@Query("user_id") String user_id, @Body CatchOrder catchOrder);

    /**
     * 升级
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("app/un/get_version")
    Observable<Result> getVersion(@Body UpdateIn updateIn);
}