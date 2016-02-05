package cn.edu.bit.szw.bitunion;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.szw.mvp.presenter.ActivityPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.szw.bitunion.entities.LoginInfo;
import cn.edu.bit.szw.bitunion.entities.Post;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.BUApi;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;
import cn.edu.bit.szw.bitunion.view.ThreadView;
import cn.edu.bit.szw.bitunion.widgets.LoadingView;
import cn.edu.bit.szw.bitunion.widgets.PageController;
import cn.edu.bit.szw.bitunion.widgets.PostDetailAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 帖子
 */
public class ThreadActivity extends ActivityPresenter<ThreadView> {
    private PageController mPagerController;
    private String tid;
    private int tid_sum;
    private List<Post> mData;

    @Override
    public void preCreateView(Bundle savedInstanceState) {
        super.preCreateView(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        tid = bundle.getString("tid");
        tid_sum = Integer.valueOf(bundle.getString("tid_sum")) + 1;
        mData = new ArrayList<Post>();
    }

    @Override
    public void afterCreateView(Bundle savedInstanceState) {
        super.afterCreateView(savedInstanceState);
        mPagerController = new PageController(new Runnable() {
            @Override
            public void run() {
                loadMore();
            }
        }, this);
        mPagerController.setMaxSize(tid_sum);
        mView.setPageController(mPagerController);
        loadMore();
    }


    public void loadMore() {
        int from = mPagerController.getNextFrom();
        int to = mPagerController.getNextTo();
        LoginInfo loginInfo = MainApplication.instance.getLoginInfo();
        JSONObject body = new JSONObject();
        body.put("action", "post");
        body.put("username", loginInfo.username);
        body.put("session", loginInfo.session);
        body.put("tid", tid);
        body.put("from", from + "");
        body.put("to", to + "");
        BUApi.loginAndRequest(this, GlobalUrls.getPostListUrl(), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastHelper.show(e.getMessage());

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        mData.addAll(ResponseParser.parsePostResponse(jsonObject));
                        mView.updateData(mData);
                        mPagerController.mNeedNextPage = true;
                        mPagerController.nextPage();
                    }
                });
    }
}