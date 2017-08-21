package com.innouni.magpie.main.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/8/18.
 */

public class comFunction {

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /** 判断wifi 3g 是否正常打开 **/
    public static boolean isWiFi_3G(Context inContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) inContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        //NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(activeNetInfo == null) return false;
        if(!activeNetInfo.isAvailable()) return false;
        if(activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) return true;
        if(activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) return true;
        if(activeNetInfo.getState() == NetworkInfo.State.CONNECTED) return true;
        return false;
    }
    /** 消息提示 **/
    public static void toastMsg(Context context,String msg){
        Toast toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /***时间格式**/
    public static String getDateToString2() {
        return new SimpleDateFormat("MM-dd HH:mm").format(System.currentTimeMillis());
    }
}
