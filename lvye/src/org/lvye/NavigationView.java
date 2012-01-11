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

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract.Constants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.lvye.util.DisplayUtils;
import org.lvye.api.ApiConstants;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author 姜丝@lvye.org
 * 
 */
public class NavigationView extends FrameLayout implements
  OnItemClickListener {

  private class SubActivity {
    private final Intent startIntent;
    private final String header;

    private SubActivity(Intent startIntent) {
      this.startIntent = startIntent;
      this.header = null;
    }

    private SubActivity(String header) {
      this.startIntent = null;
      this.header = header;
    }

    public boolean isHeader() {
      return header != null;
    }

    @Override
    public String toString() {
      if (startIntent != null)
        return header;
      else
        return header;
    }
  }

  private class NavigationListAdapter extends ArrayAdapter<SubActivity> {
    public NavigationListAdapter(Context context, SubActivity[] activities) {
      super(context, android.R.layout.simple_list_item_1,
        android.R.id.text1, activities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      convertView = super.getView(position, convertView, parent);
      SubActivity activity = this.getItem(position);
      if (activity != null && activity.isHeader()) {
        convertView.setEnabled(false);
        convertView.setBackgroundDrawable(getResources().getDrawable(
          R.drawable.news_list_title_background));
        convertView.getLayoutParams().height =
          DisplayUtils.convertToDIP(getContext(), 30);
        ((TextView) convertView.findViewById(android.R.id.text1))
          .setTextColor(getResources().getColor(R.color.news_title_text));
      } else {
        convertView.setEnabled(true);
        convertView.setBackgroundDrawable(null);
        convertView.getLayoutParams().height =
          DisplayUtils.convertToDIP(getContext(), 50);
        ((TextView) convertView.findViewById(android.R.id.text1))
          .setTextColor(getResources().getColor(R.color.black));
      }
      return convertView;
    }
  }

  public NavigationView(Context context) {
    super(context);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    init();
  }

  private void init() {

    ViewGroup.inflate(getContext(), R.layout.navigation, this);

    ListView listView = (ListView) findViewById(R.id.ActivityList);

    String grouping = null;
    String description = "Top Stories";
    String topicId = "1002";
    Map<String, String> params = new HashMap<String, String>();
    params.put("id", topicId);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position,
    long id) {
    SubActivity s = (SubActivity) parent.getItemAtPosition(position);
    if (!s.isHeader()) {
      this.setVisibility(View.GONE);
      getContext().startActivity(s.startIntent);
    }
  }
}
