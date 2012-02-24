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
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
public class Story {
	public static final String LOG_TAG = Story.class.getName();
	private String title = "";
	private String subtitle = "";
	private String post_id = "";
	private String lastModifiedDate = "";
	private String author = "";
	private String size = "";
	private boolean sticky = false;

	public Story(String title, String subtitle, String post_id,
			String lastModifiedDate, String author, String size) {
		this.title = title;
		this.subtitle = subtitle;
		this.setPost_id(post_id);
		this.lastModifiedDate = lastModifiedDate;
		this.author = author;
		this.size = size;
	}

	public Story(String title, String post_id, String size, String author) {
		this.title = title;
		this.setPost_id(post_id);
		this.size = size;
		this.author = author;
	}

	public Story(String title) {
		this.title = title;
	}

	public String getTeaser() {
		return "楼主:" + this.author + "     字节数:" + this.size;
	}
	

	@Override
	public String toString() {
		return title;
	}

	public String getTitle() {
		return title;
	}

	/**
	 * @return the post_id
	 */
	public String getPost_id() {
		return post_id;
	}

	/**
	 * @param post_id
	 *            the post_id to set
	 */
	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	/**
	 * @return the sticky
	 */
	public boolean isSticky() {
		return sticky;
	}

	/**
	 * @param sticky
	 *            the sticky to set
	 */
	public void setSticky(boolean sticky) {
		this.sticky = sticky;
	}

	/**
	 * 
	 * parse http response message and create story object
	 * 
	 */
	public static class StoryFactory {
		public static List<Story> parseStories(ArrayList<String> httpResp) {
			LinkedList<Story> result = new LinkedList<Story>();
			for (String t : httpResp) {
				String s1 = t
						.substring(t.indexOf("<a href"), t.indexOf("</a>"));
				String s2 = t.substring(t.indexOf("<small>"),
						t.indexOf("</small>"));
				String[] s = s1.split(">");
				String post_id = s[0].substring(s[0].indexOf("post_id=") + 8,
						s[0].length() - 1);
				String topic = s[1];
				String size = s2.substring(8, s2.length() - 1);

				String s3 = t.substring(t.indexOf("uname="), t.length());
				String author = s3.substring(s3.indexOf("'>") + 2,
						s3.indexOf("</a>"));
				Story story = new Story(topic, post_id, size, author);
				// if a sticky story
				if (t.contains("sticky")) {
					story.setSticky(true);
				} else {
					story.setSticky(false);
				}
				result.add(story);
			}
			return result;
		}

	}

}
