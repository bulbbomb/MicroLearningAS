package com.example.microlearning.base;

import java.util.HashMap;
import java.util.Stack;

import com.example.microlearning.R;
import com.example.microlearning.learning.LearnFragment;
import com.example.microlearning.option.OptionFragment;
import com.example.microlearning.option.SettingsActivity;
import com.example.microlearning.teach.NewCourseActivity;
import com.example.microlearning.teach.TeachFragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int ITEM_ID_LEARN = 1;
	private static final int ITEM_ID_TEACH = 2;
	private static final int ITEM_ID_OPTION = 3;
	
	private TabHost mTabHost;

	/* A HashMap of Lists, where using tab identifier as keys.. */
	private HashMap<String, Stack<Fragment>> mStacks;
	
    /* Save current tabs identifier in this.. */
	private String mCurrentTab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mStacks = new HashMap<String, Stack<Fragment>>();
		mStacks.put(AppConstants.TAB_LEARN, new Stack<Fragment>());
		mStacks.put(AppConstants.TAB_TEACH, new Stack<Fragment>());
		mStacks.put(AppConstants.TAB_OPTION, new Stack<Fragment>());
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(listener);
		mTabHost.setup();
		
		initializeTabs();

	}
	
	private View createTabView(final int imageId, final int textId) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
		imageView.setImageDrawable(getResources().getDrawable(imageId));
		TextView textView = (TextView) view.findViewById(R.id.tab_main_text);
		textView.setText(textId);
		return view;
	}
	
	private void initializeTabs() {

		TabHost.TabContentFactory mTabContent = new TabHost.TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		};
		
		TabHost.TabSpec spec = mTabHost.newTabSpec(AppConstants.TAB_LEARN);
		mTabHost.setCurrentTab(-3); // Why is it -3?
		spec.setContent(mTabContent);
		spec.setIndicator(createTabView(R.drawable.tab_learn_selector, R.string.tab_learn));
		mTabHost.addTab(spec);
		
		spec = mTabHost.newTabSpec(AppConstants.TAB_TEACH);
        spec.setContent(mTabContent);
        spec.setIndicator(createTabView(R.drawable.tab_teach_selector, R.string.tab_teach));
        mTabHost.addTab(spec);
        
        spec = mTabHost.newTabSpec(AppConstants.TAB_OPTION);
        spec.setContent(mTabContent);
        spec.setIndicator(createTabView(R.drawable.tab_option_selector, R.string.tab_option));
        mTabHost.addTab(spec);
    }
	
    /*Comes here when user switch tab, or we do programmatically*/
    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
    	public void onTabChanged(String tabId) {
    		/*Set current tab..*/
    		mCurrentTab = tabId;

    		if(mStacks.get(tabId).size() == 0){
    			/*
    			 *    First time this tab is selected. So add first fragment of that tab.
    			 *    Dont need animation, so that argument is false.
    			 *    We are adding a new fragment which is not present in stack. So add to stack is true.
    			 */
    			if (tabId.equals(AppConstants.TAB_LEARN)) {
    				pushFragments(tabId, new LearnFragment(), false,true);
    			} else if (tabId.equals(AppConstants.TAB_TEACH)) {
    				pushFragments(tabId, new TeachFragment(), false,true);
    			} else if(tabId.equals(AppConstants.TAB_OPTION)) {
    				pushFragments(tabId, new OptionFragment(), false, true);
    			}
    		}else {
    			/*
    			 *    We are switching tabs, and target tab is already has atleast one fragment. 
    			 *    No need of animation, no need of stack pushing. Just show the target fragment
    			 */
    			pushFragments(tabId, mStacks.get(tabId).lastElement(), false,false);
    		}
    		
    		changeMenuItems();
    	}
	};

    /* Might be useful if we want to switch tab programmatically, from inside any of the fragment.*/
    public void setCurrentTab(int val){
          mTabHost.setCurrentTab(val);
    }
	
    /** 
     *      To add fragment to a tab. 
     *  tag             ->  Tab identifier
     *  fragment        ->  Fragment to show, in tab identified by tag
     *  shouldAnimate   ->  should animate transaction. false when we switch tabs, or adding first fragment to a tab
     *                      true when when we are pushing more fragment into navigation stack. 
     *  shouldAdd       ->  Should add to fragment navigation stack (mStacks.get(tag)). false when we are switching tabs (except for the first time)
     *                      true in all other cases.
     */
    public void pushFragments(String tag, Fragment fragment,boolean shouldAnimate, boolean shouldAdd){
      if(shouldAdd)
          mStacks.get(tag).push(fragment);
      FragmentManager   manager         =   getFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      if(shouldAnimate)
          ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
      ft.replace(R.id.realtabcontent, fragment);
      ft.commit();
    }


    public void popFragments(){
      /*    
       *    Select the second last fragment in current tab's stack.. 
       *    which will be shown after the fragment transaction given below 
       */
      Fragment fragment             =   mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

      /*pop current fragment from stack.. */
      mStacks.get(mCurrentTab).pop();

      /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
      FragmentManager   manager         =   getFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
      ft.replace(R.id.realtabcontent, fragment);
      ft.commit();
    }   


    @Override
    public void onBackPressed() {
       	if(((BaseFragment)mStacks.get(mCurrentTab).lastElement()).onBackPressed() == false){
       		/*
       		 * top fragment in current tab doesn't handles back press, we can do our thing, which is
       		 * 
       		 * if current tab has only one fragment in stack, ie first fragment is showing for this tab.
       		 *        finish the activity
       		 * else
       		 *        pop to previous fragment in stack for the same tab
       		 * 
       		 */
       		if(mStacks.get(mCurrentTab).size() == 1){
       			super.onBackPressed();  // or call finish..
       		}else{
       			popFragments();
       		}
       	}else{
       		//do nothing.. fragment already handled back button press.
       	}
    }
    
    /*
     *  Imagine if you wanted to get an image selected using ImagePicker intent to the fragment.
     *  Of course I could have created a public function in that fragment,
     *  and called it from the activity. But couldn't resist myself.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mStacks.get(mCurrentTab).size() == 0){
            return;
        }

        /*Now current fragment on screen gets onActivityResult callback..*/
        mStacks.get(mCurrentTab).lastElement().onActivityResult(requestCode, resultCode, data);
    }
    
	@Override
 	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void changeMenuItems() {
		getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if (mCurrentTab.equals(AppConstants.TAB_LEARN)) {
			MenuItem menuItem = menu.add(0, ITEM_ID_LEARN, 0, "search");
			menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else if (mCurrentTab.equals(AppConstants.TAB_TEACH)) {
			MenuItem menuItem = menu.add(0, ITEM_ID_TEACH, 0, "new");
			menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else if (mCurrentTab.equals(AppConstants.TAB_OPTION)) {
			MenuItem menuItem = menu.add(0, ITEM_ID_OPTION, 0, "option");
			menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == ITEM_ID_LEARN) {
			return true;
		} else if (id == ITEM_ID_TEACH) {
			startActivity(new Intent(MainActivity.this, NewCourseActivity.class));
			return true;
		} else if (id == ITEM_ID_OPTION) {
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
