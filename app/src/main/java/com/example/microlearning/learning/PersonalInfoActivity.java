package com.example.microlearning.learning;

import java.util.HashMap;

import com.example.microlearning.R;
import com.example.microlearning.base.AppConstants;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class PersonalInfoActivity extends Activity {

	private TabHost mTabHost;
	private HashMap<String, Fragment> mFragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);

		mFragments = new HashMap<String, Fragment>();
		
		mTabHost = (TabHost) findViewById(R.id.tabhost_personal_info);
		mTabHost.setOnTabChangedListener(listener);
		mTabHost.setup();
		
		initializeTabs();
	}

	private View createTabView(final int id) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_text, null);
		TextView textView = (TextView) view.findViewById(R.id.tab_text);
		textView.setText(id);
		return view;
	}
	
	private void initializeTabs() {
		TabHost.TabContentFactory mTabContent = new TabHost.TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent_personal_info_course);
			}
		};
		
		TabHost.TabSpec spec = mTabHost.newTabSpec(AppConstants.TAB_COURSE_TYPE_A);
		mTabHost.setCurrentTab(-3);
		spec.setContent(mTabContent);
		spec.setIndicator(createTabView(R.string.tab_course_type_a));
		mTabHost.addTab(spec);
		
		spec = mTabHost.newTabSpec(AppConstants.TAB_COURSE_TYPE_B);
		spec.setContent(mTabContent);
		spec.setIndicator(createTabView(R.string.tab_course_type_b));
		mTabHost.addTab(spec);
	}
	
	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
		@Override
		public void onTabChanged(String tabId) {

			if (mFragments.get(tabId) == null) {
				if (tabId.equals(AppConstants.TAB_COURSE_TYPE_A)) {
					mFragments.put(AppConstants.TAB_COURSE_TYPE_A,
							new PersonalInfoCourseAFragment());
				} else if (tabId.equals(AppConstants.TAB_COURSE_TYPE_B)) {
					mFragments.put(AppConstants.TAB_COURSE_TYPE_B,
							new PersonalInfoCourseBFragment());
				} else return;
			}
			replaceFragment(mFragments.get(tabId));
		}
	};
	
	public void replaceFragment(Fragment fragment) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.realtabcontent_personal_info_course, fragment);
		ft.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
