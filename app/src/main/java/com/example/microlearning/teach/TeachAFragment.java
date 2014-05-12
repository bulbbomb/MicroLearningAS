package com.example.microlearning.teach;

import com.example.microlearning.R;
import com.example.microlearning.R.layout;
import com.example.microlearning.base.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TeachAFragment extends BaseFragment {

	public TeachAFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_only_list, container, false);
	}

}
