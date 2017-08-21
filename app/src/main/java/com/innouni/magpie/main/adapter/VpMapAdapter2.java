package com.innouni.magpie.main.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

/****
 * 轮播图Adapter
 * @author xxq by 2017-08-21
 *
 */
public class VpMapAdapter2 extends PagerAdapter {

	List<ImageView> mImageViews;
	Context mContext;
	public VpMapAdapter2(Context context, List<ImageView> arrayViews) {
		this.mContext  = context;
		this.mImageViews = arrayViews;
	}
	
	@Override
	public int getCount() {
		return mImageViews.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(mImageViews.get(arg1));
		return mImageViews.get(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	@Override
	public void finishUpdate(View arg0) {
	}
}
