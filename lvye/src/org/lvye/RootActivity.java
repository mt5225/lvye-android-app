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

/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
package org.lvye;

import org.lvye.NavigationView;
import android.app.ActivityGroup;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;


/**
 * 
 * @author jiangsi@lvye
 *  A base class for all Activities that want to display the default layout,
 *         including the PlaylistView.
 */
public abstract class RootActivity extends ActivityGroup implements
OnClickListener {
	  private static final String LOG_TAG = RootActivity.class.getName();
	  private NavigationView navigationView;
	  private ProgressBar progressIndicator;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate R.layout.main");
	    //set main view
	    setContentView(R.layout.main);

	    Log.d(LOG_TAG, "create the navigation view");
	    navigationView = new NavigationView(this);
	    ((ViewGroup) findViewById(R.id.TitleContent)).addView(navigationView,
	        new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
	            LayoutParams.FILL_PARENT));
	    
	    //hide the navigation bar
	    navigationView.setVisibility(View.GONE);   
	    
	    Log.d(LOG_TAG, "create the progress indicator");
	    progressIndicator =
	            (ProgressBar) findViewById(R.id.WindowProgressIndicator);
	  }
	  
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	      case R.id.MainSearchButton:
	        //startActivityWithoutAnimation(new Intent(this, SearchActivity.class));
	        break;
	    }
	  }
	  
	  protected void startIndeterminateProgressIndicator() {
		    progressIndicator.setVisibility(View.VISIBLE);
		  }

		  protected void stopIndeterminateProgressIndicator() {
		    progressIndicator.setVisibility(View.INVISIBLE);
		  }
}
