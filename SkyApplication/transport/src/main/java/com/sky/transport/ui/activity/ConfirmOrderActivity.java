package com.sky.transport.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.app.library.utils.ThreadPoolManager;
import com.sky.transport.R;
import com.sky.transport.bean.AlipayOut;
import com.sky.transport.bean.CatchOrder;
import com.sky.transport.bean.DistanceMoneyOut;
import com.sky.transport.bean.OrderIn;
import com.sky.transport.bean.PayResult;
import com.sky.transport.contract.OrderContract;
import com.sky.transport.presenter.ConfirmOrderActivityPresenter;
import com.sky.transport.utils.AppDialogUtils;
import com.sky.transport.utils.PayDialog;
import com.sky.transport.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 确认下单【货运端】
 */
public class ConfirmOrderActivity extends BaseViewActivity<OrderContract.IConfirmPresenter>
        implements OrderContract.IConfirmOrderView{

    private static final int SDK_PAY_FLAG = 1;
    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView transportTitle;
    @BindView(R.id.transport_call_car_tv)
    TextView callCar;
    OrderIn orderIn;
    DistanceMoneyOut distanceMoneyOut;

    @BindView(R.id.transport_money)
    TextView money;
    @BindView(R.id.transport_begin_addr_name_tv)
    TextView beginAddrName;
    @BindView(R.id.transport_begin_addr_detail_tv)
    TextView beginDetail;
    @BindView(R.id.transport_end_addr_name_tv)
    TextView endAddrName;
    @BindView(R.id.transport_end_addr_detail_tv)
    TextView endDetail;
    @BindView(R.id.transport_extra_tv)
    TextView extra;
    @BindView(R.id.transport_remark)
    TextView remark;

    private IWXAPI api;
    private int payFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_confirm_order);
    }

    @Override
    protected OrderContract.IConfirmPresenter presenter() {
        return new ConfirmOrderActivityPresenter(context, this);
    }

    @Override
    protected void init() {
        transportTitle.setText(R.string.transport_confirm_order_string);
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);

        Bundle bundle = getIntent().getExtras();
        this.orderIn = (OrderIn) bundle.getSerializable("order");
        this.distanceMoneyOut = (DistanceMoneyOut) bundle.getSerializable("money");

        //初始化界面
        money.setText(distanceMoneyOut.getMoney()/100 + "");
        beginAddrName.setText(orderIn.getFrom_address());
        beginDetail.setText(orderIn.getFrom_detail());
        endAddrName.setText(orderIn.getTo_address());
        endDetail.setText(orderIn.getTo_detail());
        extra.setText("额外需求：" + orderIn.getOther_desc());
        remark.setText("备注：" + orderIn.getRemark());
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        api = WXAPIFactory.createWXAPI(this, WXPayEntryActivity.APP_ID);
    }

    @Override
    public void responseOrder(final CatchOrder catchOrder) {
        switch (payFlag){
            case 0://支付宝
                getPresenter().alipay(catchOrder);
                break;
            case 1://微信
                getPresenter().weixinPay(catchOrder);
                break;
        }
    }

    @Override
    public void responseWeixinPay(JSONObject json) {
        AppDialogUtils.closePayDialog();
        PayReq req = new PayReq();
        try {
            req.appId = json.has("appId") ? json.getString("appId") : "";
            req.partnerId = json.has("partnerId") ? json.getString("partnerId") : "";
            req.prepayId = json.has("prepayId") ? json.getString("prepayId") : "";
            req.nonceStr = json.has("nonceStr") ? json.getString("nonceStr") : "";
            req.timeStamp = json.has("timeStamp") ? json.getString("timeStamp") : "";
            req.packageValue = json.has("package") ? json.getString("package") : "";
            req.sign = json.has("sign") ? json.getString("sign") : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        api.sendReq(req);
    }

    @Override
    public void responseAlipay(final AlipayOut alipayOut) {
        AppDialogUtils.closePayDialog();
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                Map<String, String> result = alipay.payV2(alipayOut.getReturn_info(), true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @OnClick(R.id.transport_call_car_tv)
    void clickCallCar(){
        AppDialogUtils.showPayDialog(this, new PayDialog.IPayCallback() {
            @Override
            public void pay(int flag) {
                payFlag = flag;
                orderIn.setTo_detail(null);
                orderIn.setFrom_detail(null);
                getPresenter().requestOrder(orderIn);
            }
        });
    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        T.showShort(context, "支付成功");
                    } else {
                        T.showShort(context, "支付失败");
                    }
                    finish();
                    break;
                }
                default:
                    break;
            }
        };
    };
}
