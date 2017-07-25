package com.jingdroid.cook.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DisScrollViewPager extends ViewPager {
	
	private boolean isCanScroll = false;
	
	public DisScrollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DisScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!isCanScroll) {
			return false;
		} else {
			return super.onInterceptTouchEvent(ev);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!isCanScroll) {
			return false;
		} else {
			return super.onTouchEvent(ev);
		}
	}

	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}
	
}
