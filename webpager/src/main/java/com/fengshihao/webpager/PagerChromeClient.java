package com.fengshihao.webpager;

import android.webkit.WebChromeClient;

/**
 * Created by shihao on 17-5-23.
 */
class PagerChromeClient extends WebChromeClient {
	WebPager pager;
	PagerChromeClient(WebPager pager) {
		super();
		this.pager = pager;
	}
}
