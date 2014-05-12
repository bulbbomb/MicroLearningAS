package com.example.microlearning.learning;

import com.example.microlearning.R;
import com.example.microlearning.R.id;
import com.example.microlearning.R.layout;
import com.example.microlearning.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;
import android.os.Build;

public class CourseActivity extends Activity {

	VideoView mVideoView;
	ImageButton testButton;
	Button playButton, pauseButton, loadButton;
	
	int state = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);

		mVideoView = (VideoView) findViewById(R.id.videoview);
		testButton = (ImageButton) findViewById(R.id.button_full_screen);
		playButton = (Button) findViewById(R.id.button_play);
		pauseButton = (Button) findViewById(R.id.button_pause);
		loadButton = (Button) findViewById(R.id.button_load);
		
		testButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				playAndPauseVideo();
				
			}});
		
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				playVideo();
				
			}});
		
		loadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				loadVideo("/sdcard/Movies/BadApple.mp4");
				loadVideo("/mnt/sdcard/Movies/IF.mp4");
			}
			
		});
		
		pauseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pauseVideo();
				
			}});
	}

	public void loadVideo(String path) {
		mVideoView.setVideoPath(path);
		mVideoView.setMediaController(new MediaController(CourseActivity.this));
		mVideoView.requestFocus();
	}
	
	public void playVideo() {
//		if (state == 0)
//			loadVideo("/sdcard/Movies/BadApple.mp4");
		mVideoView.start();
		state = 1;
	}
	
	public void pauseVideo() {
		mVideoView.pause();
		state = 2;
	}
	
	public void playAndPauseVideo() {
		switch (state) {
//		case 0:
		case 1:
			pauseVideo();
			break;
		case 2:
			playVideo();
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.course_info, menu);
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

//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_course_info,
//					container, false);
//			return rootView;
//		}
//	}

}
