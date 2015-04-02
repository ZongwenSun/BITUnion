package cn.edu.bit.bitunion;

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
import cn.edu.bit.bitunion.entities.RequestJsonFactory;
import cn.edu.bit.bitunion.entities.ResponseParser;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.ToastHelper;
import cn.edu.bit.bitunion.widgets.NewPostsListAdapter;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class NewPostsFragment extends Fragment {
	private static final String TAG = "NewPostsFragment";
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_new_posts, null);
		listView = (ListView) view.findViewById(R.id.new_posts_listview);

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getNewPostsList();

	}

	private void getNewPostsList() {
		LoginInfo loginInfo = ((BaseActivity) getActivity()).getAppContext().getLoginInfo();
		((BaseActivity) getActivity()).showLoadingDialog();
		RequestQueueManager.getInstance(getActivity()).postJsonRequest(GlobalUrls.getNewPostsUrl(),
				RequestJsonFactory.newPostsJson(loginInfo.getUsername(), loginInfo.getSession()), new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						if (ResponseParser.isSuccess(response)) {
							List<NewPost> newPostList = ResponseParser.parseNewPostResponse(response);
							listView.setAdapter(new NewPostsListAdapter(getActivity(), newPostList));
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
}
