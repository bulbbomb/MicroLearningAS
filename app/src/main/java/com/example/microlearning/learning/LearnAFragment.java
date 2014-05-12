package com.example.microlearning.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.microlearning.R;
import com.example.microlearning.R.layout;
import com.example.microlearning.base.BaseFragment;
import com.example.microlearning.proxy.CourseRequest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LearnAFragment extends BaseFragment {

	private View rootView;
	private ListView mListView;
	SimpleAdapter adapter;
	
	public LearnAFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_only_list,
				container, false);
		
		mListView = (ListView) rootView.findViewById(R.id.listview_fragment_only_list);
		adapter = new SimpleAdapter(mActivity, getData(), R.layout.listview_learn_a, new String[]{}, new int[]{});
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (adapter.getItem(position) == null) return;
				startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
			}
		});



        new AsyncTask<Integer, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Integer[] objects) {
                return CourseRequest.getCourseInfo("");
            }

            @Override
            protected void onPostExecute(Boolean result) {

            }
        };

        CourseRequest.getCourseInfo("517e1608c3069b1ca44b1c6d");

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
