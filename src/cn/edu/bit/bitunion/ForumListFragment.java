package cn.edu.bit.bitunion;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
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
import cn.edu.bit.bitunion.entities.Forum;
import cn.edu.bit.bitunion.entities.LoginInfo;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.global.RequestJsonFactory;
import cn.edu.bit.bitunion.global.ResponseParser;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.LogUtils;
import cn.edu.bit.bitunion.tools.ToastHelper;
import cn.edu.bit.bitunion.widgets.ForumExpandableListAdapter;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class ForumListFragment extends Fragment {
	private static final String TAG = "ForumListFragment";
	List<Forum> forumlist = new ArrayList<Forum>();
	BaseExpandableListAdapter adapter;
	ExpandableListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_forum_list, null);
		listview = (ExpandableListView) view.findViewById(R.id.listview);
		adapter = new ForumExpandableListAdapter(getActivity(), forumlist);
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
				Intent intent = new Intent(getActivity(), ForumActivity.class);
				intent.putExtra("forum", forumlist.get(groupPosition).getSubForumList().get(childPosition));
				startActivity(intent);
				return true;
			}
		});

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
							forumlist.addAll(ResponseParser.parseForumList(response));
							adapter.notifyDataSetChanged();
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
}
