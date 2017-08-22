package com.innouni.magpie.main.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.comutils.main.MyListView;
import com.comutils.main.ReAsyncTask;
import com.comutils.main.utils.APIUtil;
import com.comutils.main.utils.SharePreferences;
import com.comutils.pulltorefresh.PullToRefreshBase;
import com.comutils.pulltorefresh.PullToRefreshScrollView;
import com.innouni.magpie.main.R;
import com.innouni.magpie.main.adapter.HouseAdapter;
import com.innouni.magpie.main.adapter.VpMapAdapter2;
import com.innouni.magpie.main.ui.activity.VpMapActivity;
import com.innouni.magpie.main.utils.comFunction;
import com.innouni.magpie.main.widget.TranslucentActionBar;
import com.innouni.magpie.main.widget.TranslucentScrollView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 首页
 * Created by Administrator on 2017/7/20.
 */

public class homeFragment extends Fragment {
    View mView,mContentView,zoomView;
    ViewPager vp_map;
    PullToRefreshScrollView ps_home;
    ScrollView m_sv;
    LayoutInflater mflater;
    private TranslucentScrollView translucentScrollView;
    private TranslucentActionBar actionBar;
    private List<ImageView> arrayViews = null;
    TextView tv_destination,tv_my_near,tv_time,tv_search;//目的地、我的附近、住宿时间、搜索
    ProgressBar pb_bar;//定位时进度条
    ImageView iv_house_more;//房源更多
    MyListView lv_house;//房源列表

    private ScheduledExecutorService scheduledExecutorService;
    private int currentItem = 0;
    SharePreferences isPreferences;
    ReAsyncTask isytopImagesTask ,iSyatjHousesTask;
    VpMapAdapter2 mVpMapAdapter = null;
    HouseAdapter mHouseAdapter = null;
    Context mContext = null;
    List<HashMap<String, Object>> mList = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false) ;
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        isPreferences = new SharePreferences(mContext);
        initThreads();
        initScrollViews(mView);
    }

    private void initThreads() {
        isytopImagesTask = new ReAsyncTask();
        iSyatjHousesTask = new ReAsyncTask();

    }

    private void initScrollViews(View mView) {
        ps_home = (PullToRefreshScrollView) mView.findViewById(R.id.ps_home);
        // 设置下拉刷新可用
        ps_home.setPullRefreshEnabled(true);
        // 上拉加载不可用
        ps_home.setPullLoadEnabled(false);
        // 滚动到底自动加载可用
        ps_home.setScrollLoadEnabled(false);
        ps_home.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });


        m_sv = ps_home.getRefreshableView();
        mflater = LayoutInflater.from(mContext);
        mContentView = mflater.inflate(R.layout.home_sub, null);
        m_sv.addView(mContentView);

        initScrollViewContentViews();
        initViewPager();
//        initContentViews();

        //获取数据
//        ps_home.doPullRefreshing(true,1000);

        getSytopImages();
        getSyatjHouses();



    }
