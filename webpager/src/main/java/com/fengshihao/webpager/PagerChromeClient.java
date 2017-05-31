package com.fengshihao.webpager;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by shihao on 17-5-23.
 */
class PagerChromeClient extends WebChromeClient {
	private static String TAG = "PagerChromeClient";

	WebPager pager;
	PagerChromeClient(WebPager pager) {
		super();
		this.pager = pager;
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
	}

	@Override
	public void onReceivedIcon(WebView view, Bitmap icon) {
		super.onReceivedIcon(view, icon);
	}

	@Override
	public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
		super.onReceivedTouchIconUrl(view, url, precomposed);
	}

	@Override
	public void onShowCustomView(View view, CustomViewCallback callback) {
		super.onShowCustomView(view, callback);
	}

	@Override
	public void onHideCustomView() {
		super.onHideCustomView();
	}

	@Override
	public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
		return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
	}

	@Override
	public void onRequestFocus(WebView view) {
		super.onRequestFocus(view);
	}

	@Override
	public void onCloseWindow(WebView window) {
		super.onCloseWindow(window);
	}

	@Override
	public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
		return super.onJsAlert(view, url, message, result);
	}

	@Override
	public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
		return super.onJsConfirm(view, url, message, result);
	}

	@Override
	public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
		return super.onJsPrompt(view, url, message, defaultValue, result);
	}

	@Override
	public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
		return super.onJsBeforeUnload(view, url, message, result);
	}

	@Override
	public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
		super.onGeolocationPermissionsShowPrompt(origin, callback);
	}

	@Override
	public void onGeolocationPermissionsHidePrompt() {
		super.onGeolocationPermissionsHidePrompt();
	}

	@Override
	public void onPermissionRequest(PermissionRequest request) {
		super.onPermissionRequest(request);
	}

	@Override
	public void onPermissionRequestCanceled(PermissionRequest request) {
		super.onPermissionRequestCanceled(request);
	}

	@Override
	public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.DEBUG) {
			Log.d(TAG, "onConsoleMessage " + consoleMessage.sourceId() + " "
				+ consoleMessage.lineNumber() + ":" + consoleMessage.message()) ;
			return true;
		}

		return super.onConsoleMessage(consoleMessage);
	}

	@Override
	public Bitmap getDefaultVideoPoster() {
		return super.getDefaultVideoPoster();
	}

	@Override
	public View getVideoLoadingProgressView() {
		return super.getVideoLoadingProgressView();
	}

	@Override
	public void getVisitedHistory(ValueCallback<String[]> callback) {
		super.getVisitedHistory(callback);
	}

	@Override
	public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
		return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
	}
}
