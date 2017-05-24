package com.fengshihao.webpager;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by shihao on 17-5-22.
 */
class WebPageItem {
	private static String TAG = "WebPageItem";
	private PagerWebView webview;
	private FrameLayout pageview;
	private String url;
	private int id;
	private static int idserial = 0;
	private boolean active;

	WebPageItem(String url) {
		this.url = url;
		id = idserial++;
	}

	boolean isEqual(int id) {
		return this.id == id;
	}

	@Override
	public String toString() {
		return "" + id + " " + url;
	}


	public ViewGroup getPageView() {
		if (pageview == null) {
			pageview = new FrameLayout(Utils.getAppContext());
		}
		return pageview;
	}

	public void setActive(boolean active) {
		Log.d(TAG, "setActive() called with: active = [" + active + "]");
		this.active = active;

		if (active) {
			if (webview == null) {
				webview = PagerWebView.obtain();
			}

			getPageView().addView(webview);
			webview.loadUrl(url);
		} else {
			webview.stop();
		}
	}

	public void destroyPageView() {
		if (pageview == null) return;
		Log.d(TAG, "destroyPageView() called");
		ViewGroup parent = (ViewGroup) pageview.getParent();
		parent.removeView(pageview);
		pageview.removeView(webview);
		webview.recycle();
		webview = null;
		pageview = null;
	}
}
