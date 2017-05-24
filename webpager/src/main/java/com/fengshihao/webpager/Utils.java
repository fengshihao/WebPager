package com.fengshihao.webpager;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by shihao on 17-5-24.
 */

final class Utils {
	private static String TAG = "Utils";
	private static Context appContext;
	static void setApplicationContext(Context ctx) {
		appContext = ctx;
	}

	static Context getAppContext() {
		if (appContext == null) {
			Log.e(TAG, "getAppContext: appContext is null should call setApplicationContext first");
		}
		return appContext;
	}

}
