package com.example.microlearning.learning;

import java.util.HashMap;

import com.example.microlearning.R;
import com.example.microlearning.base.AppConstants;
import com.example.microlearning.base.BaseFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class LearnFragment extends BaseFragment {
	
	private TabHost mTabHost;
	private HashMap<String, Fragment> mFragments;
	private View rootView;
	
//	private Fragment mLearnAFragment;
//	private Fragment mLearnBFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_tabs_top,
				container, false);
		
		mFragments = new HashMap<String, Fragment>();
		
		mTabHost = (TabHost) rootView.findViewById(R.id.tabhost);
		mTabHost.setOnTabChangedListener(listener);
		mTabHost.setup();
		
		initializeTabs();
		
		return rootView;
	}
	
	private View createTabView(final int id) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.tabs_text, null);
		TextView textView = (TextView) view.findViewById(R.id.tab_text);
		textView.setText(id);
		return view;
	}
	
	private void initializeTabs() {
		TabHost.TabContentFactory mTabContent = new TabHost.TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				return rootView.findViewById(R.id.realtabcontent_tabs_top);
			}
		};
		TabHost.TabSpec spec = mTabHost.newTabSpec(AppConstants.TAB_LEARN_A);
		mTabHost.setCurrentTab(-3);
		spec.setContent(mTabContent);
		spec.setIndicator(createTabView(R.string.tab_learn_a));
		mTabHost.addTab(spec);
		
		spec = mTabHost.newTabSpec(AppConstants.TAB_LEARN_B);
        spec.setContent(mTabContent);
        spec.setIndicator(createTabView(R.string.tab_learn_b));
        mTabHost.addTab(spec);
        
        spec = mTabHost.newTabSpec(AppConstants.TAB_LEARN_C);
        spec.setContent(mTabContent);
        spec.setIndicator(createTabView(R.string.tab_learn_c));
        mTabHost.addTab(spec);
	}
	
	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
		
		@Override
		public void onTabChanged(String tabId) {
			
			if (mFragments.get(tabId) == null) {
				if (tabId.equals(AppConstants.TAB_LEARN_A)) {
					mFragments.put(AppConstants.TAB_LEARN_A, new LearnAFragment());
				} else if (tabId.equals(AppConstants.TAB_LEARN_B)) {
					mFragments.put(AppConstants.TAB_LEARN_B, new LearnBFragment());
				} else if (tabId.equals(AppConstants.TAB_LEARN_C)) {
					mFragments.put(AppConstants.TAB_LEARN_C, new LearnCFragment());
				} else return;
			}
			replaceFragment(mFragments.get(tabId));
			
		}
	};
	
	public void replaceFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.realtabcontent_tabs_top, fragment);
		ft.commit();
	}
}
