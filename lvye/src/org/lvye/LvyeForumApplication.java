// Copyright 2011 LVYE.ORG
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

import org.lvye.api.ForumList;

import android.app.Application;
import android.util.Log;

/**
 * Handles application startup code for any activity
 * @author 姜丝@lvye
 */
public class LvyeForumApplication extends Application {
  private static final String LOG_TAG = LvyeForumApplication.class.getName();
  

  @Override
  public void onCreate() {
	Log.d(LOG_TAG, "application startted");
    super.onCreate();
    //init the forum list
    new ForumList();
  }
}
