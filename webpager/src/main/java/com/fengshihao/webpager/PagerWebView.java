package com.fengshihao.webpager;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shihao on 17-5-22.
 */

class PagerWebView extends WebView {
	private static String TAG = "PagerWebView";
	private static List<PagerWebView> pool = new LinkedList<>();
	private PagerWebView(Context context) {
		super(context);
		getSettings().setUserAgentString(WebPager.sUserAgentString);
	}

	// TODO: 17-5-27 shihao
	// disable pool for now
	private static boolean usingPool = false;

	private void removeAllJs() {
		// TODO: 17-5-23 shihao
	}

	void reset() {
		stop();
		removeAllJs();
		setWebChromeClient(new WebChromeClient());
		setWebViewClient(new WebViewClient());
		removeAllViews();
		clearHistory();
		if (!usingPool) {
			destroy();
		}
	}

	private boolean clearHistoryAfterPageFinished;
	public void setClearHistoryFlag(boolean clear) {
		clearHistoryAfterPageFinished = clear;
	}


	void recycle() {
		reset();
		if (usingPool) {
			pool.add(this);
		}
	}

	static PagerWebView obtain() {
		PagerWebView w = null;
		if (pool.isEmpty()) {
			w = new PagerWebView(Utils.getAppContext());
		} else {
			w = pool.remove(0);
		}
		w.init();
		return w;
	}

	private void init() {
		final WebSettings setting = getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setSupportZoom(false);
		setting.setSupportMultipleWindows(true);
		setting.setSupportZoom(false);
		setting.setBuiltInZoomControls(false);
		setting.setSupportMultipleWindows(true);
		setting.setJavaScriptCanOpenWindowsAutomatically(true);
		setting.setDomStorageEnabled(true);
		setting.setNeedInitialFocus(false);
		setting.setGeolocationEnabled(true);
		setting.setAllowFileAccess(true);
		setFocusable(true);
		setFocusableInTouchMode(true);

	}

	void stop() {
		stopLoading();
	}
}
