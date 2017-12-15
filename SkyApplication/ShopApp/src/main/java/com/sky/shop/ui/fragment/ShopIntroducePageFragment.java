package com.sky.shop.ui.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.contract.ImageContract;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.ProductIntroducePresenter;
import com.sky.shop.presenter.UploadImagePresenter;
import com.sky.shop.utils.AppDialogUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商铺简介主页
 * Created by Administrator on 2017/2/30.
 */
public class ShopIntroducePageFragment extends BaseViewFragment<ShopContract.IProductIntroducePresenter>
        implements ShopContract.IProductIntroduceView, ImageContract.IUploadView {

    @BindView(R.id.app_image)
    ImageView image;
    @BindView(R.id.app_seller_name)
    EditText sellerName;
    @BindView(R.id.app_collect_num)
    TextView collectNum;
    @BindView(R.id.app_desc)
    EditText desc;
    @BindView(R.id.app_content)
    EditText content;
    @BindView(R.id.app_addr)
    EditText addr;
    @BindView(R.id.app_mobile)
    EditText mobile;
    @BindView(R.id.app_qq)
    EditText qq;
    @BindView(R.id.app_weixin)
    EditText weixin;
    @BindView(R.id.editAndFinish)
    TextView editAndFinish;

    private ImageContract.IUploadPresenter iUploadPresenter;
    private ProductIntroduceOut productIntroduceOut = new ProductIntroduceOut();

    @Override
    protected void init() {
        isValid(false);

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sky.shop.image");
        filter.setPriority(Integer.MAX_VALUE);
        getActivity().registerReceiver(myReceiver, filter);
    }

    @Override
    protected void initViewsAndEvents() {
        onRefresh();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_product_introduce;
    }

    @Override
    protected void onDestoryFragment() {


    }

    @Override
    protected ShopContract.IProductIntroducePresenter presenter() {
        iUploadPresenter = new UploadImagePresenter(context, this);
        return new ProductIntroducePresenter(context, this);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    public void showSuccess(ProductIntroduceOut out) {
        ImageHelper.getInstance().displayDefinedImage(out.getPersonal_pic(), image,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        sellerName.setText(out.getNick_name());
        collectNum.setText(out.getNum_collect() + "");
        desc.setText(out.getShoper_desc());
        content.setText(out.getMain_business_desc());
        addr.setText(out.getAddress());
        mobile.setText(out.getMobile());
        qq.setText(out.getQq());
        weixin.setText(out.getWeixin());
    }

    @Override
    public void responseShopInfo(String msg) {
        editAndFinish.setText("编辑");
        isValid(false);
        T.showShort(context, msg);
        onRefresh();
    }

    @OnClick(R.id.app_image)
    void clickIcon(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.Permission.CAMERA_PERMISSION);
        }else{
            AppDialogUtils.showTakePhotoView(getActivity());
        }
    }

    @Override
    public void getImageUrl(String url) {
        productIntroduceOut.setPersonal_pic(url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(myReceiver);
    }

    /**
     * 接口广播
     */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.sky.shop.image".equals(intent.getAction())){
                String icon = intent.getStringExtra("image");
                L.msg("返回上传图片=>" + icon);
                if (!TextUtils.isEmpty(icon)){
                    ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(icon)).toString(), image,
                            R.mipmap.app_default_icon, R.mipmap.app_default_icon);
                    iUploadPresenter.uploadFile(icon);
                }
            }
        }

    };

    /**
     * 刷新
     */
    private void onRefresh(){
        getPresenter().requestDescData();
    }

    @OnClick(R.id.editAndFinish)
    void clickEditAndFinish(){
        if ("完成".equals(editAndFinish.getText().toString())){
            productIntroduceOut.setNick_name(sellerName.getText().toString().trim());
            productIntroduceOut.setShoper_desc(desc.getText().toString().trim());
            productIntroduceOut.setMain_business_desc(content.getText().toString().trim());
            productIntroduceOut.setAddress(addr.getText().toString().trim());
            productIntroduceOut.setMobile(mobile.getText().toString().trim());
            productIntroduceOut.setQq(qq.getText().toString().trim());
            productIntroduceOut.setWeixin(weixin.getText().toString().trim());
            getPresenter().requestShopIntroduce(productIntroduceOut);
        }else{
            editAndFinish.setText("完成");
            isValid(true);
        }
    }

    /**
     * 失效
     */
    private void isValid(boolean tag){
        image.setEnabled(tag);
        sellerName.setEnabled(tag);
        desc.setEnabled(tag);
        content.setEnabled(tag);
        addr.setEnabled(tag);
        mobile.setEnabled(tag);
        qq.setEnabled(tag);
        weixin.setEnabled(tag);
    }
}