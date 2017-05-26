package com.fengshihao.webpager;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shihao on 17-5-22.
 */

final class WebPagerAdapter extends PagerAdapter {
	private static final String TAG = "WebPagerAdapter";

	private List<WebPageItem> history = new LinkedList<>();
	private PagerChromeClient pagerChromeClient;
	private PagerWebViewClient pagerWebViewClient;

	WebPagerAdapter(@NonNull PagerWebViewClient pwc, @NonNull PagerChromeClient pcc) {
		pagerWebViewClient = pwc;
		pagerChromeClient = pcc;
	}

	void addItem(String url) {
		Log.d(TAG, "addItem() called with: url = [" + url + "]");
		if (!URLUtil.isValidUrl(url)) {
			Log.w(TAG, "addItem try to add one invalid url=" + url);
			return;
		}
		WebPageItem item = new WebPageItem(url, pagerWebViewClient, pagerChromeClient);
		history.add(item);
		notifyDataSetChanged();
	}

	WebPageItem getItem(int position) {
		if (position < 0 || position >= history.size()) {
			Log.e(TAG, "getItem: wrong position = " + position + " history size " + history.size());
			return null;
		}
		return history.get(position);
	}


	void onItemActive(int position) {
		Log.d(TAG, "onItemActive() called with: position = [" + position + "]");
		WebPageItem item = getItem(position);
		item.setActive(true);
	}


	void cutFrom(int position) {
		Log.d(TAG, "cutFrom() called with: position = [" + position + "]"
			+ "history size = " + history.size());
		if (position < 0 || position >= history.size() - 1) {
			return;
		}

		history = new LinkedList<>(history.subList(0, position + 1));
		Log.d(TAG, "after cutFrom history size=" + history.size());
		notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		int ret = history.indexOf(object);
		Log.d(TAG, "getItemPosition ret=" + ret);
		// We can not return -1 directly because it means POSITION_UNCHANGED for PagerAdapter
		return ret == -1 ? POSITION_NONE : ret;
	}

	@Override
	public int getCount() {
		int ret = history.size();
		Log.d(TAG, "getCount ret=" + ret);
		return ret;
	}

	@Override
	public boolean isViewFromObject(View v, Object o) {
		WebPageItem item = (WebPageItem) o;
		return item.getPageView() == v;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object instantiateItem(View container, int position) {
		Log.d(TAG, "1-instantiateItem");
		return initItem((ViewGroup) container, position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Log.d(TAG, "2-instantiateItem");
		return initItem(container, position);
	}

	public Object initItem(ViewGroup container, int position) {
		Log.d(TAG,
			"initItem position=" + position + " page size=" + container.getChildCount());
		WebPageItem d = getItem(position);
		d.attach(container);
		return d;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		Log.d(TAG, "destroyItem() called with: container = [" + container + "], position = [" + position + "], object = [" + object + "]");
		WebPageItem item = (WebPageItem) object;
		item.detach(container);
	}
}
