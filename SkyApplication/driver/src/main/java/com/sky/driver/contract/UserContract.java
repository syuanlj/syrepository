package com.sky.driver.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.driver.bean.BindBean;
import com.sky.driver.bean.BindIn;
import com.sky.driver.bean.BindOut;
import com.sky.driver.bean.ForgetIn;
import com.sky.driver.bean.ThidBindIn;
import com.sky.driver.bean.UpdatePwdIn;
import com.sky.driver.bean.UserBean;

/**
 * Created by sky on 2017/2/10.
 * 主页契约类
 */

public class UserContract {

    /**
     * 主页更新UI方法
     */
    public interface IMineView extends IBaseView {
        /**
         * 刷新用户界面
         */
        void showUserInfo();
    }

    /**
     * 主页更新UI方法
     */
    public interface ILoginView extends IBaseView {
        /**
         * 展示用户
         */
        void showUserInfo();

        void responseIsBindMobile(boolean isBind, String mobile);
    }

    /**
     * UI方法
     */
    public interface IRegisterView extends IBaseView {
        /**
         * 展示用户
         */
        void showUserInfo();
    }

    /**
     * UI方法
     */
    public interface IForgetView extends IBaseView {
        void showSuccess();
    }

    /**
     * UI方法
     */
    public interface ICenterView extends IBaseView {
        /**
         * 刷新界面
         */
        void refresh(UserBean userBean);

        void getImageUrl(String url);

        void showOnSuccess(String msg);
    }

    /**
     * UI方法
     */
    public interface ISettingView extends IBaseView {
        void refreshView(BindOut bindOut);
        void responseUpdatePwd(String pwd);
        void responseBindData(String msg);
    }

    /**
     * 余额
     */
    public interface IPurseView extends IBaseView {
        void refresh(double money);
    }

    /**
     * 驾驶证UI方法
     */
    public interface IDriverView extends IBaseView {
        /**
         * 展示图片
         */
        void showDriverImage(String url);

        /**
         * 展示成功信息
         */
        void showSuccess();
    }

    /**
     * 身份证UI方法
     */
    public interface IIdCardView extends IBaseView {
        /**
         * 展示正面图片
         */
        void showFrontImage(String url);

        /**
         * 展示反面图片
         */
        void showBackImage(String url);

        /**
         * 成功回调
         */
        void showSuccess();
    }

    /**
     * 主页处理业务逻辑
     */
    public interface ILoginPresenter extends IBasePresenter {
        /**
         * 登录
         */
        void login(ForgetIn forgetIn);

        /**
         * 刷新用户界面
         * @param userBean
         */
        void refreshData(UserBean userBean);

        void queryUserIsBindMobile(ThidBindIn thidBindIn);
        void responseUserIsBindMobile(BindBean bindBean);
    }

    /**
     * 注册处理业务逻辑
     */
    public interface IRegisterPresenter extends IBasePresenter {
        /**
         * 注册
         */
        void register(ForgetIn forgetIn);

        /**
         * 刷新用户界面
         * @param userBean
         */
        void refreshData(UserBean userBean);
    }

    /**
     * 主页处理业务逻辑
     */
    public interface IMinePresenter extends IBasePresenter {
        /**
         * 获取用户信息
         */
        void refreshUserInfo();

        /**
         * 刷新用户信息
         */
        void updateInfo();
    }

    /**
     * 忘记密码处理业务逻辑
     */
    public interface IForgetPresenter extends IBasePresenter {
        void forget(ForgetIn forgetIn);
        void showSuccess();
    }

    /**
     * 个人中心处理业务逻辑
     */
    public interface ICenterPresenter extends IBasePresenter {
        void getUserInfo();
        void setUserInfo();
        void uploadFile(String url);
        void showSuccess();
        void showOnSuccess(String msg);
    }

    /**
     * 验证码处理业务逻辑
     */
    public interface ICodePresenter extends IBasePresenter {
        /**
         * 发送验证码
         */
        void sendCode(String mobile);
    }

    /**
     * 设置处理业务逻辑
     */
    public interface ISettingPresenter extends IBasePresenter {
        void queryUserDetail();
        void responseUserInfo(BindOut bindOut);
        void updatePwd(UpdatePwdIn updatePwdIn);
        void responseUpdatePwd(String pwd);
        void bindData(BindIn bindIn);
        void responseBindData(String msg);
    }

    /**
     * 账户处理业务逻辑
     */
    public interface IPursePresenter extends IBasePresenter {
        void getMoney();
        void showSuccess(double money);
    }

    /**
     * 驾驶证处理业务逻辑
     */
    public interface IDriverPresenter extends IBasePresenter {
        void showSuccess();
        void saveImage();
        void showImageUrl(String url);
    }

    /**
     * 身份证处理业务逻辑
     */
    public interface IIdCardPresenter extends IBasePresenter {
        void showImageUrl(String url, int flag);
        void showSuccess();
        void saveImage();
    }

    /**
     * 网络请求
     */
    public interface ILoginModel extends IBaseModel{
        /**
         * 登录
         */
        void login(ForgetIn forgetIn);

        void queryUserIsBindMobile(ThidBindIn thidBindIn);
    }

    /**
     * 网络请求
     */
    public interface IMineModel extends IBaseModel{
        /**
         * 获取用户账户信息
         */
        void getUserData();
    }

    /**
     * 网络请求
     */
    public interface IRegisterModel extends IBaseModel{
        /**
         * 注册
         */
        void register(ForgetIn forgetIn);
    }

    /**
     * 忘记密码
     */
    public interface IForgetModel extends IBaseModel{
        void forget(ForgetIn forgetIn);
    }

    /**
     * 验证码
     */
    public interface ICodeModel extends IBaseModel{
        /**
         * 发送验证码
         */
        void sendCode(String mobile);
    }

    /**
     * 个人中心
     */
    public interface ICenterModel extends IBaseModel{
        /**
         * 展示用户信息
         */
        void getUserInfo();

        /**
         * 设置用户信息
         */
        void setUserInfo();
    }

    /**
     * 设置
     */
    public interface ISettingModel extends IBaseModel{
        void queryUserDetail();
        void updatePwd(UpdatePwdIn updatePwdIn);
        void bindData(BindIn bindIn);
    }

    /**
     * 账户
     */
    public interface IPurseModel extends IBaseModel{
        void getMoney();
    }

    /**
     * 驾驶证
     */
    public interface IDriverModel extends IBaseModel{
        void driver();
    }

    /**
     * 身份证
     */
    public interface IIdCardModel extends IBaseModel{
        void idCard();
    }
}