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

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Log;
/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
public class Client {
	private static final String LOG_TAG = Client.class.getName();

	public ArrayList<String> execute(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		Log.d(LOG_TAG, "executing request " + httpget.getURI());
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpclient.execute(httpget, responseHandler);
		String[] responseLine = responseBody.split("\n");
		// a array to hold http response string line
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<String> storyInfo =  new ArrayList<String>();
		for (String t : responseLine) {
			if (t.contains("viewtopic") ) {   //includes sticky topic  && !t.contains("stickytop")
				al.add(t);
			}
			if (t.contains("user.php") && !t.contains("版主")) {
				authors.add(t);
			}
		}
		//combine story and author 
		for(int i=0; i< al.size();i++) {
			String t1 = (String) al.get(i);
			String t2 = (String)authors.get(i);
			storyInfo.add(t1 + "|" + t2);
		}
		Log.d(LOG_TAG, "number of return topics: " + storyInfo.size());
		return storyInfo;
	}

}
