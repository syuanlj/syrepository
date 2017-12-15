package com.sky.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.sky.app.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 友盟分享
 * @author wdf
 *
 */
public class ShareUtils {

	/**
	 * 分享
	 * @param ctx
	 * @param url 分享的网页url
	 * @param text 分享内容
	 * @param title 分享标题
	 */
	public static void share(final Activity ctx, final String url, final String text, final String title) {
		final UMImage image = new UMImage(ctx, BitmapFactory.decodeResource(ctx.getResources(),
				R.mipmap.ic_launcher));
		UMWeb web = new UMWeb("http://www.51craftsman.com/");
		web.setTitle("51工匠");//标题
		web.setThumb(image);  //缩略图
		web.setDescription("同城的生活资讯服务");//描述
		new ShareAction(ctx)
				.withText(ctx.getResources().getString(R.string.app_name))
				.withSubject(ctx.getResources().getString(R.string.app_name))
				.withMedia(web)
				.setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
				.setCallback(new UMShareListener() {

					@Override
					public void onStart(SHARE_MEDIA platform) {
						//分享开始的回调
					}
					@Override
					public void onResult(SHARE_MEDIA platform) {
						Toast.makeText(ctx, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onError(SHARE_MEDIA platform, Throwable t) {
						Toast.makeText(ctx,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
						if(t!=null){
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						Toast.makeText(ctx,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
					}
				}).open();

	}

	/**
	 * 回调
	 * @param activity
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
		UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
	}
}
