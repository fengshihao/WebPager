package com.fengshihao.webpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by shihao on 17-5-22.
 */

public class WebPager extends ViewPager {
	private static String TAG = "WebPager";
	static Context appcontext;

	private WebPagerAdapter adapter;
	public WebPager(Context context) {
		super(context);
		init(context);
	}

	public WebPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		appcontext = null;
		Log.d(TAG, "onDetachedFromWindow() called");
	}


	private OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			Log.d(TAG, "onPageSelected() called with: position = [" + position + "]");
			adapter.onItemActive(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	void goTo(int offset) {
		Log.d(TAG, "goTo() called with: offset = [" + offset + "]");
		int idx = getCurrentItem() + offset;
		if (idx >= 0 && idx < getAdapter().getCount()) {
			setCurrentItem(idx);
		}
	}

	private boolean atFirstPage() {
		return getCurrentItem() == 0;
	}

	private boolean atLastPage() {
		int count = getAdapter().getCount();
		return count == 0 || count - 1 == getCurrentItem();
	}
	public void loadUrl(String url) {
		Log.d(TAG, "loadUrl() called with: url = [" + url + "]");
		if (!atLastPage()) {
			adapter.cutFrom(getCurrentItem());
		} else {
			adapter.addItem(url);
		}
	}

	private void init(Context context) {
		if (appcontext == null) {
			appcontext = context.getApplicationContext();
		}
		adapter = new WebPagerAdapter();
		setAdapter(adapter);
	}
}
