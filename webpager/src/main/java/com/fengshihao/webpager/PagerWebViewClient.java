package com.fengshihao.webpager;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by shihao on 17-5-22.
 */

final class PagerWebViewClient extends WebViewClient {
	private static String TAG = "PagerWebViewClient";

	private WebPager pager;
	public PagerWebViewClient(WebPager pager) {
		super();
		this.pager = pager;
	}

	@TargetApi(24)
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
		Log.d(TAG, "shouldOverrideUrlLoading() called with: request = [" + request.getUrl() + "]");
		if (pager.webviewClient != null) {
			if (pager.webviewClient.shouldOverrideUrlLoading(view, request)) {
				return true;
			}
		}

		if (handleUrlLoading(view, request.getUrl().toString())) {
			return true;
		}

		return super.shouldOverrideUrlLoading(view, request);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		Log.d(TAG, "shouldOverrideUrlLoading() called with: url = [" + url + "]");
		if (pager.webviewClient != null) {
			if (pager.webviewClient.shouldOverrideUrlLoading(view, url)) {
				return true;
			}
		}
		if (handleUrlLoading(view, url)) {
			return true;
		}
		return super.shouldOverrideUrlLoading(view, url);
	}

	private boolean handleUrlLoading(WebView view, String url) {
		Log.d(TAG, "handleUrlLoading() called with: " + " url = [" + url + "]");

		if (!(view instanceof PagerWebView)) {
			return false;
		}

		if (url.equals(view.getUrl())) {
			Log.d(TAG, "handleUrlLoading() is same as old url let webview process it " + url);
			return false;
		}
		mLastRequestTime = System.currentTimeMillis();
//		Log.d(TAG, "**getUrl() " + view.getUrl());
//		Log.d(TAG, "**getOriginalUrl() " + view.getOriginalUrl());
//		Log.d(TAG, "**url " + url);
		if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
			if (!isUserClick(view)) {
				Log.d(TAG, "handleUrlLoading checkIfRedirectRequest is true processed by webview.");
				return false;
			}
			Log.d(TAG, "handleUrlLoading process by pager, create new page.");
			pager.loadUrl(url);
			return true;
		}
		return false;
	}

	private boolean isUserClick(WebView webview) {
		return webview.getHitTestResult().getType() > 0;
	}

	private long mLastRequestTime;

	// see here https://stackoverflow.com/questions/3852414/in-a-webview-is-there-a-way-for-shouldoverrideurlloading-to-determine-if-it-is-c
	private boolean checkIfRedirectRequest() {
		final long now = System.currentTimeMillis();
		if (now - mLastRequestTime < 300) {
			mLastRequestTime = now;
			return true;
		}
		mLastRequestTime = now;
		return false;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		Log.d(TAG, "onPageStarted() called with: url = ["
			+ url + "], favicon = [" + favicon + "]");

	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		Log.d(TAG, "onPageFinished() called with: url = [" + url + "]");
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		super.onLoadResource(view, url);
	}

	@Override
	public void onPageCommitVisible(WebView view, String url) {
		super.onPageCommitVisible(view, url);
	}

	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
		return super.shouldInterceptRequest(view, request);
	}

	@Override
	public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
		super.onReceivedError(view, request, error);
	}

	@Override
	public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
		super.onReceivedHttpError(view, request, errorResponse);
	}

	@Override
	public void onFormResubmission(WebView view, Message dontResend, Message resend) {
		super.onFormResubmission(view, dontResend, resend);
	}

	@Override
	public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
		super.doUpdateVisitedHistory(view, url, isReload);
		Log.d(TAG, "doUpdateVisitedHistory() called with: url = [" + url + "], isReload = [" + isReload + "]");
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
		super.onReceivedSslError(view, handler, error);
	}

	@Override
	public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
		super.onReceivedClientCertRequest(view, request);
	}

	@Override
	public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
		super.onReceivedHttpAuthRequest(view, handler, host, realm);
	}

	@Override
	public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
		return super.shouldOverrideKeyEvent(view, event);
	}

	@Override
	public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
		super.onUnhandledKeyEvent(view, event);
	}

	@Override
	public void onScaleChanged(WebView view, float oldScale, float newScale) {
		super.onScaleChanged(view, oldScale, newScale);
	}

	@Override
	public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
		super.onReceivedLoginRequest(view, realm, account, args);
	}
}
