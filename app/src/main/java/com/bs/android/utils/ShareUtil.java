
package com.bs.android.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.ByteArrayOutputStream;

public class ShareUtil {
    //    SHARE_MEDIA.SINA, ,SHARE_MEDIA.WEIXIN_FAVORITE收藏
    final static SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
//                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
            };


    /**
     * 分享图片
     */
    public static void WxBitmapShare(final Activity context, Bitmap bitmap) {

        final UMShareListener umslistener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {

                Toast.makeText(context, platform + t.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
            }
        };

        UMImage web = new UMImage(context, bitmap);
        ShareAction shareAction = new ShareAction(context);
        shareAction.withMedia(web);
        shareAction.setDisplayList(displaylist);
        shareAction.setCallback(umslistener);
        shareAction.open();


    }


    public static void sharecallback(final BaseActivity activity, String img, final String title, final String content, final String url) {
        final UMShareListener umslistener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {

                Toast.makeText(activity, platform + t.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
            }
        };

        ShareAction shareAction = new ShareAction(activity);
//        UMImage thumb =  new UMImage(activity,img);
//        UMImage image = new UMImage(ShareActivity.this, "imageurl");//网络图片
//        image.setThumb(thumb);
//        UMImage image = new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.mipmap.copy));

        UMImage image = new UMImage(activity, img);
        if (!"".equals(img) && img != null) {
            image = new UMImage(activity, img);
        } else {
            image = new UMImage(activity, R.mipmap.ic_launcher);
        }
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//        压缩格式设置
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(content);//描述
        shareAction.withMedia(web);
        shareAction.setDisplayList(displaylist);
        shareAction.setCallback(umslistener);
        shareAction.open();
    }

    public static void share(final Activity activity, final String img, final String title, final String content, final String url) {
        final UMShareListener umslistener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                String type = "";
                if (platform.equals(SHARE_MEDIA.WEIXIN)) {
                    type = "1";
                } else if (platform.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {
                    type = "2";
                } else if (platform.equals(SHARE_MEDIA.QQ)) {
                    type = "3";
                } else if (platform.equals(SHARE_MEDIA.QZONE)) {
                    type = "4";
                }
                Intent i1 = new Intent("share_util_bd_send");
                i1.putExtra("type", type);
                activity.sendBroadcast(i1);
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {

                Toast.makeText(activity, platform + t.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
            }
        };

        ShareAction shareAction = new ShareAction(activity);


        UMImage image = new UMImage(activity, img);
        if (!"".equals(img) && img != null) {
            image = new UMImage(activity, img);
        } else {
            image = new UMImage(activity, R.mipmap.ic_launcher);
        }
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//        压缩格式设置
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(content);//描述
        shareAction.withMedia(web);
        shareAction.setDisplayList(displaylist);

        shareAction.addButton("umeng_sharebutton_copyurl", "umeng_sharebutton_custom", "umeng_socialize_copyurl", "umeng_socialize_copyurl");
        shareAction.setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom")) {
                    //点击后复制微信号的逻辑
                    ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    assert clipboardManager != null;
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, url));
                    if (clipboardManager.hasPrimaryClip()) {
                        clipboardManager.getPrimaryClip().getItemAt(0).getText();
                    }
                    Toast.makeText(activity, "链接已复制成功！", Toast.LENGTH_LONG).show();
                } else {
                    //分享的图片
                    UMImage image = new UMImage(activity, img);
                    if (!"".equals(img) && img != null) {
                        image = new UMImage(activity, img);
                    } else {
                        image = new UMImage(activity, R.mipmap.ic_launcher);
                    }
                    image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                    //        压缩格式设置
                    image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                    UMWeb web = new UMWeb(url);
                    //分享链接
                    web.setTitle(title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(content);//描述
                    new ShareAction(activity)
                            .setPlatform(share_media)
                            .withText("多平台分享")
                            .withMedia(web).setCallback(umslistener).share();
                }
            }
        });
        shareAction.setCallback(umslistener);
        shareAction.open();
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

}
