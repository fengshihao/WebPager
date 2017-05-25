package com.fengshihao.webpager;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by shihao on 17-5-22.
 */
class WebPageItem {
	private static String TAG = "WebPageItem";
	protected PagerWebView webview;
	private FrameLayout pageview;
	private String url;
	private int id;
	private static int idserial = 0;
	private boolean active;
	private PagerChromeClient pagerChromeClient;
	private PagerWebViewClient pagerWebViewClient;

	WebPageItem(String url, @NonNull PagerWebViewClient pwc, @NonNull PagerChromeClient pcc) {
		this.url = url;
		id = idserial++;
		pagerWebViewClient = pwc;
		pagerChromeClient = pcc;
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

		} else {
			webview.stop();
		}
	}

	void detach(ViewGroup parent) {
		Log.d(TAG, "detach() called parent=" + parent);
		parent.removeView(pageview);
		pageview.removeView(webview);
		webview.recycle();
		webview = null;
		pageview = null;
	}

	void attach(ViewGroup container) {
		Log.d(TAG, "attach() called with: container = [" + container + "] webview = [" + webview + "]");
		if (webview == null) {
			webview = PagerWebView.obtain();
			webview.setWebViewClient(pagerWebViewClient);
			webview.setWebChromeClient(pagerChromeClient);
		}

		getPageView().addView(webview);
		container.addView(pageview);
		webview.loadUrl(url);
	}
}
