// Copyright 2009 Google Inc.
// Copyright 2011 lvye.org
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.lvye;

import java.util.LinkedList;
import java.util.List;

import org.lvye.api.ForumList;
import org.lvye.api.Story;
import org.lvye.util.DisplayUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
public class LvyeActivity extends RootActivity implements OnItemClickListener {

	private static final String LOG_TAG = LvyeActivity.class.getName();
	public static String currentForumID = "";
	private String url = null;
	protected NewsListAdapter listAdapter;
	protected MenuDialog menuDialog;
	private ListView listView;
	public static LinkedList<Story> storyCache = new LinkedList<Story>();
	private ImageView mLogo;
	private final Activity activity = this;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "on Create currentForumID = " + currentForumID);
		if (getIntent() == null) {
			Log.d(LOG_TAG, "set default intent");
			setDefaultIntent();
		}
		super.onCreate(savedInstanceState);
		// get url setting info from intent

		if (currentForumID.isEmpty()) {
			currentForumID = Constants.DEFAULT_FORUM_ID;
		} else {
			currentForumID = getIntent().getStringExtra(Constants.FORUM_ID);
		}
		Log.d(LOG_TAG, "currentForumID =" + currentForumID);
		url = ForumList.getURLbyID(currentForumID);

		// Built the indicator bar under logo bar
		// TODO: move this to a layout?
		Log.d(LOG_TAG, "Building Title UI");
		ViewGroup container = (ViewGroup) findViewById(R.id.TitleContent);
		ViewGroup.inflate(this, R.layout.title, container);
		View titleBar = findViewById(R.id.TitleBar);
		TextView titleText = (TextView) findViewById(R.id.TitleText);
		titleText.setText(ForumList.getForumNameByID(currentForumID));
		titleBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.news_list_title_background));
		titleBar.getLayoutParams().height = DisplayUtils.convertToDIP(this, 20);
		titleText
				.setTextColor(getResources().getColor(R.color.news_title_text));

		// Built story list
		Log.d(LOG_TAG, "Build R.layout.news UI");
		container = (ViewGroup) findViewById(R.id.Content);
		ViewGroup.inflate(this, R.layout.news, container);
		Log.d(LOG_TAG, "Building post listView");
		listView = (ListView) findViewById(R.id.ListView01);
		listView.setOnItemClickListener(this);
		listAdapter = new NewsListAdapter(this);
		listView.setAdapter(listAdapter);

		// load stories
		listAdapter.addMoreStories(url, 0);

		// refresh while click on the logo
		mLogo = (ImageView) findViewById(R.id.Logo1);
		mLogo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				storyCache.clear();
				listAdapter.clear();
				listAdapter.notifyDataSetChanged();
				listAdapter.addMoreStories(url, 0);
			}
		});
	}

	// When first starting up, load the default forum
	private void setDefaultIntent() {
		String id = ForumList.getURLbyID(Constants.DEFAULT_FORUM_ID);
		Log.d(LOG_TAG, "set default forum url as " + url);
		Intent i = new Intent(this, LvyeActivity.class).putExtra(
				Constants.FORUM_ID, id);
		setIntent(i);
	}

	// Show full story / load more story
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Story story = (Story) parent.getAdapter().getItem(position);
		Log.d(LOG_TAG, "story item clicked at " + story.getPost_id() + " | "
				+ story.getTitle());
		if (story.getTitle().equals("END")) {
			Log.d(LOG_TAG, "load more stories ... ...");		
			listAdapter.addMoreStories(url, 2);	
			listAdapter.notifyDataSetChanged();			
		} else {
			Intent intent = new Intent(this, FullStoryActivity.class);
			intent.putExtra(Constants.STORY_ID, story.getPost_id());
			intent.putExtra(Constants.STORY_TITLE, story.getTitle());
			intent.putExtra(Constants.HAS_IMAGE, story.getHasImage());
			intent.putExtra(Constants.FORUM_ID, currentForumID);
			startActivity(intent);
		}

	}

	public ListView getListView() {
		return listView;
	}

	// Show the Forum Selector
	public void onSelectForum(View v) {
		Log.d(LOG_TAG, "forum selector button clicked");
		Intent intent = new Intent(this, NavigationActivity.class);
		startActivity(intent);
	}

	public static void addAllToStoryCache(List<Story> stories) {
		for (Story story : stories) {
			storyCache.add(story);
		}
	}

	public static void clearStoryCache() {
		storyCache.clear();
	}

	/**
	 * app exit
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(LOG_TAG, "keyCode = " + keyCode);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.d(LOG_TAG, "popup exit dialog");
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent intent = new Intent(
											Intent.ACTION_MAIN);
									intent.addCategory(Intent.CATEGORY_HOME);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(intent);
									// activity.finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
			return true;
		}
		// Pass other events along their way up the chain
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * menu dialog
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (menuDialog == null) {
				menuDialog = new MenuDialog(this);
			}
			menuDialog.getWindow().setGravity(Gravity.BOTTOM);
			menuDialog.show();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	private class MenuDialog extends AlertDialog {
		public MenuDialog(Context context) {
			super(context);
			setTitle("请选择");
			View menu = getLayoutInflater().inflate(R.layout.menu, null);
			setView(menu);
		}

		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_MENU) {
				dismiss();
				return true;
			}
			return super.onKeyUp(keyCode, event);
		}
	}
}