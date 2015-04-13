package cn.edu.bit.bitunion;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import cn.edu.bit.bitunion.entities.LoginInfo;
import cn.edu.bit.bitunion.entities.NewPost;
import cn.edu.bit.bitunion.entities.Post;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.global.RequestJsonFactory;
import cn.edu.bit.bitunion.global.ResponseParser;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.ToastHelper;
import cn.edu.bit.bitunion.widgets.PostDetailAdapter;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ThreadActivity extends BaseActivity {

	private PullToRefreshListView mListView;
	private List<Post> mDataList;
	private List<NewPost> newPosts;
	private PostDetailAdapter adapter;
	private String tid;
	private String tid_sum;
	private int hasLoadPostCount = 0;
	private static final int ONCE_LOAD_NUM = 20;

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread);
		Bundle bundle = this.getIntent().getExtras();
		tid = bundle.getString("tid");
		tid_sum = bundle.getString("tid_sum");
		// newPosts = (List<NewPost>) bundle.getSerializable("thread");
		mDataList = new ArrayList<Post>();
		mListView = (PullToRefreshListView) findViewById(R.id.post_details);

		adapter = new PostDetailAdapter(this, mDataList);
		mListView.setAdapter(adapter);

		Log.d("HSC 19:57", "" + Integer.parseInt(tid_sum));
		/*
		 * if (ONCE_LOAD_NUM < (Integer.parseInt(tid_sum) - hasLoadPostCount)) {
		 * Log.d("HSC 19:57", "" + (Integer.parseInt(tid_sum) -
		 * hasLoadPostCount)); getNewPostsList(); } else if
		 * (Integer.parseInt(tid_sum) != hasLoadPostCount && ONCE_LOAD_NUM >=
		 * (Integer.parseInt(tid_sum) - hasLoadPostCount)) {
		 * 
		 * getNewPostsList(hasLoadPostCount, Integer.parseInt(tid_sum)); }
		 */
		getNewPostsList();
		mListView.setMode(Mode.PULL_FROM_START);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				// fresh();
			}
		});

	}

	private void getNewPostsList() {
		LoginInfo loginInfo = getAppContext().getLoginInfo();
		showLoadingDialog();

		RequestQueueManager.getInstance(this).postJsonRequest(
				GlobalUrls.getPostListUrl(),
				RequestJsonFactory.PostListJson(loginInfo.getUsername(),
						loginInfo.getSession(), tid, "0", "19"),
				new Listener<JSONObject>() {

					public void onResponse(JSONObject response) {
						// Auto-generated method stub
						hideLoadingDialog();
						if (ResponseParser.isSuccess(response)) {
							hasLoadPostCount = ONCE_LOAD_NUM - 1;
							List<Post> PostList = ResponseParser
									.parsePostResponse(response);
							mDataList.addAll(PostList);
							adapter.notifyDataSetChanged();
						}
					}

				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						hideLoadingDialog();
						ToastHelper.showToast(ThreadActivity.this,
								arg0.toString());
					}
				});

	}

	private void getNewPostsList(int hasload, int sum) {
		LoginInfo loginInfo = getAppContext().getLoginInfo();
		showLoadingDialog();

		RequestQueueManager.getInstance(this).postJsonRequest(
				GlobalUrls.getPostListUrl(),
				RequestJsonFactory.PostListJson(loginInfo.getUsername(),
						loginInfo.getSession(), tid, String.valueOf(hasload),
						String.valueOf(sum)), new Listener<JSONObject>() {

					public void onResponse(JSONObject response) {
						// Auto-generated method stub
						hideLoadingDialog();
						if (ResponseParser.isSuccess(response)) {
							hasLoadPostCount = Integer.parseInt(tid_sum);
							List<Post> PostList = ResponseParser
									.parsePostResponse(response);
							mDataList.addAll(PostList);
							adapter.notifyDataSetChanged();
						}
					}

				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						hideLoadingDialog();
						ToastHelper.showToast(ThreadActivity.this,
								arg0.toString());
					}
				});

	}

	private void fresh() {
		if (checkConnection()) {
			LoginInfo loginInfo = getAppContext().getLoginInfo();
			RequestQueueManager.getInstance(this).postJsonRequest(
					GlobalUrls.getNewPostsUrl(),
					RequestJsonFactory.newPostsJson(loginInfo.getUsername(),
							loginInfo.getSession()),
					new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							// TODO Auto-generated method stub
							if (ResponseParser.isSuccess(response)) {
								List<Post> PostList = ResponseParser
										.parsePostResponse(response);
								mDataList.addAll(PostList);
								adapter.notifyDataSetChanged();
							}
							mListView.onRefreshComplete();
						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub
							mListView.onRefreshComplete();
							ToastHelper.showToast(ThreadActivity.this,
									error.toString());
						}

					});

		} else {
			mListView.onRefreshComplete();
		}
	}
}
