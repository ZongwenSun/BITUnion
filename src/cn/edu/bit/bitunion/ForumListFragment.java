package cn.edu.bit.bitunion;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.edu.bit.bitunion.entities.Forum;
import cn.edu.bit.bitunion.entities.LoginInfo;
import cn.edu.bit.bitunion.entities.RequestJsonFactory;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.ForumListParser;
import cn.edu.bit.bitunion.tools.LogUtils;
import cn.edu.bit.bitunion.tools.ToastHelper;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class ForumListFragment extends Fragment {
	private static final String TAG = "ForumListFragment";
	List<Forum> forumlist = new ArrayList<Forum>();
	BaseExpandableListAdapter adapter;
	ExpandableListView listview;
	private List<String> GroupData;
	private List<List<String>> ChildrenData;

	public ForumListFragment() {
		// TODO Auto-generated constructor stub
	}

	public void LoadListData() throws UnsupportedEncodingException {

		for (int i = 0; i < forumlist.size(); i++) {
			String groupname = "";
			groupname = URLDecoder.decode(forumlist.get(i).getName(), "UTF-8");

			Log.e("HSC", groupname);
			GroupData.add(groupname);
			List<Forum> forums = forumlist.get(i).getSubForumList();
			List<String> childdata = new ArrayList<String>();
			;
			for (int j = 0; j < forums.size(); j++) {
				String mainname = "";
				mainname = URLDecoder.decode(forums.get(j).getName(), "UTF-8");
				childdata.add(mainname);
			}
			ChildrenData.add(childdata);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_forum_list, null);

		listview = (ExpandableListView) view.findViewById(R.id.listview);
		adapter = new MyAdapter();
		listview.setAdapter(adapter);

		listview.setOnGroupExpandListener(new OnGroupExpandListener() {

			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				for (int i = 0; i < adapter.getGroupCount(); i++) {
					if (groupPosition != i) {
						listview.collapseGroup(i);
					}
				}

			}

		});

		listview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				ToastHelper.showToast(getActivity(),
						"������" + adapter.getChild(groupPosition, childPosition));
				return false;
			}
		});

		return view;
	}

	private ExpandableListView findViewById(int listview) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GroupData = new ArrayList<String>();
		ChildrenData = new ArrayList<List<String>>();
		getForumList();
	}

	private void getForumList() {
		LoginInfo loginInfo = ((BaseActivity) getActivity()).getAppContext()
				.getLoginInfo();
		((BaseActivity) getActivity()).showLoadingDialog();
		RequestQueueManager.getInstance(getActivity()).postJsonRequest(
				GlobalUrls.getForumListUrl(),
				RequestJsonFactory.forumListJson(loginInfo.getUsername(),
						loginInfo.getSession()), new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						String result;
						try {
							result = response.getString("result");
							if (result.equalsIgnoreCase("success")) {
								((BaseActivity) getActivity())
										.hideLoadingDialog();
								LogUtils.log(TAG, response.toString());
								forumlist = ForumListParser.parse(response);
								try {
									LoadListData();
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								adapter.notifyDataSetChanged();
								LogUtils.log(TAG, forumlist.size() + "");
								Log.e("HSC", forumlist.size() + "");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						ToastHelper.showToast(getActivity(), error.toString());
					}

				});
	}

	private class MyAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return GroupData.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return ChildrenData.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return GroupData.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return ChildrenData.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView myText = null;
			if (convertView != null) {
				myText = (TextView) convertView;
				myText.setText(GroupData.get(groupPosition));
			} else {
				myText = createView(GroupData.get(groupPosition));
			}
			return myText;

		}

		private TextView createView(String content) {
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 100);
			TextView myText = new TextView(getActivity());
			myText.setLayoutParams(layoutParams);
			myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			myText.setPadding(80, 0, 0, 0);
			myText.setTextSize(20);

			myText.setText(content);
			return myText;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout ll = new LinearLayout(getActivity());
			ll.setOrientation(0);
			ImageView childlogo = new ImageView(getActivity());
			childlogo.setImageResource(R.drawable.ic_childlist);
			ll.addView(childlogo);
			TextView textView = new TextView(getActivity());
			textView.setText(ChildrenData.get(groupPosition).get(childPosition));
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setPadding(50, 0, 0, 0);
			textView.setTextSize(20);
			ll.addView(textView);
			return ll;

		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

}
