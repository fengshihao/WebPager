package com.fengshihao.webpagerExample;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.fengshihao.webpager.*;
import com.fengshihao.webpager.BuildConfig;

import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

	private static String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		LinearLayout f = (LinearLayout)findViewById(R.id.container);
		WebPager wp = new WebPager(this);
		wp.setUserAgent(makeUserAgent(UA_TYPE.MOBILE));
		f.addView(wp);
		wp.loadUrl("http://m.baidu.com");
	}

	public static enum UA_TYPE {
		MOBILE, TABLET, PC
	};
	private static String sWebkitVer;
	private static String sWebviewVer;
	// There is no API to get the version of webkit.
	// We get it from default UserAgent by using regex
	// If it failed then use default version.
	private String getWebkitVersion() {
		if (sWebkitVer != null) {
			return sWebkitVer;
		}
		sWebkitVer = findVersion(Pattern.compile("(AppleWebKit)/([\\d\\.]+)"));
		return sWebkitVer != null ? sWebkitVer : "534.24";
	}

	private String getWebviewVersion() {
		if (sWebviewVer != null) {
			return sWebviewVer;
		}
		sWebviewVer = findVersion(Pattern.compile("(Version|Release|Chrome)/([\\d\\.]+)"));
		return sWebviewVer != null ? sWebviewVer : "4.01";
	}

	private String findVersion(Pattern pattern) {
		WebView wv = new WebView(this.getApplicationContext());
		final String userAgent = wv.getSettings().getUserAgentString();
		if (userAgent != null) {
			final Matcher matcher = pattern.matcher(userAgent);
			if (matcher != null && matcher.find() && matcher.groupCount() == 2) {
				return matcher.group(2);
			}
		}
		return null;
	}

	private static final String DESKTOP_USERAGENT = "Mozilla/5.0 (X11; "
		+ "Linux x86_64) AppleWebKit/%s (KHTML, like Gecko) " + "Version/%s %s/%s Safari/%s";

	private static final String MOBILE_USERAGENT = "Mozilla/5.0 (Linux; U; Android %s; %s-%s; %s Build/%s) "
		+ "AppleWebKit/%s (KHTML, like Gecko) Version/%s %s/%s Mobile Safari/%s";

	private static final String TABLET_USERAGENT = "Mozilla/5.0 (Linux; Android %s; %s-%s; %s Build/%s) "
		+ "AppleWebKit/%s (KHTML, like Gecko) Version/%s %s/%s Safari/%s";

	private String makeUserAgent(UA_TYPE type) {
		if (type == UA_TYPE.MOBILE || type == UA_TYPE.TABLET) {
			final String osVer = TextUtils.isEmpty(Build.VERSION.RELEASE) ? "4.4"
				: Build.VERSION.RELEASE;
			String language = null;
			String country = null;
			final Locale locale = Locale.getDefault();
			if (locale != null) {
				language = locale.getLanguage();
				country = locale.getCountry();
			}
			language = TextUtils.isEmpty(language) ? "en" : language;
			country = TextUtils.isEmpty(country) ? "US" : country;
			final String model = TextUtils.isEmpty(Build.MODEL) ? "Unknow" : Build.MODEL;
			final String id = TextUtils.isEmpty(Build.ID) ? "Unknow" : Build.ID;
			final String template = type == UA_TYPE.MOBILE ? MOBILE_USERAGENT : TABLET_USERAGENT;
			return String.format(template, osVer, language, country, model, id, getWebkitVersion(),
				getWebviewVersion(), "webpager", "0.1",
				getWebkitVersion());
		} else if (type == UA_TYPE.PC) {
			return String.format(DESKTOP_USERAGENT, getWebkitVersion(), getWebviewVersion(),
				"webpager", "0.1", getWebkitVersion());
		} else {

		}
		return null;
	}

}
