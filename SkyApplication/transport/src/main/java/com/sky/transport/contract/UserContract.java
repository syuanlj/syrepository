package com.sky.transport.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.transport.bean.BindBean;
import com.sky.transport.bean.BindIn;
import com.sky.transport.bean.BindOut;
import com.sky.transport.bean.ForgetIn;
import com.sky.transport.bean.ThidBindIn;
import com.sky.transport.bean.UpdatePwdIn;
import com.sky.transport.bean.UserBean;

/**
 * Created by sky on 2017/2/10.
 * 主页契约类
 */

public class UserContract {

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
     * 余额
     */
    public interface IPurseView extends IBaseView {
        void refresh(double money);
    }

    /**
     * UI方法
     */
    public interface ICenterView extends IBaseView {
        /**
         * 刷新界面
         */
        void refresh();

        void getImageUrl(String url);

        void showOnSuccess(String msg);
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
        void refreshData(UserBean userBean);
    }

    /**
     * 忘记密码处理业务逻辑
     */
    public interface IForgetPresenter extends IBasePresenter {
        void forget(UserBean userBean);
        void showSuccess();
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
     * 账户处理业务逻辑
     */
    public interface IPursePresenter extends IBasePresenter {
        void getMoney();
        void showSuccess(double money);
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
        void forget(UserBean userBean);
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
     * 账户
     */
    public interface IPurseModel extends IBaseModel{
        void getMoney();
    }

    /**
     * 设置UI方法
     */
    public interface ISettingView extends IBaseView {
        void refreshView(BindOut bindOut);
        void responseUpdatePwd(String pwd);
        void responseBindData(String msg);
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
     * 设置
     */
    public interface ISettingModel extends IBaseModel{
        void queryUserDetail();
        void updatePwd(UpdatePwdIn updatePwdIn);
        void bindData(BindIn bindIn);
    }
}