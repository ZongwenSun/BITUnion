package cn.edu.bit.bitunion;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.edu.bit.bitunion.entities.LoginInfo;
import cn.edu.bit.bitunion.entities.NewPost;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.global.RequestJsonFactory;
import cn.edu.bit.bitunion.global.ResponseParser;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.ToastHelper;
import cn.edu.bit.bitunion.widgets.NewPostsListAdapter;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class NewPostsFragment extends Fragment {
	private static final String TAG = "NewPostsFragment";
	private List<NewPost> mDataList;
	private PullToRefreshListView mListView;
	private NewPostsListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_new_posts, null);
		mListView = (PullToRefreshListView) view.findViewById(R.id.new_posts_listview);
		mAdapter = new NewPostsListAdapter(getActivity(), mDataList);
		mListView.setAdapter(mAdapter);
		mListView.setMode(Mode.PULL_FROM_START);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				fresh();
			}
		});
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mDataList = new ArrayList<NewPost>();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getNewPostsList();
	}

	private void getNewPostsList() {
		if (((BaseActivity) getActivity()).checkConnection()) {
			LoginInfo loginInfo = ((BaseActivity) getActivity()).getAppContext().getLoginInfo();
			((BaseActivity) getActivity()).showLoadingDialog();
			RequestQueueManager.getInstance(getActivity()).postJsonRequest(GlobalUrls.getNewPostsUrl(),
					RequestJsonFactory.newPostsJson(loginInfo.getUsername(), loginInfo.getSession()), new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							// TODO Auto-generated method stub
							((BaseActivity) getActivity()).hideLoadingDialog();
							if (ResponseParser.isSuccess(response)) {
								List<NewPost> newPostList = ResponseParser.parseNewPostList(response);
								mDataList.addAll(newPostList);
								mAdapter.notifyDataSetChanged();
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

	private void fresh() {
		if (((BaseActivity) getActivity()).checkConnection()) {
			LoginInfo loginInfo = ((BaseActivity) getActivity()).getAppContext().getLoginInfo();
			RequestQueueManager.getInstance(getActivity()).postJsonRequest(GlobalUrls.getNewPostsUrl(),
					RequestJsonFactory.newPostsJson(loginInfo.getUsername(), loginInfo.getSession()), new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							// TODO Auto-generated method stub
							if (ResponseParser.isSuccess(response)) {
								List<NewPost> newPostList = ResponseParser.parseNewPostList(response);
								mDataList.clear();
								mDataList.addAll(newPostList);
								mAdapter.notifyDataSetChanged();
							}
							mListView.onRefreshComplete();
						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub
							mListView.onRefreshComplete();
							ToastHelper.showToast(getActivity(), error.toString());
						}

					});

		} else {
			mListView.onRefreshComplete();
		}
	}
}
