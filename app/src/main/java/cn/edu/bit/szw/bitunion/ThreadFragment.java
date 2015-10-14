package cn.edu.bit.szw.bitunion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import cn.edu.bit.szw.bitunion.entities.Post;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.RequestQueueManager;
import cn.edu.bit.szw.bitunion.widgets.BaseUIController;
import cn.edu.bit.szw.bitunion.widgets.LoadingView;
import cn.edu.bit.szw.bitunion.widgets.PageController;
import cn.edu.bit.szw.bitunion.widgets.PostDetailAdapter;

/**
 * 帖子
 */
public class ThreadFragment extends BaseFragment {
    private LoadingView mLoading;
	private ListView mListView;
    private PageController mPagerController;
    private BaseUIController mUIController;
    private List<Post> mDataList;
    private PostDetailAdapter mAdapter;
    private String tid;
    private int tid_sum;
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		tid = bundle.getString("tid");
        tid_sum = Integer.valueOf(bundle.getString("tid_sum")) + 1;
        mDataList = new ArrayList<Post>();
	}

    @Override
    protected View createContentView(LayoutInflater inflater, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thread_fragment, null);
        mLoading = (LoadingView) view.findViewById(R.id.loading);
        mUIController = new BaseUIController(this, mLoading) {
            @Override
            protected void onRefreshUI(JSONObject response) {
                parseResult(response);
                mPagerController.mNeedNextPage = true;
                mPagerController.nextPage();
            }
        };
        mListView = (ListView) view.findViewById(R.id.post_details);
        mAdapter = new PostDetailAdapter(getActivity(), mDataList);
        mListView.setAdapter(mAdapter);
        mPagerController = new PageController(new Runnable() {
            @Override
            public void run() {
                reload(0);
            }
        }, getActivity());
        mPagerController.setMaxSize(tid_sum);
        mListView.setOnScrollListener(mPagerController);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reload(0);
    }

    @Override
    public void reload(int type) {
        int from = mPagerController.getNextFrom();
        int to = mPagerController.getNextTo();
        LoginInfo loginInfo = MainApplication.instance.getLoginInfo();
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("action", "post");
        paramsMap.put("username", loginInfo.username);
        paramsMap.put("session", loginInfo.session);
        paramsMap.put("tid", tid);
        paramsMap.put("from", from + "");
        paramsMap.put("to", to + "");
        RequestQueueManager.getInstance().postJsonRequest(GlobalUrls.getPostListUrl(),
                paramsMap,
                mUIController, false);
    }

    private void parseResult(JSONObject response){
        List<Post> PostList = ResponseParser.parsePostResponse(response);
        mDataList.addAll(PostList);
        mPagerController.nextPage();
        mAdapter.notifyDataSetChanged();
    }



}