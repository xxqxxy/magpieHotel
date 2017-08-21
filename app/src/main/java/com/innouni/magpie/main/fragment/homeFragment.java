package com.innouni.magpie.main.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.comutils.pulltorefresh.PullToRefreshBase;
import com.comutils.pulltorefresh.PullToRefreshScrollView;
import com.innouni.magpie.main.R;
import com.innouni.magpie.main.utils.comFunction;
import com.innouni.magpie.main.widget.TranslucentActionBar;
import com.innouni.magpie.main.widget.TranslucentScrollView;

/**
 * 首页
 * Created by Administrator on 2017/7/20.
 */

public class homeFragment extends Fragment {
    View mView,mContentView,zoomView;

    PullToRefreshScrollView ps_home;
    ScrollView m_sv;
    LayoutInflater mflater;
    private TranslucentScrollView translucentScrollView;
    private TranslucentActionBar actionBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false) ;
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initScrollViews(mView);



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
        mflater = LayoutInflater.from(getActivity());
        mContentView = mflater.inflate(R.layout.home_sub, null);


        m_sv.addView(mContentView);
        initContentViews();
    }

    private void initContentViews() {
        actionBar = (TranslucentActionBar) mContentView.findViewById(R.id.actionbar);
        //初始actionBar
        actionBar.setData(getString(R.string.tv_home), 0, null, 0, null, null);
        //开启渐变
        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(comFunction.getStatusBarHeight(getActivity()));

        translucentScrollView = (TranslucentScrollView) mContentView.findViewById(R.id.pullzoom_scrollview);
        //设置透明度变化监听
        translucentScrollView.setTranslucentChangedListener(new TranslucentScrollView.TranslucentChangedListener() {
            @Override
            public void onTranslucentChanged(int transAlpha) {
                Log.i("","[TranslucentChangedListener] "+transAlpha);
                actionBar.tvTitle.setVisibility(transAlpha > 15 ? View.VISIBLE : View.GONE);
            }
        });
        //关联需要渐变的视图
        translucentScrollView.setTransView(actionBar);

        zoomView = mContentView.findViewById(R.id.lay_header);
        //关联伸缩的视图
        translucentScrollView.setPullZoomView(zoomView);

    }

    TranslucentScrollView.TranslucentChangedListener mTransListener = new TranslucentScrollView.TranslucentChangedListener() {
        @Override
        public void onTranslucentChanged(int transAlpha) {

        }
    };


}
