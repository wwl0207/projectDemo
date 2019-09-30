package com.bs.android.customview.webview;

import android.net.Uri;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public class MyWebChromeClient extends WebChromeClient {

    private android.webkit.ValueCallback<Uri> uploadMessage;
    private android.webkit.ValueCallback<Uri[]> uploadMessageAboveL;


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {

        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView webView, String s) {
        super.onReceivedTitle(webView, s);

    }




    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> valueCallback) {
        uploadMessage = valueCallback;
//        openImageChooserActivity();
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType) {
        uploadMessage = valueCallback;
//        openImageChooserActivity();
    }

    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
        uploadMessage = valueCallback;
//        openImageChooserActivity();
    }

    // For Android  >= 5.0
    public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
        uploadMessageAboveL = filePathCallback;
//        openImageChooserActivity();
        return true;
    }

    @Override
    public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                             JsResult arg3) {
        /**
         * 这里写入你自定义的window alert
         */
        return super.onJsAlert(null, arg1, arg2, arg3);
    }



//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            //添加图片返回
//            if (data != null && requestCode == REQUEST_CODE_SELECT) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                if (images != null) {
//
//                    ArrayList<String> imagePathList = new ArrayList<>();
//                    String url = images.get(0).path;
//                    imagePathList.add(url);
//                    Durban.with(baseActivity)
//                            // 裁剪界面的标题。
//                            .title("裁剪")
//                            .statusBarColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))
//                            .toolBarColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))
//                            .navigationBarColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))
//                            // 图片路径list或者数组。
//                            .inputImagePaths(imagePathList)
//                            // 图片输出文件夹路径。
//                            .outputDirectory(getAppRootPath(baseActivity).getAbsolutePath()+"/shiyixiu/")
//                            // 裁剪图片输出的最大宽高。
//                            .maxWidthHeight(500, 500)
//                            // 裁剪时的宽高比。
//                            .aspectRatio(1, 1)
//                            // 图片压缩格式：JPEG、PNG。
//                            .compressFormat(Durban.COMPRESS_JPEG)
//                            // 图片压缩质量，请参考：Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
//                            .compressQuality(90)
//                            // 裁剪时的手势支持：ROTATE, SCALE, ALL, NONE.
//                            .gesture(Durban.GESTURE_ALL)
//                            .controller(
//                                    Controller.newBuilder()
//                                            .enable(false) // 是否开启控制面板。
//                                            .rotation(true) // 是否有旋转按钮。
//                                            .rotationTitle(true) // 旋转控制按钮上面的标题。
//                                            .scale(true) // 是否有缩放按钮。
//                                            .scaleTitle(true) // 缩放控制按钮上面的标题。
//                                            .build()) // 创建控制面板配置。
//                            .requestCode(200)
//                            .start();
//
//
//                }
//            }
//        } else if (requestCode == 200) {
//
//            // 解析剪切结果：
//            if (resultCode == Activity.RESULT_OK) {
//                ArrayList<String> mImageList = Durban.parseResult(data);
//
//                String imageurl = mImageList.get(0);
//
//                // TODO: 2017/8/22 上传图片
//                Uri result = Uri.fromFile(new File(imageurl));
//                if (uploadMessage != null) {
//                    uploadMessage.onReceiveValue(result);
//                }
//
//                Uri[] results = new Uri[]{result};
//                if (uploadMessageAboveL != null) {
//                    uploadMessageAboveL.onReceiveValue(results);
//                }
//
//            } else {
//                // TODO 其它处理...
//                Uri result = Uri.fromFile(new File(""));
//                Uri[] results = new Uri[]{result};
//                uploadMessageAboveL.onReceiveValue(results);
//                uploadMessageAboveL = null;
//
//            }
//
//        }
//
//        if (resultCode == Activity.RESULT_CANCELED) {
//            Uri result = Uri.fromFile(new File(""));
//            Uri[] results = new Uri[]{result};
//            if (uploadMessageAboveL != null) {
//                uploadMessageAboveL.onReceiveValue(results);
//                uploadMessageAboveL = null;
//            }
//        }
//    }

}
