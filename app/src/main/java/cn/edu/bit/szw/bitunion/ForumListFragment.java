package cn.edu.bit.szw.bitunion;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.szw.mvp.presenter.FragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bit.szw.bitunion.entities.Forum;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.BUApi;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;
import cn.edu.bit.szw.bitunion.view.ForumListView;
import cn.edu.bit.szw.bitunion.widgets.ForumExpandableListAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class ForumListFragment extends FragmentPresenter<ForumListView> {
	private static final String TAG = "ForumListFragment";

    @Override
    public void afterCreateView(Bundle savedInstanceState) {
        super.afterCreateView(savedInstanceState);
        loadData();
        mView.itemClick()
                .subscribe(new Action1<Forum>() {
                    @Override
                    public void call(Forum forum) {
                        ToastHelper.show(forum.name);
                    }
                });

    }

    private void loadData() {
        JSONObject body = new JSONObject();
        body.put("action", "forum");
        BUApi.loginAndRequest(this, GlobalUrls.getForumListUrl(), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        mView.updateData(ResponseParser.parseForumList(jsonObject));
                    }
                });
    }

}
