package cn.edu.bit.szw.bitunion.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.entities.NewPost;
import cn.edu.bit.szw.bitunion.entities.Post;
import cn.edu.bit.szw.bitunion.widgets.NewPostsListAdapter;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by szw on 16-2-4.
 */
public class NewPostsView extends RootView {
    PullToRefreshListView mListView;
    NewPostsListAdapter mAdapter;
    Observable mRefreshObservable;
    Observable<NewPost> mClickPostObservable;
    @Override
    public View createContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_posts, null);
        return view;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mTitleBar.setVisibility(View.GONE);
        mListView = get(R.id.new_posts_listview);
        mAdapter = new NewPostsListAdapter(getContext());
        mListView.setAdapter(mAdapter);
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mRefreshObservable = Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                if (!subscriber.isUnsubscribed()){
                    mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                        @Override
                        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                            subscriber.onNext(null);
                        }
                    });
                }
            }
        });
        mClickPostObservable = Observable.create(new Observable.OnSubscribe<NewPost>() {
            @Override
            public void call(final Subscriber<? super NewPost> subscriber) {
                mListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        position = position - mListView.getRefreshableView().getHeaderViewsCount();
                        subscriber.onNext((NewPost) mAdapter.getItem(position));
                    }
                });
            }
        });

    }

    public void updateData(List<NewPost> data) {
        mAdapter.updateData(data);
    }

    public void finishFresh() {
        mListView.onRefreshComplete();
    }

    public Observable<Void> freshObservable() {
        return mRefreshObservable;
    }

    public Observable<NewPost> clickPostObservable() {
        return mClickPostObservable;
    }

}
