// Copyright 2009 Google Inc.
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

package org.lvye.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

public class HttpHelper {

	/**
	 * A helper function to grab content from a URL.
	 * 
	 * @param url
	 *            URL of the item to download
	 * 
	 * @return an input stream to the content. The caller is responsible for
	 *         closing the stream. Content will be null in the case of errors.
	 * @throws java.io.IOException
	 *             if an error occurs loading the url
	 */

	public static final String LOG_TAG = HttpHelper.class.getName();

	public static InputStream download(String url) throws IOException {

		InputStream data = null;
		Log.d(LOG_TAG, "Starting download: " + url);
		HttpClient http = new DefaultHttpClient();
		HttpGet method = new HttpGet(url);

		try {
			HttpResponse response = http.execute(method);
			data = response.getEntity().getContent();
		} catch (IllegalStateException e) {
			Log.e(LOG_TAG, "error downloading", e);
		}
		Log.d(LOG_TAG, "Download complete");
		return data;
	}
}
