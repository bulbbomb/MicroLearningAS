package com.example.microlearning.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.microlearning.R;
import com.example.microlearning.R.string;
import com.example.microlearning.base.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class PersonalInfoCourseBFragment extends BaseFragment {

	private View rootView;
	private ListView mListView;
	private SimpleAdapter adapter;
	
	public PersonalInfoCourseBFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_only_list,
				container, false);
		
		mListView = (ListView) rootView.findViewById(R.id.listview_fragment_only_list);
		adapter = new SimpleAdapter(mActivity, getData(),
				R.layout.listview_learn_c, new String[]{},
				new int[]{});
		mListView.setAdapter(adapter);
		
		return rootView;
	}

	public List<HashMap<String, Object>> getData() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "");
		map.put("talent", "");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "");
		map.put("talent", "");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "");
		map.put("talent", "");
		list.add(map);
		
		return list;
	}
}
