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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lvye.Constants;

import android.util.Log;


/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
public class ForumList {
  private static final String LOG_TAG = Client.class.getName();
  public static ArrayList<Forum> forums = null;
  
  public ForumList() {
	  forums = new ArrayList<Forum>();
	  forums.add(new Forum("12", "绿野风版", ForumList.getURLbyID("12")));
	  forums.add(new Forum("35", "绿野行版", ForumList.getURLbyID("35")));
  }
  
  public ArrayList<Forum> getAllForum() {	  
	  return forums;
  }
  
  /**
   * 绿野风版(12)	http://www.lvye.org/modules/lvyebb/viewforum.php?id=12	
   * 绿野行版(35)	http://www.lvye.org/modules/lvyebb/viewforum.php?id=35     
   * @param id
   * @return url to posts given by a forum
   */
  public static final String getURLbyID (String id) {	
	  String url = Constants.urlPrefix + id;
	  Log.d(LOG_TAG, "returen URL " + url);
	  return url;
  }
  //page index: &start=50 , &start=100, etc
  public static final String getURLbyID (String id, int page_index) {	
	  String url = Constants.urlPrefix + id + "&start=" + page_index * 50;
	  Log.d(LOG_TAG, "return forum URL " + url);
	  return url;
  }
  
  //generation story url by post_id
  // http://www.lvye.org/modules/lvyebb/viewtopic.php?view=1&post_id=44139166&mode=1
  public static final String getStoryURLbyPostID(String id) {
	  String url = Constants.postUrlPrefix + id;
	  Log.d(LOG_TAG, "return story URL " + url);
	  return url;
  }
  
  public static final String getForumIDByName(String name) {
	  for(Forum fr:forums) {
		  if (fr.getName().equals(name)) {
			  return fr.getId();
		  }
	  }
	  //always return sth.
	  return "12";
  }
  public static final String getForumNameByID(String id){
	  for(Forum fr:forums) {
		  if (fr.getId().equals(id)) {
			  return fr.getName();
		  }
	  }
	  //always return sth.
	  return "绿野风版";
  }

}
