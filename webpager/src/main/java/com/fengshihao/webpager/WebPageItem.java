package com.fengshihao.webpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
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
	private Bundle bundle = new Bundle();
	private WebBackForwardList webBackForwardList;

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
		Log.d(TAG, "detach() called url=" + url);
		webBackForwardList = webview.saveState(bundle);
		parent.removeView(pageview);
		pageview.removeView(webview);
		webview.recycle();
		webview = null;
		pageview = null;
	}

	void attach(ViewGroup container) {
		Log.d(TAG, "attach() called with:  url = [" + url + "]");
		if (webview == null) {
			webview = PagerWebView.obtain();
			webview.setWebViewClient(pagerWebViewClient);
			webview.setWebChromeClient(pagerChromeClient);
		}

		getPageView().addView(webview);
		container.addView(pageview);
		if (webBackForwardList == null) {
			webview.loadUrl(url);
		} else {
			WebBackForwardList bl = webview.restoreState(bundle);
			if (webBackForwardList.getSize() != bl.getSize()) {
				Log.e(TAG, "restore history " + bl.getSize()
					+ "is not equals old history=" + webBackForwardList.getSize());
				for (int i = 0; i < bl.getSize(); i++) {
					WebHistoryItem h = bl.getItemAtIndex(i);
					Log.e(TAG, "restore history " + h.getUrl());
				}
				for (int i = 0; i < webBackForwardList.getSize(); i++) {
					WebHistoryItem h = webBackForwardList.getItemAtIndex(i);
					Log.e(TAG, "old history " + h.getUrl());
				}
			}
			bundle.clear();
			webBackForwardList = null;
		}
	}
}
