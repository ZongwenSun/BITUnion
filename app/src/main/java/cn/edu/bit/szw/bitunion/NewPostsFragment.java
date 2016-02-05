package cn.edu.bit.szw.bitunion;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.szw.mvp.presenter.FragmentPresenter;

import java.util.List;

import cn.edu.bit.szw.bitunion.entities.LoginInfo;
import cn.edu.bit.szw.bitunion.entities.NewPost;
import cn.edu.bit.szw.bitunion.entities.Post;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.BUApi;
import cn.edu.bit.szw.bitunion.network.BuError;
import cn.edu.bit.szw.bitunion.network.HttpUtil;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;
import cn.edu.bit.szw.bitunion.view.NewPostsView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NewPostsFragment extends FragmentPresenter<NewPostsView> {
	private static final String TAG = "NewPostsFragment";

    @Override
    public void afterCreateView(Bundle savedInstanceState) {
        super.afterCreateView(savedInstanceState);
        mView.freshObservable()
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        reload();
                    }
                });
        mView.clickPostObservable()
                .subscribe(new Action1<NewPost>() {
                    @Override
                    public void call(NewPost post) {
                        Intent intent = new Intent(getActivity(), ThreadActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("tid", post.tid);
                        bundle.putString("tid_sum", post.tid_sum);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
        reload();

    }


    private void reload() {
        LoginInfo loginInfo = MainApplication.instance.getLoginInfo();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", loginInfo.username);
        jsonObject.put("session", loginInfo.session);
       BUApi.loginAndRequest(this, GlobalUrls.getNewPostsUrl(), jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {
                        mView.finishFresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof BuError) {
                            BuError buError = (BuError) e;
                            ToastHelper.show(buError.getMsg());
                        }
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        List<NewPost> posts = ResponseParser.parseNewPostList(jsonObject);
                        mView.updateData(posts);
                    }
                });
    }

}
