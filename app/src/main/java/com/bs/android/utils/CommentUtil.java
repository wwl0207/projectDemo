package com.bs.android.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.bs.android.App;
import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.customview.dialog.DownloadDialog;
import com.bs.android.customview.dialog.EditDialog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.os.Environment.DIRECTORY_DCIM;

/**
 * Created by admin on 2017/12/18.
 */

public class CommentUtil {

    /**
     * 弹出支付密码弹出
     *
     * @param type 1,有返回 0.无返回
     */
    public static EditDialog showInputPasswords(BaseActivity mActivity, int type) {

        EditDialog editDialog = new EditDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        editDialog.setArguments(bundle);
        editDialog.show(mActivity.getSupportFragmentManager(), "custom");

        return editDialog;
    }
    /**
     * 检测是否安装微信
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }


    // 保留2位小数
    public static String roundNumber(Double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }

    /**
     * toast弹出文本，下一个弹出的文本覆盖上一个的文本
     * @param text 吐司内容
     */
    public static void showSingleToast(String text) {
        ToastUtils.showShortToast(text);
    }

    /**
     * dip---px转化
     * @param dp
     * @return
     */

    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, App.instance.getResources().getDisplayMetrics());
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**

     *

     * @param context 上下文

     * @param cls  启动activity的类名

     */
    public static void startActivity(Context context, Class<?> cls){
        Intent intent=new Intent(context,cls);
        context.startActivity(intent);
    }

    /**
     * 判断是否联网
     * @return
     */
    public static boolean checkNetwork(){

        boolean rs = false;
        // step 1. 获取一个连接管理器

        ConnectivityManager cm = (ConnectivityManager) App.instance.getSystemService(Context.CONNECTIVITY_SERVICE);
        // step 2. 获取当前可用的网络的信息对象  android.Manifest.permission.ACCESS_NETWORK_STATE.

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo == null){
            return false;
        }

        // step 3.获取网络类型

        int type = networkInfo.getType();

        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_MOBILE:
                rs = true;
                break;
            default:
                break;
        }
        return rs;
    }


    /**
     * 判断网络是否是WIFI状态
     * @return
     */
    public static  boolean checkWifi(){
        boolean rs = false;
        // step 1. 获取一个连接管理器
        ConnectivityManager cm = (ConnectivityManager) App.instance.getSystemService(Context.CONNECTIVITY_SERVICE);
        // step 2. 获取当前可用的网络的信息对象  android.Manifest.permission.ACCESS_NETWORK_STATE.
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo == null){
            return false;
        }
        // step 3.获取网络类型

        int type = networkInfo.getType();

        if (type== ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return rs;
    }

    /**

     * 获取运营商名字

     */
    public static String getOperatorName() {
        TelephonyManager telephonyManager = (TelephonyManager)App.instance.getSystemService(TELEPHONY_SERVICE);
        String operator = telephonyManager.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")) {
                return "中国移动";
            } else if (operator.equals("46001")) {
                return  "中国联通";
            } else if (operator.equals("46003")) {
                return "中国电信";
            }
        }
        return "";
    }


    /**
     * 获取设备号
     * @return
     */

    public static String getDeviceId(){
        return getUniquePsuedoID();
    }

    /**
     * 获取手机系统版本
     * @return
     */

    public static String getSystemVersion(){
        return  Build.VERSION.RELEASE;
    }


    //获得独一无二的Psuedo ID

    public static String getUniquePsuedoID() {
        String serial ="";

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号

            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化

            serial = "serial"; // 随便一个初始化

        }
        //使用硬件信息拼凑出来的15位号码

        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**

     * 获取设备分辨率

     * @param context

     * @return

     */
    public static String getDisplay(Context context){

        return getDisplayHeight(context)+"X"+getDisplayWidth(context);

    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int  getDisplayWidth(Context context){
        Activity activity= (Activity) context;
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.widthPixels;

    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */

    public static int getDisplayHeight(Context context){
        Activity activity= (Activity) context;
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 拷贝Assets文件到file里面
     * @param context
     * @param filename
     */

    public static void copyAssetsToFile(Context context, String filename){
        InputStream in = null;
        OutputStream out = null;
        if (!new File(context.getFilesDir(),filename).exists()){

            try {
                in =context.getResources().getAssets().open(filename);
                out = new FileOutputStream(new File(context.getFilesDir(),filename));

                byte[] buffer = new byte[1024];
                int len = 0;
                while((len=in.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }
                out.flush();
                Log.i("test","复制成功");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(in!=null){
                        in.close();
                    }
                    if(out!=null){
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }



    public interface CopyResult{

        void copySuccess(String filePath);
        void copyFail(Exception e);
    }
    /**
     * 拷贝Assets文件到file里面
     * @param context
     * @param filename
     */

    public static void copyAssetsToFile(Context context, String filename,CopyResult copyResult){
        InputStream in = null;
        OutputStream out = null;
        File outPutFile = new File(context.getFilesDir(), filename);
        if (!outPutFile.exists()){

            try {
                in =context.getResources().getAssets().open(filename);
                out = new FileOutputStream(outPutFile);

                byte[] buffer = new byte[1024];
                int len = 0;
                while((len=in.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }
                out.flush();
                Log.i("test","复制成功");
                if (copyResult!=null){
                    copyResult.copySuccess(outPutFile.getAbsolutePath());
                }
            } catch (Exception e) {
                if (copyResult!=null){
                    copyResult.copyFail(e);
                }
            } finally {
                try {
                    if(in!=null){
                        in.close();
                    }
                    if(out!=null){
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    /**
     * 获取APP版本名
     * @return
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---

            PackageManager pm = App.instance.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(App.instance.getPackageName(), 0);
            versionName = pi.versionName;
            //            versioncode = pi.versionCode;

            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }



    /**
     * 获取手机型号
     * @return
     */

    public static String getSystemModel(){
        return Build.MODEL;
    }

    /**
     * 如果输入法打开则关闭，如果没打开则打开
     */

    public static void openOrCloseInput(){
        InputMethodManager imm = (InputMethodManager)App.instance.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示键盘
     * @param view
     */
    public static void openInput(View view) {
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view, 0);
    }

    /**
     * 关闭输入法
     * @param view
     */

    public static void closeInput(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken()
                , InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 拨打客服电话
     */
    public static void callCustomTel(Context context){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + "");
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 拨打客服电话
     */
    public static void callPhoneTel(Context context, String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:"+phoneNumber);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 设置当前activity的状态栏颜色
     * @param statueBarColor   设置的颜色
     */
    public static void setStatueBarColor(Context context, int statueBarColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppCompatActivity activity= (AppCompatActivity)context;
            Window window = activity.getWindow();
            window.setStatusBarColor(statueBarColor);   //这里动态修改颜色
        }
    }




    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //  读取assets中的json文件
    public static String getAssetsJson(Context context, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                baos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toString();
    }

    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().canWrite();
        } else
            return false;
    }

    public static File getAppRootPath(Context context) {
        if (sdCardIsAvailable()) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM);
        } else {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String path = GetPathFromUri.getPath(context,uri);
            File file = new File(path);
            return file;
        }
    }


    /**
     * 获取文件或者目录的大小
     * @param file
     * @return 返回的是以M为单位的
     */
    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    public static String doubleNumberFormat(double number){
        DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(number);//format 返回的是字符串
    }


    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(File file){
        if (file.exists()){
            if (file.isDirectory()){
                File[] children = file.listFiles();
                for (File f: children) {
                    deleteFile(f);
                }
            }else {
                file.delete();
            }
        }
    }


    /**
     * 弹出确认对话框
     * @param context
     * @param content  内容
     * @param onClickListener  确定回调
     */
    public static void showDialog(Context context, String content, final View.OnClickListener onClickListener){

        final Dialog dialog = new Dialog(context, R.style.custom_confirm_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.view_custom_confirm_dialog, null);

        TextView tv_dialog_content=root.findViewById(R.id.tv_dialog_content);
        TextView tv_dialog_cancel=root.findViewById(R.id.tv_dialog_cancel);
        TextView tv_dialog_confirm=root.findViewById(R.id.tv_dialog_confirm);
        tv_dialog_content.setText(content);
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                }catch (Exception e){

                }
            }
        });

        tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                    if (onClickListener!=null){
                        onClickListener.onClick(view);
                    }
                }catch (Exception e){

                }
            }
        });


        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); //设置对话框背景透明 ，对于AlertDialog 就不管用了

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
//        lp.width = (int)App.instance.getResources().getDisplayMetrics().widthPixels; // 宽度
        //      lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
        //      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
        lp.width=root.getMeasuredWidth();
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);


        dialog.show();


    }



    /**
     * 弹出不能取消确认对话框
     * @param context
     * @param content  内容
     * @param onClickListener  确定回调
     */
    public static void showCannotCancelDialog(Context context, String content,String sure, final View.OnClickListener onClickListener){

        final Dialog dialog = new Dialog(context, R.style.custom_confirm_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.view_custom_cannot_cancel_confirm_dialog, null);

        TextView tv_dialog_content=root.findViewById(R.id.tv_dialog_content);
        TextView tv_dialog_cancel=root.findViewById(R.id.tv_dialog_cancel);
        TextView tv_dialog_confirm=root.findViewById(R.id.tv_dialog_confirm);
        tv_dialog_content.setText(content);
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                }catch (Exception e){

                }
            }
        });

        tv_dialog_confirm.setText(sure);

        tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                    if (onClickListener!=null){
                        onClickListener.onClick(view);
                    }
                }catch (Exception e){

                }
            }
        });

        dialog.setCancelable(false);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); //设置对话框背景透明 ，对于AlertDialog 就不管用了

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
//        lp.width = (int)App.instance.getResources().getDisplayMetrics().widthPixels; // 宽度
        //      lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
        //      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
        lp.width=root.getMeasuredWidth();
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);

        dialog.show();


    }


    /**

     * Bitmap保存成File

     *

     * @param bitmap input bitmap

     * @param name output file's name

     * @return String output file's path

     */

    public  static String bitmap2File(Bitmap bitmap, String name) {

        //系统相册目录
        File f = new File(CommentUtil.getAppRootPath(App.instance) +"/", name +  ".jpg");

        if  (f.exists()) {
            f.delete();
        }

        if (!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }

        FileOutputStream fOut = null;

        try  {
            f.createNewFile();
            fOut = new FileOutputStream(f);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            fOut.flush();

            fOut.close();
//            bitmap.recycle();

        } catch (IOException e) {

            Log.e("logger",e.toString());
            return  null;

        }

        return  f.getAbsolutePath();

    }


    /**
     * 弹出确认对话框
     * @param context
     * @param content  内容
     * @param onClickListener  确定回调
     */
    public static void showMainAdDialog(Context context, String content, final View.OnClickListener onClickListener){

        final Dialog dialog = new Dialog(context, R.style.custom_confirm_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.view_custom_main_ad_dialog, null);

        TextView tv_main_ad_content=root.findViewById(R.id.tv_main_ad_content);
        tv_main_ad_content.setText(content);

        RelativeLayout rl_main_ad_sure=root.findViewById(R.id.rl_main_ad_sure);
        rl_main_ad_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog!=null) {
                    if (onClickListener!=null){
                        onClickListener.onClick(view);
                    }
                    try {
                        dialog.dismiss();
                    }catch (Exception e){

                    }
                }
            }
        });


        dialog.setContentView(root);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); //设置对话框背景透明 ，对于AlertDialog 就不管用了

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        lp.x = 0; // 新位置X坐标
//        lp.y = -20; // 新位置Y坐标
//        lp.width = (int)App.instance.getResources().getDisplayMetrics().widthPixels; // 宽度
        //      lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
        //      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
        lp.width=root.getMeasuredWidth();
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);


        dialog.show();


    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 版本更新
     */
    public static void updateVersion(Context context,String title,String content, String apkPath, final boolean isForceUpdate) {

        DownloadBuilder builder= AllenVersionChecker
                .getInstance()
                .downloadOnly(UIData.create().setTitle(title).setContent(content).setDownloadUrl(apkPath));

        NotificationBuilder notificationBuilder = NotificationBuilder.create()
                .setRingtone(true)
                .setIcon(R.mipmap.ic_launcher)
                .setTicker("正在下载新版本")
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("正在下载,请稍后...");

        builder.setNotificationBuilder(notificationBuilder);
        builder.setForceRedownload(true);


        /**
         * 强制更新
         */
        builder.setForceUpdateListener(new ForceUpdateListener() {
            @Override
            public void onShouldForceUpdate() {

            }
        });
        builder.setCustomVersionDialogListener(new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                DownloadDialog downloadDialog = new DownloadDialog(context, R.style.DownloadDialog, R.layout.download_custom_dialog);
                TextView textView = downloadDialog.findViewById(R.id.tv_dialog_content);

//                TextView  versionchecklib_version_dialog_cancel=downloadDialog.findViewById(R.id.versionchecklib_version_dialog_cancel);
//                if (isForceUpdate){
//                    versionchecklib_version_dialog_cancel.setVisibility(View.GONE);
//                }else {
//                    versionchecklib_version_dialog_cancel.setText("取消");
//                }
                textView.setText(versionBundle.getContent());
                downloadDialog.setCanceledOnTouchOutside(false);
                downloadDialog.setCancelable(false);
                return downloadDialog;
            }
        });

        builder.executeMission(context);

    }


    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static String captureWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        view.destroyDrawingCache();
        String path = bitmap2File(ret, "分享图片");
        return path;
    }
    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        view.destroyDrawingCache();
        return ret;
    }




    /**
     * 截取除了导航栏之外的整个屏幕
     */
    public static String screenShotWholeScreen(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        String path = bitmap2File(bitmap, "分享图片");
        return path;

    }


    public  static void hideSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     *复制拷贝内容到粘贴板
     */
    public static void copyTv(Context context,String str){
        ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, str));
        if (clipboardManager.hasPrimaryClip()){
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }
    }


    // corners 为边框圆角（单位为px ） frame 为边框颜色，content为内部填充颜色
    public static GradientDrawable setBackgroundShap(Context context, int corners, int frameColor, int contentColor) {
        int strokeWidth = 2; // 3dp 边框宽度
        int roundRadius = 10; // 8dp 圆角半径
        int strokeColor = frameColor;// 边框颜色
        int fillColor = contentColor;// 内部填充颜色
        GradientDrawable gd = new GradientDrawable();// 创建drawable
        gd.setColor(context.getResources().getColor(fillColor));
        gd.setCornerRadius(dip2px(context, corners));
        gd.setStroke(strokeWidth, context.getResources().getColor(strokeColor));
        return gd;
    }

    // corners 为边框圆角（单位为px ） frame 为边框颜色，content为内部填充颜色 status 1 为虚线圆角
    public static GradientDrawable setBackgroundShap(Context context, int corners, int frameColor, int contentColor, int status) {
        int strokeWidth = dip2px(context, 1); // 1dp 边框宽度
        int strokeColor = frameColor;// 边框颜色
        int fillColor = contentColor;// 内部填充颜色
        GradientDrawable gd = new GradientDrawable();// 创建drawable
        gd.setColor(context.getResources().getColor(fillColor));
        gd.setCornerRadius(dip2px(context, corners));
        // gd.setStroke(strokeWidth,
        // context.getResources().getColor(strokeColor));
        int dashWidth = dip2px(context, 6);
        int dashGap = dip2px(context, 3);
        gd.setStroke(strokeWidth, context.getResources().getColor(strokeColor), dashWidth, dashGap);
        return gd;
    }

    // corners 为边框圆角（单位为px ） frame 为边框颜色，content为内部填充颜色
    public static GradientDrawable setBackgroundShapTwo(Context context, int corners, String frameColor, String endColor, String contentColor) {
        int strokeWidth = 2; // 3dp 边框宽度
        int roundRadius = 10; // 8dp 圆角半径
        int strokeColor = Color.parseColor(frameColor);// 边框颜色
        int fillColor = Color.parseColor(contentColor);// 内部填充颜色
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{Color.parseColor(frameColor), Color.parseColor(endColor)});// 创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(dip2px(context, corners));
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    // corners 为边框圆角（单位为px ） frame 为边框颜色，content为内部填充颜色
    public static GradientDrawable setBackgroundShap(Context context, int corners, String frameColor, String contentColor) {
        int strokeWidth = 2; // 3dp 边框宽度
        int roundRadius = 10; // 8dp 圆角半径
        int strokeColor = Color.parseColor(frameColor);// 边框颜色
        int fillColor = Color.parseColor(contentColor);// 内部填充颜色
        GradientDrawable gd = new GradientDrawable();// 创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(dip2px(context, corners));
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }
    // 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public static String getAssetJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
