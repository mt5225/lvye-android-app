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

import java.util.ArrayList;
import java.util.List;

import org.lvye.api.Story;
import org.lvye.api.Client;
import org.lvye.api.Story.StoryFactory;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class NewsListAdapter extends ArrayAdapter<Story> {
	private static final String LOG_TAG = NewsListAdapter.class.getName();
	private final LayoutInflater inflater;
	private RootActivity rootActivity = null;
	private List<Story> moreStories = null;

	public NewsListAdapter(Context context) {
		super(context, R.layout.news_item);
		if (context instanceof RootActivity) {
			rootActivity = (RootActivity) context;
		}
		inflater = LayoutInflater.from(getContext());

	}

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what >= 0) {
				if (moreStories != null) {
					for (Story s : moreStories) {
							add(s);
					}
					add(new Story("END"));
					
				}
			} else {
				Toast.makeText(
						rootActivity,
						rootActivity.getResources().getText(
								R.string.msg_check_connection),
						Toast.LENGTH_LONG).show();
			}
			if (rootActivity != null) {
				rootActivity.stopIndeterminateProgressIndicator();
			}

		}
	};

	// send message to refresh UI after story loading
	public void addMoreStories(final String url) {
		if (rootActivity != null) {
			rootActivity.startIndeterminateProgressIndicator();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (getMoreStories(url)) {
					handler.sendEmptyMessage(0);
				} else {
					handler.sendEmptyMessage(-1);
				}
			}
		}).start();
	}

	//clear and load stories given a forum url
	private boolean getMoreStories(String url) {
		ArrayList<String> stories = null;
		try {
			Client client = new Client();
			stories = client.execute(url);
		} catch (Exception e) {
			Log.e(LOG_TAG, "", e);
			return false;
		}

		if (stories == null) {
			Log.d(LOG_TAG, "stories: none");
		} else {
			Log.d(LOG_TAG, "number of stories: " + stories.size());
			moreStories = StoryFactory.parseStories(stories);
		}
		return true;
	}

	// built the story list view
	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.news_item, parent, false);
		}

		Story story = getItem(position);

		Log.d(LOG_TAG, "current position =" + position + story.toString());
		TextView teaser = (TextView) convertView
				.findViewById(R.id.NewsItemTeaserText);
		TextView name = (TextView) convertView
				.findViewById(R.id.NewsItemNameText);
		if (!story.getTitle().equals("END")) {
			name.setText(Html.fromHtml(story.toString()));
			name.setTypeface(name.getTypeface(), Typeface.BOLD);
			String teaserText = story.getTeaser();
			teaser.setText(Html.fromHtml(teaserText));
			teaser.setVisibility(View.VISIBLE);
		} else {
			// title='END' marker means it's the end of the list.
			teaser.setVisibility(View.INVISIBLE);
			name.setTypeface(name.getTypeface(), Typeface.ITALIC);
			name.setText(R.string.msg_load_more);
		}
		return convertView;
	}
}
