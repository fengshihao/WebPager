package com.fengshihao.webpagerExample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.fengshihao.webpager.WebPager;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		LinearLayout f = (LinearLayout)findViewById(R.id.container);
		WebPager wp = new WebPager(this);
		f.addView(wp);
		wp.loadUrl("http://www.baidu.com");
	}
}
