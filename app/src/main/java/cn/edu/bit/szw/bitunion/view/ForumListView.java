package cn.edu.bit.szw.bitunion.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.List;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.entities.Forum;
import cn.edu.bit.szw.bitunion.widgets.ForumExpandableListAdapter;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by szw on 16-2-4.
 */
public class ForumListView extends RootView {
    ExpandableListView mExpandListView;
    ForumExpandableListAdapter mAdapter;
    Observable<Forum> itemClickObservable;
    @Override
    public View createContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_list, null);
        return view;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mTitleBar.setVisibility(View.GONE);
        mExpandListView = get(R.id.listview);
        mAdapter = new ForumExpandableListAdapter(getContext());
        mExpandListView.setGroupIndicator(null);
        mExpandListView.setAdapter(mAdapter);
        mExpandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            public void onGroupExpand(int groupPosition) {
                // TODO Auto-generated method stub
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        mExpandListView.collapseGroup(i);
                    }
                }

            }

        });

        itemClickObservable = Observable.create(new Observable.OnSubscribe<Forum>() {
            @Override
            public void call(final Subscriber<? super Forum> subscriber) {
                mExpandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Forum item = (Forum) mAdapter.getChild(groupPosition, childPosition);
                        subscriber.onNext(item);
                        return true;
                    }
                });
            }
        });
    }

    public void updateData(List<Forum> data) {
        mAdapter.updateData(data);
    }

    public Observable<Forum> itemClick() {
        return itemClickObservable;
    }
}
