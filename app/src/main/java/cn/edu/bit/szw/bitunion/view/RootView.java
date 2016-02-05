package cn.edu.bit.szw.bitunion.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.szw.mvp.view.ViewImpl;

import cn.edu.bit.szw.bitunion.R;

/**
 * Created by szw on 16-2-3.
 */
public class RootView extends ViewImpl {
    private static final LayoutParams sLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    protected View mTitleBar;
    protected TextView mTitle;
    protected ImageView mLeft;
    protected FrameLayout mContentContainer;
    protected View mContentView;

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.create(inflater, container, savedInstanceState);
        mContentView = createContentView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.root_view;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mTitleBar = get(R.id.base_fragment_titlebar);
        mTitle = get(R.id.base_titlebar_title);
        mLeft = get(R.id.base_titlebar_left);
        mContentContainer = get(R.id.base_fragment_content);
        setContentView(mContentView);
    }

    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }


    public void setContentView(View contentView) {
        if (contentView != null) {
            mContentContainer.addView(contentView, sLp);
        }
    }

    public View createContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return null;
    }



}
