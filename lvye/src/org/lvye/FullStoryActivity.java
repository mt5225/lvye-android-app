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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * 
 * @author 姜丝@lvye.org
 *
 */
public class FullStoryActivity extends Activity {
	public static final String LOG_TAG = FullStoryActivity.class.getName();
	private String storyID ="";
	private String currentForumID="";
	private String title="";
	
	@Override 
    public void onCreate(Bundle savedInstanceState) { 
		Log.d(LOG_TAG, "on Create");
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.story);
        
        //get story info from intent message      
        storyID = getIntent().getStringExtra(Constants.STORY_ID);
        currentForumID = getIntent().getStringExtra(Constants.FORUM_ID);
        title = getIntent().getStringExtra(Constants.STORY_TITLE);
        
        //set title
        TextView titleView = (TextView)findViewById(R.id.StoryTitle);
        titleView.setText(title);
        
        //load the story page
        WebView webview = (WebView)findViewById(R.id.webView1);
        //Enable JavaScript support 
        webview.getSettings().setJavaScriptEnabled(true); 
        
        //load the page content
        String url = ForumList.getStoryURLbyPostID(storyID);
        Log.d(LOG_TAG, url);
        webview.loadUrl(url); 
    } 

	// Show the Forum Selector
		public void onBackToTopicList(View v) {
			Log.d(LOG_TAG, "back to topic list");
			Intent intent = new Intent(this, LvyeActivity.class);
			intent.putExtra(Constants.FORUM_ID, currentForumID);
			startActivity(intent);
		}
		
    //Share the story
		public void onShare(View v) {
			Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
            String msg = "绿野ORG分享: " + title; 
            msg = msg + "  " + ForumList.getStoryURLbyPostID(storyID);
            shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent,
                getString(R.string.msg_share_story)));
		}
}
