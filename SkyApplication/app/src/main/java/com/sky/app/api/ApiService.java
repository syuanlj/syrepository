package com.sky.app.api;

import com.sky.app.bean.AddCommentInfo;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.AddressIn;
import com.sky.app.bean.ApplyAccountIn;
import com.sky.app.bean.ApplyRecordIn;
import com.sky.app.bean.AreaIn;
import com.sky.app.bean.BankIn;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.BindIn;
import com.sky.app.bean.CancelOrderIn;
import com.sky.app.bean.CaseIn;
import com.sky.app.bean.Category;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.Empty;
import com.sky.app.bean.ForgetIn;
import com.sky.app.bean.Message;
import com.sky.app.bean.MessageIn;
import com.sky.app.bean.MutiOrderIn;
import com.sky.app.bean.MutiShopCarIn;
import com.sky.app.bean.MyCollectIn;
import com.sky.app.bean.OrderIn;
import com.sky.app.bean.ProductCategoryIn;
import com.sky.app.bean.ProductDetailRequest;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.PubCommentIn;
import com.sky.app.bean.PublishIn;
import com.sky.app.bean.QQWeixinIn;
import com.sky.app.bean.SearchDecorationCity;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.SellMessageComplete;
import com.sky.app.bean.ShopCarIn;
import com.sky.app.bean.ShopProductIn;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.bean.SupplyFilter;
import com.sky.app.bean.SupplyIn;
import com.sky.app.bean.ThidBindIn;
import com.sky.app.bean.UpdateIn;
import com.sky.app.bean.UpdatePwdIn;
import com.sky.app.bean.UserBean;
import com.sky.app.library.base.bean.Result;
import com.sky.app.ui.activity.openIM.OpenIMUserBean;
import com.sky.app.ui.activity.search.RequestBoutiqueBean;
import com.sky.app.ui.activity.search.SearchShopUser;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
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
     * 商户注册
     *
     * @return
     */
    @POST("user/update_info")
    Observable<Result> sellRegister(@Query("user_id") String user_id, @Body SellMessageComplete sellMessageComplete);

    /**
     * 商品评论
     *
     * @return
     */
    @POST("user/un/user_comment_list")
    Observable<Result> productMessage(@Body CommentRequest comment);

    /**
     * 商品详情
     *
     * @return
     */
    @POST("product/un/product_detail")
    Observable<Result> productDetail(@Body ProductDetailRequest productDetailRequest);

    /**
     * 搜索商品
     *
     * @return
     */
    @POST("product/un/get_all_products")
    Observable<Result> searchProduct(@Body SearchProductRequest searchProductRequest);

    /**
     * 我的收藏
     */
    @POST("collect/collect_list")
    Observable<Result> getCollect(@Query("user_id") String user_id, @Body MyCollectIn myCollectIn);

    /**
     * 获取消息
     */
    @POST("msg/show")
    Observable<Result> getMessage(@Query("user_id") String user_id, @Body MessageIn messageIn);

    /**
     * 删除消息
     */
    @POST("msg/delete")
    Observable<Result> deleteMessage(@Query("user_id") String user_id, @Body Message message);

    /**
     * 查询用户综合接口
     */
    @POST("diction/un/get_user_by_group")
    Observable<Result> searchUser(@Body SearchUser empty);

    /**
     * 查找装饰城
     */
    @POST("diction/un/get_three_dir")
    Observable<Result> searchDecorationCity(@Body SearchDecorationCity empty);

    /**
     * 查找工厂
     */
    @POST("diction/un/get_companys")
    Observable<Result> searchFactory(@Body Category empty);

    /**
     * 查找区域
     */
    @POST("diction/un/get_area_dir")
    Observable<Result> searchPlace(@Body Empty empty);

    /**
     * 查找一级分类
     */
    @POST("diction/un/get_one_dir")
    Observable<Result> searchFirstCatogory(@Body Empty empty);


    /**
     * 查找二级分类
     */
    @POST("diction/un/get_two_dir")
    Observable<Result> searchSecondCatogory(@Body Category category);

    /**
     * 装饰城中的三级分类
     *
     * @return
     */
    @POST("h5_shopMenuController/get_user_by_group?tp=1")
    Observable<Result> searchUserDecoration(@Query("decorative_id") String decorative_id, @Query("two_dir_id") String two_dir_id, @Query("user_level_recommend") String user_level_recommend);

    @POST("h5_shopMenuController/get_user_by_group?tp=1")
    Observable<Result> searchUserShopDecoration(@Query("decorative_id") String decorative_id, @Query("two_dir_id") String two_dir_id, @Query("user_level_recommend") String user_level_recommend,@Query("page") int page);

    /**
     * 装饰城中的二级分类
     *
     * @param empty
     * @return
     */
    @POST("h5_shopMenuController/get_two_dir?one_dir_id=FW500001")
    Observable<Result> searchDecorationTwo(@Body Empty empty);


    /**
     * 测试
     *
     * @param requestMap 参数
     * @return 回调监听
     */
    @FormUrlEncoded
    @POST("sdk/login/submitUserData.action")
    Observable<String> requestLog(@FieldMap Map<String, String> requestMap);


    /**
     * 获取个人中心数据
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/percenter")
    Observable<Result> requestCenter(@Query("user_id") String user_id, @Body BaseUser baseUser);


    /**
     * open登录
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/openImControl/selectUserInfo")
    Observable<Result> openIMLogin(@Query("user_id") String user_id, @Body OpenIMUserBean openIMUserBean);

    /**
     * open失败登录
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/openImControl/insertUserInfo")
    Observable<Result> openIMRegister(@Query("user_id") String user_id, @Body OpenIMUserBean openIMUserBean);

    /**
     * open修改账户信息
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/openImControl/updateUserInfo")
    Observable<Result> openIMUpDate(@Query("user_id") String user_id, @Body OpenIMUserBean openIMUserBean);

    /**
     * open删除账户信息
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/openImControl/updateUserInfo")
    Observable<Result> openIMDelete(@Query("user_id") String user_id, @Body OpenIMUserBean openIMUserBean);

//    /**
//     * open登录
//     *
//     * @return 回调监听
//     */
//    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
//    @POST("/select/selectOneInfo")
//    Observable<Result> openIMLogin(@Query("user_id") String user_id, @Body BaseUser baseUser);
///**
//     * open失败登录
//     *
//     * @return 回调监听
//     */
//    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
//    @POST("/select/insertInfo")
//    Observable<Result> openIMRegister(@Query("user_id") String user_id,@Body BaseUser baseUser);


    /**
     * 忘记密码
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/change_pwd")
    Observable<Result> requestForget(@Body ForgetIn forgetIn);

    /**
     * 注册
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/register")
    Observable<Result> requestRegister(@Body ForgetIn forgetIn);

    /**
     * 登录
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/login")
    Observable<Result> requestLogin(@Body ForgetIn forgetIn);


    /**
     * QQ登录
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/qq_login")
    Observable<Result> requestQQLogin(@Body QQWeixinIn qqWeixinIn);

    /**
     * 微信登录
     *
     * @return 回调监听
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/wx_login")
    Observable<Result> requestWeixinLogin(@Body QQWeixinIn qqWeixinIn);

    /**
     * 发送验证码
     *
     * @return 回调监听
     */
    @FormUrlEncoded
    @POST("sms/send.do")
    Observable<String> requestCode(@FieldMap Map<String, String> map);

    /**
     * 设置个人信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/update_info")
    Observable<Result> requestSettingInfo(@Query("user_id") String user_id, @Body UserBean bean);

    /**
     * Banner
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/news/un/get_new_list")
    Observable<Result> requestBanner(@Body BannerIn bannerIn);

    /**
     * 精品店的数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("h5_shopMenuController/get_user_by_group?tp=1")
    Observable<Result> requestBoutique(@Body SearchUser empty);

    /**
     * 查询一级分类
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/diction/un/get_one_dir")
    Observable<Result> requestFirstCategory(@Body Empty empty);

    /**
     * 首页的品质好店
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("h5_userController/getUserList?user_level_recommend=1")
    Observable<Result> requestGoodShop(@Body Empty empty);

    /**
     * 查询首页供应商和采购商数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/un/get_index_info")
    Observable<Result> requestSupply(@Body SupplyIn supplyIn);

    /**
     * 查询首页工匠头条
     *
     * @param
     * @param
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("h5_noticeControl/getNoticeList?isEffective=1&noticeTit")
    Observable<Result> requestHeadlines(@Body Empty empty);

    /**
     * 获取精品好货
     *
     * @param empty
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("h5_productController/get_products?product_recommend=1")
    Observable<Result> requestGoods(@Body Empty empty);

    /**
     * 查询供应商和采购商数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/un/get_supply_info")
    Observable<Result> requestSupplyList(@Body SupplyFilter supplyFilter);

    /**
     * 发布数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/add_supply_info")
    Observable<Result> requestPublishContent(@Query("user_id") String user_id, @Body PublishIn publishIn);

    /**
     * 收藏发布信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/user_collect")
    Observable<Result> requestCollectPublish(@Query("user_id") String user_id, @Body CollectPubIn collectPubIn);

    /**
     * 取消收藏发布信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/cancel_collect")
    Observable<Result> cancelCollectPublish(@Query("user_id") String user_id, @Body CollectPubIn collectPubIn);

    /**
     * 发布详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/un/product_detail")
    Observable<Result> requestSupplyDetail(@Body ProductIn productIn);

    /**
     * 联系客服
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/msg/un/get_customer_phone")
    Observable<Result> requestMobile(@Body Empty empty);

    /**
     * 我的发布
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/get_my_releases")
    Observable<Result> requestMyPublish(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 删除我的发布
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/delete_supply_info")
    Observable<Result> requestDelMyPublish(@Query("user_id") String user_id, @Body ProductIn productIn);

    /**
     * 修改我的发布
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/update_supply_info")
    Observable<Result> requestEditMyPublish(@Query("user_id") String user_id, @Body PublishIn publishIn);

    /**
     * 我的订单
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product_order/get_my_orders")
    Observable<Result> requestMyOrder(@Query("user_id") String user_id, @Body OrderIn orderIn);

    /**
     * 取消我的订单
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product_order/cancel_order")
    Observable<Result> requestCancelMyOrder(@Query("user_id") String user_id, @Body CancelOrderIn cancelOrderIn);

    /**
     * 订单确认收货
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product_order/sure_order")
    Observable<Result> requestConfirmMyOrder(@Query("user_id") String user_id, @Body CancelOrderIn cancelOrderIn);

    /**
     * 删除订单
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product_order/delete_order")
    Observable<Result> requestDelMyOrder(@Query("user_id") String user_id, @Body CancelOrderIn cancelOrderIn);

    /**
     * 查询我的收货地址
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/get_address")
    Observable<Result> requestDelMyAddress(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 添加我的收货地址
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/add_delivery_address")
    Observable<Result> requestAddMyAddress(@Query("user_id") String user_id, @Body AddressDetail addressDetail);

    /**
     * 更新我的收货地址
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/update_delivery_address")
    Observable<Result> requestUpdateMyAddress(@Query("user_id") String user_id, @Body AddressDetail addressDetail);

    /**
     * 设置默认我的收货地址
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/set_default_address")
    Observable<Result> requestSetMyAddress(@Query("user_id") String user_id, @Body AddressIn addressIn);

    /**
     * 查询默认我的收货地址
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/get_default_address")
    Observable<Result> queryDefaultAddress(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 删除收货地址
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/delete_delivery_address")
    Observable<Result> requestDelMyAddress(@Query("user_id") String user_id, @Body AddressIn addressIn);

    /**
     * 查询区域
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/address/un/get_area_info")
    Observable<Result> requestArea(@Body AreaIn areaIn);

    /**
     * 查询我的购物车
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/shop_cart/show")
    Observable<Result> requestMyShoppingCar(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 删除购物车
     *
     * @param user_id
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/shop_cart/delete_cart")
    Observable<Result> requestDelShoppingCar(@Query("user_id") String user_id, @Body ShopCarIn shopCarIn);

    /**
     * 更新购物车数量
     *
     * @param user_id
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/shop_cart/update_cart")
    Observable<Result> requestUpdateShoppingCar(@Query("user_id") String user_id, @Body ShopCarIn shopCarIn);

    /**
     * 购物车展示商品
     *
     * @param user_id
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/shop_cart/get_cart_products_for_addorder")
    Observable<Result> requestQueryShoppingCarList(@Query("user_id") String user_id, @Body MutiShopCarIn mutiShopCarIn);

    /**
     * 确认订单
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product_order/add_product_order")
    Observable<Result> requestConfirmOrder(@Query("user_id") String user_id, @Body SingleOrderIn singleOrderIn);

    /**
     * 购物车合并下单确认订单
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/shop_cart/merge_add_order")
    Observable<Result> requestMutiConfirmOrder(@Query("user_id") String user_id, @Body MutiOrderIn mutiOrderIn);

    /**
     * 添加购物
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/shop_cart/add_to_cart")
    Observable<Result> requestAddShoppingCar(@Query("user_id") String user_id, @Body SingleOrderIn singleOrderIn);

    /**
     * 店铺-首页商品查询
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/un/get_user_home_products")
    Observable<Result> requestShopHomeData(@Body ShopProductIn shopProductIn);

    /**
     * 商家个人名片、店铺简介、名片简介
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/un/personal_info")
    Observable<Result> requestDescData(@Body ProductIntroduceIn productIntroduceIn);

    /**
     * 查询商户商品分类
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/diction/un/get_user_dir")
    Observable<Result> requestProductCategory(@Body ProductCategoryIn productCategoryIn);

    /**
     * 分页查看评价
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/un/user_comment_list")
    Observable<Result> requestComment(@Body CommentRequest commentRequest);

    /**
     * 发布评价
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/un/user_comment_list")
    Observable<Result> publishComment(@Body PubCommentIn pubCommentIn);

    /**
     * 分页查看用户的案例
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product/un/get_cases_list")
    Observable<Result> requestUserCaseList(@Body CaseIn caseIn);

    /**
     * 提现账户
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/my_bank_list")
    Observable<Result> requestMyAccountBank(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 申请提现
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/withdraw_add")
    Observable<Result> requestApplyAccount(@Query("user_id") String user_id, @Body ApplyAccountIn applyAccountIn);

    /**
     * 账户中心金额
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/account_center")
    Observable<Result> requestAccountMoney(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 我的提现记录
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/my_withdraw_list")
    Observable<Result> queryRecord(@Query("user_id") String user_id, @Body ApplyRecordIn applyRecordIn);

    /**
     * 删除提现账户
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/account/bank_del")
    Observable<Result> delRecord(@Query("user_id") String user_id, @Body BankIn bankIn);

    /**
     * 用户个人资料
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/un/user_detail")
    Observable<Result> queryUserDetail(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 评论
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/product_order/add_order_comment")
    Observable<Result> addComment(@Query("user_id") String user_id, @Body AddCommentInfo addCommentInfo);

    /**
     * 修改登录密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/modify_password")
    Observable<Result> updateUserPwd(@Query("user_id") String user_id, @Body UpdatePwdIn updatePwdIn);

    /**
     * 绑定手机号 微信 qq
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/bind_info")
    Observable<Result> updateUserPwd(@Query("user_id") String user_id, @Body BindIn bindIn);

    /**
     * 获取绑定手机号 微信 qq
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/user/get_bind_info")
    Observable<Result> queryBindInfo(@Query("user_id") String user_id, @Body BaseUser baseUser);

    /**
     * 微信QQ  openid 用户信息查询接口
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("user/un/get_user_info_by_openid")
    Call<Result> queryUserIsBindMobile(@Body ThidBindIn thidBindIn);

    /**
     * 升级
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("app/un/get_version")
    Observable<Result> getVersion(@Body UpdateIn updateIn);

    /**
     * 支付宝
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/zfb_pay/gj")
    Observable<Result> alipay(@Query("user_id") String user_id, @Body CancelOrderIn catchOrder);

    /**
     * 微信支付
     *
     * @return
     */
    @Headers({"Content-Type: application/json; charset=UTF-8", "Accept: application/json; charset=UTF-8"})
    @POST("/wx_pay/product_app")
    Observable<Result> weixinPay(@Query("user_id") String user_id, @Body CancelOrderIn catchOrder);


}