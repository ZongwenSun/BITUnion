package cn.edu.bit.szw.bitunion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.szw.bitunion.base.BaseFragment;
import cn.edu.bit.szw.bitunion.entities.Forum;
import cn.edu.bit.szw.bitunion.entities.LoginInfo;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.RequestQueueManager;
import cn.edu.bit.szw.bitunion.widgets.BaseUIController;
import cn.edu.bit.szw.bitunion.widgets.ForumExpandableListAdapter;
import cn.edu.bit.szw.bitunion.widgets.LoadingView;


public class ForumListFragment extends BaseFragment {
	private static final String TAG = "ForumListFragment";
	List<Forum> forumlist = new ArrayList<Forum>();
	BaseExpandableListAdapter adapter;
	ExpandableListView listview;
    LoadingView mLoading;
    BaseUIController mUIController;

	@Override
	protected View createContentView(LayoutInflater inflater, Bundle savedInstanceState) {
        mTitleBar.setVisibility(View.GONE);
		View view = inflater.inflate(R.layout.fragment_forum_list, null);
        mLoading = (LoadingView) view.findViewById(R.id.loading);
        mUIController = new BaseUIController(this, mLoading) {
            @Override
            protected void onRefreshUI(JSONObject response) {
                forumlist.clear();
                forumlist.addAll(ResponseParser.parseForumList(response));
                adapter.notifyDataSetChanged();
            }
        };
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
                intent.putExtra("forum", forumlist.get(groupPosition).subForumList.get(childPosition));
                startActivity(intent);
                return true;
            }
        });

		return view;
	}

	@Override
	public void reload(int type) {
        LoginInfo loginInfo = MainApplication.instance.getLoginInfo();
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put("action", "forum");
        paramsMap.put("username", loginInfo.username);
        paramsMap.put("session", loginInfo.session);
        RequestQueueManager.getInstance().postJsonRequest(GlobalUrls.getForumListUrl(), paramsMap, mUIController);
    }


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		reload(0);
	}

}
