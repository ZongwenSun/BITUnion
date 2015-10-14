package cn.edu.bit.szw.bitunion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.szw.bitunion.base.BaseFragment;
import cn.edu.bit.szw.bitunion.entities.LoginInfo;
import cn.edu.bit.szw.bitunion.entities.NewPost;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.RequestQueueManager;
import cn.edu.bit.szw.bitunion.widgets.BaseUIController;
import cn.edu.bit.szw.bitunion.widgets.LoadingView;
import cn.edu.bit.szw.bitunion.widgets.NewPostsListAdapter;
public class NewPostsFragment extends BaseFragment {
	private static final String TAG = "NewPostsFragment";
	private List<NewPost> mDataList;
	private PullToRefreshListView mListView;
	private NewPostsListAdapter mAdapter;
    private LoadingView mLoadingView;
    private BaseUIController mUIController;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mDataList = new ArrayList<NewPost>();
	}

	@Override
	protected View createContentView(LayoutInflater inflater, Bundle savedInstanceState) {
        mTitleBar.setVisibility(View.GONE);
		View view = inflater.inflate(R.layout.fragment_new_posts, null);
        mLoadingView = (LoadingView)view.findViewById(R.id.loading);

		mListView = (PullToRefreshListView) view.findViewById(R.id.new_posts_listview);
		mAdapter = new NewPostsListAdapter(getActivity(), mDataList);
		mListView.setAdapter(mAdapter);
		mListView.setMode(Mode.PULL_FROM_START);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                reload(0);
            }
        });

        mLoadingView.bindWithPullToRefreshLayout(mListView);
        mUIController = new BaseUIController(this, mLoadingView) {
            @Override
            protected void onRefreshUI(JSONObject response) {
                List<NewPost> newPostList = ResponseParser.parseNewPostList(response);
                mDataList.clear();
                mDataList.addAll(newPostList);
                mAdapter.notifyDataSetChanged();
            }
        };
		final ListView mRefreshableView = mListView.getRefreshableView();
		mRefreshableView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                position = position - mRefreshableView.getHeaderViewsCount();
                Bundle bundle = new Bundle();
                bundle.putString("tid", mDataList.get(position).tid);
                bundle.putString("tid_sum", mDataList.get(position).tid_sum);
                startFragment(ThreadFragment.class, bundle);
            }
        });
		return view;
	}


	@Override
	public void reload(int type) {
        HashMap<String, String> paramsMap = new HashMap<>();
        LoginInfo loginInfo = MainApplication.instance.getLoginInfo();
        paramsMap.put("username", loginInfo.username);
        paramsMap.put("session", loginInfo.session);
        RequestQueueManager.getInstance().postJsonRequest(GlobalUrls.getNewPostsUrl(), paramsMap, mUIController);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		reload(0);
	}
}
