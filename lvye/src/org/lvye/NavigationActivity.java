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

/**
 * @author 姜丝@lvye.org
 */
package org.lvye;

import java.util.ArrayList;

import org.lvye.api.Forum;
import org.lvye.api.ForumList;
import org.lvye.util.DisplayUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
public class NavigationActivity extends Activity implements OnItemClickListener {
	private static final String LOG_TAG = NavigationActivity.class.getName();
	private ListView listView;

	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "create forum list view");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);
		listView = (ListView) findViewById(R.id.ForumList);
		listView.setOnItemClickListener(this);
		listView.setAdapter(new ForumListAdapter(this, (new ForumList())
				.getAllForum()));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Forum forum = (Forum) parent.getAdapter().getItem(position);
		Log.d(LOG_TAG, "select forum " + forum.getName());
		String forumID = ForumList.getForumIDByName(forum.getName());
		if (forumID != "999") {
			if (forumID.equals(LvyeActivity.currentForumID)) { //donot need update, self-destroy
				this.finish();
			} else {
				Intent intent = new Intent(parent.getContext(),  //update the stories list by rebuilt
						LvyeActivity.class).putExtra(Constants.FORUM_ID,
						forumID);
				startActivity(intent);
			}
		}
	}

	private class ForumListAdapter extends ArrayAdapter<Forum> {

		public ForumListAdapter(Context context, ArrayList<Forum> items) {
			// Call through to ArrayAdapter implementation
			super(context, android.R.layout.simple_list_item_1,
					android.R.id.text1, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Inflate a new row if one isn’t recycled
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.forum_item,
						parent, false);
			}

			Forum forum = getItem(position);
			// Log.d(LOG_TAG, "disploy forum name:" + forum.getName());
			TextView textView = (TextView) convertView
					.findViewById(R.id.ForumTitle);

			if (forum.getId() == "999") {
				convertView.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.news_list_title_background));
				convertView.getLayoutParams().height = DisplayUtils
						.convertToDIP(getContext(), 40);

				textView.setTextColor(getResources().getColor(
						R.color.news_title_text));

			} else {
				convertView.setBackgroundDrawable(null);
				convertView.getLayoutParams().height = DisplayUtils
						.convertToDIP(getContext(), 50);
				textView.setTextColor(getResources().getColor(R.color.black));
			}
			textView.setText(forum.getName());
			return convertView;
		}
	}

}
