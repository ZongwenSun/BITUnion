package com.szw.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szw.mvp.helper.GenericHelper;
import com.szw.mvp.view.IView;

/**
 * Created by szw on 16-2-3.
 */
public abstract class FragmentPresenter<T extends IView> extends Fragment implements IPresenter<T> {
    protected T mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preCreateView(savedInstanceState);
        try {
            mView = getViewClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException("create IView error", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IView error", e);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView.create(inflater, container, savedInstanceState);
        mView.initWidget();
        afterCreateView(savedInstanceState);
        return mView.getRootView();
    }
    @Override
    public Class<T> getViewClass() {
        return GenericHelper.getViewClass(getClass());
    }

    @Override
    public void afterCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void preCreateView(Bundle savedInstanceState) {

    }
}
