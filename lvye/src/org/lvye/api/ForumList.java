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

package org.lvye.api;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;


/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
public class ForumList {
  private static final String LOG_TAG = Client.class.getName();
  private static final String urlPrefix = "http://www.lvye.org/modules/lvyebb/viewforum.php?mode=1&id=";	
  private static final Map<String, String> forums = new HashMap<String, String>(){
	  {
        put("12", "绿野风版");
        put("35", "绿野行版");
      }
  };
  
  public ForumList() {}
  
  public Map<String, String> getForumList() {	  
	  return forums;
  }
  
  /**
   * 绿野风版(12)	http://www.lvye.org/modules/lvyebb/viewforum.php?id=12	
   * 绿野行版(35)	http://www.lvye.org/modules/lvyebb/viewforum.php?id=35     
   * @param id
   * @return url to posts given by a forum
   */
  public static final String getURLbyID (String id) {	
	  String url = ForumList.urlPrefix + id;
	  Log.d(LOG_TAG, "returen URL " + url);
	  return ForumList.urlPrefix + id;
  }
  //page index: &start=50 , &start=100, etc
  public static final String getURLbyID (String id, int page_index) {	
	  String url = ForumList.urlPrefix + id + "&start=" + page_index * 50;
	  Log.d(LOG_TAG, "returen URL " + url);
	  return url;
  }
  
  public static final String getForumNamebyID (String id) {
	  Log.d(LOG_TAG, "returen Forum Name " + forums.get(id));
	  return forums.get(id);
  }

}

