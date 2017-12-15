package com.sky.driver.api;

import com.sky.app.library.base.bean.Result;
import com.sky.driver.bean.Add_Account_ApplyBean;
import com.sky.driver.bean.ApplyAccountIn;
import com.sky.driver.bean.ApplyRecordIn;
import com.sky.driver.bean.BankIn;
import com.sky.driver.bean.BaseUser;
import com.sky.driver.bean.BindIn;
import com.sky.driver.bean.CatchOrder;
import com.sky.driver.bean.DriverIn;
import com.sky.driver.bean.Empty;
import com.sky.driver.bean.ForgetIn;
import com.sky.driver.bean.IdCardIn;
import com.sky.driver.bean.LocationBean;
import com.sky.driver.bean.MyOrderFilter;
import com.sky.driver.bean.OrderFilter;
import com.sky.driver.bean.OrderIn;
import com.sky.driver.bean.QQWeixinIn;
import com.sky.driver.bean.ThidBindIn;
import com.sky.driver.bean.UpdateIn;
import com.sky.driver.bean.UpdatePwdIn;
import com.sky.driver.bean.UserBean;
import com.sky.driver.bean.Withdraw_AddInfo;

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
    Observable<Result> requestForget(@Body ForgetIn forgetIn);

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
    @POST("/order/truck_list")
    Observable<Result> requestMyOrder(@Query("user_id") String user_id, @Body MyOrderFilter myOrderFilter);

    /**
     * 驾驶证认证
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/truck_driver_authentication")
    Observable<Result> requestDriverAuth(@Query("user_id") String user_id, @Body DriverIn driverIn);

    /**
     * 身份证认证
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/truck_idcard_authentication")
    Observable<Result> requestCard(@Query("user_id") String user_id, @Body IdCardIn idCardIn);

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
     * 等待接单的订单
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/wait_list")
    Observable<Result> requestWaitOrderList(@Query("user_id") String user_id, @Body OrderFilter orderFilter);

    /**
     * 接单
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/truck_accept")
    Observable<Result> requestCatchOrder(@Query("user_id") String user_id, @Body CatchOrder catchOrder);

    /**
     * 完成订单
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/order/truck_finsh")
    Observable<Result> requestFinishOrder(@Query("user_id") String user_id, @Body OrderIn orderIn);

    /**
     * 提现账户
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/my_bank_list")
    Observable<Result> requestMyAccountBank(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 申请提现
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/withdraw_add")
    Observable<Result> requestApplyAccount(@Query("user_id") String user_id, @Body ApplyAccountIn applyAccountIn);

    /**
     * 账户中心金额
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/account_center")
    Observable<Result> requestAccountMoney(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 我的提现记录
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/my_withdraw_list")
    Observable<Result> queryRecord(@Query("user_id") String user_id, @Body ApplyRecordIn applyRecordIn);

    /**
     * 删除提现账户
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/bank_del")
    Observable<Result> delRecord(@Query("user_id") String user_id, @Body BankIn bankIn);

    /**
     * 添加提现账户
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/bank_add")
    Observable<Result> AddRecord(@Query("user_id") String user_id,@Body Add_Account_ApplyBean info);

    /**
     * 添加提现账户
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/withdraw_add")
    Observable<Result> Withdraw_add(@Query("user_id") String user_id,@Body Withdraw_AddInfo info);

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
     * 获取绑定手机号 微信 qq
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/get_bind_info")
    Observable<Result> queryBindInfo(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 微信QQ  openid 用户信息查询接口
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/get_user_info_by_openid")
    Observable<Result> queryUserIsBindMobile(@Body ThidBindIn thidBindIn);

    /**
     * 升级
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("app/un/get_version")
    Observable<Result> getVersion(@Body UpdateIn updateIn);

    /**
     * 获取体现手续费
     * @param empty
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("account/get_withdraw_rate")
    Observable<Result> getWithdrawRate(@Query("user_id") String user_id, @Body Empty empty);

    /**
     * 获取定位信息
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("order/wait_distince_list")
    Observable<Result> getLocation(@Query("user_id") String user_id, @Body LocationBean locationBean);
}