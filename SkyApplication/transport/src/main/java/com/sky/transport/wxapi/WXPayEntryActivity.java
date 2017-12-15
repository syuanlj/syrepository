package com.sky.transport.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.T;
import com.sky.transport.ui.activity.ConfirmOrderActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	public static final String APP_ID = "wx1b545b681c5bdb34";
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode){
				case BaseResp.ErrCode.ERR_OK:
					T.showShort(this, "支付成功");
					break;
				default:
					T.showShort(this, "支付失败");
					break;
			}
		}
		BaseAppManager.getInstance().finishActivity(ConfirmOrderActivity.class);
		finish();
	}
}