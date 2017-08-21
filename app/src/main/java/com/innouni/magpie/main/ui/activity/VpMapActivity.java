package com.innouni.magpie.main.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.comutils.main.ReAsyncTask;
import com.comutils.main.utils.APIUtil;
import com.comutils.main.utils.SharePreferences;
import com.innouni.magpie.main.R;
import com.innouni.magpie.main.adapter.VpMapAdapter2;
import com.innouni.magpie.main.utils.comFunction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/21.
 */

public class VpMapActivity extends Activity {

    ViewPager vp_map;
    private List<ImageView> arrayViews = null;
    private ScheduledExecutorService scheduledExecutorService;
    private int currentItem = 0;
    SharePreferences isPreferences;
    ReAsyncTask isytopImagesTask;
    VpMapAdapter2 mVpMapAdapter = null;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vp_map);
    mContext = this;
        isPreferences = new SharePreferences(this);
        isytopImagesTask = new ReAsyncTask();
        initViewPager();
        getSytopImages();

    }

    private void initViewPager() {
        vp_map = (ViewPager) findViewById(R.id.vp_map);
        arrayViews = new ArrayList<>();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
        int ht = (int) ((370 * getResources().getDisplayMetrics().widthPixels) / 640);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ht);
        vp_map.setLayoutParams(layoutParams);

    }
    private class ScrollTask implements Runnable {
        public void run() {
            Log.i("","tag ScrollTask ");

            synchronized (vp_map) {
                Log.i("","tag ScrollTask = "+currentItem);
                currentItem = (currentItem + 1) % arrayViews.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            vp_map.setCurrentItem(currentItem);
        };
    };


    /**顶部轮播图***/
    private void getSytopImages(){
        if(comFunction.isWiFi_3G(mContext)){
            if(!isytopImagesTask.isRun()){
                getiSytopImages();
            }
        }else{
            comFunction.toastMsg(mContext, getString(R.string.tv_net_not_visable));
        }
    }


    private void getiSytopImages(){
        ReAsyncTask.FinishCallback iFinishCallback = new ReAsyncTask.FinishCallback() {
            @Override
            public void taskFinish(int code, String result) {
                Log.i("", "tag sytopImages  = " + result);
                JSONObject jsonj = null;
                JSONArray jarray = null;
                String msg = null;
                if (result == null) {
                    comFunction.toastMsg(mContext, getString(R.string.tv_unknown_err));
                }else{
                    try {
                        jsonj = new JSONObject(result);
                        if (code == 200) {
                            ImageView imageView;
                            arrayViews.clear();
                            Log.i("", "tag sytopImages  = " + jsonj.getString("data"));
                            jarray = jsonj.getJSONArray("data");
                            int len = jarray.length();
                            for (int i = 0; i <len; i++){
                                String map = jarray.get(i)+"";
                                imageView = new ImageView(mContext);
                                Log.i("", "tag sytopImages map =  "+map);

                                Glide.with(mContext).load(map).placeholder(R.mipmap.ic_launcher).into(imageView);

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        comFunction.toastMsg(mContext, "点击了轮播图图片");
                                    }
                                });
                                arrayViews.add(imageView);
                            }
                            mVpMapAdapter = new VpMapAdapter2(mContext, arrayViews);
                            vp_map.setAdapter(mVpMapAdapter);
                            vp_map.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    Log.i("","tag OnPageChangeListener = onPageSelected"+position);
                                    currentItem = position;
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

                          /*   vp_map.setOnPageChangeListener(new  android.support.v4.view.ViewPager.OnPageChangeListener() {
                               @Override
                                public void onPageScrollStateChanged(int arg0) {
                                    Log.i("","tag OnPageChangeListener = onPageScrollStateChanged");
                                }

                                @Override
                                public void onPageScrolled(int arg0, float arg1, int arg2) {
                                    Log.i("","tag OnPageChangeListener = onPageScrolled");
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    Log.i("","tag OnPageChangeListener = onPageSelected"+position);
                                    currentItem = position;
                                }
                            });*/


                        }else{
                            msg = jsonj.getString("msg");
                        }

                        if(msg!=null){
                            comFunction.toastMsg(mContext, msg);
                            msg = null;
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                isytopImagesTask.setRun(false);

            }
        };


        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("app_mid", isPreferences.getSp().getString("m_id", "")));
        paramList.add(new BasicNameValuePair("mtoken", isPreferences.getSp().getString("m_token", "")));
        paramList.add(new BasicNameValuePair(APIUtil.APP_KEY_STR , APIUtil.APP_KEY));
        isytopImagesTask.loadData(APIUtil.API_URL+"sytopImages",paramList , iFinishCallback );
    }


}
