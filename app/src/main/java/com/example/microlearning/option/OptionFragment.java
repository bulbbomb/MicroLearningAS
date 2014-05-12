package com.example.microlearning.option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.microlearning.R;
import com.example.microlearning.R.layout;
import com.example.microlearning.base.BaseFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class OptionFragment extends BaseFragment {

	private View rootView;
	private ListView mListView;
	private TextView toLogin;
	SimpleAdapter adapter;
	
	public OptionFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_option, container, false);
		
		mListView = (ListView) rootView.findViewById(R.id.listview_option);
		adapter = new SimpleAdapter(mActivity, getData(), R.layout.listview_option, new String[]{}, new int[]{});
		mListView.setAdapter(adapter);
		
		toLogin = (TextView) rootView.findViewById(R.id.textview_login_please);
		toLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mActivity, LoginActivity.class));
			}
		});
				
		return rootView;
	}

	public List<HashMap<String, Object>> getData() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "");
		map.put("course", "");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "");
		map.put("course", "");
		list.add(map);
		
		return list;
	}
	
}
