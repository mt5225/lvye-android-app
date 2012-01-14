// Copyright 2010 Google Inc.
// Copyright 2011 lvye.org
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.lvye;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * 
 * A base class for all Activities that want to display the
 *         default layout,
 * 
 */
public abstract class RootActivity extends ActivityGroup implements
		OnClickListener {
	private static final String LOG_TAG = RootActivity.class.getName();
	
	private ProgressBar progressIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(LOG_TAG, "onCreate R.layout.main");
		setContentView(R.layout.main);
		progressIndicator = (ProgressBar) findViewById(R.id.WindowProgressIndicator);
	}
	
	@Override
	public void onClick(View v) {
		
	}

	protected void startIndeterminateProgressIndicator() {
		progressIndicator.setVisibility(View.VISIBLE);
	}

	protected void stopIndeterminateProgressIndicator() {
		progressIndicator.setVisibility(View.INVISIBLE);
	}
}