//
//    private void initContentViews() {
//        actionBar = (TranslucentActionBar) mContentView.findViewById(R.id.actionbar);
//        //初始actionBar
//        actionBar.setData(getString(R.string.tv_home), 0, null, 0, null, null);
//        //开启渐变
//        actionBar.setNeedTranslucent();
//        //设置状态栏高度
//        actionBar.setStatusBarHeight(comFunction.getStatusBarHeight(mContext));
//
//        translucentScrollView = (TranslucentScrollView) mContentView.findViewById(R.id.pullzoom_scrollview);
//        //设置透明度变化监听
//        translucentScrollView.setTranslucentChangedListener(new TranslucentScrollView.TranslucentChangedListener() {
//            @Override
//            public void onTranslucentChanged(int transAlpha) {
//                Log.i("","[TranslucentChangedListener] "+transAlpha);
//                actionBar.tvTitle.setVisibility(transAlpha > 15 ? View.VISIBLE : View.GONE);
//            }
//        });
//        //关联需要渐变的视图
//        translucentScrollView.setTransView(actionBar);
//
//        zoomView = mContentView.findViewById(R.id.lay_header);
//        //关联伸缩的视图
//        translucentScrollView.setPullZoomView(zoomView);
//    }


    private void initScrollViewContentViews(){
        tv_destination = (TextView) mContentView.findViewById(R.id.tv_destination);
        tv_my_near = (TextView) mContentView.findViewById(R.id.tv_my_near);
        tv_search = (TextView) mContentView.findViewById(R.id.tv_search);
        tv_time = (TextView) mContentView.findViewById(R.id.tv_time);

        pb_bar = (ProgressBar) mContentView.findViewById(R.id.pb_bar);

        lv_house = (MyListView) mContentView.findViewById(R.id.lv_house);
        lv_house.setDividerHeight(0);
        lv_house.setDivider(new ColorDrawable(getResources().getColor(R.color.cr_gray_1)));
        lv_house.setDividerHeight((int) (getResources().getDisplayMetrics().density * 5));
        lv_house.setDivider(new ColorDrawable(getResources().getColor(R.color.cr_gray_3)));

        mList = new ArrayList<>();
        mHouseAdapter = new HouseAdapter(mContext ,mList, mOnClickListener );
        lv_house.setAdapter(mHouseAdapter);

    }


    private void initViewPager() {
        vp_map = (ViewPager) mContentView.findViewById(R.id.vp_map);
        arrayViews = new ArrayList<>();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
        int ht = (int) ((370 * getResources().getDisplayMetrics().widthPixels) / 640);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ht);
        vp_map.setLayoutParams(layoutParams);

    }
    private class ScrollTask implements Runnable {
        public void run() {

            Log.i("","tag ScrollTask = ");

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

                                Glide.with(mContext).load(map).placeholder(R.mipmap.icon_home_top_lunbo).into(imageView);

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                       startActivity(new Intent(mContext, VpMapActivity.class));
                                    }
                                });
                                arrayViews.add(imageView);
                            }
                            mVpMapAdapter = new VpMapAdapter2(mContext, arrayViews);
                            vp_map.setAdapter(mVpMapAdapter);

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


    /***优质房源***/
    private void getSyatjHouses(){
        if(comFunction.isWiFi_3G(mContext)){
            if(!iSyatjHousesTask.isRun()){
                getiSyatjHouses();
            }
        }else{
            comFunction.toastMsg(mContext, getString(R.string.tv_net_not_visable));
        }
    }

    private void getiSyatjHouses(){

        ReAsyncTask.FinishCallback iFinishCallback = new ReAsyncTask.FinishCallback() {
            @Override
            public void taskFinish(int code, String result) {
                Log.i("", "tag syayzHouses  = " + result);
                JSONObject jsonj = null;
                JSONArray jarray = null;
                String msg = null;
                if (result == null) {
                    comFunction.toastMsg(mContext, getString(R.string.tv_unknown_err));
                }else{
                    try {
                        jsonj = new JSONObject(result);
                        if(code == 200){
                            jarray = jsonj.getJSONArray("data");
                            int len = jarray.length();
                            for (int i = 0;i<len;i++){
                                HashMap<String , Object> hMap = new HashMap<>();
                                hMap.put("hs_id", jarray.getJSONObject(i).getString("hs_id").toString().replace("null" ,""));//房源id
                                hMap.put("hs_title", jarray.getJSONObject(i).getString("hs_title").toString().replace("null" ,""));//房源标题
                                hMap.put("price", jarray.getJSONObject(i).getString("price").toString().replace("null" ,"0"));//价格
                                hMap.put("hs_fist_img", jarray.getJSONObject(i).getString("hs_fist_img").toString().replace("null" ,""));//展示图
                                hMap.put("hs_address", jarray.getJSONObject(i).getString("hs_address").toString().replace("null" ,""));//大致地址
                                hMap.put("comments", jarray.getJSONObject(i).getString("comments").toString().replace("null" ,""));//评论数量
                                hMap.put("hs_total_score", jarray.getJSONObject(i).getString("hs_total_score").toString().replace("null" ,""));//评分
                                hMap.put("c_state", jarray.getJSONObject(i).getString("c_state").toString().replace("null" ,"2"));//收藏状态
                                mList.add(hMap);
                            }

                        }else{
                            msg = jsonj.getString("msg");
                        }

                        if(msg == null){
                            mHouseAdapter.notifyDataSetChanged();
                        }else{
                            comFunction.toastMsg(mContext, msg);
                            msg = null;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                iSyatjHousesTask.setRun(false);
            }
        };

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair(APIUtil.APP_KEY_STR , APIUtil.APP_KEY));
        iSyatjHousesTask.loadData(APIUtil.API_URL+"syayzHouses",paramList , iFinishCallback );

    }



    //优质房源Item点击事件
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            String  position = (String) view.getTag();
            switch (view.getId()){
                case R.id.ll_house:

                    break;

                case R.id.iv_c_state:

                    break;

                default:
                    break;
            }

        }
    };

    TranslucentScrollView.TranslucentChangedListener mTransListener = new TranslucentScrollView.TranslucentChangedListener() {
        @Override
        public void onTranslucentChanged(int transAlpha) {

        }
    };


}
