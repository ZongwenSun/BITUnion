package cn.edu.bit.bitunion;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.bit.bitunion.entities.Forum;
import cn.edu.bit.bitunion.entities.LoginInfo;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.global.RequestJsonFactory;
import cn.edu.bit.bitunion.global.ResponseParser;
import cn.edu.bit.bitunion.network.RequestQueueManager;
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
			GroupData.add(groupname);
			List<Forum> forums = forumlist.get(i).getSubForumList();
			List<String> childdata = new ArrayList<String>();
			for (int j = 0; j < forums.size(); j++) {
				String mainname = "";
				mainname = URLDecoder.decode(forums.get(j).getName(), "UTF-8");
				childdata.add(mainname);
			}
			ChildrenData.add(childdata);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				ToastHelper.showToast(getActivity(), "Äãµã»÷ÁË" + adapter.getChild(groupPosition, childPosition));
				return false;
			}
		});

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GroupData = new ArrayList<String>();
		ChildrenData = new ArrayList<List<String>>();

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getForumList();
	}

	private void getForumList() {

		LoginInfo loginInfo = ((BaseActivity) getActivity()).getAppContext().getLoginInfo();
		((BaseActivity) getActivity()).showLoadingDialog();
		RequestQueueManager.getInstance(getActivity()).postJsonRequest(GlobalUrls.getForumListUrl(),
				RequestJsonFactory.forumListJson(loginInfo.getUsername(), loginInfo.getSession()), new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						((BaseActivity) getActivity()).hideLoadingDialog();
						if (ResponseParser.isSuccess(response)) {
							LogUtils.log(TAG, response.toString());
							forumlist = ResponseParser.parseForumList(response);
							try {
								LoadListData();
								adapter.notifyDataSetChanged();
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						((BaseActivity) getActivity()).hideLoadingDialog();
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
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			GroupHolder holder;
			if (view == null) {
				view = LayoutInflater.from(getActivity()).inflate(R.layout.forumlistview_group, null);
				holder = new GroupHolder();
				holder.grouptext = (TextView) view.findViewById(R.id.listgroup);
				Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/xingkai.ttf");
				holder.grouptext.setTypeface(typeface);
				view.setTag(holder);
			} else {
				holder = (GroupHolder) view.getTag();
			}
			holder.grouptext.setText(GroupData.get(groupPosition));
			return view;

		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			ChildHolder cholder;
			if (view == null) {
				view = LayoutInflater.from(getActivity()).inflate(R.layout.forumlistview_children, null);
				cholder = new ChildHolder();
				cholder.imageView = (ImageView) view.findViewById(R.id.img);
				cholder.childtext = (TextView) view.findViewById(R.id.listgroup);
				Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/xingkai.ttf");
				cholder.childtext.setTypeface(typeface);
				view.setTag(cholder);
			} else {
				cholder = (ChildHolder) view.getTag();
			}
			// cholder.imageView.setBackgroundResource(R.drawable.ic_launcher);
			cholder.childtext.setText(ChildrenData.get(groupPosition).get(childPosition));
			return view;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	static class GroupHolder {
		TextView grouptext;
	}

	static class ChildHolder {
		ImageView imageView;
		TextView childtext;
	}
}
