package com.sky.transport.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.L;
import com.sky.transport.utils.WeixinLogin;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信一键登录回调
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		WeixinLogin.getInstance(this).onNewIntent(getIntent(), this);
    }

	@Override
	public void onReq(BaseReq baseReq) {
	}

	@Override
	public void onResp(BaseResp resp) {
		L.msg("微信授权返回");
		switch (resp.errCode){
			case BaseResp.ErrCode.ERR_OK://发送成功
				DialogUtils.hideLoading();
				String code = ((SendAuth.Resp)resp).code;
				L.msg("getAccessToken==>" + code);
				WeixinLogin.getInstance(this).getAccessToken(code);
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				if (null != WeixinLogin.mIWeixinLoginListener){
					WeixinLogin.mIWeixinLoginListener.onFailure("用户取消登录");
				}
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
			case BaseResp.ErrCode.ERR_SENT_FAILED:
			case BaseResp.ErrCode.ERR_UNSUPPORT:
			default:
				if (null != WeixinLogin.mIWeixinLoginListener){
					WeixinLogin.mIWeixinLoginListener.onFailure("登录失败");
				}
				break;
		}
		finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		WeixinLogin.getInstance(this).onNewIntent(intent, this);
	}
}