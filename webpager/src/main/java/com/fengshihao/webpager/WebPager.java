package com.fengshihao.webpager;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

/**
 * Created by shihao on 17-5-22.
 */

public class WebPager extends CustomViewPager {
	private static String TAG = "WebPager";

	static String sUserAgentString;
	private WebPagerAdapter adapter;
	private PagerWebViewClient pagerWebViewClient;
	private PagerChromeClient pagerChromeClient;

	public WebPager(Context context) {
		super(context);
		init(context);
	}

	public WebPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public static void setUserAgent(String ua) {
		if (TextUtils.isEmpty(ua)) return;
		Log.d(TAG, "setUserAgent() called with: ua = [" + ua + "]");
		sUserAgentString = ua;
	}

	private OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			Log.d(TAG, "onPageScrolled() called with: position = [" + position
				+ "], positionOffset = [" + positionOffset
				+ "], positionOffsetPixels = [" + positionOffsetPixels + "]");
		}

		@Override
		public void onPageSelected(int position) {
			Log.d(TAG, "onPageSelected() called with: position = [" + position + "]");
			//adapter.onItemActive(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			Log.d(TAG, "onPageScrollStateChanged() called with: state = [" + state + "]");
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
		}
		adapter.addItem(url);
		goTo(1);
	}

	private void init(Context context) {
		pagerWebViewClient = new PagerWebViewClient(this);
		pagerChromeClient = new PagerChromeClient(this);
		Utils.setApplicationContext(context.getApplicationContext());
		adapter = new WebPagerAdapter(pagerWebViewClient, pagerChromeClient);
		setAdapter(adapter);
	}

	WebChromeClient chromeClient;
	public void setChromeClient(WebChromeClient cc) {
		chromeClient = cc;
	}

	WebViewClient webviewClient;
	public void setWebViewClient(WebViewClient wvc) {
		webviewClient = wvc;
	}
}
