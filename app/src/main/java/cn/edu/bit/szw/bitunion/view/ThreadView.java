package cn.edu.bit.szw.bitunion.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.entities.Post;
import cn.edu.bit.szw.bitunion.widgets.PageController;
import cn.edu.bit.szw.bitunion.widgets.PostDetailAdapter;
import rx.Observable;

/**
 * Created by szw on 16-2-5.
 */
public class ThreadView extends RootView {
    ListView mListView;
    PostDetailAdapter mAdapter;

    @Override
    public View createContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thread_fragment, null);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mListView = get(R.id.post_details);
        mAdapter = new PostDetailAdapter(getContext());
        mListView.setAdapter(mAdapter);
    }

    public void setPageController(PageController pageController) {
        mListView.setOnScrollListener(pageController);
    }

    public void updateData(List<Post> data) {
        mAdapter.updateData(data);
    }
}
