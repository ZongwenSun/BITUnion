package com.szw.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by szw on 16-2-3.
 */
public abstract class ViewImpl implements IView {
    protected final SparseArray<View> mViews = new SparseArray<View>();
    protected View mRootView;

    public void create(LayoutInflater inflater){
        create(inflater, null, null);
    }

    public void create(LayoutInflater inflater, ViewGroup container) {
        create(inflater, container, null);
    }


    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
    }

    public void initWidget() {

    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    public abstract int getLayoutId();

    public <T extends View> T get(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) mRootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    protected Context getContext() {
        if (mRootView == null) {
            throw new RuntimeException("rootView not created, please call this method after create");
        }
        return mRootView.getContext();
    }

    protected String getString(int id) {
        return getContext().getString(id);
    }

    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
