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

import org.lvye.api.ForumList;
import org.lvye.api.Story;
import org.lvye.util.DisplayUtils;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
	private String currentForumID = "";
	private String url = null;
	protected NewsListAdapter listAdapter;
	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "on Create");
		if (getIntent() == null) {
			Log.d(LOG_TAG, "set default intent");
			setDefaultIntent();
		}
		super.onCreate(savedInstanceState);
		Log.d(LOG_TAG, "after super.onCreate");

		// get url setting info from intent
		currentForumID = getIntent().getStringExtra(Constants.FORUM_ID);
		if (currentForumID == null) {
			currentForumID = Constants.DEFAULT_FORUM_ID;
		}
		url = ForumList.getURLbyID(currentForumID);
		Log.d(LOG_TAG, "forum url=" + url);

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

		Log.d(LOG_TAG, "Build R.layout.news UI");
		container = (ViewGroup) findViewById(R.id.Content);
		ViewGroup.inflate(this, R.layout.news, container);

		Log.d(LOG_TAG, "Building post listView");
		listView = (ListView) findViewById(R.id.ListView01);
		listView.setOnItemClickListener(this);
		listAdapter = new NewsListAdapter(this);
		listView.setAdapter(listAdapter);

		// load stories
		listAdapter.addMoreStories(url);
	}

	// When first starting up, load the 绿野风版
	private void setDefaultIntent() {
		String id = ForumList.getURLbyID(Constants.DEFAULT_FORUM_ID);
		Log.d(LOG_TAG, "set default forum url as " + url);
		Intent i = new Intent(this, LvyeActivity.class).putExtra(
				Constants.FORUM_ID, id);
		setIntent(i);
	}

	//Show full story
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Story story = (Story) parent.getAdapter().getItem(position);
		Log.d(LOG_TAG, "story item clicked at " + story.getPost_id() + "|" + story.getTitle());
		Intent intent = new Intent(this, FullStoryActivity.class);
		intent.putExtra(Constants.STORY_ID, story.getPost_id());
		intent.putExtra(Constants.STORY_TITLE, story.getTitle());
		intent.putExtra(Constants.FORUM_ID, currentForumID);
		startActivity(intent);

	}

	// Show the Forum Selector
	public void onSelectForum(View v) {
		Log.d(LOG_TAG, "forum selector button clicked");
		Intent intent = new Intent(this, NavigationActivity.class);
		startActivity(intent);
	}

}