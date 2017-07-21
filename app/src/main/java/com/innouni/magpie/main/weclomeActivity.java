package com.innouni.magpie.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.comutils.main.utils.SharePreferences;

/**
 * 欢迎页 weclome
 * 1.版本检测
 * 2.自动登录
 * Created by xxq on 2017/7/21.
 */

public class weclomeActivity extends Activity {

    SharePreferences isPreferences;
    Context mContext = weclomeActivity.this;
    private static final int MSG_DELAYED = 2000;//消息延迟  ，用于该页面在手机上停留一定时间之后再进行其他操作

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MSG_DELAYED){
                startActivity(new Intent(mContext , mainActivity.class));
            }
        }
    };
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weclome);
        isPreferences = new SharePreferences(this);
        isPreferences.updateSp("wlact_from", "");
        isPreferences.updateSp("iswelcome", true);
        isPreferences.updateSp("vsn_name", "");
        isPreferences.updateSp("vsn_code", "");
        isPreferences.updateSp("vsn_apppath", "");

        mHandler.sendEmptyMessageDelayed(MSG_DELAYED, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isPreferences.getSp().getString("wlact_from", "").equals("main")) {// Exit
            isPreferences.updateSp("iswelcome", false);
            if(mHandler!=null){
                mHandler.removeMessages(MSG_DELAYED);
                mHandler = null;
            }

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } else if (isPreferences.getSp().getString("wlact_from", "").equals("settings")) {// Login
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isPreferences.updateSp("iswelcome", false);

    }
}
