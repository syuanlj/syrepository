package com.sky.driver.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.driver.bean.AccountBankList;
import com.sky.driver.bean.BankIn;
import com.sky.driver.contract.AccountContract;
import com.sky.driver.model.AccountDetailModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 提现账户
 */

public class AccountDetailFragmentPresenter extends BasePresenter<AccountContract.IAccountDetailView>
        implements AccountContract.IAccountDetailPresenter {

    private AccountContract.IAccountDetailModel iAccountDetailModel;

    /**
     * 构造
     */
    public AccountDetailFragmentPresenter(Context context,
                                          AccountContract.IAccountDetailView iAccountDetailView) {
        super(context, iAccountDetailView);
        this.iAccountDetailModel = new AccountDetailModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iAccountDetailModel){
            iAccountDetailModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadData() {
        iAccountDetailModel.queryMyAccountList();
    }

    @Override
    public void refreshData(AccountBankList accountBankList) {
        getView().getRefreshData(accountBankList.getList());
    }

    @Override
    public void del(BankIn bankIn) {
        iAccountDetailModel.del(bankIn);
    }

    @Override
    public void showDelSuccess(String msg) {
        getView().showDelSuccess(msg);
    }
}