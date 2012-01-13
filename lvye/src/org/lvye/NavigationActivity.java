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

public class NavigationActivity extends Activity {
	private static final String LOG_TAG = NavigationActivity.class.getName();

	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "create forum list view");
		super.onCreate(savedInstanceState);
		ListView list = new ListView(this);

		setContentView(list);
		ForumListAdapter adapter = new ForumListAdapter(this,
				R.layout.navigation, R.id.line1,
				(new ForumList()).getAllForum());
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Forum forum = (Forum) parent.getAdapter().getItem(position);
				Log.d(LOG_TAG, "select forum " + forum.getName());
				String forumID = ForumList.getForumIDByName(forum.getName());
				Intent intent = new Intent(parent.getContext(),
						LvyeActivity.class).putExtra(Constants.FORUM_ID, forumID);
				startActivity(intent);
			}
		});
	}

	private class ForumListAdapter extends ArrayAdapter<Forum> {

		public ForumListAdapter(Context context, int layout, int resId,
				ArrayList<Forum> items) {
			// Call through to ArrayAdapter implementation
			super(context, layout, resId, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			// Inflate a new row if one isn’t recycled
			if (row == null) {
				row = getLayoutInflater().inflate(R.layout.navigation, parent,
						false);
			}

			Forum forum = getItem(position);
			Log.d(LOG_TAG, "disploy forum name:" + forum.getName());
			TextView text = (TextView) row.findViewById(R.id.line1);
			text.setText(forum.getName());
			return row;
		}
	}

}
