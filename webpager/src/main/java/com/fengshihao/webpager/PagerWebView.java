package com.fengshihao.webpager;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shihao on 17-5-22.
 */

class PagerWebView extends WebView {

	private static List<PagerWebView> pool = new LinkedList<>();
	private static WebChromeClient sWebChromeClient = new PagerChromeClient();
	private static WebViewClient sWebViewClient = new PagerWebViewClient();

	private PagerWebView(Context context) {
		super(context);
	}

	private void removeAllJs() {
		// TODO: 17-5-23 shihao
	}

	void reset() {
		stop();
		removeAllJs();
		setWebChromeClient(new WebChromeClient());
		setWebViewClient(new WebViewClient());
		loadUrl("about:blank");
		clearHistory();
		restoreState(new Bundle());
	}


	void recycle() {
		reset();
		pool.add(this);
	}

	static PagerWebView obtain() {
		PagerWebView w = null;
		if (pool.isEmpty()) {
			w = new PagerWebView(WebPager.appcontext);
		} else {
			w = pool.remove(0);
		}
		w.init();
		return w;
	}

	private void init() {
		setWebChromeClient(sWebChromeClient);
		setWebViewClient(sWebViewClient);
	}

	void stop() {
		stopLoading();
	}
}
