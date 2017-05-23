package com.fengshihao.webpager;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebHistoryItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shihao on 17-5-22.
 */

final class WebPagerAdapter extends PagerAdapter {
	private static final String TAG = "WebPagerAdapter";

	private List<WebPageItem> history = new LinkedList<>();

	void addItem(String url) {
		Log.d(TAG, "addItem() called with: url = [" + url + "]");
		if (!URLUtil.isValidUrl(url)) {
			Log.w(TAG, "addItem try to add one invalid url=" + url);
			return;
		}
		WebPageItem item = new WebPageItem(url);
		item.setActive(true);
		history.add(item);
		notifyDataSetChanged();
	}

	WebPageItem getItem(int position) {
		if (position < 0 || position >= history.size()) {
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
		if (position < 0 || position >= history.size() - 1) {
			return;
		}
		Log.d(TAG, "cutFrom() called with: position = [" + position + "]");

		history = new LinkedList<>(history.subList(0, position + 1));
		Log.d(TAG, "after cutFrom history size=" + history.size());
		notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		int ret = history.indexOf(object);
		Log.d(TAG, "getItemPosition ret=" + ret);
		return ret;
	}

	@Override
	public int getCount() {
		return history.size();
	}

	@Override
	public boolean isViewFromObject(View v, Object o) {
		WebPageItem item = (WebPageItem) o;
		return item.getPageView() == v;
	}

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
		container.addView(d.getPageView());
		return d;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		Log.d(TAG, "destroyItem() called with: container = [" + container + "], position = [" + position + "], object = [" + object + "]");
		WebPageItem item = (WebPageItem) object;
		item.destroyPageView();
	}
}
