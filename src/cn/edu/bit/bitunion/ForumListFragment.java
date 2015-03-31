package cn.edu.bit.bitunion;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.edu.bit.bitunion.entities.LoginInfo;
import cn.edu.bit.bitunion.entities.RequestJsonFactory;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.LogUtils;
import cn.edu.bit.bitunion.tools.ToastHelper;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class ForumListFragment extends Fragment {
	private static final String TAG = "ForumListFragment";

	public ForumListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_forum_list, null);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
						String result;
						try {
							result = response.getString("result");
							if (result.equalsIgnoreCase("success")) {
								((BaseActivity) getActivity()).hideLoadingDialog();
								LogUtils.log(TAG, response.toString());
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
}
