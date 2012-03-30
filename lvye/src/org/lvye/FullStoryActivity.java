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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
public class FullStoryActivity extends Activity {
	public static final String LOG_TAG = FullStoryActivity.class.getName();
	private String storyID = "";
	private String title = "";
	private String url = "";
	private Boolean has_image = false;
	final Activity activity = this;
	private ProgressBar mProgress;
	public int mProgressStatus = 0;
	private WebView webview;
	private ImageView mLogo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "on Create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story);

		// get story info from intent message
		storyID = getIntent().getStringExtra(Constants.STORY_ID);
		title = getIntent().getStringExtra(Constants.STORY_TITLE);
		mProgress = (ProgressBar) findViewById(R.id.progressBar1);
		has_image = getIntent().getBooleanExtra(Constants.HAS_IMAGE,false);

		// load the story page
		webview = (WebView) findViewById(R.id.webView1);
		webview.getSettings().setJavaScriptEnabled(false);
		webview.getSettings().setUseWideViewPort(true);
		if (has_image) {
			webview.getSettings().setLoadWithOverviewMode(true);	
		}
		webview.getSettings().setUseWideViewPort(true);
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				Log.d(LOG_TAG, "progress = " + progress);
				if (progress < 100) {
					mProgress.setProgress(progress);
				} else {
					mProgress.setVisibility(View.GONE);
				}
			}
		});
		// load the page content
		url = ForumList.getStoryURLbyPostID(storyID);
		Log.d(LOG_TAG, url);
		webview.loadUrl(url);

		// refresh content while user click logo
		mLogo = (ImageView) findViewById(R.id.imageView1);
		mLogo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mProgress.setVisibility(View.VISIBLE);
				webview.loadUrl(url);
			}
		});
	}

	// Share the story
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
