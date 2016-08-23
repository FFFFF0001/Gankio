package com.mifind.gankio.utils;

import com.mifind.gankio.GankApp;
import com.mifind.gankio.onekeyshare.OnekeyShare;
import com.mifind.gankio.onekeyshare.ShareContentCustomizeCallback;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by JW.Xuan on 2016/8/23 12:31.
 * 邮箱：mifind@sina.com
 */
public class ShareUtils {
    public static void showShare(final String url, final String title) {
        final OnekeyShare oks = new OnekeyShare();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (SinaWeibo.NAME.equals(platform.getName())) {


                } else if ("Wechat".equals(platform.getName()) || "WechatMoments".equals(platform.getName()) || "WechatFavorite".equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setUrl(url);
                    paramsToShare.setImageUrl("http://www.mob.com/static/app/icon/1471917030.png");
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
            }
        });
        oks.show(GankApp.getContext());
    }
}
